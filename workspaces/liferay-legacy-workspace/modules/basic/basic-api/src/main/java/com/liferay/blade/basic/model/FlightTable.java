/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blade.basic.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Flight&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Flight
 * @generated
 */
public class FlightTable extends BaseTable<FlightTable> {

	public static final FlightTable INSTANCE = new FlightTable();

	public final Column<FlightTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FlightTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FlightTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FlightTable, Long> flightId = createColumn(
		"flightId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FlightTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FlightTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FlightTable, String> flightNumber = createColumn(
		"flightNumber", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Integer> capacity = createColumn(
		"capacity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<FlightTable, Date> flightDate = createColumn(
		"flightDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private FlightTable() {
		super("Flight", FlightTable::new);
	}

}