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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DECountRequestExecutor;
import com.liferay.data.engine.executor.DEDeleteRequestExecutor;
import com.liferay.data.engine.executor.DEGetRequestExecutor;
import com.liferay.data.engine.executor.DEListRequestExecutor;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DataDefinitionDECountRequest;
import com.liferay.data.engine.service.DataDefinitionDECountResponse;
import com.liferay.data.engine.service.DataDefinitionDEDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionDEDeleteResponse;
import com.liferay.data.engine.service.DataDefinitionDEGetRequest;
import com.liferay.data.engine.service.DataDefinitionDEGetResponse;
import com.liferay.data.engine.service.DataDefinitionDEListRequest;
import com.liferay.data.engine.service.DataDefinitionDEListResponse;
import com.liferay.data.engine.service.DataDefinitionDESaveRequest;
import com.liferay.data.engine.service.DataDefinitionDESaveResponse;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEDataDefinitionService.class)
public class DEDataDefinitionServiceImpl implements DEDataDefinitionService {

	@Override
	public DataDefinitionDECountResponse execute(
			DataDefinitionDECountRequest dataDefinitionDECountRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionGroupId =
				dataDefinitionDECountRequest.getGroupId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionGroupId,
				ActionKeys.VIEW);

			return deCountRequestExecutor.execute(dataDefinitionDECountRequest);
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionDEDeleteResponse execute(
			DataDefinitionDEDeleteRequest dataDefinitionDEDeleteRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				dataDefinitionDEDeleteRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.DELETE);

			return deDeleteRequestExecutor.execute(
				dataDefinitionDEDeleteRequest);
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				dataDefinitionDEDeleteRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionDEGetResponse execute(
			DataDefinitionDEGetRequest dataDefinitionDEGetRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				dataDefinitionDEGetRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.VIEW);

			return deGetRequestExecutor.execute(dataDefinitionDEGetRequest);
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				dataDefinitionDEGetRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionDEListResponse execute(
			DataDefinitionDEListRequest dataDefinitionDEListRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionGroupId =
				dataDefinitionDEListRequest.getGroupId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionGroupId,
				ActionKeys.VIEW);

			return deListRequestExecutor.execute(dataDefinitionDEListRequest);
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionDESaveResponse execute(
			DataDefinitionDESaveRequest dataDefinitionDESaveRequest)
		throws DEDataDefinitionException {

		DEDataDefinition deDataDefinition =
			dataDefinitionDESaveRequest.getDEDataDefinition();

		try {
			long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

			if (deDataDefinitionId == 0) {
				checkCreateDataDefinitionPermission(
					dataDefinitionDESaveRequest.getGroupId(),
					getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataDefinitionId,
					ActionKeys.UPDATE);
			}

			DataDefinitionDESaveResponse dataDefinitionDESaveResponse =
				deSaveRequestExecutor.execute(dataDefinitionDESaveRequest);

			return DataDefinitionDESaveResponse.Builder.of(
				dataDefinitionDESaveResponse.getDEDataDefinitionId());
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinition.getDEDataDefinitionId(), nsse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	protected void checkCreateDataDefinitionPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		String resourceName = DEDataEnginePermissionSupport.RESOURCE_NAME;
		String actionId = DEActionKeys.ADD_DATA_DEFINITION_ACTION;

		if (!deDataEnginePermissionSupport.contains(
				permissionChecker, resourceName, groupId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, groupId, actionId);
		}
	}

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException(
				"Permission checker is not initialized");
		}

		return permissionChecker;
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataDefinition> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DECountRequestExecutor deCountRequestExecutor;

	@Reference
	protected DEDataEnginePermissionSupport deDataEnginePermissionSupport;

	@Reference
	protected DEDeleteRequestExecutor deDeleteRequestExecutor;

	@Reference
	protected DEGetRequestExecutor deGetRequestExecutor;

	@Reference
	protected DEListRequestExecutor deListRequestExecutor;

	@Reference
	protected DESaveRequestExecutor deSaveRequestExecutor;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataDefinitionServiceImpl.class);

	private ModelResourcePermission<DEDataDefinition> _modelResourcePermission;

}