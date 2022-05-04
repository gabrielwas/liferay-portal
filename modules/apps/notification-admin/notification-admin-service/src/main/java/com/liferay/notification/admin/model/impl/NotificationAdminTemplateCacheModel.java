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

package com.liferay.notification.admin.model.impl;

import com.liferay.notification.admin.model.NotificationAdminTemplate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing NotificationAdminTemplate in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationAdminTemplateCacheModel
	implements CacheModel<NotificationAdminTemplate>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationAdminTemplateCacheModel)) {
			return false;
		}

		NotificationAdminTemplateCacheModel
			notificationAdminTemplateCacheModel =
				(NotificationAdminTemplateCacheModel)object;

		if ((notificationAdminTemplateId ==
				notificationAdminTemplateCacheModel.
					notificationAdminTemplateId) &&
			(mvccVersion == notificationAdminTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationAdminTemplateId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", notificationAdminTemplateId=");
		sb.append(notificationAdminTemplateId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationAdminTemplate toEntityModel() {
		NotificationAdminTemplateImpl notificationAdminTemplateImpl =
			new NotificationAdminTemplateImpl();

		notificationAdminTemplateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			notificationAdminTemplateImpl.setUuid("");
		}
		else {
			notificationAdminTemplateImpl.setUuid(uuid);
		}

		notificationAdminTemplateImpl.setNotificationAdminTemplateId(
			notificationAdminTemplateId);
		notificationAdminTemplateImpl.setCompanyId(companyId);
		notificationAdminTemplateImpl.setUserId(userId);

		if (userName == null) {
			notificationAdminTemplateImpl.setUserName("");
		}
		else {
			notificationAdminTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationAdminTemplateImpl.setCreateDate(null);
		}
		else {
			notificationAdminTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationAdminTemplateImpl.setModifiedDate(null);
		}
		else {
			notificationAdminTemplateImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			notificationAdminTemplateImpl.setName("");
		}
		else {
			notificationAdminTemplateImpl.setName(name);
		}

		notificationAdminTemplateImpl.resetOriginalValues();

		return notificationAdminTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		notificationAdminTemplateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(notificationAdminTemplateId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long notificationAdminTemplateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;

}