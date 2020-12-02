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

package com.liferay.app.builder.internal.data.engine.content.type;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.app.builder.internal.field.type.settings.checkbox.CheckboxAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.checkbox.multiple.CheckboxMultipleAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.date.DateAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.document.library.DocumentLibraryAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.fieldset.FieldSetAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.numeric.NumericAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.radio.RadioAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.select.SelectAppBuilderFieldTypeSettings;
import com.liferay.app.builder.internal.field.type.settings.text.TextAppBuilderFieldTypeSettings;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "content.type=app-builder",
	service = DataDefinitionContentType.class
)
public class AppBuilderDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public boolean allowEmptyDataDefinition() {
		return true;
	}

	@Override
	public boolean allowInvalidAvailableLocalesForProperty() {
		return true;
	}

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(AppBuilderApp.class);
	}

	@Override
	public String getContentType() {
		return "app-builder";
	}

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings(String fieldType) {

		return _ddmFormFieldTypeSettings.get(fieldType);
	}

	@Override
	public String getPortletResourceName() {
		return AppBuilderConstants.RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (StringUtil.contains(
				resourceName, DDLRecord.class.getName(), StringPool.DASH)) {

			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				_appBuilderAppDataRecordLinkLocalService.
					fetchDDLRecordAppBuilderAppDataRecordLink(primKey);

			if (Objects.nonNull(appBuilderAppDataRecordLink)) {
				Boolean hasPermission = WorkflowPermissionUtil.hasPermission(
					permissionChecker, appBuilderAppDataRecordLink.getGroupId(),
					resourceName, primKey, ActionKeys.VIEW);

				if (hasPermission != null) {
					return hasPermission;
				}
			}
		}

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return DataDefinitionContentType.super.hasPermission(
			permissionChecker, companyId, groupId, resourceName, primKey,
			userId, actionId);
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return _portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Override
	public boolean isDataRecordCollectionPermissionCheckingEnabled() {
		return true;
	}

	@Activate
	protected void activate() {
		_ddmFormFieldTypeSettings.put(
			"checkbox", CheckboxAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"checkbox_multiple",
			CheckboxMultipleAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"date", DateAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"document_library",
			DocumentLibraryAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"fieldset", FieldSetAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"numeric", NumericAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"radio", RadioAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"select", SelectAppBuilderFieldTypeSettings.class);
		_ddmFormFieldTypeSettings.put(
			"text", TextAppBuilderFieldTypeSettings.class);
	}

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	private final Map<String, Class<? extends DDMFormFieldTypeSettings>>
		_ddmFormFieldTypeSettings = new HashMap<>();

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + AppBuilderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}