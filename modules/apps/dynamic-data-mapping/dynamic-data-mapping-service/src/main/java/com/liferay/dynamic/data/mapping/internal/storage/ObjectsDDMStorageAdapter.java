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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "ddm.storage.adapter.type=objects",
	service = DDMStorageAdapter.class
)
public class ObjectsDDMStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
			DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {

		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public DDMStorageAdapterGetResponse get(
			DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {

		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {
			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getObjectDefinitions(0, 1000);

			Stream<ObjectDefinition> stream = objectDefinitions.stream();

			Optional<ObjectDefinition> objectDefinitionOptional = stream.filter(
				objectDefinition -> StringUtil.equals(
					objectDefinition.getName(),
					_getObjectName(
						ddmStorageAdapterSaveRequest.getStructureId()))
			).findFirst();

			DDMFormValues ddmFormValues =
				ddmStorageAdapterSaveRequest.getDDMFormValues();

			ObjectDefinition objectDefinition =
				objectDefinitionOptional.orElseGet(
					() -> {
						try {
							List<ObjectField> objectFields = new ArrayList<>();

							for (DDMFormFieldValue ddmFormValue :
									ddmFormValues.getDDMFormFieldValues()) {

								objectFields.add(
									_createObjectField(
										_getObjectFieldName(ddmFormValue),
										_getObjectFieldType(ddmFormValue)));
							}

							return _objectDefinitionLocalService.
								addObjectDefinition(
									ddmStorageAdapterSaveRequest.getUserId(),
									_getObjectName(
										ddmStorageAdapterSaveRequest.
											getStructureId()),
									objectFields);
						}
						catch (PortalException portalException) {
							throw new RuntimeException(portalException);
						}
					});

			HashMap<String, Serializable> map = new HashMap<>();

			for (DDMFormFieldValue ddmFormValue :
					ddmFormValues.getDDMFormFieldValues()) {

				Value value = ddmFormValue.getValue();

				Map<Locale, String> values = value.getValues();

				map.put(
					_getObjectFieldName(ddmFormValue),
					values.get(value.getDefaultLocale()));
			}

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				ddmStorageAdapterSaveRequest.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(), map,
				new ServiceContext());

			return DDMStorageAdapterSaveResponse.Builder.newBuilder(
				objectEntry.getPrimaryKey()
			).build();
		}
		catch (PortalException portalException) {
			throw new StorageException(portalException);
		}
	}

	private ObjectField _createObjectField(
		boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
		String name, String type) {

		ObjectField objectField = _objectFieldLocalService.createObjectField(0);

		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private ObjectField _createObjectField(String name, String type) {
		return _createObjectField(true, false, null, name, type);
	}

	private String _getObjectFieldName(DDMFormFieldValue ddmFormValue) {
		DDMFormField ddmFormField = ddmFormValue.getDDMFormField();

		return StringUtil.toLowerCase(ddmFormField.getName());
	}

	private String _getObjectFieldType(DDMFormFieldValue ddmFormValue) {
		DDMFormField ddmFormField = ddmFormValue.getDDMFormField();

		return StringUtil.upperCaseFirstLetter(ddmFormField.getDataType());
	}

	private String _getObjectName(Long ddmStructureId) {
		return "Structure" + ddmStructureId;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}