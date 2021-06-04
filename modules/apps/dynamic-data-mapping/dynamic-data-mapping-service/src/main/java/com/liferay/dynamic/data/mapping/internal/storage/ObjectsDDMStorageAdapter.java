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
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component(
	immediate = true, property = "ddm.storage.adapter.type=objects",
	service = DDMStorageAdapter.class
)
public class ObjectsDDMStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
		DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {
		return null;
	}

	@Override
	public DDMStorageAdapterGetResponse get(
		DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {
		return null;
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {

			DDMFormValues ddmFormValues =
				ddmStorageAdapterSaveRequest.getDDMFormValues();

			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getObjectDefinitions(0, 1000);


			Optional<ObjectDefinition> first = objectDefinitions.stream().filter(
				objectDefinition -> objectDefinition.getName().equals ( "Structure" +
																		ddmStorageAdapterSaveRequest.getStructureId())).findFirst();
			List<Company> companies = _companyLocalService.getCompanies();

			if (companies.size() != 1) {
				return null;
			}

			Company company = companies.get(0);

			User user = _userLocalService.fetchUserByEmailAddress(
				company.getCompanyId(), "test@liferay.com");

			if (user == null) {
				return null;
			}

			ObjectDefinition objectDefinition = null;

			if (!first.isPresent()) {

				List<ObjectField> objectFields = new ArrayList<>();

				for(DDMFormFieldValue ddmFormValue : ddmFormValues.getDDMFormFieldValues()){
					DDMFormField ddmFormField = ddmFormValue.getDDMFormField();

					objectFields.add(_createObjectField(StringUtil.toLowerCase(ddmFormField.getName()),
						capitalize(ddmFormField.getDataType())));

				}

				objectDefinition =
					_objectDefinitionLocalService.addObjectDefinition(
						user.getUserId(), "Structure" +
										  ddmStorageAdapterSaveRequest.getStructureId(), objectFields);

			}else{
				objectDefinition = first.get();
			}

			HashMap<String, Serializable> map = new HashMap<String, Serializable>();

			for(DDMFormFieldValue ddmFormValue : ddmFormValues.getDDMFormFieldValues()){
				DDMFormField ddmFormField = ddmFormValue.getDDMFormField();

				Value value = ddmFormValue.getValue();

				Map<Locale, String> values = value.getValues();

				map.put(ddmFormField.getName(),values.get(value.getDefaultLocale()));

			}

			_objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				map,
				new ServiceContext());


		}
		catch (PortalException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String capitalize(String str)
	{
		if(str == null) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private ObjectField _createObjectField(String name, String type) {
		return _createObjectField(true, false, null, name, type);
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

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;
}
