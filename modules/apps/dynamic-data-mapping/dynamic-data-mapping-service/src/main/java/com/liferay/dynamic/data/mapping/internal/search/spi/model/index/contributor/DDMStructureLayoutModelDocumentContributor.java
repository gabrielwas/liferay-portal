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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marcelo Mello
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureLayout",
	service = ModelDocumentContributor.class
)
public class DDMStructureLayoutModelDocumentContributor
	implements ModelDocumentContributor<DDMStructureLayout> {

	@Override
	public void contribute(Document document, DDMStructureLayout ddmStructureLayout) {

		DDMFormLayout ddmFormLayout = ddmStructureLayout.getDDMFormLayout();

		document.addKeyword(Field.CLASS_NAME_ID,
			classNameLocalService.getClassNameId(DDMStructureLayout.class));

		document.addKeyword(Field.COMPANY_ID,
			ddmStructureLayout.getCompanyId());

		document.addKeyword(Field.CLASS_PK,
			ddmStructureLayout.getStructureLayoutId());

		document.addKeyword("description", ddmStructureLayout.getDescription());
		document.addKeyword(Field.GROUP_ID, ddmStructureLayout.getGroupId());
		document.addKeyword("name", ddmStructureLayout.getName());

		document.addKeyword("structureLayoutId",
			ddmStructureLayout.getStructureLayoutId());

		document.addKeyword("structureVersionId",
			ddmStructureLayout.getStructureVersionId());

		document.addKeyword(Field.USER_ID, ddmStructureLayout.getUserId());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

}