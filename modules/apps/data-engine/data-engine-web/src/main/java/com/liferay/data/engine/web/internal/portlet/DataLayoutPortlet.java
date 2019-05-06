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

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.web.constants.DataEnginePortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
		"javax.portlet.name=" + DataEnginePortletKeys.DATA_LAYOUT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DataLayoutPortlet extends MVCPortlet {
	
	public void saveLayout() {
		
		try {
			
			User _user = userLocalService.getUser(20129);
			
			DataDefinitionField dataDefinitionField1 = _createTextField(
					"field1");
					
			DataDefinitionField dataDefinitionField2 = _createSingleSelectionField(
					"field2");
			
			DataDefinition dataDefinition = new DataDefinition() {
				{
					name = new LocalizedValue[] {
							new LocalizedValue() {
								{
									key = "en_US";
									value = "Text Tooltip";
								}
								
							}
					};
					
					dataDefinitionFields = new DataDefinitionField[] {
							dataDefinitionField1, dataDefinitionField2
					};
					
					storageType = "json";
					
					siteId = _user.getGroupId();
					userId = _user.getUserId();
				}
			};
			
			dataDefinition = dataDefinitionResource.postSiteDataDefinition(_user.getGroupId(), dataDefinition);
			
			Long definitionId = dataDefinition.getId();
			
			DataLayoutColumn dataLayoutColumn = new DataLayoutColumn() {
				{
					columnSize = 12;
					fieldNames = new String[] {"field1", "field2"};
				}
			};
			
			DataLayoutRow dataLayoutRow = new DataLayoutRow() {
				{
					dataLayoutColums = new DataLayoutColumn[] {dataLayoutColumn};
				}
			};
			
			DataLayoutPage dataLayoutPage = new DataLayoutPage() {
				{
					title = new LocalizedValue[] {
							new LocalizedValue() {
								{
									key = "en_US";
									value = "Page";
								}
								
							}
					};
					
					description = new LocalizedValue[] {
							new LocalizedValue() {
								{
									key = "en_US";
									value = StringPool.BLANK;
								}
								
							}
					};
					
					dataLayoutRows = new DataLayoutRow[] {dataLayoutRow};
					
					
					
					
				}
			};
			
			DataLayout dataLayout = new DataLayout() {
				{
					name = new LocalizedValue[] {
							new LocalizedValue() {
								{
									key = "en_US";
									value = "layout";
								}
								
							}
					};
					
					description = new LocalizedValue[] {
							new LocalizedValue() {
								{
									key = "en_US";
									value = "this is a layout";
								}
								
							}
					};
					
					dataLayoutPages = new DataLayoutPage[] {dataLayoutPage};
					
					paginationMode = "wizard";
					defaultLanguageId = "en_US";
					dataDefinitionId = definitionId;
					
				}
			};
			
			dataLayoutResource.postDataDefinitionDataLayout(definitionId, dataLayout);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private DataDefinitionField _createTextField(String name1) {
		
		DataDefinitionField dataDefinitionField = new DataDefinitionField() {
			{
				fieldType = "text";
				name = name1;
				customProperties = new CustomProperty[] {
						
						new CustomProperty(){
							{
								key = "displayStyle";
								value = "singleline";
							}
						},
						
						new CustomProperty(){
							{
								key = "placeholder";
								value = new LocalizedValue() {
									{
										key = "en_US";
										value = "Text Placeholder";
									}
									
								};
							}
						},
						
						new CustomProperty(){
							{
								key = "predefinedValue";
								value = "Simple Text";
							}
						},
						
						new CustomProperty(){
							{
								key = "tooltip";
								value = new LocalizedValue() {
									{
										key = "en_US";
										value = "Text Tooltip";
									}
									
								};
							}
						}
				
				};
				label = new LocalizedValue[] {
						
						new LocalizedValue() {
							{
								key = "en_US";
								value = "Text Label";
							}
							
						}
						
				};
						
			}
		};

		return dataDefinitionField;
		
	}
	
	private DataDefinitionField _createSingleSelectionField(String name1) {
		
		CustomProperty option1 = new CustomProperty() {
			{
				key = "Option Label 1";
				value = "Option Value 1";
			}
			
		};
		
		CustomProperty option2 = new CustomProperty() {
			{
				key = "Option Label 2";
				value = "Option Value 2";
			}
			
		};
		
		DataDefinitionField dataDefinitionField = new DataDefinitionField() {
			{
				fieldType = "select";
				name = name1;
				customProperties = new CustomProperty[] {
						
						new CustomProperty(){
							{
								key = "options";
								value = new CustomProperty[] {option1, option2};
							}
						},
						new CustomProperty(){
							{
								key = "multiple";
								value = false;
							}
						}
				
				};
						
			}
		};

		return dataDefinitionField;
		
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		System.out.println("here");
		//saveLayout();

		super.render(renderRequest, renderResponse);
	}
	
	@Reference
	protected DataDefinitionResource dataDefinitionResource;
	
	@Reference
	protected DataLayoutResource dataLayoutResource;
	
	@Reference
	protected UserLocalService userLocalService;
	
	private static final String _OPERATION_SAVE_PERMISSION = "save";
	
}