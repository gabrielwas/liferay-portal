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

package com.liferay.app.builder.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderApp}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderApp
 * @generated
 */
@ProviderType
public class AppBuilderAppWrapper
	extends BaseModelWrapper<AppBuilderApp>
	implements AppBuilderApp, ModelWrapper<AppBuilderApp> {

	public AppBuilderAppWrapper(AppBuilderApp appBuilderApp) {
		super(appBuilderApp);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("deDataLayoutId", getDeDataLayoutId());
		attributes.put("deDataListViewId", getDeDataListViewId());
		attributes.put("settings", getSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long appBuilderAppId = (Long)attributes.get("appBuilderAppId");

		if (appBuilderAppId != null) {
			setAppBuilderAppId(appBuilderAppId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long deDataLayoutId = (Long)attributes.get("deDataLayoutId");

		if (deDataLayoutId != null) {
			setDeDataLayoutId(deDataLayoutId);
		}

		Long deDataListViewId = (Long)attributes.get("deDataListViewId");

		if (deDataListViewId != null) {
			setDeDataListViewId(deDataListViewId);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}
	}

	/**
	 * Returns the app builder app ID of this app builder app.
	 *
	 * @return the app builder app ID of this app builder app
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	/**
	 * Returns the company ID of this app builder app.
	 *
	 * @return the company ID of this app builder app
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this app builder app.
	 *
	 * @return the create date of this app builder app
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the de data layout ID of this app builder app.
	 *
	 * @return the de data layout ID of this app builder app
	 */
	@Override
	public long getDeDataLayoutId() {
		return model.getDeDataLayoutId();
	}

	/**
	 * Returns the de data list view ID of this app builder app.
	 *
	 * @return the de data list view ID of this app builder app
	 */
	@Override
	public long getDeDataListViewId() {
		return model.getDeDataListViewId();
	}

	/**
	 * Returns the group ID of this app builder app.
	 *
	 * @return the group ID of this app builder app
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this app builder app.
	 *
	 * @return the modified date of this app builder app
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this app builder app.
	 *
	 * @return the primary key of this app builder app
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings of this app builder app.
	 *
	 * @return the settings of this app builder app
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	/**
	 * Returns the user ID of this app builder app.
	 *
	 * @return the user ID of this app builder app
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this app builder app.
	 *
	 * @return the user name of this app builder app
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this app builder app.
	 *
	 * @return the user uuid of this app builder app
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this app builder app.
	 *
	 * @return the uuid of this app builder app
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app builder app ID of this app builder app.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the company ID of this app builder app.
	 *
	 * @param companyId the company ID of this app builder app
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this app builder app.
	 *
	 * @param createDate the create date of this app builder app
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the de data layout ID of this app builder app.
	 *
	 * @param deDataLayoutId the de data layout ID of this app builder app
	 */
	@Override
	public void setDeDataLayoutId(long deDataLayoutId) {
		model.setDeDataLayoutId(deDataLayoutId);
	}

	/**
	 * Sets the de data list view ID of this app builder app.
	 *
	 * @param deDataListViewId the de data list view ID of this app builder app
	 */
	@Override
	public void setDeDataListViewId(long deDataListViewId) {
		model.setDeDataListViewId(deDataListViewId);
	}

	/**
	 * Sets the group ID of this app builder app.
	 *
	 * @param groupId the group ID of this app builder app
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this app builder app.
	 *
	 * @param modifiedDate the modified date of this app builder app
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this app builder app.
	 *
	 * @param primaryKey the primary key of this app builder app
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this app builder app.
	 *
	 * @param settings the settings of this app builder app
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * Sets the user ID of this app builder app.
	 *
	 * @param userId the user ID of this app builder app
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this app builder app.
	 *
	 * @param userName the user name of this app builder app
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this app builder app.
	 *
	 * @param userUuid the user uuid of this app builder app
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this app builder app.
	 *
	 * @param uuid the uuid of this app builder app
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AppBuilderAppWrapper wrap(AppBuilderApp appBuilderApp) {
		return new AppBuilderAppWrapper(appBuilderApp);
	}

}