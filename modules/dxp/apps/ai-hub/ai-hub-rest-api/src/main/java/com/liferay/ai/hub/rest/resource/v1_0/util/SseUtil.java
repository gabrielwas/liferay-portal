/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * @author Feliphe Marinho
 */
public class SseUtil {

	public static void addEvictionListener(Consumer<String> evictionListener) {
		_evictionListeners.add(evictionListener);
	}

	public static void closeAll() {
		if (_sseEventSinkHolders.isEmpty() || !PortalRunMode.isTestMode()) {
			return;
		}

		for (String sseEventSinkKey :
				new HashSet<>(_sseEventSinkHolders.keySet())) {

			evict(sseEventSinkKey);
		}
	}

	public static int getMaxSseEventSinks() {
		return _maxSseEventSinks;
	}

	public static Set<String> getSSEEventSinksKeys() {
		if (!PortalRunMode.isTestMode()) {
			return null;
		}

		return _sseEventSinkHolders.keySet();
	}

	public static void initialize(Sse sse, SseEventSink sseEventSink) {
		if (_sseEventSinkHolders.size() >= _maxSseEventSinks) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Rejecting SSE subscription because the maximum ",
						"number of concurrent connections (", _maxSseEventSinks,
						") has been reached"));
			}

			sseEventSink.close();

			return;
		}

		String sseEventSinkKey = PortalUUIDUtil.generate();

		_sseEventSinkHolders.put(
			sseEventSinkKey, new SseEventSinkHolder(sse, sseEventSink));

		sseEventSink.send(
			sse.newEventBuilder(
			).data(
				String.class, sseEventSinkKey
			).name(
				"Subscribe"
			).build());
	}

	public static Set<String> reap(long maxLifetime) {
		Set<String> evictedSseEventSinkKeys = new HashSet<>();

		long currentTime = System.currentTimeMillis();

		for (Map.Entry<String, SseEventSinkHolder> entry :
				_sseEventSinkHolders.entrySet()) {

			String sseEventSinkKey = entry.getKey();

			SseEventSinkHolder sseEventSinkHolder = entry.getValue();

			SseEventSink sseEventSink = sseEventSinkHolder.getSseEventSink();

			boolean evict = false;

			if (sseEventSink.isClosed()) {
				evict = true;
			}
			else if ((currentTime - sseEventSinkHolder.getCreateTime()) >=
						maxLifetime) {

				evict = true;
			}
			else {
				try {
					Sse sse = sseEventSinkHolder.getSse();

					sseEventSink.send(
						sse.newEventBuilder(
						).comment(
							"heartbeat"
						).build()
					).exceptionally(
						throwable -> {
							evict(sseEventSinkKey);

							return null;
						}
					);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}

					evict = true;
				}
			}

			if (evict) {
				evict(sseEventSinkKey);

				evictedSseEventSinkKeys.add(sseEventSinkKey);
			}
		}

		return evictedSseEventSinkKeys;
	}

	public static void removeEvictionListener(
		Consumer<String> evictionListener) {

		_evictionListeners.remove(evictionListener);
	}

	public static void send(
		String data, String name, String nodeName, String sseEventSinkKey) {

		send(null, data, name, nodeName, sseEventSinkKey);
	}

	public static void send(
		String[] agentDefinitionExternalReferenceCodes, String data,
		String name, String nodeName, String sseEventSinkKey) {

		if (Validator.isBlank(sseEventSinkKey)) {
			return;
		}

		SseEventSinkHolder sseEventSinkHolder = _sseEventSinkHolders.get(
			sseEventSinkKey);

		if (sseEventSinkHolder == null) {
			return;
		}

		SseEventSink sseEventSink = sseEventSinkHolder.getSseEventSink();

		if (sseEventSink.isClosed()) {
			evict(sseEventSinkKey);

			return;
		}

		try {
			Sse sse = sseEventSinkHolder.getSse();

			sseEventSink.send(
				sse.newEventBuilder(
				).data(
					String.class,
					JSONUtil.put(
						"agentDefinitionExternalReferenceCodes",
						() -> {
							if (agentDefinitionExternalReferenceCodes == null) {
								return null;
							}

							return JSONUtil.putAll(
								agentDefinitionExternalReferenceCodes);
						}
					).put(
						"data", data
					).put(
						"nodeName", nodeName
					).toString()
				).name(
					Validator.isBlank(name) ? nodeName : name
				).build()
			).exceptionally(
				throwable -> {
					evict(sseEventSinkKey);

					return null;
				}
			);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Evicting SSE event sink " + sseEventSinkKey +
						" because of a send failure",
					exception);
			}

			evict(sseEventSinkKey);
		}
	}

	public static void setMaxSseEventSinks(int maxSseEventSinks) {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		_maxSseEventSinks = maxSseEventSinks;
	}

	protected static void evict(String sseEventSinkKey) {
		SseEventSinkHolder sseEventSinkHolder = _sseEventSinkHolders.remove(
			sseEventSinkKey);

		if (sseEventSinkHolder == null) {
			return;
		}

		try {
			SseEventSink sseEventSink = sseEventSinkHolder.getSseEventSink();

			if (!sseEventSink.isClosed()) {
				sseEventSink.close();
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		for (Consumer<String> evictionListener : _evictionListeners) {
			try {
				evictionListener.accept(sseEventSinkKey);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final int _DEFAULT_MAX_SSE_EVENT_SINKS = 4000;

	private static final Log _log = LogFactoryUtil.getLog(SseUtil.class);

	private static final Set<Consumer<String>> _evictionListeners =
		new CopyOnWriteArraySet<>();
	private static volatile int _maxSseEventSinks =
		_DEFAULT_MAX_SSE_EVENT_SINKS;
	private static final Map<String, SseEventSinkHolder> _sseEventSinkHolders =
		new ConcurrentHashMap<>();

	private static class SseEventSinkHolder {

		public long getCreateTime() {
			return _createTime;
		}

		public Sse getSse() {
			return _sse;
		}

		public SseEventSink getSseEventSink() {
			return _sseEventSink;
		}

		private SseEventSinkHolder(Sse sse, SseEventSink sseEventSink) {
			_sse = sse;
			_sseEventSink = sseEventSink;

			_createTime = System.currentTimeMillis();
		}

		private final long _createTime;
		private final Sse _sse;
		private final SseEventSink _sseEventSink;

	}

}