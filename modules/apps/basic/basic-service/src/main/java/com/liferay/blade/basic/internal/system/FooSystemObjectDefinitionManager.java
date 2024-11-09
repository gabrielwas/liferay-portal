package com.liferay.blade.basic.internal.system;

import com.liferay.blade.basic.model.Foo;
import com.liferay.blade.basic.model.FooTable;
import com.liferay.blade.basic.service.FooLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = SystemObjectDefinitionManager.class)
public class FooSystemObjectDefinitionManager extends
	BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		long increment = _counterLocalService.increment();

		Foo foo = _fooLocalService.createFoo(increment);

		foo.setExternalReferenceCode(String.valueOf(increment));
		foo.setField1(GetterUtil.getString(values.get("field1")));
		foo.setField2(GetterUtil.getBoolean(values.get("field2")));

		foo = _fooLocalService.addFoo(foo);

		return foo.getFooId();

	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

			return _fooLocalService.deleteFoo( (Foo) baseModel);
	}

	@Override
	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

			return _fooLocalService.fetchFooByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId)
		throws PortalException {
		return _fooLocalService.getFooByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {
		Foo foo = _fooLocalService.getFoo(primaryKey);

		return foo.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "ERC_FOO";
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK);
	}

	@Override
	public Map<String, String> getLabelKeys() {
		return HashMapBuilder.put(
			"label", "Foo"
		).put(
			"pluralLabel", "Foos"
		).build();
	}

	@Override
	public Class<?> getModelClass() {
		return Foo.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Collections.singletonList(
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("field1")
			).name(
				"field1"
			).required(
				false
			).system(
				true
			).build());
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return FooTable.INSTANCE.fooId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return FooTable.INSTANCE;
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public void updateBaseModel(
		long primaryKey, User user, Map<String, Object> values)
		throws Exception {
		//TODO
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private FooLocalService _fooLocalService;
}