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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class CustomPropertyUtil {

	public static CustomProperty[] addCostumProperty(
		CustomProperty[] customProperties, String property, Object value) {

		Map<String, Object> propertyMap = toCustomPropertyMap(customProperties);

		propertyMap.put(property, value);

		return toCustomProperty(propertyMap);
	}

	public static Boolean getBooleanCustomProperty(
		CustomProperty[] customProperties, String property,
		boolean defaultValue) {

		Map<String, Object> propertyMap = toCustomPropertyMap(customProperties);

		if (propertyMap.isEmpty()) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(propertyMap.get(property));
	}

	public static String getStringCustomProperty(
		CustomProperty[] customProperties, String property) {

		Map<String, Object> propertyMap = toCustomPropertyMap(customProperties);

		return GetterUtil.getString(propertyMap.get(property));
	}

	public static CustomProperty[] toCustomProperty(
		Map<String, Object> customPropertiesMap) {

		List<CustomProperty> customProperties = new ArrayList<>();

		for (Map.Entry<String, Object> entry : customPropertiesMap.entrySet()) {
			CustomProperty customProperty = new CustomProperty();

			customProperty.setKey(entry.getKey());
			customProperty.setValue(entry.getValue());

			customProperties.add(customProperty);
		}

		return customProperties.toArray(
			new CustomProperty[customPropertiesMap.size()]);
	}

	public static Map<String, Object> toCustomPropertyMap(
		CustomProperty[] customProperties) {

		if (customProperties == null) {
			return Collections.emptyMap();
		}

		Map<String, Object> propertyMap = new HashMap<>();

		for (CustomProperty customProperty : customProperties) {
			propertyMap.put(customProperty.getKey(), customProperty.getValue());
		}

		return propertyMap;
	}

}