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

package com.liferay.data.engine.rest.internal.content.type;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.DataEngineNativeObjectField;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = DataEngineNativeObject.class)
public class ImplementNativeDE implements DataEngineNativeObject {

	@Override
	public String getClassName() {
		return "NO Class Name Data Engine ";
	}

	@Override
	public List<DataEngineNativeObjectField> getDataEngineNativeObjectFields() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return "NO Name Data Engine";
	}

}