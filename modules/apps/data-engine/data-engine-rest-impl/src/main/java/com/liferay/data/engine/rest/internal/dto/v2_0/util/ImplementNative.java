package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.DataEngineNativeObjectField;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = DataEngineNativeObject.class)
public class ImplementNative implements DataEngineNativeObject {

	@Override
	public String getClassName() {
		return "Native Object Class Name";
	}

	@Override
	public List<DataEngineNativeObjectField> getDataEngineNativeObjectFields() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return "Native Object Name";
	}
}
