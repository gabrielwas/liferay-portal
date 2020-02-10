package com.liferay.dynamic.data.mapping.form.builder.converter;

import com.liferay.dynamic.data.mapping.form.builder.converter.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.builder.converter.serializer.DDMFormRuleSerializerContext;

import java.util.List;

public interface DDMFormRuleConverter {

	public List<DDMFormRule> convert(
		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules);

	public List<com.liferay.dynamic.data.mapping.model.DDMFormRule> convert(
		List<DDMFormRule> ddmFormRules,
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext);
}
