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

package com.liferay.object.internal.events;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.util.SystemObjectDefinitionPayloadSerializer;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(property = "key=login.events.post", service = LifecycleAction.class)
public class LoginPostAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {

			httpServletRequest.getCookies();

			User user = _portal.getUser(httpServletRequest);

			Class<?> modelClass = user.getModelClass();

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					user.getCompanyId(), modelClass.getName());

			_objectActionEngine.executeObjectActions(
				modelClass.getName(), user.getCompanyId(), "onAfterLogin",
				_systemObjectDefinitionPayloadSerializer.serialize(
					modelClass, "onAfterLogin", objectDefinition, user, user,
					user.getUserId()),
				user.getUserId());
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;
//
	@Reference
	private Portal _portal;

	@Reference
	private SystemObjectDefinitionPayloadSerializer
		_systemObjectDefinitionPayloadSerializer;

}