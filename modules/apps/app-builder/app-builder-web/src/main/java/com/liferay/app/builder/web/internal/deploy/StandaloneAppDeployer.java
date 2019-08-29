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
import com.liferay.app.builder.web.internal.layout.type.AppPortletLayoutTypeController;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

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
			key -> Optional.ofNullable(
				_deployAppPortlet(
					"App" + appId, appBuilderApp.getCompanyId(),
					AppBuilderPortletKeys.STANDALONE_APP + "_" + appId)));

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.DEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		Optional<ServiceRegistration<?>> serviceRegistration =
			_serviceRegistrationsMap.remove(appId);

		if (serviceRegistration == null) {
			return;
		}

		serviceRegistration.ifPresent(s -> s.unregister());

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

	protected Group addFormsGroup(long companyId, String appGroupName)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), appGroupName);

		return _groupLocalService.addGroup(
			_userLocalService.getDefaultUserId(companyId),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null,
			GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			"/" + StringUtil.toLowerCase(appGroupName), false, false, true,
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
			StringPool.BLANK, StringPool.BLANK, portletName, true, null,
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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.app.builder.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected ServletContext servletContext;

	private ServiceRegistration<?> _deployAppPortlet(
		String appGroupName, long companyId, String portletName) {

		Group group = _groupLocalService.fetchFriendlyURLGroup(
			companyId, "/" + StringUtil.toLowerCase(appGroupName));

		if (group == null) {
			try {
				group = addFormsGroup(companyId, appGroupName);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);

				return null;
			}
		}
		else {
			group.setActive(true);

			_groupLocalService.updateGroup(group);
		}

		if (Validator.isNull(
				_layoutLocalService.fetchDefaultLayout(
					group.getGroupId(), false))) {

			try {
				addPublicLayout(companyId, group.getGroupId(), portletName);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);

				return null;
			}
		}

		return _bundleContext.registerService(
			LayoutTypeController.class,
			new AppPortletLayoutTypeController(servletContext),
			new HashMapDictionary<String, Object>() {
				{
					put("layout.type", portletName);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StandaloneAppDeployer.class);

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private BundleContext _bundleContext;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private final ConcurrentHashMap<Long, Optional<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();
	private UserLocalService _userLocalService;

}