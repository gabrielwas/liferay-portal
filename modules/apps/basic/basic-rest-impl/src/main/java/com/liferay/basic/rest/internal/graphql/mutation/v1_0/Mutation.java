/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.basic.rest.internal.graphql.mutation.v1_0;

import com.liferay.basic.rest.dto.v1_0.Flight;
import com.liferay.basic.rest.resource.v1_0.FlightResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setFlightResourceComponentServiceObjects(
		ComponentServiceObjects<FlightResource>
			flightResourceComponentServiceObjects) {

		_flightResourceComponentServiceObjects =
			flightResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createFlightsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.postFlightsPageExportBatch(
				search, _filterBiFunction.apply(flightResource, filterString),
				_sortsBiFunction.apply(flightResource, sortsString),
				callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Flight createFlight(@GraphQLName("flight") Flight flight)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.postFlight(flight));
	}

	@GraphQLField
	public Response createFlightBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.postFlightBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Flight updateFlightByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("flight") Flight flight)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.putFlightByExternalReferenceCode(
				externalReferenceCode, flight));
	}

	@GraphQLField
	public boolean deleteFlight(@GraphQLName("flightId") Long flightId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.deleteFlight(flightId));

		return true;
	}

	@GraphQLField
	public Response deleteFlightBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.deleteFlightBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Flight patchFlight(
			@GraphQLName("flightId") Long flightId,
			@GraphQLName("flight") Flight flight)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.patchFlight(flightId, flight));
	}

	@GraphQLField
	public Flight updateFlight(
			@GraphQLName("flightId") Long flightId,
			@GraphQLName("flight") Flight flight)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.putFlight(flightId, flight));
	}

	@GraphQLField
	public Response updateFlightBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.putFlightBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Flight createFlightCopy(@GraphQLName("flightId") Long flightId)
		throws Exception {

		return _applyComponentServiceObjects(
			_flightResourceComponentServiceObjects,
			this::_populateResourceContext,
			flightResource -> flightResource.postFlightCopy(flightId));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(FlightResource flightResource)
		throws Exception {

		flightResource.setContextAcceptLanguage(_acceptLanguage);
		flightResource.setContextCompany(_company);
		flightResource.setContextHttpServletRequest(_httpServletRequest);
		flightResource.setContextHttpServletResponse(_httpServletResponse);
		flightResource.setContextUriInfo(_uriInfo);
		flightResource.setContextUser(_user);
		flightResource.setGroupLocalService(_groupLocalService);
		flightResource.setRoleLocalService(_roleLocalService);

		flightResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		flightResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<FlightResource>
		_flightResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}