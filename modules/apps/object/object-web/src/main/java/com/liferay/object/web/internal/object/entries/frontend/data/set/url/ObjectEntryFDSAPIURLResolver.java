/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.frontend.data.set.url;

import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectEntryFDSAPIURLResolver implements FDSAPIURLResolver{

	public ObjectEntryFDSAPIURLResolver(String schema){
		_schema = schema;
	}

	@Override
	public String getSchema() {
		return _schema;
	}

	@Override
	public String resolve(String baseURL, HttpServletRequest httpServletRequest)
		throws PortalException {

		Object object = httpServletRequest.getAttribute("INFO_ITEM");

		ObjectEntry objectEntry =
			object instanceof ObjectEntry ? (ObjectEntry)object : null;

		String currentExternalReferenceCode = StringPool.BLANK;

		if (objectEntry != null) {
			currentExternalReferenceCode = objectEntry.getExternalReferenceCode();
		}

		return StringUtil.replace(
			baseURL, new String[] {"{currentExternalReferenceCode}"},
			new String[] {String.valueOf(currentExternalReferenceCode)});
	}

	String _schema;

}
