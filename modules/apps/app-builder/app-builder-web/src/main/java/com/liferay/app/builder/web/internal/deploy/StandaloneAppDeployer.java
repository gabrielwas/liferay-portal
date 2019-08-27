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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.web.internal.portlet.StandaloneAppPortlet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "com.app.builder.deploy.type=standalone",
	service = AppDeployer.class
)
public class StandaloneAppDeployer implements AppDeployer {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		_serviceRegistrationsMap.computeIfAbsent(
			appId,
			key -> _deployAppPortlet(
				"App" + appId,
				appBuilderApp.getName(LocaleThreadLocal.getDefaultLocale()),
				appBuilderApp.getCompanyId(),
				AppBuilderPortletKeys.STANDALONE_APP + "_" + appId));

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.DEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrationsMap.remove(appId);

		if (serviceRegistration == null) {
			return;
		}

		serviceRegistration.unregister();

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		Group group = _groupLocalService.getGroup(
			appBuilderApp.getCompanyId(), "App" + appId);

		group.setActive(false);

		_groupLocalService.updateGroup(group);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.UNDEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	protected Group addFormsGroup(long companyId, String appGroupKey)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), appGroupKey);

		return _groupLocalService.addGroup(
			_userLocalService.getDefaultUserId(companyId),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null,
			GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			"/" + StringUtil.toLowerCase(appGroupKey), false, false, true,
			null);
	}

	protected Layout addPublicLayout(
			long companyId, long groupId, String portletName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layoutUpdateable", Boolean.FALSE);

		serviceContext.setScopeGroupId(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		return _layoutLocalService.addLayout(
			defaultUserId, groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Shared",
			StringPool.BLANK, StringPool.BLANK, portletName, true, "/shared",
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private ServiceRegistration<?> _deployAppPortlet(
		String appGroupKey, String appName, long companyId,
		String portletName) {

		Group group = _groupLocalService.fetchFriendlyURLGroup(
			companyId, "/" + StringUtil.toLowerCase(appGroupKey));

		if (group == null) {
			try {
				group = addFormsGroup(companyId, appGroupKey);
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}
		}else{
			group.setActive(true);
			_groupLocalService.updateGroup(group);
		}

		Layout sharedLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), false, "/shared");

		if (sharedLayout == null) {
			try {
				addPublicLayout(companyId, group.getGroupId(), portletName);
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}
		}

		return _bundleContext.registerService(
			Portlet.class, new StandaloneAppPortlet(),
			new HashMapDictionary<String, Object>() {
				{
					put(
						"com.liferay.fragment.entry.processor.portlet.alias",
						"app");
					put("com.liferay.portlet.add-default-resource", true);
					put(
						"com.liferay.portlet.application-type",
						"full-page-application");
					put("com.liferay.portlet.friendly-url-mapping", "app");
					put(
						"com.liferay.portlet.display-category",
						"category.collaboration");
					put("com.liferay.portlet.use-default-template", "true");
					put("javax.portlet.display-name", appName);
					put("javax.portlet.name", portletName);
					put(
						"javax.portlet.init-param.template-path",
						"/META-INF/resources/");
					put("javax.portlet.init-param.view-template", "/view.jsp");
					put(
						"javax.portlet.security-role-ref",
						"administrator,guest,power-user,user ");
					put("javax.portlet.supports.mime-type", "text/html ");
				}
			});
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private BundleContext _bundleContext;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private final ConcurrentHashMap<Long, ServiceRegistration<?>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();
	private UserLocalService _userLocalService;

}