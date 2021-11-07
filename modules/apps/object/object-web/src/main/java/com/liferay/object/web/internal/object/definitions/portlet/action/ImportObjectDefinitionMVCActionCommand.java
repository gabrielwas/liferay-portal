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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/import_object_definition"
	},
	service = MVCActionCommand.class
)
public class ImportObjectDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			UploadPortletRequest uploadPortletRequest =
				_getUploadPortletRequest(actionRequest);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
				FileUtil.read(uploadPortletRequest.getFile("jsonFile")));

			objectDefinition.setName(ParamUtil.getString(actionRequest, "name"));

			ObjectDefinitionResource.Builder objectDefinitionResourcedBuilder =
				_objectDefinitionResourceFactory.create();

			ObjectDefinitionResource objectDefinitionResource =
				objectDefinitionResourcedBuilder.user(
					themeDisplay.getUser()
				).build();


			objectDefinitionResource.postObjectDefinition(objectDefinition);

			SessionMessages.add(
				actionRequest, "importObjectDefinitionSuccessMessage");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			SessionErrors.add(
				actionRequest, "importObjectDefinitionErrorMessage");

			hideDefaultErrorMessage(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}



	private UploadPortletRequest _getUploadPortletRequest(
		ActionRequest actionRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(actionRequest);

		return new UploadPortletRequestImpl(
			_portal.getUploadServletRequest(
				liferayPortletRequest.getHttpServletRequest()),
			liferayPortletRequest,
			_portal.getPortletNamespace(
				liferayPortletRequest.getPortletName()));
	}


	private static final Log _log = LogFactoryUtil.getLog(
		ImportObjectDefinitionMVCActionCommand.class);

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private Portal _portal;

}