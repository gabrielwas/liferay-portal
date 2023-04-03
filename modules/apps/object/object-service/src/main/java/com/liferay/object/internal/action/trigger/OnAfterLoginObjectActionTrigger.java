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

package com.liferay.object.internal.action.trigger;

import com.liferay.object.action.trigger.ObjectActionTrigger;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"object.action.trigger.class.name=com.liferay.portal.kernel.model.User",
		"object.action.trigger.key=OnAfterLogin"
	},
	service = ObjectActionTrigger.class
)
public class OnAfterLoginObjectActionTrigger implements ObjectActionTrigger {

	@Override
	public String getKey() {
		return "onAfterLogin";
	}

}