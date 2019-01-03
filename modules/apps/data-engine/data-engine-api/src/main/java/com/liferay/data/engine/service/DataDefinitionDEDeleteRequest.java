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
import com.liferay.data.engine.executor.DEDeleteRequest;
import com.liferay.data.engine.executor.DEDeleteRequestExecutor;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionDEDeleteRequest implements DEDeleteRequest {

	@Override
	public DataDefinitionDEDeleteResponse accept(
			DEDeleteRequestExecutor deDeleteRequestExecutor)
		throws DEDataDefinitionException {

		return deDeleteRequestExecutor.executeDeleteRequest(this);
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public static final class Builder {

		public DataDefinitionDEDeleteRequest build() {
			return _dataDefinitionDEDeleteRequest;
		}

		public Builder byId(long deDataDefinitionId) {
			_dataDefinitionDEDeleteRequest._deDataDefinitionId =
				deDataDefinitionId;

			return this;
		}

		private final DataDefinitionDEDeleteRequest
			_dataDefinitionDEDeleteRequest =
				new DataDefinitionDEDeleteRequest();

	}

	private DataDefinitionDEDeleteRequest() {
	}

	private long _deDataDefinitionId;

}