<%--
/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
--%>

<%@ include file="/init.jsp" %>

<strong><liferay-ui:message key="welcome-to-the-blade-service-builder-web" /></strong>

<aui:button-row>
	<portlet:renderURL var="editFooURL">
		<portlet:param name="mvcPath" value="/edit_foo.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<aui:button href="<%= editFooURL %>" value="add-foo" />
</aui:button-row>

<liferay-ui:search-container
	total="<%= flightLocalService.getFlightsCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= flightLocalService.getFlights(searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.blade.basic.model.Flight"
		escapedModel="<%= true %>"
		modelVar="flight"
	>
		<liferay-ui:search-container-column-text
			name="id"
			property="flightId"
			valign="top"
		/>

		<liferay-ui:search-container-column-text
			name="flightNumber"
			valign="top"
		>
			<strong><%= flight.getFlightNumber() %></strong>

			<br />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			property="active"
			valign="top"
		/>

		<liferay-ui:search-container-column-text
			property="capacity"
			valign="top"
		/>

		<liferay-ui:search-container-column-date
			name="flightDate"
			valign="top"
			value="<%= flight.getFlightDate() %>"
		/>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action"
			path="/foo_action.jsp"
			valign="top"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>