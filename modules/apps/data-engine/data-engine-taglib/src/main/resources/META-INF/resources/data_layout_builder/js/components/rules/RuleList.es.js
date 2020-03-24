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

import React, {useContext, useState} from 'react';

import AppContext from '../../AppContext.es';
import Button from '../button/Button.es';
import RuleEditorModal from './RuleEditorModal.es';

export default () => {
	const [isRulesEditorVisible, setRulesEditorVisible] = useState(false);

	const [
		{
			dataLayout: {dataRules},
		},
	] = useContext(AppContext);

	return (
		<>
			<Button
				displayType="primary"
				monospaced={false}
				onClick={() => setRulesEditorVisible(!isRulesEditorVisible)}
			>
				{Liferay.Language.get('add')}
			</Button>

			{dataRules.map(({conditions, logicalOperator}, index) => (
				<p key={index}>
					<div>conditions: {JSON.stringify(conditions)}</div>
					<div>logicalOperator: {logicalOperator}</div>
				</p>
			))}

			<RuleEditorModal
				isVisible={isRulesEditorVisible}
				onClose={() => setRulesEditorVisible(false)}
			/>
		</>
	);
};
