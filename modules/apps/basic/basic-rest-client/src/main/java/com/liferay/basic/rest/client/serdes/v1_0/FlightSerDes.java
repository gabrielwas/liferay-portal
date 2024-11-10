/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.basic.rest.client.serdes.v1_0;

import com.liferay.basic.rest.client.dto.v1_0.Flight;
import com.liferay.basic.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class FlightSerDes {

	public static Flight toDTO(String json) {
		FlightJSONParser flightJSONParser = new FlightJSONParser();

		return flightJSONParser.parseToDTO(json);
	}

	public static Flight[] toDTOs(String json) {
		FlightJSONParser flightJSONParser = new FlightJSONParser();

		return flightJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Flight flight) {
		if (flight == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (flight.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(flight.getActions()));
		}

		if (flight.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(flight.getActive());
		}

		if (flight.getCapacity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"capacity\": ");

			sb.append(flight.getCapacity());
		}

		if (flight.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(flight.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (flight.getFlightDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"flightDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(flight.getFlightDate()));

			sb.append("\"");
		}

		if (flight.getFlightNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"flightNumber\": ");

			sb.append("\"");

			sb.append(_escape(flight.getFlightNumber()));

			sb.append("\"");
		}

		if (flight.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(flight.getId());
		}

		if (flight.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(flight.getStatus());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FlightJSONParser flightJSONParser = new FlightJSONParser();

		return flightJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Flight flight) {
		if (flight == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (flight.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(flight.getActions()));
		}

		if (flight.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(flight.getActive()));
		}

		if (flight.getCapacity() == null) {
			map.put("capacity", null);
		}
		else {
			map.put("capacity", String.valueOf(flight.getCapacity()));
		}

		if (flight.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(flight.getExternalReferenceCode()));
		}

		if (flight.getFlightDate() == null) {
			map.put("flightDate", null);
		}
		else {
			map.put(
				"flightDate",
				liferayToJSONDateFormat.format(flight.getFlightDate()));
		}

		if (flight.getFlightNumber() == null) {
			map.put("flightNumber", null);
		}
		else {
			map.put("flightNumber", String.valueOf(flight.getFlightNumber()));
		}

		if (flight.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(flight.getId()));
		}

		if (flight.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(flight.getStatus()));
		}

		return map;
	}

	public static class FlightJSONParser extends BaseJSONParser<Flight> {

		@Override
		protected Flight createDTO() {
			return new Flight();
		}

		@Override
		protected Flight[] createDTOArray(int size) {
			return new Flight[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "capacity")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "flightDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "flightNumber")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Flight flight, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					flight.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					flight.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "capacity")) {
				if (jsonParserFieldValue != null) {
					flight.setCapacity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					flight.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "flightDate")) {
				if (jsonParserFieldValue != null) {
					flight.setFlightDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "flightNumber")) {
				if (jsonParserFieldValue != null) {
					flight.setFlightNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					flight.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					flight.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}