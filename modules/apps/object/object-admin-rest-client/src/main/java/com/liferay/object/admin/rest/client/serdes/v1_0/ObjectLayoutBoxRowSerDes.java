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

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutBoxColumn;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutBoxRow;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutBoxRowSerDes {

	public static ObjectLayoutBoxRow toDTO(String json) {
		ObjectLayoutBoxRowJSONParser objectLayoutBoxRowJSONParser =
			new ObjectLayoutBoxRowJSONParser();

		return objectLayoutBoxRowJSONParser.parseToDTO(json);
	}

	public static ObjectLayoutBoxRow[] toDTOs(String json) {
		ObjectLayoutBoxRowJSONParser objectLayoutBoxRowJSONParser =
			new ObjectLayoutBoxRowJSONParser();

		return objectLayoutBoxRowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectLayoutBoxRow objectLayoutBoxRow) {
		if (objectLayoutBoxRow == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectLayoutBoxRow.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectLayoutBoxRow.getId());
		}

		if (objectLayoutBoxRow.getObjectLayoutBoxColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayoutBoxColumns\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectLayoutBoxRow.getObjectLayoutBoxColumns().length;
				 i++) {

				sb.append(
					String.valueOf(
						objectLayoutBoxRow.getObjectLayoutBoxColumns()[i]));

				if ((i + 1) <
						objectLayoutBoxRow.getObjectLayoutBoxColumns().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectLayoutBoxRow.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(objectLayoutBoxRow.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectLayoutBoxRowJSONParser objectLayoutBoxRowJSONParser =
			new ObjectLayoutBoxRowJSONParser();

		return objectLayoutBoxRowJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		if (objectLayoutBoxRow == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectLayoutBoxRow.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectLayoutBoxRow.getId()));
		}

		if (objectLayoutBoxRow.getObjectLayoutBoxColumns() == null) {
			map.put("objectLayoutBoxColumns", null);
		}
		else {
			map.put(
				"objectLayoutBoxColumns",
				String.valueOf(objectLayoutBoxRow.getObjectLayoutBoxColumns()));
		}

		if (objectLayoutBoxRow.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put(
				"priority", String.valueOf(objectLayoutBoxRow.getPriority()));
		}

		return map;
	}

	public static class ObjectLayoutBoxRowJSONParser
		extends BaseJSONParser<ObjectLayoutBoxRow> {

		@Override
		protected ObjectLayoutBoxRow createDTO() {
			return new ObjectLayoutBoxRow();
		}

		@Override
		protected ObjectLayoutBoxRow[] createDTOArray(int size) {
			return new ObjectLayoutBoxRow[size];
		}

		@Override
		protected void setField(
			ObjectLayoutBoxRow objectLayoutBoxRow, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBoxRow.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectLayoutBoxColumns")) {

				if (jsonParserFieldValue != null) {
					objectLayoutBoxRow.setObjectLayoutBoxColumns(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectLayoutBoxColumnSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectLayoutBoxColumn[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBoxRow.setPriority(
						Long.valueOf((String)jsonParserFieldValue));
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

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}