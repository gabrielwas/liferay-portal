/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blade.basic.model.impl;

import com.liferay.blade.basic.model.Flight;
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
 * The cache model class for representing Flight in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FlightCacheModel
	implements CacheModel<Flight>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FlightCacheModel)) {
			return false;
		}

		FlightCacheModel flightCacheModel = (FlightCacheModel)object;

		if ((flightId == flightCacheModel.flightId) &&
			(mvccVersion == flightCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, flightId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", flightId=");
		sb.append(flightId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", flightNumber=");
		sb.append(flightNumber);
		sb.append(", active=");
		sb.append(active);
		sb.append(", capacity=");
		sb.append(capacity);
		sb.append(", flightDate=");
		sb.append(flightDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Flight toEntityModel() {
		FlightImpl flightImpl = new FlightImpl();

		flightImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			flightImpl.setUuid("");
		}
		else {
			flightImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			flightImpl.setExternalReferenceCode("");
		}
		else {
			flightImpl.setExternalReferenceCode(externalReferenceCode);
		}

		flightImpl.setFlightId(flightId);
		flightImpl.setGroupId(groupId);
		flightImpl.setCompanyId(companyId);
		flightImpl.setUserId(userId);

		if (userName == null) {
			flightImpl.setUserName("");
		}
		else {
			flightImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			flightImpl.setCreateDate(null);
		}
		else {
			flightImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			flightImpl.setModifiedDate(null);
		}
		else {
			flightImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (flightNumber == null) {
			flightImpl.setFlightNumber("");
		}
		else {
			flightImpl.setFlightNumber(flightNumber);
		}

		flightImpl.setActive(active);
		flightImpl.setCapacity(capacity);

		if (flightDate == Long.MIN_VALUE) {
			flightImpl.setFlightDate(null);
		}
		else {
			flightImpl.setFlightDate(new Date(flightDate));
		}

		flightImpl.resetOriginalValues();

		return flightImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		flightId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		flightNumber = objectInput.readUTF();

		active = objectInput.readBoolean();

		capacity = objectInput.readInt();
		flightDate = objectInput.readLong();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(flightId);

		objectOutput.writeLong(groupId);

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

		if (flightNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(flightNumber);
		}

		objectOutput.writeBoolean(active);

		objectOutput.writeInt(capacity);
		objectOutput.writeLong(flightDate);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long flightId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String flightNumber;
	public boolean active;
	public int capacity;
	public long flightDate;

}