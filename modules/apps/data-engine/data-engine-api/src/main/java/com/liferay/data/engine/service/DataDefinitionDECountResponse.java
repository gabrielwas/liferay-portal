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

import com.liferay.data.engine.executor.DECountResponse;

/**
 * @author Marcela Cunha
 */
public class DataDefinitionDECountResponse implements DECountResponse {

	public int getTotal() {
		return _total;
	}

	public static final class Builder {

		public static Builder newBuilder(int total) {
			return new Builder(total);
		}

		public static DataDefinitionDECountResponse of(int total) {
			return newBuilder(
				total
			).build();
		}

		public DataDefinitionDECountResponse build() {
			return _dataDefinitionDECountResponse;
		}

		private Builder(int total) {
			_dataDefinitionDECountResponse._total = total;
		}

		private final DataDefinitionDECountResponse
			_dataDefinitionDECountResponse =
				new DataDefinitionDECountResponse();

	}

	private DataDefinitionDECountResponse() {
	}

	private int _total;

}