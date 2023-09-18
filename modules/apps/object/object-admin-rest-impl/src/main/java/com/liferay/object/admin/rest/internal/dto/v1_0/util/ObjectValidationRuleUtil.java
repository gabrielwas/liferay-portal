/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleSettingLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Pedro Leite
 */
public class ObjectValidationRuleUtil {

	public static List<ObjectValidationRuleSetting>
		toObjectValidationRuleSettings(
			long objectDefinitionId,
			ObjectFieldLocalService objectFieldLocalService,
			ObjectValidationRuleSettingLocalService
				objectValidationRuleSettingLocalService,
			com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRuleSetting[]
				objectValidationRuleSettings) {

		return TransformUtil.transformToList(
			objectValidationRuleSettings,
			objectValidationRuleSetting -> {
				ObjectValidationRuleSetting
					serviceBuilderObjectValidationRuleSetting =
						objectValidationRuleSettingLocalService.
							createObjectValidationRuleSetting(0L);

				if (StringUtil.equals(
						objectValidationRuleSetting.getName(),
						ObjectValidationRuleSettingConstants.
							NAME_KEY_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE)) {

					return _setObjectValidationRuleSettingProperties(
						ObjectValidationRuleSettingConstants.
							NAME_KEY_OBJECT_FIELD_ID,
						objectFieldLocalService, objectValidationRuleSetting,
						objectDefinitionId,
						serviceBuilderObjectValidationRuleSetting);
				}

				if (StringUtil.equals(
						objectValidationRuleSetting.getName(),
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE)) {

					return _setObjectValidationRuleSettingProperties(
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_ID,
						objectFieldLocalService, objectValidationRuleSetting,
						objectDefinitionId,
						serviceBuilderObjectValidationRuleSetting);
				}

				serviceBuilderObjectValidationRuleSetting.setName(
					objectValidationRuleSetting.getName());
				serviceBuilderObjectValidationRuleSetting.setValue(
					String.valueOf(objectValidationRuleSetting.getValue()));

				return serviceBuilderObjectValidationRuleSetting;
			});
	}

	private static ObjectValidationRuleSetting
			_setObjectValidationRuleSettingProperties(
				String nameObjectFieldId,
				ObjectFieldLocalService objectFieldLocalService,
				com.liferay.object.admin.rest.dto.v1_0.
					ObjectValidationRuleSetting objectValidationRuleSetting,
				long objectDefinitionId,
				ObjectValidationRuleSetting
					serviceBuilderObjectValidationRuleSetting)
		throws PortalException {

		serviceBuilderObjectValidationRuleSetting.setName(nameObjectFieldId);

		ObjectField objectField = objectFieldLocalService.getObjectField(
			String.valueOf(objectValidationRuleSetting.getValue()),
			objectDefinitionId);

		serviceBuilderObjectValidationRuleSetting.setValue(
			String.valueOf(objectField.getObjectFieldId()));

		return serviceBuilderObjectValidationRuleSetting;
	}

}