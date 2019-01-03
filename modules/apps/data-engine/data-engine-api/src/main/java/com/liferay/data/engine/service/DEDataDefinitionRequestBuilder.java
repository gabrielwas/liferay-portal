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

import com.liferay.data.engine.model.DEDataDefinition;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionRequestBuilder {

	public static DataDefinitionDECountRequest.Builder countBuilder() {
		return new DataDefinitionDECountRequest.Builder();
	}

	public static DataDefinitionDEDeleteRequest.Builder deleteBuilder() {
		return new DataDefinitionDEDeleteRequest.Builder();
	}

	public static DataDefinitionDEGetRequest.Builder getBuilder() {
		return new DataDefinitionDEGetRequest.Builder();
	}

	public static DataDefinitionDEListRequest.Builder listBuilder() {
		return new DataDefinitionDEListRequest.Builder();
	}

	public static DataDefinitionDESaveRequest.Builder saveBuilder(
		DEDataDefinition deDataDefinition) {

		return new DataDefinitionDESaveRequest.Builder(deDataDefinition);
	}

}