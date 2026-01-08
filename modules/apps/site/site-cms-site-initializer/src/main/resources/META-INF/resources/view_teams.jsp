<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewTeamsSectionDisplayContext viewTeamsSectionDisplayContext = (ViewTeamsSectionDisplayContext)request.getAttribute(ViewTeamsSectionDisplayContext.class.getName());
%>

<div class="cms-section">
	<div>
		<react:component
			module="{Breadcrumb} from site-cms-site-initializer"
			props="<%= viewTeamsSectionDisplayContext.getBreadcrumbProps() %>"
		/>
	</div>

	<div>
		<frontend-data-set:headless-display
			additionalProps="<%= viewTeamsSectionDisplayContext.getAdditionalProps() %>"
			apiURL="<%= viewTeamsSectionDisplayContext.getAPIURL() %>"
			bulkActionDropdownItems="<%= viewTeamsSectionDisplayContext.getBulkActionDropdownItems() %>"
			creationMenu="<%= viewTeamsSectionDisplayContext.getCreationMenu() %>"
			emptyState="<%= viewTeamsSectionDisplayContext.getEmptyState() %>"
			fdsActionDropdownItems="<%= viewTeamsSectionDisplayContext.getFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CMSSiteInitializerFDSNames.TEAMS_SECTION %>"
			itemsPerPage="<%= 20 %>"
			propsTransformer="{AssetsFDSPropsTransformer} from site-cms-site-initializer"
			selectedItemsKey="embedded.id"
			selectionType="multiple"
			showSelectAll="<%= true %>"
			style="fluid"
		/>
	</div>
</div>