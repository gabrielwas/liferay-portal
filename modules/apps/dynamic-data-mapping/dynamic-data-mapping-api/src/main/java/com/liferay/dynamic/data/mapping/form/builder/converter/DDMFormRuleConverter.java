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

package com.liferay.dynamic.data.mapping.form.builder.converter;

import com.liferay.dynamic.data.mapping.form.builder.converter.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.builder.converter.serializer.DDMFormRuleSerializerContext;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public interface DDMFormRuleConverter {

	public List<DDMFormRule> convert(
		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules);

	public List<com.liferay.dynamic.data.mapping.model.DDMFormRule> convert(
		List<DDMFormRule> ddmFormRules,
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext);

}