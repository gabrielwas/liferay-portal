package com.liferay.dynamic.data.mapping.form.web.internal;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

@Component(service = FragmentRenderer.class)
public class FragmentText implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "text-field";
	}

	@Override
	public String getLabel(Locale locale) {
		return "text-field";
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws IOException {

		try {

			DDMForm ddmForm = _createDDMForm(LocaleUtil.US);

			httpServletRequest.setAttribute(
				"fragmentRendererContext", fragmentRendererContext);

			String ddmFormHTML = _ddmFormRenderer.render(ddmForm,
				createDDMFormRenderingContext(ddmForm, httpServletRequest,
					httpServletResponse));

			setNamespacedAttribute(httpServletRequest, "content", ddmFormHTML);

			_jspRenderer.renderJSP(_servletContext,
				httpServletRequest, httpServletResponse, "/frag/page.jsp");

		}
		catch (PortalException e) {
			e.printStackTrace();
		}
	}

	private DDMForm _createDDMForm(Locale locale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(Collections.singleton(locale));
		ddmForm.setDefaultLocale(locale);

		DDMFormField ddmFormField = _createDDMFormField(
			"Text1", "Text1", "text", "string", true, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	private DDMFormField _createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setFieldReference(name);
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	public void setNamespacedAttribute(
		HttpServletRequest httpServletRequest, String key, Object value) {

		if (value instanceof Boolean) {
			value = String.valueOf(value);
		}
		else if (value instanceof Number) {
			value = String.valueOf(value);
		}

		httpServletRequest.setAttribute(key, value);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		DDMFormInstance ddmFormInstance = _getDDMFormInstance();

		ddmFormRenderingContext.setContainerId(
			"form_" + StringUtil.randomString());

		ddmFormRenderingContext.setGroupId(ddmFormInstance.getGroupId());

		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);

		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);

		ddmFormRenderingContext.setLocale(getLocale(httpServletRequest, ddmForm));
		ddmFormRenderingContext.setViewMode(true);

		ddmFormRenderingContext.setReadOnly(true);

		//setDDMFormValues(ddmFormRenderingContext, ddmForm);

		ddmFormRenderingContext.setPortletNamespace(
			_portal.getPortletNamespace(
				_portal.getPortletId(httpServletRequest)));
		ddmFormRenderingContext.setShowSubmitButton(false);

		return ddmFormRenderingContext;
	}

	@Reference
	private Portal _portal;

	private DDMFormInstance _getDDMFormInstance(){
		return _ddmFormInstanceLocalService.fetchFormInstance(39444);
	}

	protected Locale getLocale(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		Locale locale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(httpServletRequest));

		if (ddmForm == null) {
			return locale;
		}

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		if (availableLocales.contains(locale)) {
			return locale;
		}

		return ddmForm.getDefaultLocale();
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dynamic.data.mapping.form.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}
