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

package com.liferay.notification.admin.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationAdminTemplateLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationAdminTemplateLocalService
 * @generated
 */
public class NotificationAdminTemplateLocalServiceWrapper
	implements NotificationAdminTemplateLocalService,
			   ServiceWrapper<NotificationAdminTemplateLocalService> {

	public NotificationAdminTemplateLocalServiceWrapper() {
		this(null);
	}

	public NotificationAdminTemplateLocalServiceWrapper(
		NotificationAdminTemplateLocalService
			notificationAdminTemplateLocalService) {

		_notificationAdminTemplateLocalService =
			notificationAdminTemplateLocalService;
	}

	/**
	 * Adds the notification admin template to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationAdminTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationAdminTemplate the notification admin template
	 * @return the notification admin template that was added
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		addNotificationAdminTemplate(
			com.liferay.notification.admin.model.NotificationAdminTemplate
				notificationAdminTemplate) {

		return _notificationAdminTemplateLocalService.
			addNotificationAdminTemplate(notificationAdminTemplate);
	}

	/**
	 * Creates a new notification admin template with the primary key. Does not add the notification admin template to the database.
	 *
	 * @param notificationAdminTemplateId the primary key for the new notification admin template
	 * @return the new notification admin template
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		createNotificationAdminTemplate(long notificationAdminTemplateId) {

		return _notificationAdminTemplateLocalService.
			createNotificationAdminTemplate(notificationAdminTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the notification admin template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationAdminTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationAdminTemplateId the primary key of the notification admin template
	 * @return the notification admin template that was removed
	 * @throws PortalException if a notification admin template with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
			deleteNotificationAdminTemplate(long notificationAdminTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.
			deleteNotificationAdminTemplate(notificationAdminTemplateId);
	}

	/**
	 * Deletes the notification admin template from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationAdminTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationAdminTemplate the notification admin template
	 * @return the notification admin template that was removed
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		deleteNotificationAdminTemplate(
			com.liferay.notification.admin.model.NotificationAdminTemplate
				notificationAdminTemplate) {

		return _notificationAdminTemplateLocalService.
			deleteNotificationAdminTemplate(notificationAdminTemplate);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationAdminTemplateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationAdminTemplateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationAdminTemplateLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationAdminTemplateLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _notificationAdminTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _notificationAdminTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationAdminTemplateLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _notificationAdminTemplateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		fetchNotificationAdminTemplate(long notificationAdminTemplateId) {

		return _notificationAdminTemplateLocalService.
			fetchNotificationAdminTemplate(notificationAdminTemplateId);
	}

	/**
	 * Returns the notification admin template with the matching UUID and company.
	 *
	 * @param uuid the notification admin template's UUID
	 * @param companyId the primary key of the company
	 * @return the matching notification admin template, or <code>null</code> if a matching notification admin template could not be found
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		fetchNotificationAdminTemplateByUuidAndCompanyId(
			String uuid, long companyId) {

		return _notificationAdminTemplateLocalService.
			fetchNotificationAdminTemplateByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationAdminTemplateLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _notificationAdminTemplateLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationAdminTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification admin template with the primary key.
	 *
	 * @param notificationAdminTemplateId the primary key of the notification admin template
	 * @return the notification admin template
	 * @throws PortalException if a notification admin template with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
			getNotificationAdminTemplate(long notificationAdminTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.
			getNotificationAdminTemplate(notificationAdminTemplateId);
	}

	/**
	 * Returns the notification admin template with the matching UUID and company.
	 *
	 * @param uuid the notification admin template's UUID
	 * @param companyId the primary key of the company
	 * @return the matching notification admin template
	 * @throws PortalException if a matching notification admin template could not be found
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
			getNotificationAdminTemplateByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.
			getNotificationAdminTemplateByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the notification admin templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationAdminTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification admin templates
	 * @param end the upper bound of the range of notification admin templates (not inclusive)
	 * @return the range of notification admin templates
	 */
	@Override
	public java.util.List
		<com.liferay.notification.admin.model.NotificationAdminTemplate>
			getNotificationAdminTemplates(int start, int end) {

		return _notificationAdminTemplateLocalService.
			getNotificationAdminTemplates(start, end);
	}

	/**
	 * Returns the number of notification admin templates.
	 *
	 * @return the number of notification admin templates
	 */
	@Override
	public int getNotificationAdminTemplatesCount() {
		return _notificationAdminTemplateLocalService.
			getNotificationAdminTemplatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationAdminTemplateLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationAdminTemplateLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the notification admin template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationAdminTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationAdminTemplate the notification admin template
	 * @return the notification admin template that was updated
	 */
	@Override
	public com.liferay.notification.admin.model.NotificationAdminTemplate
		updateNotificationAdminTemplate(
			com.liferay.notification.admin.model.NotificationAdminTemplate
				notificationAdminTemplate) {

		return _notificationAdminTemplateLocalService.
			updateNotificationAdminTemplate(notificationAdminTemplate);
	}

	@Override
	public NotificationAdminTemplateLocalService getWrappedService() {
		return _notificationAdminTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationAdminTemplateLocalService
			notificationAdminTemplateLocalService) {

		_notificationAdminTemplateLocalService =
			notificationAdminTemplateLocalService;
	}

	private NotificationAdminTemplateLocalService
		_notificationAdminTemplateLocalService;

}