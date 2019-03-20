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

import com.liferay.data.engine.model.DEDataLayout;

import java.util.List;

/**
 * @author Marcelo Mello
 */
public class DEDataLayoutSearchResponse {

	public List<DEDataLayout> getDeDataLayouts() {
		return _deDataLayouts;
	}

	public static final class Builder {

		public static Builder newBuilder(List<DEDataLayout> deDataLayouts) {
			return new Builder(deDataLayouts);
		}

		public static DEDataLayoutSearchResponse of(
			List<DEDataLayout> deDataLayouts) {

			return newBuilder(
				deDataLayouts
			).build();
		}

		public DEDataLayoutSearchResponse build() {
			return _deDataLayoutSearchResponse;
		}

		private Builder(List<DEDataLayout> deDataLayouts) {
			_deDataLayoutSearchResponse._deDataLayouts = deDataLayouts;
		}

		private final DEDataLayoutSearchResponse _deDataLayoutSearchResponse =
			new DEDataLayoutSearchResponse();

	}

	private DEDataLayoutSearchResponse() {
	}

	private List<DEDataLayout> _deDataLayouts;

}