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

package com.liferay.notification.admin.service.persistence;

import com.liferay.notification.admin.exception.NoSuchNotificationAdminTemplateException;
import com.liferay.notification.admin.model.NotificationAdminTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification admin template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationAdminTemplateUtil
 * @generated
 */
@ProviderType
public interface NotificationAdminTemplatePersistence
	extends BasePersistence<NotificationAdminTemplate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationAdminTemplateUtil} to access the notification admin template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification admin templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid(String uuid);

	/**
	 * Returns a range of all the notification admin templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @return the range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notification admin templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the notification admin templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification admin template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Returns the first notification admin template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification admin template, or <code>null</code> if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns the last notification admin template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Returns the last notification admin template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification admin template, or <code>null</code> if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns the notification admin templates before and after the current notification admin template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationAdminTemplateId the primary key of the current notification admin template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a notification admin template with the primary key could not be found
	 */
	public NotificationAdminTemplate[] findByUuid_PrevAndNext(
			long notificationAdminTemplateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Removes all the notification admin templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of notification admin templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification admin templates
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the notification admin templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notification admin templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @return the range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notification admin templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the notification admin templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification admin template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Returns the first notification admin template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification admin template, or <code>null</code> if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns the last notification admin template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Returns the last notification admin template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification admin template, or <code>null</code> if a matching notification admin template could not be found
	 */
	public NotificationAdminTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns the notification admin templates before and after the current notification admin template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationAdminTemplateId the primary key of the current notification admin template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a notification admin template with the primary key could not be found
	 */
	public NotificationAdminTemplate[] findByUuid_C_PrevAndNext(
			long notificationAdminTemplateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationAdminTemplate> orderByComparator)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Removes all the notification admin templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notification admin templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification admin templates
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the notification admin template in the entity cache if it is enabled.
	 *
	 * @param notificationAdminTemplate the notification admin template
	 */
	public void cacheResult(
		NotificationAdminTemplate notificationAdminTemplate);

	/**
	 * Caches the notification admin templates in the entity cache if it is enabled.
	 *
	 * @param notificationAdminTemplates the notification admin templates
	 */
	public void cacheResult(
		java.util.List<NotificationAdminTemplate> notificationAdminTemplates);

	/**
	 * Creates a new notification admin template with the primary key. Does not add the notification admin template to the database.
	 *
	 * @param notificationAdminTemplateId the primary key for the new notification admin template
	 * @return the new notification admin template
	 */
	public NotificationAdminTemplate create(long notificationAdminTemplateId);

	/**
	 * Removes the notification admin template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationAdminTemplateId the primary key of the notification admin template
	 * @return the notification admin template that was removed
	 * @throws NoSuchNotificationAdminTemplateException if a notification admin template with the primary key could not be found
	 */
	public NotificationAdminTemplate remove(long notificationAdminTemplateId)
		throws NoSuchNotificationAdminTemplateException;

	public NotificationAdminTemplate updateImpl(
		NotificationAdminTemplate notificationAdminTemplate);

	/**
	 * Returns the notification admin template with the primary key or throws a <code>NoSuchNotificationAdminTemplateException</code> if it could not be found.
	 *
	 * @param notificationAdminTemplateId the primary key of the notification admin template
	 * @return the notification admin template
	 * @throws NoSuchNotificationAdminTemplateException if a notification admin template with the primary key could not be found
	 */
	public NotificationAdminTemplate findByPrimaryKey(
			long notificationAdminTemplateId)
		throws NoSuchNotificationAdminTemplateException;

	/**
	 * Returns the notification admin template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationAdminTemplateId the primary key of the notification admin template
	 * @return the notification admin template, or <code>null</code> if a notification admin template with the primary key could not be found
	 */
	public NotificationAdminTemplate fetchByPrimaryKey(
		long notificationAdminTemplateId);

	/**
	 * Returns all the notification admin templates.
	 *
	 * @return the notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findAll();

	/**
	 * Returns a range of all the notification admin templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @return the range of notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the notification admin templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the notification admin templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification admin templates
	 */
	public java.util.List<NotificationAdminTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationAdminTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification admin templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification admin templates.
	 *
	 * @return the number of notification admin templates
	 */
	public int countAll();

}