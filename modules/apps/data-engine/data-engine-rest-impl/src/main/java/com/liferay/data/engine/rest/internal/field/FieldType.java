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

package com.liferay.data.engine.rest.internal.field;

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataEngineUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public abstract class FieldType {

	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		if (!jsonObject.has("name")) {
			throw new Exception("Name is required");
		}

		if (!jsonObject.has("type")) {
			throw new Exception("Type is required");
		}

		return new DataDefinitionField() {
			{
				defaultValue = jsonObject.get("defaultValue");
				fieldType = jsonObject.getString("type");
				indexable = jsonObject.getBoolean("indexable", true);

				LocalizedValue[] labels =
					LocalizedValueUtil.getLocalizedProperty(
						"label", jsonObject);

				if (labels != null) {
					label = labels;
				}

				localizable = jsonObject.getBoolean("localizable", false);
				name = jsonObject.getString("name");
				repeatable = jsonObject.getBoolean("repeatable", false);

				LocalizedValue[] tips = LocalizedValueUtil.getLocalizedProperty(
					"tip", jsonObject);

				if (tips != null) {
					tip = tips;
				}
			}
		};
	}

	public void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DataDefinitionField dataDefinitionField, boolean readOnly) {

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		CustomProperty[] customProperties =
			dataDefinitionField.getCustomProperties();

		context.put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR));
		context.put(
			"label",
			LocalizedValueUtil.getLocalizedValue(
				dataDefinitionField.getLabel(), languageId));
		context.put("name", dataDefinitionField.getName());
		context.put(
			"readOnly",
			DataEngineUtil.getBooleanCustomProperty(
				customProperties, "readOnly", false));
		context.put(
			"required",
			DataEngineUtil.getBooleanCustomProperty(
				customProperties, "required", false));
		context.put(
			"showLabel",
			DataEngineUtil.getBooleanCustomProperty(
				customProperties, "showLabel", true));
		context.put(
			"tip",
			LocalizedValueUtil.getLocalizedValue(
				dataDefinitionField.getTip(), languageId));
		context.put("type", dataDefinitionField.getFieldType());
		context.put(
			"visible",
			DataEngineUtil.getBooleanCustomProperty(
				customProperties, "visible", true));
	}

	public JSONObject serialize(
			DataDefinitionField dataDefinitionField, JSONFactory jsonFactory)
		throws Exception {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		Map<Locale, String> label = LocalizedValueUtil.toLocalizationMap(
			dataDefinitionField.getLabel());

		if (!label.isEmpty()) {
			LocalizedValueUtil.setLocalizedProperty(
				"label", jsonFactory, jsonObject, label);
		}

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		Map<Locale, String> tip = LocalizedValueUtil.toLocalizationMap(
			dataDefinitionField.getTip());

		if (!tip.isEmpty()) {
			LocalizedValueUtil.setLocalizedProperty(
				"tip", jsonFactory, jsonObject, tip);
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		jsonObject.put("type", type);

		return jsonObject;
	}

}