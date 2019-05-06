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

package com.liferay.data.engine.taglib.servlet.taglib.util;

import com.liferay.data.engine.rest.internal.renderer.v1_0.DataLayoutRenderer;
import com.liferay.data.engine.spi.field.type.FieldTypeTracker;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.template.soy.renderer.SoyComponentRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = {})
public class DataLayoutTaglibUtil {

	public static String renderDataLayout(
			Long dataLayoutId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return DataLayoutRenderer.render(
			dataLayoutId, _ddmStructureLayoutLocalService,
			_ddmStructureVersionLocalService, _fieldTypeTracker,
			httpServletRequest, httpServletResponse, _npmResolver,
			_soyComponentRenderer);
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLayoutLocalService(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		DataLayoutTaglibUtil._ddmStructureLayoutLocalService =
			ddmStructureLayoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		DataLayoutTaglibUtil._ddmStructureVersionLocalService =
			ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setFieldTypeTracker(FieldTypeTracker fieldTypeTracker) {
		DataLayoutTaglibUtil._fieldTypeTracker = fieldTypeTracker;
	}

	@Reference(unbind = "-")
	protected void setNPMResolver(NPMResolver npmResolver) {
		DataLayoutTaglibUtil._npmResolver = npmResolver;
	}

	@Reference(unbind = "-")
	protected void setSoyComponentRenderer(
		SoyComponentRenderer soyComponentRenderer) {

		DataLayoutTaglibUtil._soyComponentRenderer = soyComponentRenderer;
	}

	private static DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private static DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private static FieldTypeTracker _fieldTypeTracker;
	private static NPMResolver _npmResolver;
	private static SoyComponentRenderer _soyComponentRenderer;

}