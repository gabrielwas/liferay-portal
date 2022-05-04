/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.notification.admin.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.admin.exception.NoSuchNotificationAdminTemplateException;
import com.liferay.notification.admin.model.NotificationAdminTemplate;
import com.liferay.notification.admin.service.NotificationAdminTemplateLocalServiceUtil;
import com.liferay.notification.admin.service.persistence.NotificationAdminTemplatePersistence;
import com.liferay.notification.admin.service.persistence.NotificationAdminTemplateUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class NotificationAdminTemplatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.notification.admin.service"));

	@Before
	public void setUp() {
		_persistence = NotificationAdminTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationAdminTemplate> iterator =
			_notificationAdminTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationAdminTemplate notificationAdminTemplate =
			_persistence.create(pk);

		Assert.assertNotNull(notificationAdminTemplate);

		Assert.assertEquals(notificationAdminTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		_persistence.remove(newNotificationAdminTemplate);

		NotificationAdminTemplate existingNotificationAdminTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationAdminTemplate.getPrimaryKey());

		Assert.assertNull(existingNotificationAdminTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationAdminTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationAdminTemplate newNotificationAdminTemplate =
			_persistence.create(pk);

		newNotificationAdminTemplate.setMvccVersion(RandomTestUtil.nextLong());

		newNotificationAdminTemplate.setUuid(RandomTestUtil.randomString());

		newNotificationAdminTemplate.setCompanyId(RandomTestUtil.nextLong());

		newNotificationAdminTemplate.setUserId(RandomTestUtil.nextLong());

		newNotificationAdminTemplate.setUserName(RandomTestUtil.randomString());

		newNotificationAdminTemplate.setCreateDate(RandomTestUtil.nextDate());

		newNotificationAdminTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newNotificationAdminTemplate.setName(RandomTestUtil.randomString());

		_notificationAdminTemplates.add(
			_persistence.update(newNotificationAdminTemplate));

		NotificationAdminTemplate existingNotificationAdminTemplate =
			_persistence.findByPrimaryKey(
				newNotificationAdminTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationAdminTemplate.getMvccVersion(),
			newNotificationAdminTemplate.getMvccVersion());
		Assert.assertEquals(
			existingNotificationAdminTemplate.getUuid(),
			newNotificationAdminTemplate.getUuid());
		Assert.assertEquals(
			existingNotificationAdminTemplate.getNotificationAdminTemplateId(),
			newNotificationAdminTemplate.getNotificationAdminTemplateId());
		Assert.assertEquals(
			existingNotificationAdminTemplate.getCompanyId(),
			newNotificationAdminTemplate.getCompanyId());
		Assert.assertEquals(
			existingNotificationAdminTemplate.getUserId(),
			newNotificationAdminTemplate.getUserId());
		Assert.assertEquals(
			existingNotificationAdminTemplate.getUserName(),
			newNotificationAdminTemplate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationAdminTemplate.getCreateDate()),
			Time.getShortTimestamp(
				newNotificationAdminTemplate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationAdminTemplate.getModifiedDate()),
			Time.getShortTimestamp(
				newNotificationAdminTemplate.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationAdminTemplate.getName(),
			newNotificationAdminTemplate.getName());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		NotificationAdminTemplate existingNotificationAdminTemplate =
			_persistence.findByPrimaryKey(
				newNotificationAdminTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationAdminTemplate, newNotificationAdminTemplate);
	}

	@Test(expected = NoSuchNotificationAdminTemplateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationAdminTemplate>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"NotificationAdminTemplate", "mvccVersion", true, "uuid", true,
			"notificationAdminTemplateId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		NotificationAdminTemplate existingNotificationAdminTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationAdminTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationAdminTemplate, newNotificationAdminTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationAdminTemplate missingNotificationAdminTemplate =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationAdminTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationAdminTemplate newNotificationAdminTemplate1 =
			addNotificationAdminTemplate();
		NotificationAdminTemplate newNotificationAdminTemplate2 =
			addNotificationAdminTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationAdminTemplate1.getPrimaryKey());
		primaryKeys.add(newNotificationAdminTemplate2.getPrimaryKey());

		Map<Serializable, NotificationAdminTemplate>
			notificationAdminTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, notificationAdminTemplates.size());
		Assert.assertEquals(
			newNotificationAdminTemplate1,
			notificationAdminTemplates.get(
				newNotificationAdminTemplate1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationAdminTemplate2,
			notificationAdminTemplates.get(
				newNotificationAdminTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationAdminTemplate>
			notificationAdminTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationAdminTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationAdminTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationAdminTemplate>
			notificationAdminTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationAdminTemplates.size());
		Assert.assertEquals(
			newNotificationAdminTemplate,
			notificationAdminTemplates.get(
				newNotificationAdminTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationAdminTemplate>
			notificationAdminTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(notificationAdminTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationAdminTemplate.getPrimaryKey());

		Map<Serializable, NotificationAdminTemplate>
			notificationAdminTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, notificationAdminTemplates.size());
		Assert.assertEquals(
			newNotificationAdminTemplate,
			notificationAdminTemplates.get(
				newNotificationAdminTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationAdminTemplateLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationAdminTemplate>() {

				@Override
				public void performAction(
					NotificationAdminTemplate notificationAdminTemplate) {

					Assert.assertNotNull(notificationAdminTemplate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationAdminTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationAdminTemplateId",
				newNotificationAdminTemplate.getNotificationAdminTemplateId()));

		List<NotificationAdminTemplate> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationAdminTemplate existingNotificationAdminTemplate =
			result.get(0);

		Assert.assertEquals(
			existingNotificationAdminTemplate, newNotificationAdminTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationAdminTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationAdminTemplateId", RandomTestUtil.nextLong()));

		List<NotificationAdminTemplate> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationAdminTemplate newNotificationAdminTemplate =
			addNotificationAdminTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationAdminTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationAdminTemplateId"));

		Object newNotificationAdminTemplateId =
			newNotificationAdminTemplate.getNotificationAdminTemplateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationAdminTemplateId",
				new Object[] {newNotificationAdminTemplateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationAdminTemplateId = result.get(0);

		Assert.assertEquals(
			existingNotificationAdminTemplateId,
			newNotificationAdminTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationAdminTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationAdminTemplateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationAdminTemplateId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationAdminTemplate addNotificationAdminTemplate()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationAdminTemplate notificationAdminTemplate =
			_persistence.create(pk);

		notificationAdminTemplate.setMvccVersion(RandomTestUtil.nextLong());

		notificationAdminTemplate.setUuid(RandomTestUtil.randomString());

		notificationAdminTemplate.setCompanyId(RandomTestUtil.nextLong());

		notificationAdminTemplate.setUserId(RandomTestUtil.nextLong());

		notificationAdminTemplate.setUserName(RandomTestUtil.randomString());

		notificationAdminTemplate.setCreateDate(RandomTestUtil.nextDate());

		notificationAdminTemplate.setModifiedDate(RandomTestUtil.nextDate());

		notificationAdminTemplate.setName(RandomTestUtil.randomString());

		_notificationAdminTemplates.add(
			_persistence.update(notificationAdminTemplate));

		return notificationAdminTemplate;
	}

	private List<NotificationAdminTemplate> _notificationAdminTemplates =
		new ArrayList<NotificationAdminTemplate>();
	private NotificationAdminTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}