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

package com.liferay.object.web.internal.object.definitions.portlet.action.util;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectLayoutColumnJSONObjectUtil {

	public static void modifyObjectLayoutColumnJSONObject(
			JSONObject objectDefinitionJSONObject,
			UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction)
		throws Exception {

		JSONArray objectLayoutsJsonArray = (JSONArray) objectDefinitionJSONObject.get("objectLayouts");

		for (int i = 0; i < objectLayoutsJsonArray.length(); i++) {
			JSONObject objectLayout = (JSONObject)  objectLayoutsJsonArray.get(i);

			JSONArray objectLayoutTabsJsonArray = (JSONArray) objectLayout.get("objectLayoutTabs");

			for (int j = 0; j < objectLayoutTabsJsonArray.length(); j++) {
				JSONObject objectLayoutTab = (JSONObject) objectLayoutTabsJsonArray.get(j);

				JSONArray objectLayoutBoxesJsonArray = (JSONArray) objectLayoutTab.get("objectLayoutBoxes");

				for (int k = 0; k < objectLayoutBoxesJsonArray.length(); k++) {

					JSONObject objectLayoutBox = (JSONObject) objectLayoutBoxesJsonArray.get(k);

					JSONArray objectLayoutRowsJsonArray = (JSONArray) objectLayoutBox.get("objectLayoutRows");

					for (int l = 0; l < objectLayoutRowsJsonArray.length(); l++) {
						JSONObject objectLayoutRow = (JSONObject) objectLayoutRowsJsonArray.get(l);

						JSONArray objectLayoutColumnsJsonArray = (JSONArray) objectLayoutRow.get("objectLayoutColumns");

						JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

						for (int m = 0; m < objectLayoutColumnsJsonArray.length(); m++) {

							JSONObject jsonObject =
								(JSONObject) objectLayoutColumnsJsonArray.get(
									m);

							jsonArray.put(
								() -> unsafeFunction.apply(jsonObject));

						}

						objectLayoutRow.put("objectLayoutColumns", jsonArray);

					}

				}

			}

		}

//
//		List<JSONObject> objectLayoutColumns = _getJSONObjectNestedProperties(
//			(JSONArray)objectDefinitionJSONObject.get("objectLayouts"),
//			"objectLayoutTabs", "objectLayoutBoxes", "objectLayoutRows",
//			"objectLayoutColumns", "objectLayoutColumn");
//
//		for (JSONObject objectLayoutColumnJSONObject : objectLayoutColumns) {
//			unsafeFunction.apply(objectLayoutColumnJSONObject);
//		}
	}

//	private static void _getJSONObjectNestedProperties(
//		JSONObject jsonObject, String... properties) {
//
//		if (properties.length == 1) {
//
//		}
//
//		JSONArray jsonArray = (JSONArray) jsonObject.get(properties[0]);
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			_getJSONObjectNestedProperties((JSONObject)jsonArray.get(i),
//				Arrays.copyOfRange(properties, 1, properties.length));
//		}
//
//	}

//	private static List<JSONObject> _getJSONObjectNestedProperties(
//		JSONArray jsonArray, String... properties) {
//
//		List<JSONObject> values = new ArrayList<>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jsonObject = (JSONObject)jsonArray.get(i);
//
//			if (properties.length == 1) {
//				values.add(jsonObject);
//
//				continue;
//			}
//
//			values.addAll(
//				_getJSONObjectNestedProperties(
//					(JSONArray)jsonObject.get(properties[0]),
//					Arrays.copyOfRange(properties, 1, properties.length)));
//		}
//
//		return values;
//	}

}