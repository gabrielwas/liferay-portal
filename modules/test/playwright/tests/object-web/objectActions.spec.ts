/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);


test.describe('Create Actions', () => {
	test('Commerce Order - Can create actions for Commerce Order object', async ({
		apiHelpers,
		modelBuilderPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		await page.goto('/');


        await viewObjectDefinitionsPage.goto();

		//await viewObjectDefinitionsPage.openObjectFolder('default');





        //await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();
       // await page.getByRole('tab', { name: 'Control Panel' }).click();
        //await page.getByRole('menuitem', { name: 'Objects' }).click();
        await page.getByRole('link', { name: 'Commerce Order', exact: true }).click();
        await page.getByRole('link', { name: 'Actions' }).click();
        await page.getByTestId('fdsCreationActionButton').click();
        await page.frameLocator('iframe').getByPlaceholder('Text to translate...').click();
        await page.frameLocator('iframe').getByPlaceholder('Text to translate...').fill('Able');
        await page.frameLocator('iframe').getByRole('tab', { name: 'Action Builder' }).click();
        await page.frameLocator('iframe').getByText('Choose a Trigger').click();
        await page.frameLocator('iframe').getByRole('option', { name: 'On Order Status Update' }).click();
        await page.frameLocator('iframe').getByText('Choose an Action').click();
        await page.frameLocator('iframe').getByRole('option', { name: 'Groovy Script' }).click();
        await page.frameLocator('iframe').getByRole('button', { name: 'Save' }).click();




		const ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition('default');

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder('default');

		await viewObjectDefinitionsPage.viewInModelBuilder();

		const objectFieldLabel = 'objectFieldLabel' + getRandomInt();

		await modelBuilderPage.createObjectField({
			listTypeDefinitionName: ListTypeDefinition.name,
			mandatory: false,
			objectDefinitionName: objectDefinition.name,
			objectFieldBusinessType: 'Picklist',
			objectFieldLabel,
		});

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.name})
				.getByText(objectFieldLabel)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			ListTypeDefinition.id
		);
	});
});