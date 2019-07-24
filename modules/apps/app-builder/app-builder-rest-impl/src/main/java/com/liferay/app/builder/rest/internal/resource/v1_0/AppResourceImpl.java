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

package com.liferay.app.builder.rest.internal.resource.v1_0;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app.properties",
	scope = ServiceScope.PROTOTYPE, service = AppResource.class
)
public class AppResourceImpl extends BaseAppResourceImpl {

	@Override
	public App postDataDefinitionApp(Long dataDefinitionId, App app)
		throws Exception {

		return _toApp(
			_appBuilderAppLocalService.addAppBuilderApp(
				app.getUserId(), app.getSiteId(), dataDefinitionId,
				app.getDataLayoutId(), app.getDataListViewId(),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				_toJSON(app.getSettings())));
	}

	private App _toApp(AppBuilderApp appBuilderApp) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			appBuilderApp.getSettings());

		return new App() {
			{
				dataDefinitionId = appBuilderApp.getDdmStructureId();
				dataLayoutId = appBuilderApp.getDdmStructureLayoutId();
				dataListViewId = appBuilderApp.getDeDataListViewId();
				dateCreated = appBuilderApp.getCreateDate();
				dateModified = appBuilderApp.getModifiedDate();
				id = appBuilderApp.getAppBuilderAppId();
				name = LocalizedValueUtil.toStringObjectMap(
					appBuilderApp.getNameMap());
				settings = _toSettings(jsonObject);
				siteId = appBuilderApp.getGroupId();
				userId = appBuilderApp.getUserId();
			}
		};
	}

	private String _toJSON(Map<String, Object> settings) {
		return JSONUtil.put(
			"deploymentStatus", settings.get("deploymentStatus")
		).put(
			"deploymentTypes", settings.get("deploymentTypes")
		).toString();
	}

	private Map<String, Object> _toSettings(JSONObject settings) {
		return new HashMap<String, Object>() {
			{
				put("deploymentStatus", settings.get("deploymentStatus"));
				put(
					"deploymentTypes",
					JSONUtil.toObjectList(
						(JSONArray)settings.get("deploymentTypes")));
			}
		};
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

}