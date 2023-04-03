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

package com.liferay.object.internal.system.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.util.SystemObjectDefinitionPayloadSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.extension.EntityExtensionThreadLocal;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = {SystemObjectDefinitionPayloadSerializer.class})
public class SystemObjectDefinitionPayloadSerializerImpl<T extends BaseModel<T>> implements
	SystemObjectDefinitionPayloadSerializer<T> {

	@Override
	public JSONObject serialize(
			Class<T> modelClass, String objectActionTriggerKey,
			ObjectDefinition objectDefinition, T originalBaseModel, T baseModel,
			long userId)
		throws PortalException {

		String dtoConverterType = _getDTOConverterType(modelClass);

		return JSONUtil.put(
			"classPK", baseModel.getPrimaryKeyObj()
		).put(
			"extendedProperties",
			HashMapBuilder.<String, Object>putAll(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition,
						GetterUtil.getLong(baseModel.getPrimaryKeyObj()))
			).putAll(
				EntityExtensionThreadLocal.getExtendedProperties()
			).build()
		).put(
			"model" + modelClass.getSimpleName(), baseModel.getModelAttributes()
		).put(
			"modelDTO" + dtoConverterType, _toDTO(modelClass, baseModel, userId)
		).put(
			"objectActionTriggerKey", objectActionTriggerKey
		).put(
			"original" + modelClass.getSimpleName(),
			() -> {
				if (originalBaseModel == null) {
					return null;
				}

				return originalBaseModel.getModelAttributes();
			}
		).put(
			"originalDTO" + dtoConverterType,
			() -> {
				if (originalBaseModel == null) {
					return null;
				}

				return _toDTO(modelClass, originalBaseModel, userId);
			}
		);
	}

	private DTOConverter<T, ?> _getDTOConverter(Class<T> modelClass) {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_systemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		return (DTOConverter<T, ?>)_dtoConverterRegistry.getDTOConverter(
			jaxRsApplicationDescriptor.getApplicationName(),
			modelClass.getName(), jaxRsApplicationDescriptor.getVersion());
	}

	private String _getDTOConverterType(Class<T> modelClass) {
		DTOConverter<T, ?> dtoConverter = _getDTOConverter(modelClass);

		if (dtoConverter == null) {
			return modelClass.getSimpleName();
		}

		return dtoConverter.getContentType();
	}

	private Map<String, Object> _toDTO(
		Class<T> modelClass, T baseModel, long userId) {

		DTOConverter<T, ?> dtoConverter = _getDTOConverter(modelClass);

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		if (dtoConverter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DTO converter found for " + modelClass.getName());
			}

			return modelAttributes;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No user found with user ID " + userId);
			}

			return modelAttributes;
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry,
				baseModel.getPrimaryKeyObj(), user.getLocale(), null, user);

		try {
			Object object = dtoConverter.toDTO(defaultDTOConverterContext);

			if (object == null) {
				return modelAttributes;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(object));

			return jsonObject.put(
				"createDate", modelAttributes.get("createDate")
			).put(
				"modifiedDate", modelAttributes.get("modifiedDate")
			).put(
				"status", modelAttributes.get("status")
			).put(
				"userName", user.getFullName()
			).put(
				"uuid", modelAttributes.get("uuid")
			).toMap();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return baseModel.getModelAttributes();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectDefinitionPayloadSerializerImpl.class);

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private SystemObjectDefinitionMetadata _systemObjectDefinitionMetadata;

	@Reference
	private UserLocalService _userLocalService;
}