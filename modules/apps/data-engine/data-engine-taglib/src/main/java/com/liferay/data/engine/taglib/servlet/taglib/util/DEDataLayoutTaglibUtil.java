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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.renderer.DEDataLayoutRenderer;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.data.engine.service.DEDataLayoutRequestBuilder;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = {})
public class DEDataLayoutTaglibUtil {

	public static DEDataLayout getDEDataLayout(long deDataLayoutId)
		throws DEDataLayoutException {

		DEDataLayoutGetRequest deDataLayoutGetRequest =
			DEDataLayoutRequestBuilder.getBuilder(
			).byId(
				deDataLayoutId
			).build();

		DEDataLayoutGetResponse deDataLayoutGetResponse =
			_deDataLayoutService.execute(deDataLayoutGetRequest);

		return deDataLayoutGetResponse.getDEDataLayout();
	}

	public static String renderDataLayout(
			DEDataLayout deDataLayout, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean readOnly)
		throws PortalException {

		return _deDataLayoutRenderer.render(
			httpServletRequest, httpServletResponse, deDataLayout, readOnly);
	}

	@Reference(unbind = "-")
	protected void setDEDataLayoutService(
		DEDataLayoutService deDataLayoutService) {

		_deDataLayoutService = deDataLayoutService;
	}
	
	@Reference(unbind = "-")
	protected void setDEDataLayoutRenderer(DEDataLayoutRenderer deDataLayoutRenderer) {
		_deDataLayoutRenderer = deDataLayoutRenderer;
	}

	private static DEDataLayoutRenderer _deDataLayoutRenderer;
	private static DEDataLayoutService _deDataLayoutService;

}