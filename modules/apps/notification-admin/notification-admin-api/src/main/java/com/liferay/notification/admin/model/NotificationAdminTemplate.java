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

package com.liferay.notification.admin.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the NotificationAdminTemplate service. Represents a row in the &quot;NotificationAdminTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see NotificationAdminTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.notification.admin.model.impl.NotificationAdminTemplateImpl"
)
@ProviderType
public interface NotificationAdminTemplate
	extends NotificationAdminTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.notification.admin.model.impl.NotificationAdminTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<NotificationAdminTemplate, Long>
		NOTIFICATION_ADMIN_TEMPLATE_ID_ACCESSOR =
			new Accessor<NotificationAdminTemplate, Long>() {

				@Override
				public Long get(
					NotificationAdminTemplate notificationAdminTemplate) {

					return notificationAdminTemplate.
						getNotificationAdminTemplateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<NotificationAdminTemplate> getTypeClass() {
					return NotificationAdminTemplate.class;
				}

			};

}