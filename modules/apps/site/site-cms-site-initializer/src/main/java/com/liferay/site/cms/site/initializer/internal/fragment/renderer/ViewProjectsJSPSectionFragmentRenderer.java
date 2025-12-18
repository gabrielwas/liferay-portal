/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
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

		return new ViewProjectsDisplayContext(_assetLibraryResourceFactory,
			_depotEntryLocalService, _dlConfiguration, groupLocalService,
			httpServletRequest, language,_objectDefinitionLocalService, _objectDefinitionService,
			_objectDefinitionSettingLocalService,
			_objectEntryFolderModelResourcePermission, _portal);
	}

	@Override
	protected String getJSPPath() {
		return "/view_projects.jsp";
	}

	@Reference
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;


	@Reference
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;
	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectEntryFolder)"
	)
	private ModelResourcePermission<ObjectEntryFolder>
		_objectEntryFolderModelResourcePermission;

	@Reference
	private Portal _portal;


}