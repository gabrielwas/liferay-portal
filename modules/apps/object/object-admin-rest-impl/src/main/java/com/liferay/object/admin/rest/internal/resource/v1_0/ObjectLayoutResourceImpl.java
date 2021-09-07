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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectLayoutResource.class
)
public class ObjectLayoutResourceImpl extends BaseObjectLayoutResourceImpl {

	@Override
	public Page<ObjectLayout> getObjectDefinitionObjectLayoutsPage(
		Long objectDefinitionId, Pagination pagination) {

		_objectLayoutLocalService.getObjectLayouts(
			objectDefinitionId, pagination.getStartPosition(),
			pagination.getEndPosition());

		return Page.of(
			transform(
				_objectLayoutLocalService.getObjectLayouts(
					objectDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toObjectLayout),
			pagination,
			_objectLayoutLocalService.getObjectLayoutsCount(
				objectDefinitionId));
	}

	@Override
	public ObjectLayout postObjectDefinitionObjectLayout(
			Long objectDefinitionId, ObjectLayout objectLayout)
		throws Exception {

		return _toObjectLayout(
			_objectLayoutLocalService.addObjectLayout(
				contextUser.getUserId(), objectDefinitionId,
				objectLayout.getDefaultObjectLayout(),
				LocalizedMapUtil.getLocalizedMap(objectLayout.getName())));
	}

	private ObjectLayout _toObjectLayout(
		com.liferay.object.model.ObjectLayout serviceBuilderObjectLayout) {

		return new ObjectLayout() {
			{
				dateCreated = serviceBuilderObjectLayout.getCreateDate();
				dateModified = serviceBuilderObjectLayout.getModifiedDate();
				defaultObjectLayout =
					serviceBuilderObjectLayout.getDefaultObjectLayout();
				id = serviceBuilderObjectLayout.getObjectLayoutId();
				name = LocalizedMapUtil.getI18nMap(
					serviceBuilderObjectLayout.getNameMap());
				objectDefinitionId =
					serviceBuilderObjectLayout.getObjectDefinitionId();
			}
		};
	}

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

}