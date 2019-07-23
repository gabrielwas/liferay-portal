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

package com.liferay.app.builder.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.app.builder.rest.client.dto.v1_0.App;
import com.liferay.app.builder.rest.client.pagination.Page;
import com.liferay.app.builder.rest.client.serdes.v1_0.AppSerDes;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseAppResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL("http://localhost:8080/o/app-builder/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		App app1 = randomApp();

		String json = objectMapper.writeValueAsString(app1);

		App app2 = AppSerDes.toDTO(json);

		Assert.assertTrue(equals(app1, app2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		App app = randomApp();

		String json1 = objectMapper.writeValueAsString(app);
		String json2 = AppSerDes.toJSON(app);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetSiteAppsPage() throws Exception {
		Long siteId = testGetSiteAppsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteAppsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			App irrelevantApp = testGetSiteAppsPage_addApp(
				irrelevantSiteId, randomIrrelevantApp());

			Page<App> page = invokeGetSiteAppsPage(
				irrelevantSiteId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantApp), (List<App>)page.getItems());
			assertValid(page);
		}

		App app1 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app2 = testGetSiteAppsPage_addApp(siteId, randomApp());

		Page<App> page = invokeGetSiteAppsPage(
			siteId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2), (List<App>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteAppsPageWithPagination() throws Exception {
		Long siteId = testGetSiteAppsPage_getSiteId();

		App app1 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app2 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app3 = testGetSiteAppsPage_addApp(siteId, randomApp());

		Page<App> page1 = invokeGetSiteAppsPage(
			siteId, null, Pagination.of(1, 2), null);

		List<App> apps1 = (List<App>)page1.getItems();

		Assert.assertEquals(apps1.toString(), 2, apps1.size());

		Page<App> page2 = invokeGetSiteAppsPage(
			siteId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<App> apps2 = (List<App>)page2.getItems();

		Assert.assertEquals(apps2.toString(), 1, apps2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3),
			new ArrayList<App>() {
				{
					addAll(apps1);
					addAll(apps2);
				}
			});
	}

	@Test
	public void testGetSiteAppsPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteAppsPage_getSiteId();

		App app1 = randomApp();
		App app2 = randomApp();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				app1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		app1 = testGetSiteAppsPage_addApp(siteId, app1);

		app2 = testGetSiteAppsPage_addApp(siteId, app2);

		for (EntityField entityField : entityFields) {
			Page<App> ascPage = invokeGetSiteAppsPage(
				siteId, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(app1, app2), (List<App>)ascPage.getItems());

			Page<App> descPage = invokeGetSiteAppsPage(
				siteId, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(app2, app1), (List<App>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteAppsPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteAppsPage_getSiteId();

		App app1 = randomApp();
		App app2 = randomApp();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(app1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(app2, entityField.getName(), "Bbb");
		}

		app1 = testGetSiteAppsPage_addApp(siteId, app1);

		app2 = testGetSiteAppsPage_addApp(siteId, app2);

		for (EntityField entityField : entityFields) {
			Page<App> ascPage = invokeGetSiteAppsPage(
				siteId, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(app1, app2), (List<App>)ascPage.getItems());

			Page<App> descPage = invokeGetSiteAppsPage(
				siteId, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(app2, app1), (List<App>)descPage.getItems());
		}
	}

	protected App testGetSiteAppsPage_addApp(Long siteId, App app)
		throws Exception {

		return invokePostSiteApp(siteId, app);
	}

	protected Long testGetSiteAppsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteAppsPage_getIrrelevantSiteId() throws Exception {
		return irrelevantGroup.getGroupId();
	}

	protected Page<App> invokeGetSiteAppsPage(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/apps", siteId);

		if (keywords != null) {
			location = HttpUtil.addParameter(location, "keywords", keywords);
		}

		if (pagination != null) {
			location = HttpUtil.addParameter(
				location, "page", pagination.getPage());
			location = HttpUtil.addParameter(
				location, "pageSize", pagination.getPageSize());
		}

		if (sortString != null) {
			location = HttpUtil.addParameter(location, "sort", sortString);
		}

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, AppSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteAppsPageResponse(
			Long siteId, String keywords, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/apps", siteId);

		if (keywords != null) {
			location = HttpUtil.addParameter(location, "keywords", keywords);
		}

		if (pagination != null) {
			location = HttpUtil.addParameter(
				location, "page", pagination.getPage());
			location = HttpUtil.addParameter(
				location, "pageSize", pagination.getPageSize());
		}

		if (sortString != null) {
			location = HttpUtil.addParameter(location, "sort", sortString);
		}

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostSiteApp() throws Exception {
		App randomApp = randomApp();

		App postApp = testPostSiteApp_addApp(randomApp);

		assertEquals(randomApp, postApp);
		assertValid(postApp);
	}

	protected App testPostSiteApp_addApp(App app) throws Exception {
		return invokePostSiteApp(testGetSiteAppsPage_getSiteId(), app);
	}

	protected App invokePostSiteApp(Long siteId, App app) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setBody(
			AppSerDes.toJSON(app), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/apps", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return AppSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteAppResponse(Long siteId, App app)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			AppSerDes.toJSON(app), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/apps", siteId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(App app1, App app2) {
		Assert.assertTrue(app1 + " does not equal " + app2, equals(app1, app2));
	}

	protected void assertEquals(List<App> apps1, List<App> apps2) {
		Assert.assertEquals(apps1.size(), apps2.size());

		for (int i = 0; i < apps1.size(); i++) {
			App app1 = apps1.get(i);
			App app2 = apps2.get(i);

			assertEquals(app1, app2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<App> apps1, List<App> apps2) {
		Assert.assertEquals(apps1.size(), apps2.size());

		for (App app1 : apps1) {
			boolean contains = false;

			for (App app2 : apps2) {
				if (equals(app1, app2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(apps2 + " does not contain " + app1, contains);
		}
	}

	protected void assertValid(App app) {
		boolean valid = true;

		if (app.getDateCreated() == null) {
			valid = false;
		}

		if (app.getDateModified() == null) {
			valid = false;
		}

		if (app.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(app.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataLayoutId", additionalAssertFieldName)) {
				if (app.getDataLayoutId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataListViewId", additionalAssertFieldName)) {
				if (app.getDataListViewId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (app.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (app.getSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (app.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<App> page) {
		boolean valid = false;

		Collection<App> apps = page.getItems();

		int size = apps.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(App app1, App app2) {
		if (app1 == app2) {
			return true;
		}

		if (!Objects.equals(app1.getSiteId(), app2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataLayoutId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDataLayoutId(), app2.getDataLayoutId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataListViewId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDataListViewId(), app2.getDataListViewId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDateCreated(), app2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDateModified(), app2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getId(), app2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getName(), app2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getSettings(), app2.getSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getUserId(), app2.getUserId())) {
					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_appResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_appResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, App app) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataLayoutId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataListViewId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(app.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(app.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("settings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected App randomApp() throws Exception {
		return new App() {
			{
				dataLayoutId = RandomTestUtil.randomLong();
				dataListViewId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected App randomIrrelevantApp() throws Exception {
		App randomIrrelevantApp = randomApp();

		randomIrrelevantApp.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantApp;
	}

	protected App randomPatchApp() throws Exception {
		return randomApp();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAppResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private AppResource _appResource;

	private URL _resourceURL;

}