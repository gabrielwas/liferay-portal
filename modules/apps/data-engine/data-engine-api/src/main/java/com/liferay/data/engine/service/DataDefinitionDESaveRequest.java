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

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DESaveRequest;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.model.DEDataDefinition;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionDESaveRequest implements DESaveRequest {

	@Override
	public DataDefinitionDESaveResponse accept(
			DESaveRequestExecutor deSaveRequestExecutor)
		throws DEDataDefinitionException {

		return deSaveRequestExecutor.executeSaveRequest(this);
	}

	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public Builder(DEDataDefinition deDataDefinition) {
			_dataDefinitionDESaveRequest._deDataDefinition = deDataDefinition;
		}

		public DataDefinitionDESaveRequest build() {
			return _dataDefinitionDESaveRequest;
		}

		public Builder inGroup(long groupId) {
			_dataDefinitionDESaveRequest._groupId = groupId;

			return this;
		}

		public Builder onBehalfOf(long userId) {
			_dataDefinitionDESaveRequest._userId = userId;

			return this;
		}

		private final DataDefinitionDESaveRequest _dataDefinitionDESaveRequest =
			new DataDefinitionDESaveRequest();

	}

	private DataDefinitionDESaveRequest() {
	}

	private DEDataDefinition _deDataDefinition;
	private long _groupId;
	private long _userId;

}