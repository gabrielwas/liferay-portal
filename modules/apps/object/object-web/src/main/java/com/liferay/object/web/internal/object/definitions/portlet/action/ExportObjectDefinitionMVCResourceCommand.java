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

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/export_object_definition"
	},
	service = MVCResourceCommand.class
)
public class ExportObjectDefinitionMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long objectDefinitionId = ParamUtil.getLong(
			resourceRequest, "objectDefinitionId");

		ObjectDefinitionResource.Builder objectDefinitionResourcedBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourcedBuilder.user(
				themeDisplay.getUser()
			).build();

		ObjectDefinition objectDefinition =
			objectDefinitionResource.getObjectDefinition(objectDefinitionId);

		_sanitize(objectDefinition);

		String dataDefinitionString = String.valueOf(objectDefinition);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			StringBundler.concat(
				"Object_",objectDefinition.getName(),
				StringPool.UNDERLINE, String.valueOf(objectDefinitionId),
				StringPool.UNDERLINE, Time.getTimestamp(), ".json"),
			dataDefinitionString.getBytes(), ContentTypes.APPLICATION_JSON);
	}

	private void _sanitize(ObjectDefinition objectDefinition) {

		objectDefinition.setDateCreated((Date)null);
		objectDefinition.setDateModified((Date)null);
		objectDefinition.setId((Long)null);
	}

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

}