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

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	public ObjectEntryModelPreFilterContributor(
		ModelPreFilterContributor workflowStatusModelPreFilterContributor) {

		_workflowStatusModelPreFilterContributor =
			workflowStatusModelPreFilterContributor;
	}

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long objectDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("objectDefinitionId"));

		long entryClassPK = GetterUtil.getLong(
			searchContext.getAttribute("entryClassPK"));

		if (entryClassPK > 0) {

			String relatedObjectClassName = (String)searchContext.getAttribute(
				"relatedObjectClassName");
			String relationshipName = (String)searchContext.getAttribute(
				"relationshipName");
			String relationshipType = (String)searchContext.getAttribute(
				"relationshipType");
			String pkObjectFieldName = (String)searchContext.getAttribute(
				"pkObjectFieldName");

			if (Objects.equals(relationshipType,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				booleanFilter.addRequiredTerm("relatedEntries.entryClassPK", entryClassPK);
				booleanFilter.addTerm("relatedEntries.relatedObjectClassName", relatedObjectClassName);
				booleanFilter.addTerm("relatedEntries.relationshipName", relationshipName);
			}else{
//				booleanFilter.addRequiredTerm("nestedFieldArray.fieldName", StringBundler.concat(
//					"r_", relationshipName, "_", pkObjectFieldName));


				BooleanQuery booleanQuery = new BooleanQueryImpl();
				booleanQuery.addRequiredTerm("nestedFieldArray.fieldName",StringBundler.concat(
					"r_", relationshipName, "_", pkObjectFieldName) );
				booleanQuery.addRequiredTerm("nestedFieldArray.value_long", entryClassPK );
				booleanFilter.add(new QueryFilter(new NestedQuery("nestedFieldArray", booleanQuery)), BooleanClauseOccur.MUST);

//				BooleanQuery booleanQuery = new BooleanQueryImpl();
//				BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();
//
//				try {
//
//					nestedBooleanQuery.add(
//						new TermQueryImpl("nestedFieldArray.value_long", String.valueOf(entryClassPK)),
//						BooleanClauseOccur.MUST);
//					nestedBooleanQuery.add(
//						new TermQueryImpl("nestedFieldArray.fieldName", StringBundler.concat(
//							"r_", relationshipName, "_", pkObjectFieldName)),
//						BooleanClauseOccur.MUST);
//					booleanQuery.add(
//						new NestedQuery("nestedFieldArray", nestedBooleanQuery),
//						BooleanClauseOccur.MUST);
//				}
//				catch (ParseException e) {
//					e.printStackTrace();
//				}
//
//				booleanFilter.add(new QueryFilter(booleanQuery), BooleanClauseOccur.MUST);


				//booleanFilter.addRequiredTerm("nestedFieldArray.value_long", entryClassPK);
			}

		}

		if (_log.isDebugEnabled()) {
			_log.debug("Object definition ID " + objectDefinitionId);
		}

		if (objectDefinitionId > 0) {
			booleanFilter.addRequiredTerm(
				"objectDefinitionId", objectDefinitionId);
		}

		_workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelPreFilterContributor.class);

	private final ModelPreFilterContributor
		_workflowStatusModelPreFilterContributor;

}