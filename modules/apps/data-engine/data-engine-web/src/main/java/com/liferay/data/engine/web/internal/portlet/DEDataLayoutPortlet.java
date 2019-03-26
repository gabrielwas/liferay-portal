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

package com.liferay.data.engine.web.internal.portlet;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataLayoutRequestBuilder;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.data.engine.web.constants.DEPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.application-type=full-page-application",
		"com.liferay.portlet.application-type=widget",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Data Engine Layout",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/display/",
		"javax.portlet.init-param.view-template=/display/view.jsp",
		"javax.portlet.name=" + DEPortletKeys.DATA_LAYOUT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DEDataLayoutPortlet extends MVCPortlet {
	
//	//------------------------------------------------------------------------------------------------
	
	private User _user;
	
	@Reference
	protected DEDataDefinitionService deDataDefinitionService;
	
	@Reference
	protected DEDataLayoutService deDataLayoutService;
	
	@Reference
	protected UserLocalService userLocalService;
	
	public void saveLayout() {

		try {

			_user = userLocalService.getUser(20139);
	
			DEDataDefinitionField deDataDefinitionField1 = _createTextField(
					"field1");
	
			DEDataDefinition deDataDefinition = new DEDataDefinition();
	
			deDataDefinition.addName(LocaleUtil.US, "Definition Test");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(
					deDataDefinitionField1));
			deDataDefinition.setStorageType("json");
	
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).inGroup(
					_user.getGroupId()
				).onBehalfOf(
					_user.getUserId()
				).build();
			
			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse = 
				deDataDefinitionService.execute(deDataDefinitionSaveRequest);
			
			DEDataDefinition deDataDefinitionSaved =
				deDataDefinitionSaveResponse.getDEDataDefinition();

			deDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaved.getDEDataDefinitionId());

			DEDataLayoutColumn deDataLayoutColumn1 = new DEDataLayoutColumn();

			deDataLayoutColumn1.setColumnSize(4);
			deDataLayoutColumn1.setFieldsName(
				Arrays.asList("field1"));

			Queue<DEDataLayoutColumn> deDataLayoutColumns = new ArrayDeque<>();

			deDataLayoutColumns.add(deDataLayoutColumn1);

			DEDataLayoutRow deDataLayoutRow = new DEDataLayoutRow();

			deDataLayoutRow.setDEDataLayoutColumns(deDataLayoutColumns);

			Queue<DEDataLayoutRow> deDataLayoutRows = new ArrayDeque<>();

			deDataLayoutRows.add(deDataLayoutRow);

			DEDataLayoutPage deDataLayoutPage = new DEDataLayoutPage();

			deDataLayoutPage.setTitle(
				new HashMap() {
					{
						put("en_US", "Page");
					}
				});
			
			deDataLayoutPage.setDescription(
				new HashMap() {
					{
						put("en_US", StringPool.BLANK);
					}
				});
			
			deDataLayoutPage.setDEDataLayoutRows(deDataLayoutRows);

			Queue<DEDataLayoutPage> deDataLayoutPages = new ArrayDeque<>();

			deDataLayoutPages.add(deDataLayoutPage);

			DEDataLayout deDataLayout = new DEDataLayout();

			deDataLayout.setName(
				new HashMap() {
					{
						put(LocaleUtil.US, "layout");
					}
				});
			deDataLayout.setDescription(
				new HashMap() {
					{
						put(LocaleUtil.US, "this is a layout");
					}
				});
			deDataLayout.setDEDataLayoutPages(deDataLayoutPages);
			deDataLayout.setPaginationMode("wizard");
			deDataLayout.setDefaultLanguageId("en_US");

			deDataLayout.setDEDataDefinition(deDataDefinition);
			
			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					deDataLayout
				).inGroup(
					_user.getGroupId()
				).onBehalfOf(
					_user.getUserId()
				).build();

			deDataLayoutService.execute(deDataLayoutSaveRequest);
			
		} catch (DEDataDefinitionException e) {
			e.printStackTrace();
		} catch (DEDataLayoutException e) {
			e.printStackTrace();
		} catch (PrincipalException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private DEDataDefinitionField _createTextField(String name) {
		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			name, "text");

		deDataDefinitionField.setCustomProperty("displayStyle", "singleline");

		deDataDefinitionField.addLabels(
			new HashMap() {
				{
					put("en_US", "Text Label");
				}
			});
		deDataDefinitionField.setCustomProperty(
			"placeholder",
			new HashMap() {
				{
					put("en_US", "Text Placeholder");
				}
			});

		deDataDefinitionField.setCustomProperty(
			"predefinedValue", "Simple Text");

		deDataDefinitionField.setCustomProperty(
			"tooltip",
			new HashMap() {
				{
					put("en_US", "Text Tooltip");
				}
			});

		return deDataDefinitionField;
	}
	
//	//------------------------------------------------------------------------------------------------
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		System.out.println("here");
		//saveLayout();
		
		super.render(renderRequest, renderResponse);
	}
	
	
}
