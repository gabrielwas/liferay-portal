/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.basic.rest.internal.resource.v1_0;

import com.liferay.basic.rest.dto.v1_0.Flight;
import com.liferay.basic.rest.resource.v1_0.FlightResource;

import com.liferay.blade.basic.service.FlightLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/flight.properties",
	scope = ServiceScope.PROTOTYPE, service = FlightResource.class
)
public class FlightResourceImpl extends BaseFlightResourceImpl {


	@Override
	public Flight postFlight(Flight flight) throws Exception {

		long increment = _counterLocalService.increment();

		com.liferay.blade.basic.model.Flight serviceBuilderFlight = _flightLocalService.createFlight(increment);

		serviceBuilderFlight.setExternalReferenceCode(String.valueOf(increment));
		serviceBuilderFlight.setFlightNumber(flight.getFlightNumber());
		serviceBuilderFlight.setActive(flight.getActive());
		serviceBuilderFlight.setCapacity(flight.getCapacity());

		serviceBuilderFlight = _flightLocalService.addFlight(serviceBuilderFlight);



		return _toFlight(serviceBuilderFlight);
	}

	@Override
	public Flight getFlight(
		Long flightId)
		throws Exception {

		return _toFlight(_flightLocalService.getFlight(flightId));
	}

	@Override
	public Page<Flight> getFlightsPage(String search,
		Aggregation aggregation,
		 Filter filter,
		Pagination pagination,
	 	Sort[] sorts)
		throws Exception {

		return Page.of(TransformUtil.transform(_flightLocalService.getFlights(QueryUtil.ALL_POS, QueryUtil.ALL_POS), this::_toFlight));

	}

	@Override
	public void deleteFlight(
		Long flightId)
		throws Exception {

		_flightLocalService.deleteFlight(flightId);

	}

	@Override
	public Flight getFlightByExternalReferenceCode(
		String externalReferenceCode)
		throws Exception {

		return _toFlight(_flightLocalService.getFlightByExternalReferenceCode(externalReferenceCode, contextCompany.getCompanyId()));
	}

	private Flight _toFlight(com.liferay.blade.basic.model.Flight serviceBuilderFlight){

		return new Flight(){
			{
				setFlightNumber(serviceBuilderFlight.getFlightNumber());
				setActive(serviceBuilderFlight.isActive());
				setCapacity(serviceBuilderFlight.getCapacity());
			}
		};

	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private FlightLocalService _flightLocalService;

}