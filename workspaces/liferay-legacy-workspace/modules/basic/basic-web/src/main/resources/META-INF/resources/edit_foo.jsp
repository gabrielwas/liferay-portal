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

<%
String redirect = ParamUtil.getString(request, "redirect");

long flightId = ParamUtil.getLong(request, "flightId");

Flight flight = null;

if (flightId > 0) {
	flight = flightLocalService.getFlight(flightId);
}
%>

<aui:form action="<%= (javax.portlet.ActionURL)renderResponse.createActionURL() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (flight == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="flightId" type="hidden" value="<%= flightId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (flight != null) ? flight.getFlightNumber() : "new-foo" %>'
	/>

	<aui:model-context bean="<%= flight %>" model="<%= Flight.class %>" />

	<aui:fieldset>
		<aui:input name="flightNumber" />

		<aui:input name="active" />

		<aui:input name="capacity" />

		<aui:input name="flightDate" />

		<liferay-expando:custom-attributes-available className="<%= Flight.class.getName() %>">
			<liferay-expando:custom-attribute-list
				className="<%= Flight.class.getName() %>"
				classPK="<%= (flight != null) ? flight.getFlightId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>