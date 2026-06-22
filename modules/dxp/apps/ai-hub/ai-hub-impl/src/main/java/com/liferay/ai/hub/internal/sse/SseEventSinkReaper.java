/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.sse;

import com.liferay.ai.hub.internal.mcp.tool.provider.MCPToolProviderUtil;
import com.liferay.ai.hub.internal.memory.ChatMemoryProviderUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Periodically reaps Server-Sent Events connections that no longer have a live
 * client so that their Tomcat connection slot and per-connection heap state are
 * released. The registry in {@link SseUtil} is process local, so this runs on
 * every cluster node.
 *
 * @author Gabriel Albuquerque
 */
@Component(service = {})
public class SseEventSinkReaper {

	@Activate
	protected void activate() {
		SseUtil.addEvictionListener(_evictionListener);

		_scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(
			new NamedThreadFactory(
				SseEventSinkReaper.class.getName(), Thread.MIN_PRIORITY,
				SseEventSinkReaper.class.getClassLoader()));

		_scheduledExecutorService.scheduleWithFixedDelay(
			this::_reap, _HEARTBEAT_INTERVAL, _HEARTBEAT_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	@Deactivate
	protected void deactivate() {
		SseUtil.removeEvictionListener(_evictionListener);

		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdownNow();
		}
	}

	private void _reap() {
		try {
			Set<String> evictedSseEventSinkKeys = SseUtil.reap(_MAX_LIFETIME);

			if (!evictedSseEventSinkKeys.isEmpty() && _log.isInfoEnabled()) {
				_log.info(
					"Evicted " + evictedSseEventSinkKeys.size() +
						" stale SSE event sinks");
			}
		}
		catch (Throwable throwable) {
			_log.error("Unable to reap SSE event sinks", throwable);
		}
	}

	private static final long _HEARTBEAT_INTERVAL = TimeUnit.SECONDS.toMillis(
		20);

	private static final long _MAX_LIFETIME = TimeUnit.MINUTES.toMillis(15);

	private static final Log _log = LogFactoryUtil.getLog(
		SseEventSinkReaper.class);

	private final Consumer<String> _evictionListener = sseEventSinkKey -> {
		ChatMemoryProviderUtil.delete(sseEventSinkKey);

		MCPToolProviderUtil.close(sseEventSinkKey);
	};

	private ScheduledExecutorService _scheduledExecutorService;

}