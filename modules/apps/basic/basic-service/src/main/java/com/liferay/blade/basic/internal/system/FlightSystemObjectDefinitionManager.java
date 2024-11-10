package com.liferay.blade.basic.internal.system;

import com.liferay.blade.basic.model.Flight;
import com.liferay.blade.basic.model.FlightTable;
import com.liferay.blade.basic.service.FlightLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = SystemObjectDefinitionManager.class)
public class FlightSystemObjectDefinitionManager extends
	BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		long increment = _counterLocalService.increment();

		Flight flight = _flightLocalService.createFlight(increment);

		flight.setExternalReferenceCode(String.valueOf(increment));

		flight.setActive(GetterUtil.getBoolean(values.get("active")));
		flight.setCapacity(GetterUtil.getInteger(values.get("capacity")));
		flight.setFlightNumber(GetterUtil.getString(values.get("flightNumber")));

		flight = _flightLocalService.addFlight(flight);

		return flight.getFlightId();

	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _flightLocalService.deleteFlight((Flight) baseModel);
	}

	@Override
	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _flightLocalService.fetchFlightByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId)
		throws PortalException {

		return _flightLocalService.getFlightByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {

		Flight flight = _flightLocalService.getFlight(primaryKey);

		return flight.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "ERC_FLIGHT";
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Basic.REST", "basic",
			"flights", "v1.0");
	}

	@Override
	public Map<String, String> getLabelKeys() {
		return HashMapBuilder.put(
			"label", "Flight"
		).put(
			"pluralLabel", "Flights"
		).build();
	}

	@Override
	public Class<?> getModelClass() {
		return Flight.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			new BooleanObjectFieldBuilder(
			).labelMap(
				createLabelMap("active")
			).name(
				"active"
			).required(
				false
			).system(
				true
			).build(),
			new IntegerObjectFieldBuilder(
			).labelMap(
				createLabelMap("capacity")
			).name(
				"capacity"
			).required(
				false
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("flightNumber")
			).name(
				"flightNumber"
			).required(
				false
			).system(
				true
			).build());
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return FlightTable.INSTANCE.flightId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return FlightTable.INSTANCE;
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

	@Override
	public Page<?> getPage(
		User user, String search, Filter filter, Pagination pagination,
		Sort[] sorts)
		throws Exception {

		return Page.of(_flightLocalService.getFlights(QueryUtil.ALL_POS, QueryUtil.ALL_POS));
	}

	@Override
	public String getTitleObjectFieldName() {
		return "flightNumber";
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private FlightLocalService _flightLocalService;
}