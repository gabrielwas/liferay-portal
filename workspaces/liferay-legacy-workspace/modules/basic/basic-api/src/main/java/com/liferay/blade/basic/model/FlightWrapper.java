/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blade.basic.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Flight}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Flight
 * @generated
 */
public class FlightWrapper
	extends BaseModelWrapper<Flight> implements Flight, ModelWrapper<Flight> {

	public FlightWrapper(Flight flight) {
		super(flight);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("flightId", getFlightId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("flightNumber", getFlightNumber());
		attributes.put("active", isActive());
		attributes.put("capacity", getCapacity());
		attributes.put("flightDate", getFlightDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long flightId = (Long)attributes.get("flightId");

		if (flightId != null) {
			setFlightId(flightId);
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

		String flightNumber = (String)attributes.get("flightNumber");

		if (flightNumber != null) {
			setFlightNumber(flightNumber);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Integer capacity = (Integer)attributes.get("capacity");

		if (capacity != null) {
			setCapacity(capacity);
		}

		Date flightDate = (Date)attributes.get("flightDate");

		if (flightDate != null) {
			setFlightDate(flightDate);
		}
	}

	@Override
	public Flight cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this flight.
	 *
	 * @return the active of this flight
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the capacity of this flight.
	 *
	 * @return the capacity of this flight
	 */
	@Override
	public int getCapacity() {
		return model.getCapacity();
	}

	/**
	 * Returns the company ID of this flight.
	 *
	 * @return the company ID of this flight
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this flight.
	 *
	 * @return the create date of this flight
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this flight.
	 *
	 * @return the external reference code of this flight
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the flight date of this flight.
	 *
	 * @return the flight date of this flight
	 */
	@Override
	public Date getFlightDate() {
		return model.getFlightDate();
	}

	/**
	 * Returns the flight ID of this flight.
	 *
	 * @return the flight ID of this flight
	 */
	@Override
	public long getFlightId() {
		return model.getFlightId();
	}

	/**
	 * Returns the flight number of this flight.
	 *
	 * @return the flight number of this flight
	 */
	@Override
	public String getFlightNumber() {
		return model.getFlightNumber();
	}

	/**
	 * Returns the group ID of this flight.
	 *
	 * @return the group ID of this flight
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this flight.
	 *
	 * @return the modified date of this flight
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this flight.
	 *
	 * @return the mvcc version of this flight
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this flight.
	 *
	 * @return the primary key of this flight
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this flight.
	 *
	 * @return the user ID of this flight
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this flight.
	 *
	 * @return the user name of this flight
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this flight.
	 *
	 * @return the user uuid of this flight
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this flight.
	 *
	 * @return the uuid of this flight
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this flight is active.
	 *
	 * @return <code>true</code> if this flight is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this flight is active.
	 *
	 * @param active the active of this flight
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the capacity of this flight.
	 *
	 * @param capacity the capacity of this flight
	 */
	@Override
	public void setCapacity(int capacity) {
		model.setCapacity(capacity);
	}

	/**
	 * Sets the company ID of this flight.
	 *
	 * @param companyId the company ID of this flight
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this flight.
	 *
	 * @param createDate the create date of this flight
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this flight.
	 *
	 * @param externalReferenceCode the external reference code of this flight
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the flight date of this flight.
	 *
	 * @param flightDate the flight date of this flight
	 */
	@Override
	public void setFlightDate(Date flightDate) {
		model.setFlightDate(flightDate);
	}

	/**
	 * Sets the flight ID of this flight.
	 *
	 * @param flightId the flight ID of this flight
	 */
	@Override
	public void setFlightId(long flightId) {
		model.setFlightId(flightId);
	}

	/**
	 * Sets the flight number of this flight.
	 *
	 * @param flightNumber the flight number of this flight
	 */
	@Override
	public void setFlightNumber(String flightNumber) {
		model.setFlightNumber(flightNumber);
	}

	/**
	 * Sets the group ID of this flight.
	 *
	 * @param groupId the group ID of this flight
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this flight.
	 *
	 * @param modifiedDate the modified date of this flight
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this flight.
	 *
	 * @param mvccVersion the mvcc version of this flight
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this flight.
	 *
	 * @param primaryKey the primary key of this flight
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this flight.
	 *
	 * @param userId the user ID of this flight
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this flight.
	 *
	 * @param userName the user name of this flight
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this flight.
	 *
	 * @param userUuid the user uuid of this flight
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this flight.
	 *
	 * @param uuid the uuid of this flight
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected FlightWrapper wrap(Flight flight) {
		return new FlightWrapper(flight);
	}

}