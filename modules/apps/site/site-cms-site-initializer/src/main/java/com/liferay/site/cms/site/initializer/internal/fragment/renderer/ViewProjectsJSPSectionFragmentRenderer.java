/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewProjectsDisplayContext;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewStructuresDisplayContext;
import jakarta.servlet.http.HttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sam Ziemer
 */
@Component(service = FragmentRenderer.class)
public class ViewProjectsJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<ViewProjectsDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabelKey() {
		return "projects";
	}

	@Override
	protected ViewProjectsDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewProjectsDisplayContext(httpServletRequest, _objectDefinitionLocalService);
	}

	@Override
	protected String getJSPPath() {
		return "/view_projects.jsp";
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}