/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.blade.basic.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFlightExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateFlightExternalReferenceCodeException() {
	}

	public DuplicateFlightExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateFlightExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateFlightExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}