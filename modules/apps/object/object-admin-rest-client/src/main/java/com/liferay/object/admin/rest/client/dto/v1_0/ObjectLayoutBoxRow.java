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
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectLayoutBoxRowSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutBoxRow implements Cloneable, Serializable {

	public static ObjectLayoutBoxRow toDTO(String json) {
		return ObjectLayoutBoxRowSerDes.toDTO(json);
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

	public ObjectLayoutBoxColumn[] getObjectLayoutBoxColumns() {
		return objectLayoutBoxColumns;
	}

	public void setObjectLayoutBoxColumns(
		ObjectLayoutBoxColumn[] objectLayoutBoxColumns) {

		this.objectLayoutBoxColumns = objectLayoutBoxColumns;
	}

	public void setObjectLayoutBoxColumns(
		UnsafeSupplier<ObjectLayoutBoxColumn[], Exception>
			objectLayoutBoxColumnsUnsafeSupplier) {

		try {
			objectLayoutBoxColumns = objectLayoutBoxColumnsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectLayoutBoxColumn[] objectLayoutBoxColumns;

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
	public ObjectLayoutBoxRow clone() throws CloneNotSupportedException {
		return (ObjectLayoutBoxRow)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutBoxRow)) {
			return false;
		}

		ObjectLayoutBoxRow objectLayoutBoxRow = (ObjectLayoutBoxRow)object;

		return Objects.equals(toString(), objectLayoutBoxRow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectLayoutBoxRowSerDes.toJSON(this);
	}

}