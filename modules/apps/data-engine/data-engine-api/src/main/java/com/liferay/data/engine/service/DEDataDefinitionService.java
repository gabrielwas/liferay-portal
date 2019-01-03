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

package com.liferay.data.engine.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.data.engine.exception.DEDataDefinitionException;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DEDataDefinitionService {

	public DataDefinitionDECountResponse execute(
			DataDefinitionDECountRequest dataDefinitionDECountRequest)
		throws DEDataDefinitionException;

	public DataDefinitionDEDeleteResponse execute(
			DataDefinitionDEDeleteRequest dataDefinitionDEDeleteRequest)
		throws DEDataDefinitionException;

	public DataDefinitionDEGetResponse execute(
			DataDefinitionDEGetRequest dataDefinitionDEGetRequest)
		throws DEDataDefinitionException;

	public DataDefinitionDEListResponse execute(
			DataDefinitionDEListRequest dataDefinitionDEListRequest)
		throws DEDataDefinitionException;

	public DataDefinitionDESaveResponse execute(
			DataDefinitionDESaveRequest dataDefinitionDESaveRequest)
		throws DEDataDefinitionException;

}