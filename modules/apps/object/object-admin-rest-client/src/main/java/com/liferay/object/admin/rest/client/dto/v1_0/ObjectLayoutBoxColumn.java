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

package com.liferay.object.admin.rest.client.dto.v1_0;

import com.liferay.object.admin.rest.client.function.UnsafeSupplier;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectLayoutBoxColumnSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutBoxColumn implements Cloneable, Serializable {

	public static ObjectLayoutBoxColumn toDTO(String json) {
		return ObjectLayoutBoxColumnSerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Long getObjectFieldId() {
		return objectFieldId;
	}

	public void setObjectFieldId(Long objectFieldId) {
		this.objectFieldId = objectFieldId;
	}

	public void setObjectFieldId(
		UnsafeSupplier<Long, Exception> objectFieldIdUnsafeSupplier) {

		try {
			objectFieldId = objectFieldIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectFieldId;

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public void setPriority(
		UnsafeSupplier<Long, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long priority;

	@Override
	public ObjectLayoutBoxColumn clone() throws CloneNotSupportedException {
		return (ObjectLayoutBoxColumn)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutBoxColumn)) {
			return false;
		}

		ObjectLayoutBoxColumn objectLayoutBoxColumn =
			(ObjectLayoutBoxColumn)object;

		return Objects.equals(toString(), objectLayoutBoxColumn.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectLayoutBoxColumnSerDes.toJSON(this);
	}

}