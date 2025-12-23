/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Sam Ziemer
 */
public class ViewProjectsDisplayContext extends BaseSectionDisplayContext {

	public ViewProjectsDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission,
		Portal portal) {

		super(
			depotEntryLocalService, dlConfiguration, groupLocalService,
			httpServletRequest, language, objectDefinitionService,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission, portal);

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	public Map<String, Object> getBreadcrumbProps() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		_addBreadcrumbItem(jsonArray, false, null, _getLayoutName());

		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems", jsonArray
		).put(
			"hideSpace", true
		).build();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	@Override
	public List<DropdownItem> getCreationMenuDropdownItems() {
		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					themeDisplay.getCompanyId(), "CMPProject");

			//TODO: It should work using this, but it doesn't
			//			ActionUtil.getStructuredContentDropdownItem(
			//				httpServletRequest, "forms", "project",
			//				"L_CMP_PROJECT", "")

			return new ArrayList<>(
				List.of(
					DropdownItemBuilder.putData(
						"objectDefinitionId",
						String.valueOf(objectDefinition.getObjectDefinitionId())
					).putData(
						"action", "createAsset"
					).setHref(
						StringBundler.concat(
							themeDisplay.getPortalURL(),
							themeDisplay.getPathMain(),
							GroupConstants.CMS_FRIENDLY_URL,
							"/add_project?objectDefinitionId=",
							objectDefinition.getObjectDefinitionId(),
							"&objectEntryFolderExternalReferenceCode=", "",
							"&plid=", themeDisplay.getPlid(), "&redirect=",
							themeDisplay.getURLCurrent())
					).setIcon(
						"forms"
					).setLabel(
						LanguageUtil.get(httpServletRequest, "project")
					).build()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description", ""
		).put(
			"image", "/states/cms_empty_state.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-assets-yet")
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return Collections.emptyList();
	}

	@Override
	protected String getCMSSectionFilterString() {
		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					themeDisplay.getCompanyId(), "CMPProject");

			return StringBundler.concat(
				"objectDefinitionId eq ",
				objectDefinition.getObjectDefinitionId());
		}
		catch (PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private void _addBreadcrumbItem(
		JSONArray jsonArray, boolean active, String friendlyURL, String label) {

		jsonArray.put(
			JSONUtil.put(
				"active", active
			).put(
				"href", friendlyURL
			).put(
				"label", label
			));
	}

	private String _getLayoutName() {
		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return null;
		}

		return layout.getName(themeDisplay.getLocale(), true);
	}

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

}