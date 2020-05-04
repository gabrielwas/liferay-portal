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

import React from 'react';

import CollapsablePanel from '../collapsable-panel/CollapsablePanel.es';
import FieldType from './FieldType.es';

export default ({
	deleteLabel,
	fieldTypes,
	keywords,
	onClick,
	onDelete,
	onDoubleClick,
}) => {
	const regex = new RegExp(keywords, 'ig');
	const fieldTypeList = fieldTypes
		.filter(({system}) => !system)
		.filter(({description, label}) => {
			if (!keywords) {
				return true;
			}

			return regex.test(description) || regex.test(label);
		});

	const FieldTypeWrapper = ({
		expanded,
		fieldType,
		setExpanded = () => {},
		showArrows,
	}) => (
		<FieldType
			{...fieldType}
			deleteLabel={deleteLabel}
			icon={
				showArrows
					? expanded
						? 'angle-down'
						: 'angle-right'
					: fieldType.icon
			}
			onClick={onClick}
			onClickIcon={() => setExpanded(!expanded)}
			onDelete={onDelete}
			onDoubleClick={onDoubleClick}
		/>
	);

	return fieldTypeList.map((fieldType, index) => {
		const {nestedDataDefinitionFields = []} = fieldType;
		const key = `${fieldType.name}_${index}`;

		if (nestedDataDefinitionFields.length) {
			const Header = ({expanded, setExpanded}) => (
				<FieldTypeWrapper
					expanded={expanded}
					fieldType={fieldType}
					setExpanded={setExpanded}
					showArrows
				/>
			);

			return (
				<div className="field-type-list">
					<CollapsablePanel Header={Header} key={key}>
						<div className="list-item position-relative">
							{nestedDataDefinitionFields.map(
								(nestedFieldType) => (
									<FieldTypeWrapper
										fieldType={nestedFieldType}
										key={`${nestedFieldType.name}_${index}`}
									/>
								)
							)}
						</div>
					</CollapsablePanel>
				</div>
			);
		}

		return <FieldTypeWrapper fieldType={fieldType} key={key} />;
	});
};
