/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blade.basic.service.impl;

import com.liferay.blade.basic.model.Flight;
import com.liferay.blade.basic.service.base.FlightLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.blade.basic.model.Flight",
	service = AopService.class
)
public class FlightLocalServiceImpl extends FlightLocalServiceBaseImpl {

	@Override
	public Flight addFlight(Flight flight) {

		flight.setFlightId(_counterLocalService.increment());

		return flightPersistence.update(flight);

	}

	@Reference
	private CounterLocalService _counterLocalService;

}