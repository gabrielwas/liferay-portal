package journal.color.picker.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.description=journal-color-picker-description",
		"ddm.form.field.type.display.order:Integer=10",
		"ddm.form.field.type.icon=adjust",
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.JournalColorPicker",
		"ddm.form.field.type.js.module=journal-color-picker-form-field",
		"ddm.form.field.type.label=journal-color-picker-label",
		"ddm.form.field.type.name=journalColorPicker"
	},
	service = DDMFormFieldType.class
)
public class JournalColorPickerDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getName() {
		return "journalColorPicker";
	}
	
	@Override
	public Class<? extends DDMFormFieldTypeSettings>
	    getDDMFormFieldTypeSettings() {

	    return JournalColorPickerDDMFormFieldTypeSettings.class;
	}

}