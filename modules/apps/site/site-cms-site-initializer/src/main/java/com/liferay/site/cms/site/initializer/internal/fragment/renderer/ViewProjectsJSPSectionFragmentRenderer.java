/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewProjectsDisplayContext;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewStructuresDisplayContext;
import com.liferay.site.cms.site.initializer.internal.util.InfoItemUtil;
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

		long groupId = InfoItemUtil.getGroupId(httpServletRequest);

		//Group group = _groupService.getGroup(groupId);

		return new ViewProjectsDisplayContext(groupId, httpServletRequest, _objectDefinitionLocalService);
	}

	@Override
	protected String getJSPPath() {
		return "/view_projects.jsp";
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}