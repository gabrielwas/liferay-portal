/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	public ObjectEntryModelPreFilterContributor(
		AccountEntryLocalService accountEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ModelPreFilterContributor workflowStatusModelPreFilterContributor) {

		_accountEntryLocalService = accountEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_workflowStatusModelPreFilterContributor =
			workflowStatusModelPreFilterContributor;
	}

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long objectDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("objectDefinitionId"));

		if (_log.isDebugEnabled()) {
			_log.debug("Object definition ID " + objectDefinitionId);
		}

		if (objectDefinitionId > 0) {

			booleanFilter.addRequiredTerm(
				"objectDefinitionId", objectDefinitionId);
		}

		boolean accountEntryRestricted = true;

		// 0 - Take the objectDefinitionId from somewhere
		// 1 - Make it work with organization latter
		// 2 - Fill the accountEntryIds inside ContextSXPParameterContributor

//		try {
//			ObjectDefinition objectDefinition =
//				_objectDefinitionLocalService.getObjectDefinition(
//					objectDefinitionId);
//
//			accountEntryRestricted =
//				objectDefinition.isAccountEntryRestricted();
//		}
//		catch (PortalException portalException) {
//			throw new RuntimeException(portalException);
//		}

		if (accountEntryRestricted) {

			BooleanFilter accountRestrictedBooleanFilter = new BooleanFilter();

			BooleanFilter accountEntryIdsBooleanFilter = new BooleanFilter();

			long[] accountEntryIds;

			try {
				accountEntryIds = ListUtil.toLongArray(
					_accountEntryLocalService.getUserAccountEntries(
						searchContext.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
						null,
						AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES,
						WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					AccountEntry::getAccountEntryId);
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}

//			long[] accountEntryIds = (long[])searchContext.getAttribute(
//				"accountEntryIds");

			for (Long accountEntryId : accountEntryIds) {
				Filter filter = new TermFilter(
					"accountEntryId", String.valueOf(accountEntryId));

				accountEntryIdsBooleanFilter.add(
					filter, BooleanClauseOccur.SHOULD);
			}

			accountRestrictedBooleanFilter.add(
				accountEntryIdsBooleanFilter, BooleanClauseOccur.MUST);

			accountRestrictedBooleanFilter.addTerm(
				"isAccountRestricted", Boolean.TRUE.toString(),
				BooleanClauseOccur.MUST);

			booleanFilter.add(
				accountRestrictedBooleanFilter, BooleanClauseOccur.MUST);
		}

		_workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelPreFilterContributor.class);

	private final AccountEntryLocalService _accountEntryLocalService;

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ModelPreFilterContributor
		_workflowStatusModelPreFilterContributor;

}