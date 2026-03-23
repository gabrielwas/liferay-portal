/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const salesforceLoginURL = process.env.SALESFORCE_LOGIN_URL;
const salesforceConsumerKey = process.env.SALESFORCE_CONSUMER_KEY;
const salesforceConsumerSecret = process.env.SALESFORCE_CONSUMER_SECRET;
const salesforceUsername = process.env.SALESFORCE_USERNAME;
const salesforcePassword = process.env.SALESFORCE_PASSWORD;

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-135430': {enabled: true},
	}),
	instanceSettingsPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.beforeEach(async ({instanceSettingsPage, page}) => {
	test.skip(
		!salesforceLoginURL ||
			!salesforceConsumerKey ||
			!salesforceConsumerSecret ||
			!salesforceUsername ||
			!salesforcePassword,
		'Requires Salesforce environment variables: SALESFORCE_LOGIN_URL, SALESFORCE_CONSUMER_KEY, SALESFORCE_CONSUMER_SECRET, SALESFORCE_USERNAME, SALESFORCE_PASSWORD'
	);

	page.setViewportSize({height: 1080, width: 1920});

	await instanceSettingsPage.goToInstanceSetting(
		'Third Party',
		'Salesforce Integration'
	);

	await page
		.locator(
			'div.ddm-field[data-field-name="loginURL"] textarea'
		)
		.fill(salesforceLoginURL!);

	await page
		.locator(
			'div.ddm-field[data-field-name="consumerKey"] textarea'
		)
		.fill(salesforceConsumerKey!);

	await page
		.locator(
			'div.ddm-field[data-field-name="consumerSecret"] textarea'
		)
		.fill(salesforceConsumerSecret!);

	await page
		.locator(
			'div.ddm-field[data-field-name="username"] textarea'
		)
		.fill(salesforceUsername!);

	await page
		.locator(
			'div.ddm-field[data-field-name="password"] input[type="password"]'
		)
		.fill(salesforcePassword!);

	await instanceSettingsPage.saveAndWaitForAlert();
});

test(
	'LPD-78504 Assert CRUD with created custom object using Salesforce storage type',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: AssertCRUDWithCreatedCustomObject

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinitionName = 'Name' + getRandomInt();
		const objectDefinitionLabel = getRandomString();

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				externalReferenceCode: getRandomString(),
				label: {
					en_US: objectDefinitionLabel,
				},
				name: objectDefinitionName,
				objectFields,
				panelCategoryKey: 'control_panel.object',
				pluralLabel: {
					en_US: objectDefinitionLabel + 's',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
				storageType: 'salesforce',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const fieldLabel = objectFields[0].label['en_US'];
		const fieldName = objectFields[0].name!;

		// Create

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const createValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: createValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		// Read

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(createValue)
		).toBeVisible();

		// Update

		await page.getByRole('button', {name: 'Actions'}).click();

		await page.getByRole('menuitem', {name: 'View'}).click();

		const updateValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: updateValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue)
		).toBeVisible();

		// Delete

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await viewObjectEntriesPage.deletionConfirmationModal
			.getByRole('button', {
				name: 'Delete',
			})
			.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue, {exact: true})
		).toBeAttached({attached: false});
	}
);

test(
	'LPD-78504 Assert CRUD with created standard object using Salesforce storage type',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: AssertCRUDWithCreatedStandardObject

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinitionName = 'Name' + getRandomInt();
		const objectDefinitionLabel = getRandomString();

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				externalReferenceCode: getRandomString(),
				label: {
					en_US: objectDefinitionLabel,
				},
				name: objectDefinitionName,
				objectFields,
				panelCategoryKey: 'control_panel.object',
				pluralLabel: {
					en_US: objectDefinitionLabel + 's',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
				storageType: 'salesforce',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const fieldLabel = objectFields[0].label['en_US'];
		const fieldName = objectFields[0].name!;

		// Create

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const createValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: createValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		// Read

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(createValue)
		).toBeVisible();

		// Update

		await page.getByRole('button', {name: 'Actions'}).click();

		await page.getByRole('menuitem', {name: 'View'}).click();

		const updateValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: updateValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue)
		).toBeVisible();

		// Delete

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await viewObjectEntriesPage.deletionConfirmationModal
			.getByRole('button', {
				name: 'Delete',
			})
			.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue, {exact: true})
		).toBeAttached({attached: false});
	}
);

test(
	'LPD-78504 Assert CRUD with form container using Salesforce storage type',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: AssertCRUDWithFormContainer

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinitionName = 'Name' + getRandomInt();
		const objectDefinitionLabel = getRandomString();

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				enableFormContainer: true,
				externalReferenceCode: getRandomString(),
				label: {
					en_US: objectDefinitionLabel,
				},
				name: objectDefinitionName,
				objectFields,
				panelCategoryKey: 'control_panel.object',
				pluralLabel: {
					en_US: objectDefinitionLabel + 's',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
				storageType: 'salesforce',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const fieldLabel = objectFields[0].label['en_US'];
		const fieldName = objectFields[0].name!;

		// Create

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const createValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: createValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		// Read

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(createValue)
		).toBeVisible();

		// Update

		await page.getByRole('button', {name: 'Actions'}).click();

		await page.getByRole('menuitem', {name: 'View'}).click();

		const updateValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: updateValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue)
		).toBeVisible();

		// Delete

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await viewObjectEntriesPage.deletionConfirmationModal
			.getByRole('button', {
				name: 'Delete',
			})
			.click();

		await expect(
			page
				.locator(`.cell-${fieldLabel}`)
				.nth(1)
				.getByText(updateValue, {exact: true})
		).toBeAttached({attached: false});
	}
);
