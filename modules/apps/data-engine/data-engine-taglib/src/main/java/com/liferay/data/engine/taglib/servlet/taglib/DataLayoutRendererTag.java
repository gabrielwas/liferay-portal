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

package com.liferay.data.engine.taglib.servlet.taglib;

import com.liferay.data.engine.taglib.servlet.taglib.base.BaseDataLayoutRendererTag;
import com.liferay.data.engine.taglib.servlet.taglib.util.DataLayoutTaglibUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutRendererTag extends BaseDataLayoutRendererTag {

	protected String getDEDataLayoutHTML() {
		String deDataLayoutHTML = StringPool.BLANK;

		HttpServletResponse response = getResponse();

		try {
			deDataLayoutHTML = DataLayoutTaglibUtil.renderDataLayout(
				getDataLayoutId(), request, response);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return deDataLayoutHTML;
	}

	protected Locale getLocale(HttpServletRequest request) {
		return LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request));
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	protected HttpServletResponse getResponse() {
		return PortalUtil.getHttpServletResponse(
			(RenderResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "content", getDEDataLayoutHTML());
		setNamespacedAttribute(
			request, "languageId", LanguageUtil.getLanguageId(request));
		setNamespacedAttribute(
			request, "resourceBundle", getResourceBundle(getLocale(request)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutRendererTag.class);

}