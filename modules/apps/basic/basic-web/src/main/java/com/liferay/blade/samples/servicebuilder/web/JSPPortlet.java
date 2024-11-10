/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blade.samples.servicebuilder.web;

import com.liferay.blade.basic.model.Flight;
import com.liferay.blade.basic.service.FlightLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Liferay
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Flights",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=com_liferay_blade_samples_servicebuilder_web",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class JSPPortlet extends MVCPortlet {

	public FlightLocalService getFlightLocalService() {
		return _flightLocalService;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFlight(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFlight(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				if (SessionErrors.isEmpty(actionRequest)) {
					SessionMessages.add(actionRequest, "requestProcessed");
				}

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		// set service bean

		request.setAttribute("flightLocalService", getFlightLocalService());

		super.render(request, response);
	}

	protected void deleteFlight(ActionRequest actionRequest) throws Exception {
		long flightId = ParamUtil.getLong(actionRequest, "flightId");

		getFlightLocalService().deleteFlight(flightId);
	}

	protected void updateFlight(ActionRequest actionRequest) throws Exception {
		long flightId = ParamUtil.getLong(actionRequest, "flightId");

		String flightNumber = ParamUtil.getString(
			actionRequest, "flightNumber");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		int capacity = ParamUtil.getInteger(actionRequest, "capacity");

		int dateMonth = ParamUtil.getInteger(actionRequest, "flightDateMonth");
		int dateDay = ParamUtil.getInteger(actionRequest, "flightDateDay");
		int dateYear = ParamUtil.getInteger(actionRequest, "flightDateYear");
		int dateHour = ParamUtil.getInteger(actionRequest, "flightDateHour");
		int dateMinute = ParamUtil.getInteger(
			actionRequest, "flightDateMinute");
		int dateAmPm = ParamUtil.getInteger(actionRequest, "flightDateAmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Date flightDate = PortalUtil.getDate(
			dateMonth, dateDay, dateYear, dateHour, dateMinute,
			PortalException.class);

		if (flightId <= 0) {
			Flight flight = getFlightLocalService().createFlight(0);

			flight.setFlightNumber(flightNumber);
			flight.setActive(active);
			flight.setCapacity(capacity);
			flight.setFlightDate(flightDate);
			flight.isNew();

			getFlightLocalService().addFlight(flight);
		}
		else {
			Flight flight = getFlightLocalService().fetchFlight(flightId);

			flight.setFlightId(flightId);
			flight.setFlightNumber(flightNumber);
			flight.setActive(active);
			flight.setCapacity(capacity);
			flight.setFlightDate(flightDate);

			getFlightLocalService().updateFlight(flight);
		}
	}

	@Reference
	private volatile FlightLocalService _flightLocalService;

}