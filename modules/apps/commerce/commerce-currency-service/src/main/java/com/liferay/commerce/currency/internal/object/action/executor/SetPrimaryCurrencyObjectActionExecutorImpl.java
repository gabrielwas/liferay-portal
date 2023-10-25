/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.object.action.executor;

import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = ObjectActionExecutor.class)
public class SetPrimaryCurrencyObjectActionExecutorImpl
	implements ObjectActionExecutor, ObjectDefinitionScoped {

	@Override
	public void execute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_commerceCurrencyLocalService.setPrimary(
					payloadJSONObject.getLong("classPK"), true);

				return null;
			});
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return Arrays.asList("C_Currency");
	}

	@Override
	public String getKey() {
		return "set-primary";
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

}