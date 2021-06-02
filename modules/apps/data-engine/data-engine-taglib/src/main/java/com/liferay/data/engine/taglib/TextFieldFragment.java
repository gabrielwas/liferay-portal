package com.liferay.data.engine.taglib;

import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.taglib.internal.servlet.taglib.util.DataLayoutTaglibUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;

import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Set;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = FragmentRenderer.class)
public class TextFieldFragment implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sample-components";
	}

	@Override
	public String getLabel(Locale locale) {
		return "sample-components";
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws IOException {

		try {

			DDMFormInstance ddmFormInstance = _getDDMFormInstance();

			DDMForm ddmForm = ddmFormInstance.getDDMForm();

			DDMFormInstanceVersion latestDDMFormInstanceVersion = _ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				ddmFormInstance.getFormInstanceId(), WorkflowConstants.STATUS_APPROVED);

			DDMStructureVersion ddmStructureVersion =
				latestDDMFormInstanceVersion.getStructureVersion();

//			String render = _ddmFormRenderer.render(
//				ddmForm, ddmStructureVersion.getDDMFormLayout(),
//				createDDMFormRenderingContext(ddmForm, httpServletRequest,
//					httpServletResponse));

			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<h3>Context</h3>");
			printWriter.write("<ul>");

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			printWriter.write("<li>Added by: " + fragmentEntryLink.getUserName());
			printWriter.write("<li>Added in: " + fragmentEntryLink.getCreateDate());

			printWriter.write("<li>Locale: " + fragmentRendererContext.getLocale());
			printWriter.write("<li>Mode: " + fragmentRendererContext.getMode());
			printWriter.write("<li>PreviewClassPK: " + fragmentRendererContext.getPreviewClassPK());
			printWriter.write("<li>PreviewType: " + fragmentRendererContext.getPreviewType());
			printWriter.write("<li>Segment experiences: " + StringUtil.merge(fragmentRendererContext.getSegmentsExperienceIds(), ", "));
			printWriter.write("</ul>");

			// System.out.println(render);

		}
		catch (PortalException e) {
			e.printStackTrace();
		}
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
	private DDMFormInstanceVersionLocalService _ddmFormInstanceVersionLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.data.engine.taglib)",
		unbind = "-"
	)
	private ServletContext _servletContext;
}
