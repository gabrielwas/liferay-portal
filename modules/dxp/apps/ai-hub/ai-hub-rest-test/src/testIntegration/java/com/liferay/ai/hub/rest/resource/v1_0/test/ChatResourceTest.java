/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.FeatureFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class ChatResourceTest extends BaseChatResourceTestCase {

	@After
	public void tearDown() {
		SseUtil.closeAll();
	}

	@Override
	@Test
	public void testGetChatSubscribe() throws Exception {
		Assert.assertNotNull(
			SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "chats/subscribe"));
	}

	@Test
	public void testGetChatSubscribeReapEvictsStaleEventSink()
		throws Exception {

		Set<String> evictedSseEventSinkKeys = ConcurrentHashMap.newKeySet();

		Consumer<String> evictionListener = evictedSseEventSinkKeys::add;

		SseUtil.addEvictionListener(evictionListener);

		try {
			String sseEventSinkKey = SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "chats/subscribe");

			Assert.assertTrue(
				SseUtil.getSSEEventSinksKeys(
				).contains(
					sseEventSinkKey
				));

			// A zero maximum lifetime evicts every connection regardless of
			// liveness, which deterministically exercises the eviction path.

			Set<String> reapedSseEventSinkKeys = SseUtil.reap(0);

			Assert.assertTrue(reapedSseEventSinkKeys.contains(sseEventSinkKey));

			Assert.assertFalse(
				SseUtil.getSSEEventSinksKeys(
				).contains(
					sseEventSinkKey
				));
			Assert.assertTrue(
				evictedSseEventSinkKeys.contains(sseEventSinkKey));
		}
		finally {
			SseUtil.removeEvictionListener(evictionListener);
		}
	}

	@Test
	public void testGetChatSubscribeRejectsWhenAtCapacity() throws Exception {
		int maxSseEventSinks = SseUtil.getMaxSseEventSinks();

		try {
			SseUtil.setMaxSseEventSinks(1);

			SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "chats/subscribe");

			Assert.assertEquals(
				1,
				SseUtil.getSSEEventSinksKeys(
				).size());

			SseEventSourceTestUtil.openExpectingRejection("chats/subscribe");

			Assert.assertEquals(
				1,
				SseUtil.getSSEEventSinksKeys(
				).size());
		}
		finally {
			SseUtil.setMaxSseEventSinks(maxSseEventSinks);
		}
	}

}