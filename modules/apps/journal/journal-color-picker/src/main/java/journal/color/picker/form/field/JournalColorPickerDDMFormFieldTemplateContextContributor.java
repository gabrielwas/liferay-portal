/**
 * 
 */
package journal.color.picker.form.field;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;

/**
 * @author Gabriel Albuquerque
 *
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=journalColorPicker",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		JournalColorPickerDDMFormFieldTemplateContextContributor.class
	}
)
public class JournalColorPickerDDMFormFieldTemplateContextContributor implements DDMFormFieldTemplateContextContributor{
	
	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

        parameters.put(
            "placeholder", (String)ddmFormField.getProperty("placeholder"));
        parameters.put("mask", (String)ddmFormField.getProperty("mask"));

        return parameters;
	}

	protected String getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		if (predefinedValue == null) {
			return null;
		}

		return predefinedValue.getString(
			ddmFormFieldRenderingContext.getLocale());
	}
}
