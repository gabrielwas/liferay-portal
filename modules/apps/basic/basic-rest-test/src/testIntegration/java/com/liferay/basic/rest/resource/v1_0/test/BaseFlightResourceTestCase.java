/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.basic.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.basic.rest.client.dto.v1_0.Flight;
import com.liferay.basic.rest.client.http.HttpInvoker;
import com.liferay.basic.rest.client.pagination.Page;
import com.liferay.basic.rest.client.pagination.Pagination;
import com.liferay.basic.rest.client.resource.v1_0.FlightResource;
import com.liferay.basic.rest.client.serdes.v1_0.FlightSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseFlightResourceTestCase {

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_flightResource.setContextCompany(testCompany);

		FlightResource.Builder builder = FlightResource.builder();

		flightResource = builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		Flight flight1 = randomFlight();

		String json = objectMapper.writeValueAsString(flight1);

		Flight flight2 = FlightSerDes.toDTO(json);

		Assert.assertTrue(equals(flight1, flight2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		Flight flight = randomFlight();

		String json1 = objectMapper.writeValueAsString(flight);
		String json2 = FlightSerDes.toJSON(flight);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	protected ObjectMapper getClientSerDesObjectMapper() {
		return new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Flight flight = randomFlight();

		flight.setExternalReferenceCode(regex);
		flight.setFlightNumber(regex);

		String json = FlightSerDes.toJSON(flight);

		Assert.assertFalse(json.contains(regex));

		flight = FlightSerDes.toDTO(json);

		Assert.assertEquals(regex, flight.getExternalReferenceCode());
		Assert.assertEquals(regex, flight.getFlightNumber());
	}

	@Test
	public void testGetFlightsPage() throws Exception {
		Page<Flight> page = flightResource.getFlightsPage(
			null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Flight flight1 = testGetFlightsPage_addFlight(randomFlight());

		Flight flight2 = testGetFlightsPage_addFlight(randomFlight());

		page = flightResource.getFlightsPage(
			null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(flight1, (List<Flight>)page.getItems());
		assertContains(flight2, (List<Flight>)page.getItems());
		assertValid(page, testGetFlightsPage_getExpectedActions());

		flightResource.deleteFlight(flight1.getId());

		flightResource.deleteFlight(flight2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetFlightsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetFlightsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Flight flight1 = randomFlight();

		flight1 = testGetFlightsPage_addFlight(flight1);

		for (EntityField entityField : entityFields) {
			Page<Flight> page = flightResource.getFlightsPage(
				null, null, getFilterString(entityField, "between", flight1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(flight1),
				(List<Flight>)page.getItems());
		}
	}

	@Test
	public void testGetFlightsPageWithFilterDoubleEquals() throws Exception {
		testGetFlightsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetFlightsPageWithFilterStringContains() throws Exception {
		testGetFlightsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetFlightsPageWithFilterStringEquals() throws Exception {
		testGetFlightsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetFlightsPageWithFilterStringStartsWith()
		throws Exception {

		testGetFlightsPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetFlightsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Flight flight1 = testGetFlightsPage_addFlight(randomFlight());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Flight flight2 = testGetFlightsPage_addFlight(randomFlight());

		for (EntityField entityField : entityFields) {
			Page<Flight> page = flightResource.getFlightsPage(
				null, null, getFilterString(entityField, operator, flight1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(flight1),
				(List<Flight>)page.getItems());
		}
	}

	@Test
	public void testGetFlightsPageWithPagination() throws Exception {
		Page<Flight> flightPage = flightResource.getFlightsPage(
			null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(flightPage.getTotalCount());

		Flight flight1 = testGetFlightsPage_addFlight(randomFlight());

		Flight flight2 = testGetFlightsPage_addFlight(randomFlight());

		Flight flight3 = testGetFlightsPage_addFlight(randomFlight());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<Flight> page1 = flightResource.getFlightsPage(
				null, null, null,
				Pagination.of(
					(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
					pageSizeLimit),
				null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(flight1, (List<Flight>)page1.getItems());

			Page<Flight> page2 = flightResource.getFlightsPage(
				null, null, null,
				Pagination.of(
					(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
					pageSizeLimit),
				null);

			assertContains(flight2, (List<Flight>)page2.getItems());

			Page<Flight> page3 = flightResource.getFlightsPage(
				null, null, null,
				Pagination.of(
					(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
					pageSizeLimit),
				null);

			assertContains(flight3, (List<Flight>)page3.getItems());
		}
		else {
			Page<Flight> page1 = flightResource.getFlightsPage(
				null, null, null, Pagination.of(1, totalCount + 2), null);

			List<Flight> flights1 = (List<Flight>)page1.getItems();

			Assert.assertEquals(
				flights1.toString(), totalCount + 2, flights1.size());

			Page<Flight> page2 = flightResource.getFlightsPage(
				null, null, null, Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<Flight> flights2 = (List<Flight>)page2.getItems();

			Assert.assertEquals(flights2.toString(), 1, flights2.size());

			Page<Flight> page3 = flightResource.getFlightsPage(
				null, null, null, Pagination.of(1, (int)totalCount + 3), null);

			assertContains(flight1, (List<Flight>)page3.getItems());
			assertContains(flight2, (List<Flight>)page3.getItems());
			assertContains(flight3, (List<Flight>)page3.getItems());
		}
	}

	@Test
	public void testGetFlightsPageWithSortDateTime() throws Exception {
		testGetFlightsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, flight1, flight2) -> {
				BeanTestUtil.setProperty(
					flight1, entityField.getName(),
					new Date(System.currentTimeMillis() - (2 * Time.MINUTE)));
			});
	}

	@Test
	public void testGetFlightsPageWithSortDouble() throws Exception {
		testGetFlightsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, flight1, flight2) -> {
				BeanTestUtil.setProperty(flight1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(flight2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetFlightsPageWithSortInteger() throws Exception {
		testGetFlightsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, flight1, flight2) -> {
				BeanTestUtil.setProperty(flight1, entityField.getName(), 0);
				BeanTestUtil.setProperty(flight2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetFlightsPageWithSortString() throws Exception {
		testGetFlightsPageWithSort(
			EntityField.Type.STRING,
			(entityField, flight1, flight2) -> {
				Class<?> clazz = flight1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						flight1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						flight2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						flight1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						flight2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						flight1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						flight2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetFlightsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Flight, Flight, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Flight flight1 = randomFlight();
		Flight flight2 = randomFlight();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, flight1, flight2);
		}

		flight1 = testGetFlightsPage_addFlight(flight1);

		flight2 = testGetFlightsPage_addFlight(flight2);

		Page<Flight> page = flightResource.getFlightsPage(
			null, null, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<Flight> ascPage = flightResource.getFlightsPage(
				null, null, null,
				Pagination.of(1, (int)page.getTotalCount() + 1),
				entityField.getName() + ":asc");

			assertContains(flight1, (List<Flight>)ascPage.getItems());
			assertContains(flight2, (List<Flight>)ascPage.getItems());

			Page<Flight> descPage = flightResource.getFlightsPage(
				null, null, null,
				Pagination.of(1, (int)page.getTotalCount() + 1),
				entityField.getName() + ":desc");

			assertContains(flight2, (List<Flight>)descPage.getItems());
			assertContains(flight1, (List<Flight>)descPage.getItems());
		}
	}

	protected Flight testGetFlightsPage_addFlight(Flight flight)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFlightsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"flights",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		// No namespace

		JSONObject flightsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/flights");

		long totalCount = flightsJSONObject.getLong("totalCount");

		Flight flight1 = testGraphQLGetFlightsPage_addFlight();
		Flight flight2 = testGraphQLGetFlightsPage_addFlight();

		flightsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/flights");

		Assert.assertEquals(
			totalCount + 2, flightsJSONObject.getLong("totalCount"));

		assertContains(
			flight1,
			Arrays.asList(
				FlightSerDes.toDTOs(flightsJSONObject.getString("items"))));
		assertContains(
			flight2,
			Arrays.asList(
				FlightSerDes.toDTOs(flightsJSONObject.getString("items"))));

		// Using the namespace basic_v1_0

		flightsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(new GraphQLField("basic_v1_0", graphQLField)),
			"JSONObject/data", "JSONObject/basic_v1_0", "JSONObject/flights");

		Assert.assertEquals(
			totalCount + 2, flightsJSONObject.getLong("totalCount"));

		assertContains(
			flight1,
			Arrays.asList(
				FlightSerDes.toDTOs(flightsJSONObject.getString("items"))));
		assertContains(
			flight2,
			Arrays.asList(
				FlightSerDes.toDTOs(flightsJSONObject.getString("items"))));
	}

	protected Flight testGraphQLGetFlightsPage_addFlight() throws Exception {
		return testGraphQLFlight_addFlight();
	}

	@Test
	public void testPostFlight() throws Exception {
		Flight randomFlight = randomFlight();

		Flight postFlight = testPostFlight_addFlight(randomFlight);

		assertEquals(randomFlight, postFlight);
		assertValid(postFlight);
	}

	protected Flight testPostFlight_addFlight(Flight flight) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetFlightByExternalReferenceCode() throws Exception {
		Flight postFlight = testGetFlightByExternalReferenceCode_addFlight();

		Flight getFlight = flightResource.getFlightByExternalReferenceCode(
			postFlight.getExternalReferenceCode());

		assertEquals(postFlight, getFlight);
		assertValid(getFlight);
	}

	protected Flight testGetFlightByExternalReferenceCode_addFlight()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFlightByExternalReferenceCode() throws Exception {
		Flight flight = testGraphQLGetFlightByExternalReferenceCode_addFlight();

		// No namespace

		Assert.assertTrue(
			equals(
				flight,
				FlightSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"flightByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												flight.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/flightByExternalReferenceCode"))));

		// Using the namespace basic_v1_0

		Assert.assertTrue(
			equals(
				flight,
				FlightSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"basic_v1_0",
								new GraphQLField(
									"flightByExternalReferenceCode",
									new HashMap<String, Object>() {
										{
											put(
												"externalReferenceCode",
												"\"" +
													flight.
														getExternalReferenceCode() +
															"\"");
										}
									},
									getGraphQLFields()))),
						"JSONObject/data", "JSONObject/basic_v1_0",
						"Object/flightByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetFlightByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		// No namespace

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"flightByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));

		// Using the namespace basic_v1_0

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"basic_v1_0",
						new GraphQLField(
							"flightByExternalReferenceCode",
							new HashMap<String, Object>() {
								{
									put(
										"externalReferenceCode",
										irrelevantExternalReferenceCode);
								}
							},
							getGraphQLFields()))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Flight testGraphQLGetFlightByExternalReferenceCode_addFlight()
		throws Exception {

		return testGraphQLFlight_addFlight();
	}

	@Test
	public void testPutFlightByExternalReferenceCode() throws Exception {
		Flight postFlight = testPutFlightByExternalReferenceCode_addFlight();

		Flight randomFlight = randomFlight();

		Flight putFlight = flightResource.putFlightByExternalReferenceCode(
			postFlight.getExternalReferenceCode(), randomFlight);

		assertEquals(randomFlight, putFlight);
		assertValid(putFlight);

		Flight getFlight = flightResource.getFlightByExternalReferenceCode(
			putFlight.getExternalReferenceCode());

		assertEquals(randomFlight, getFlight);
		assertValid(getFlight);

		Flight newFlight = testPutFlightByExternalReferenceCode_createFlight();

		putFlight = flightResource.putFlightByExternalReferenceCode(
			newFlight.getExternalReferenceCode(), newFlight);

		assertEquals(newFlight, putFlight);
		assertValid(putFlight);

		getFlight = flightResource.getFlightByExternalReferenceCode(
			putFlight.getExternalReferenceCode());

		assertEquals(newFlight, getFlight);

		Assert.assertEquals(
			newFlight.getExternalReferenceCode(),
			putFlight.getExternalReferenceCode());
	}

	protected Flight testPutFlightByExternalReferenceCode_createFlight()
		throws Exception {

		return randomFlight();
	}

	protected Flight testPutFlightByExternalReferenceCode_addFlight()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteFlight() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Flight flight = testDeleteFlight_addFlight();

		assertHttpResponseStatusCode(
			204, flightResource.deleteFlightHttpResponse(flight.getId()));

		assertHttpResponseStatusCode(
			404, flightResource.getFlightHttpResponse(flight.getId()));

		assertHttpResponseStatusCode(
			404, flightResource.getFlightHttpResponse(0L));
	}

	protected Flight testDeleteFlight_addFlight() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteFlight() throws Exception {

		// No namespace

		Flight flight1 = testGraphQLDeleteFlight_addFlight();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteFlight",
						new HashMap<String, Object>() {
							{
								put("flightId", flight1.getId());
							}
						})),
				"JSONObject/data", "Object/deleteFlight"));

		JSONArray errorsJSONArray1 = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"flight",
					new HashMap<String, Object>() {
						{
							put("flightId", flight1.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray1.length() > 0);

		// Using the namespace basic_v1_0

		Flight flight2 = testGraphQLDeleteFlight_addFlight();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"basic_v1_0",
						new GraphQLField(
							"deleteFlight",
							new HashMap<String, Object>() {
								{
									put("flightId", flight2.getId());
								}
							}))),
				"JSONObject/data", "JSONObject/basic_v1_0",
				"Object/deleteFlight"));

		JSONArray errorsJSONArray2 = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"basic_v1_0",
					new GraphQLField(
						"flight",
						new HashMap<String, Object>() {
							{
								put("flightId", flight2.getId());
							}
						},
						new GraphQLField("id")))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray2.length() > 0);
	}

	protected Flight testGraphQLDeleteFlight_addFlight() throws Exception {
		return testGraphQLFlight_addFlight();
	}

	@Test
	public void testGetFlight() throws Exception {
		Flight postFlight = testGetFlight_addFlight();

		Flight getFlight = flightResource.getFlight(postFlight.getId());

		assertEquals(postFlight, getFlight);
		assertValid(getFlight);
	}

	protected Flight testGetFlight_addFlight() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFlight() throws Exception {
		Flight flight = testGraphQLGetFlight_addFlight();

		// No namespace

		Assert.assertTrue(
			equals(
				flight,
				FlightSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"flight",
								new HashMap<String, Object>() {
									{
										put("flightId", flight.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/flight"))));

		// Using the namespace basic_v1_0

		Assert.assertTrue(
			equals(
				flight,
				FlightSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"basic_v1_0",
								new GraphQLField(
									"flight",
									new HashMap<String, Object>() {
										{
											put("flightId", flight.getId());
										}
									},
									getGraphQLFields()))),
						"JSONObject/data", "JSONObject/basic_v1_0",
						"Object/flight"))));
	}

	@Test
	public void testGraphQLGetFlightNotFound() throws Exception {
		Long irrelevantFlightId = RandomTestUtil.randomLong();

		// No namespace

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"flight",
						new HashMap<String, Object>() {
							{
								put("flightId", irrelevantFlightId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));

		// Using the namespace basic_v1_0

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"basic_v1_0",
						new GraphQLField(
							"flight",
							new HashMap<String, Object>() {
								{
									put("flightId", irrelevantFlightId);
								}
							},
							getGraphQLFields()))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Flight testGraphQLGetFlight_addFlight() throws Exception {
		return testGraphQLFlight_addFlight();
	}

	@Test
	public void testPatchFlight() throws Exception {
		Flight postFlight = testPatchFlight_addFlight();

		Flight randomPatchFlight = randomPatchFlight();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Flight patchFlight = flightResource.patchFlight(
			postFlight.getId(), randomPatchFlight);

		Flight expectedPatchFlight = postFlight.clone();

		BeanTestUtil.copyProperties(randomPatchFlight, expectedPatchFlight);

		Flight getFlight = flightResource.getFlight(patchFlight.getId());

		assertEquals(expectedPatchFlight, getFlight);
		assertValid(getFlight);
	}

	protected Flight testPatchFlight_addFlight() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutFlight() throws Exception {
		Flight postFlight = testPutFlight_addFlight();

		Flight randomFlight = randomFlight();

		Flight putFlight = flightResource.putFlight(
			postFlight.getId(), randomFlight);

		assertEquals(randomFlight, putFlight);
		assertValid(putFlight);

		Flight getFlight = flightResource.getFlight(putFlight.getId());

		assertEquals(randomFlight, getFlight);
		assertValid(getFlight);
	}

	protected Flight testPutFlight_addFlight() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostFlightCopy() throws Exception {
		Flight randomFlight = randomFlight();

		Flight postFlight = testPostFlightCopy_addFlight(randomFlight);

		assertEquals(randomFlight, postFlight);
		assertValid(postFlight);
	}

	protected Flight testPostFlightCopy_addFlight(Flight flight)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Flight testGraphQLFlight_addFlight() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Flight flight, List<Flight> flights) {
		boolean contains = false;

		for (Flight item : flights) {
			if (equals(flight, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(flights + " does not contain " + flight, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Flight flight1, Flight flight2) {
		Assert.assertTrue(
			flight1 + " does not equal " + flight2, equals(flight1, flight2));
	}

	protected void assertEquals(List<Flight> flights1, List<Flight> flights2) {
		Assert.assertEquals(flights1.size(), flights2.size());

		for (int i = 0; i < flights1.size(); i++) {
			Flight flight1 = flights1.get(i);
			Flight flight2 = flights2.get(i);

			assertEquals(flight1, flight2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Flight> flights1, List<Flight> flights2) {

		Assert.assertEquals(flights1.size(), flights2.size());

		for (Flight flight1 : flights1) {
			boolean contains = false;

			for (Flight flight2 : flights2) {
				if (equals(flight1, flight2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				flights2 + " does not contain " + flight1, contains);
		}
	}

	protected void assertValid(Flight flight) throws Exception {
		boolean valid = true;

		if (flight.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (flight.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (flight.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("capacity", additionalAssertFieldName)) {
				if (flight.getCapacity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (flight.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("flightDate", additionalAssertFieldName)) {
				if (flight.getFlightDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("flightNumber", additionalAssertFieldName)) {
				if (flight.getFlightNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (flight.getStatus() == null) {
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

	protected void assertValid(Page<Flight> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Flight> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Flight> flights = page.getItems();

		int size = flights.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.basic.rest.dto.v1_0.Flight.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Flight flight1, Flight flight2) {
		if (flight1 == flight2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)flight1.getActions(), (Map)flight2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						flight1.getActive(), flight2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("capacity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						flight1.getCapacity(), flight2.getCapacity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						flight1.getExternalReferenceCode(),
						flight2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("flightDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						flight1.getFlightDate(), flight2.getFlightDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("flightNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						flight1.getFlightNumber(), flight2.getFlightNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(flight1.getId(), flight2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						flight1.getStatus(), flight2.getStatus())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		if (clazz.getClassLoader() == null) {
			return new java.lang.reflect.Field[0];
		}

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_flightResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_flightResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, Flight flight) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("capacity")) {
			sb.append(String.valueOf(flight.getCapacity()));

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = flight.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("flightDate")) {
			if (operator.equals("between")) {
				Date date = flight.getFlightDate();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(flight.getFlightDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("flightNumber")) {
			Object object = flight.getFlightNumber();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			sb.append(String.valueOf(flight.getStatus()));

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Flight randomFlight() throws Exception {
		return new Flight() {
			{
				active = RandomTestUtil.randomBoolean();
				capacity = RandomTestUtil.randomInt();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				flightDate = RandomTestUtil.nextDate();
				flightNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomInt();
			}
		};
	}

	protected Flight randomIrrelevantFlight() throws Exception {
		Flight randomIrrelevantFlight = randomFlight();

		return randomIrrelevantFlight;
	}

	protected Flight randomPatchFlight() throws Exception {
		return randomFlight();
	}

	protected FlightResource flightResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = source.getClass();

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					_getAllDeclaredFields(sourceClass)) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				try {
					Method setMethod = _getMethod(
						targetClass, field.getName(), "set",
						getMethod.getReturnType());

					setMethod.invoke(target, getMethod.invoke(source));
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static List<java.lang.reflect.Field> _getAllDeclaredFields(
			Class<?> clazz) {

			List<java.lang.reflect.Field> fields = new ArrayList<>();

			while ((clazz != null) && (clazz != Object.class)) {
				for (java.lang.reflect.Field field :
						clazz.getDeclaredFields()) {

					fields.add(field);
				}

				clazz = clazz.getSuperclass();
			}

			return fields;
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseFlightResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.basic.rest.resource.v1_0.FlightResource _flightResource;

}