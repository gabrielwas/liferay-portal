package journal.color.picker.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=journalColorPicker",
	service = DDMFormFieldRenderer.class
)
public class JournalColorPickerDDMFormFieldRenderer extends BaseDDMFormFieldRenderer {

	@Override
	public String getTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public String getTemplateNamespace() {
		return "DDMJournalColorPicker.render";
	}

	@Override
	public TemplateResource getTemplateResource() {
		return _templateResource;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_templateResource = getTemplateResource(
			"/META-INF/resources/journal-color-picker.soy");
	}
	
	@Override
	protected void populateOptionalContext(
	    Template template, DDMFormField ddmFormField,
	    DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

	    Map<String, Object> parameters =
	    		journalColorPickerDDMFormFieldTemplateContextContributor.getParameters(
	         ddmFormField, ddmFormFieldRenderingContext);

	    template.putAll(parameters);
	}
	
	@Reference
	protected JournalColorPickerDDMFormFieldTemplateContextContributor
	    journalColorPickerDDMFormFieldTemplateContextContributor;

	private TemplateResource _templateResource;

}