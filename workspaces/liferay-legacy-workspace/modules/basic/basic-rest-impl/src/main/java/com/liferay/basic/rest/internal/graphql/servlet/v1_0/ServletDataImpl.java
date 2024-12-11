/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.basic.rest.internal.graphql.servlet.v1_0;

import com.liferay.basic.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.basic.rest.internal.graphql.query.v1_0.Query;
import com.liferay.basic.rest.internal.resource.v1_0.FlightResourceImpl;
import com.liferay.basic.rest.resource.v1_0.FlightResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setFlightResourceComponentServiceObjects(
			_flightResourceComponentServiceObjects);

		Query.setFlightResourceComponentServiceObjects(
			_flightResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Basic.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/basic-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createFlightsPageExportBatch",
						new ObjectValuePair<>(
							FlightResourceImpl.class,
							"postFlightsPageExportBatch"));
					put(
						"mutation#createFlight",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "postFlight"));
					put(
						"mutation#createFlightBatch",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "postFlightBatch"));
					put(
						"mutation#updateFlightByExternalReferenceCode",
						new ObjectValuePair<>(
							FlightResourceImpl.class,
							"putFlightByExternalReferenceCode"));
					put(
						"mutation#deleteFlight",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "deleteFlight"));
					put(
						"mutation#deleteFlightBatch",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "deleteFlightBatch"));
					put(
						"mutation#patchFlight",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "patchFlight"));
					put(
						"mutation#updateFlight",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "putFlight"));
					put(
						"mutation#updateFlightBatch",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "putFlightBatch"));
					put(
						"mutation#createFlightCopy",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "postFlightCopy"));

					put(
						"query#flights",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "getFlightsPage"));
					put(
						"query#flightByExternalReferenceCode",
						new ObjectValuePair<>(
							FlightResourceImpl.class,
							"getFlightByExternalReferenceCode"));
					put(
						"query#flight",
						new ObjectValuePair<>(
							FlightResourceImpl.class, "getFlight"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FlightResource>
		_flightResourceComponentServiceObjects;

}