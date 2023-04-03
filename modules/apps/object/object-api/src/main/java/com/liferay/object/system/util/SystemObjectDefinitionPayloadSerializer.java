package com.liferay.object.system.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;

public interface SystemObjectDefinitionPayloadSerializer<T extends BaseModel<T>> {

	public JSONObject serialize(
		Class<T> modelClass, String objectActionTriggerKey,
		ObjectDefinition objectDefinition, T originalBaseModel, T baseModel,
		long userId) throws PortalException;
}
