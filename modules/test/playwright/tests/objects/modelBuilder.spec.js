/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {test as apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {test as homePageTest} from '../../fixtures/homePage.fixture';
import {test as objectsPagesTest} from '../../fixtures/objectsPages.fixture';
import {test as instanceSettingsTest} from '../../fixtures/instanceSettings.fixture';
import {getRandomInt} from '../../utils/util';
import { FeatureFlagPage } from '../../pages/instanceSettings/featureFlag.page';

const { chromium } = require('playwright');

export const test = mergeTests(apiHelpersTest, homePageTest, objectsPagesTest, instanceSettingsTest);

// test.beforeAll(async () => {
// 	const browser = await chromium.launch();
// 	const page = await browser.newPage();
// 	const featureFlagPage = new FeatureFlagPage(page);

// 	await featureFlagPage.toggleFeatureFlag('LPS-148856');
// });


test('created object folders are on the left side bar', async ({
	_api,
	_homePage,
	_objectDefinitionsPage,
}) => {

	await _homePage.goto();

	await _homePage.page.evaluate(() => Liferay.Util.fetch(
		'/o/com-liferay-feature-flag-web/set-enabled',
		{
			body: Liferay.Util.objectToFormData({
				companyId: Liferay.ThemeDisplay.getCompanyId(),
				enabled: 'true',
				key: 'LPS-148856',
			}),
			method: 'POST',
		}
	));

	//await _featureFlagPage.toggleFeatureFlag('LPS-148856');

	await _objectDefinitionsPage.goto();

	const objectFolderExternalReferenceCode = 'objectFolder' + getRandomInt();

	const objectFolder = await _objectDefinitionsPage.createObjectFolder(
		objectFolderExternalReferenceCode
	);

	await expect(
		_objectDefinitionsPage.page
			.locator('li')
			.filter({hasText: objectFolderExternalReferenceCode})
	).toBeVisible();

	// Clean up

	await _api.objectAdmin.deleteObjectFolder(objectFolder.id);
});

test('uncategorized folder does not contains delete and edit options', async ({
	_objectDefinitionsPage,
	_featureFlagPage,
	_homePage,
}) => {


	await _homePage.goto();

	await _homePage.page.evaluate(() => Liferay.Util.fetch(
		'/o/com-liferay-feature-flag-web/set-enabled',
		{
			body: Liferay.Util.objectToFormData({
				companyId: Liferay.ThemeDisplay.getCompanyId(),
				enabled: 'true',
				key: 'LPS-148856',
			}),
			method: 'POST',
		}
	));

	//await _featureFlagPage.toggleFeatureFlag('LPS-148856');

	await _objectDefinitionsPage.goto();
	await _objectDefinitionsPage.clickUncategorizedObjectFolder();
	await _objectDefinitionsPage.openObjectFolderActions();

	await expect(
		_objectDefinitionsPage.objectFolderDeleteFolderOption
	).toBeHidden();
	await expect(
		_objectDefinitionsPage.objectFolderEditLabelAndERCOption
	).toBeHidden();
});

test('can create relationship by dragging node handles', async ({
	_api,
	_homePage,
	_modelBuilderPage,
	_objectDefinitionsPage,
	_featureFlagPage,
	page,
}) => {

	await _homePage.goto();

	await _homePage.page.evaluate(() => Liferay.Util.fetch(
		'/o/com-liferay-feature-flag-web/set-enabled',
		{
			body: Liferay.Util.objectToFormData({
				companyId: Liferay.ThemeDisplay.getCompanyId(),
				enabled: 'true',
				key: 'LPS-148856',
			}),
			method: 'POST',
		}
	));

	//await _featureFlagPage.toggleFeatureFlag('LPS-148856');

	await _homePage.goto();

	const objectFolder = await _api.objectAdmin.postRandomObjectFolder();
	const objectDefinition1 = await _api.objectAdmin.postRandomObjectDefinition(
		objectFolder.externalReferenceCode
	);
	const objectDefinition2 = await _api.objectAdmin.postRandomObjectDefinition(
		objectFolder.externalReferenceCode
	);

	await _objectDefinitionsPage.goto();
	await _objectDefinitionsPage.openObjectFolder(
		objectFolder.externalReferenceCode
	);
	await _objectDefinitionsPage.viewInModelBuilder();

	const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

	const objectRelationship = await _modelBuilderPage.createObjectRelationship(
		objectDefinition1.externalReferenceCode,
		objectDefinition2.externalReferenceCode,
		objectRelationshipLabel,
		'One to Many'
	);

	// -- Missing refact from here --

	await expect(
		page.locator('g > text').filter({hasText: objectRelationshipLabel})
	).toBeVisible();

	await page.getByRole('button', {name: 'Show All Fields'}).last().click();

	// await expect(
	// 	page.getByText('new-one-to-many-relationship-1relationship')
	// ).toBeVisible();

	// Clean up

	await _api.objectAdmin.deleteObjectRelationship(objectRelationship.id);
	await _api.objectAdmin.deleteObjectDefinition(objectDefinition1.id);
	await _api.objectAdmin.deleteObjectDefinition(objectDefinition2.id);
	await _api.objectAdmin.deleteObjectFolder(objectFolder.id);
});
