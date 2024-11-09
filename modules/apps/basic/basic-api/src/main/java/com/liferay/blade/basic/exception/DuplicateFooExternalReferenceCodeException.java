/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.blade.basic.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFooExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateFooExternalReferenceCodeException() {
	}

	public DuplicateFooExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateFooExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateFooExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}