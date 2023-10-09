/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.object.deployer;

import com.liferay.commerce.currency.constants.CurrencyRepositoryConstants;
import com.liferay.commerce.currency.internal.model.listener.util.ImportDefaultValuesUtil;
import com.liferay.commerce.currency.object.entity.CurrencyObjectEntity;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.repository.ObjectRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		if (!StringUtil.equals(objectDefinition.getName(), "C_Currency")) {
			return Collections.emptyList();
		}

		try {
			Company company = _companyLocalService.getCompany(
				objectDefinition.getCompanyId());

			int currenciesCount =
				_currencyObjectRepository.getObjectEntitiesCount(
					0, company.getCompanyId(), 0, null,
					CurrencyRepositoryConstants.FIND_BY_PRIMARY, true);

			if (currenciesCount == 0) {
				ImportDefaultValuesUtil.importDefaultValues(
					_commerceCurrencyLocalService, company);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return Collections.emptyList();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionDeployerImpl.class);

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ObjectRepository<CurrencyObjectEntity> _currencyObjectRepository;

}