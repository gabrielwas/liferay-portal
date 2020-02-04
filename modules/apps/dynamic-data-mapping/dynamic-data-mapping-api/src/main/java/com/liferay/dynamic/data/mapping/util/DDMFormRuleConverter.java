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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormRuleConverter {

	public static List<DDMFormRule> toDDMFormRules(JSONArray jsonArray) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormRule ddmFormRule = _getDDMFormRule(
				jsonArray.getJSONObject(i));

			ddmFormRules.add(ddmFormRule);
		}

		return ddmFormRules;
	}

	public static JSONArray toJSONArray(List<DDMFormRule> ddmFormRules) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			jsonArray.put(_toJSONObject(ddmFormRule));
		}

		return jsonArray;
	}

	private static DDMFormRule _getDDMFormRule(JSONObject jsonObject) {
		String condition = jsonObject.getString("condition");

		List<String> actions = _getDDMFormRuleActions(
			jsonObject.getJSONArray("actions"));

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		boolean enabled = jsonObject.getBoolean("enabled", true);

		ddmFormRule.setEnabled(enabled);

		return ddmFormRule;
	}

	private static List<String> _getDDMFormRuleActions(JSONArray jsonArray) {
		List<String> actions = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			actions.add(jsonArray.getString(i));
		}

		return actions;
	}

	private static JSONArray _ruleActionsToJSONArray(List<String> ruleActions) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String ruleAction : ruleActions) {
			jsonArray.put(ruleAction);
		}

		return jsonArray;
	}

	private static JSONObject _toJSONObject(DDMFormRule ddmFormRule) {
		return JSONUtil.put(
			"actions", _ruleActionsToJSONArray(ddmFormRule.getActions())
		).put(
			"condition", ddmFormRule.getCondition()
		).put(
			"enabled", ddmFormRule.isEnabled()
		);
	}

}