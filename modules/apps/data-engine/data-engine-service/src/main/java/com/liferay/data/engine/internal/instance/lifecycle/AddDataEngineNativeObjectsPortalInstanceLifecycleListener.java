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

package com.liferay.data.engine.internal.instance.lifecycle;

import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	configurationPid = "com.liferay.data.engine.internal.configuration.DataEngineConfiguration",
	immediate = true, service = PortalInstanceLifecycleListener.class
)
public class AddDataEngineNativeObjectsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		//	for (DataEngineNativeObject dataEngineNativeObject :
		//		_dataEngineNativeObjectTracker.getDataEngineNativeObjects()) {
		//
		//		_dataEngineNativeObjectTracker.createDataEngineNativeObject(
		//			company.getCompanyId(), dataEngineNativeObject);
		//		}
	}

	@Reference
	private DataEngineNativeObjectTracker _dataEngineNativeObjectTracker;

}