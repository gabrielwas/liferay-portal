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
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import getFormContainerDefinition from '../../layout-content-page-editor-web/main/utils/getFormContainerDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
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
        'LPS-178052': {enabled: true},
    }),
    instanceSettingsPagesTest,
    isolatedSiteTest,
    loginTest(),
    objectPagesTest,
    pageEditorPagesTest
);

test.beforeEach(async ({instanceSettingsPage, page}) => {
    test.skip(
        !salesforceLoginURL ||
            !salesforceConsumerKey ||
            !salesforceConsumerSecret ||
            !salesforceUsername ||
            !salesforcePassword,
        'Requires Salesforce environment variables.'
    );

    page.setViewportSize({height: 1080, width: 1920});

    await instanceSettingsPage.goToInstanceSetting(
        'Third Party',
        'Salesforce Integration'
    );

    await page.locator('div.ddm-field[data-field-name="loginURL"] textarea').fill(salesforceLoginURL!);
    await page.locator('div.ddm-field[data-field-name="consumerKey"] textarea').fill(salesforceConsumerKey!);
    await page.locator('div.ddm-field[data-field-name="consumerSecret"] textarea').fill(salesforceConsumerSecret!);
    await page.locator('div.ddm-field[data-field-name="username"] textarea').fill(salesforceUsername!);
    await page.locator('div.ddm-field[data-field-name="password"] input[type="password"]').fill(salesforcePassword!);

    await instanceSettingsPage.saveAndWaitForAlert();
});

async function runSalesforceCRUDTest({
    apiHelpers,
    page,
	fieldIndex = 0,
	objectConfig,
    viewObjectEntriesPage,
    isStandardObject = false
}) {
    const objectDefinitionAPIClient = await apiHelpers.buildRestClient(ObjectDefinitionAPI);

    const {body: objectDefinition} = await objectDefinitionAPIClient.postObjectDefinition(objectConfig);

    apiHelpers.data.push({
        id: objectDefinition.id,
        type: 'objectDefinition',
    });

    const fieldLabel = objectConfig.objectFields[fieldIndex].label['en_US'];

    // Create
    await viewObjectEntriesPage.goto(objectDefinition.className);
    await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

    const createValue = isStandardObject ? `Last Name ${getRandomInt()}` : getRandomString();

    await viewObjectEntriesPage.fillObjectEntry({
        objectFieldBusinessType: 'Text',
        objectFieldLabel: fieldLabel,
        objectFieldValue: createValue,
    });

    await viewObjectEntriesPage.saveObjectEntryButton.click();
    await waitForAlert(page);
    await viewObjectEntriesPage.backButton.click();

    // Read
    await expect(page.getByRole('cell', { name: createValue })).toBeVisible();

    // Update
    await page.getByRole('button', {name: 'Actions'}).last().click();
    await page.getByRole('menuitem', {name: 'View'}).click();

    const updateValue = isStandardObject ? `Last Name Updated ${getRandomInt()}` : getRandomString();

    await viewObjectEntriesPage.fillObjectEntry({
        objectFieldBusinessType: 'Text',
        objectFieldLabel: fieldLabel,
        objectFieldValue: updateValue,
    });

    await viewObjectEntriesPage.saveObjectEntryButton.click();
    await expect(viewObjectEntriesPage.successMessage).toBeVisible();
    await viewObjectEntriesPage.backButton.click();

    await expect(page.getByRole('cell', { name: updateValue })).toBeVisible();

    // Delete
    await viewObjectEntriesPage.frontendDatasetActions.last().click();
    await viewObjectEntriesPage.frontendDatasetDeleteAction.click();
    await viewObjectEntriesPage.deletionConfirmationModal
        .getByRole('button', { name: 'Delete' })
        .click();

    await expect(page.getByRole('cell', { name: updateValue })).toBeAttached({attached: false});
}

test(
    'LPS-162131 Assert CRUD with created custom object using Salesforce storage type',
    {tag: '@LPS-162131'},
    async ({apiHelpers, page, viewObjectEntriesPage}) => {
        const objectFields = generateObjectFields({
            objectFieldBusinessTypes: [{
                businessType: 'Text',
                externalReferenceCode: 'Title__c',
                label: { en_US: 'Title' },
                name: 'title',
            }],
        });

        await runSalesforceCRUDTest({
            apiHelpers,
            page,
            objectConfig: {
                active: true,
                externalReferenceCode: 'Poshi_Test__c',
                label: { en_US: "Poshi Test" },
                name: "PoshiTest",
                objectFields,
                panelCategoryKey: 'control_panel.object',
                pluralLabel: { en_US: "Poshi Tests" },
                portlet: true,
                scope: 'company',
                status: { code: 0 },
                storageType: 'salesforce',
            },
			viewObjectEntriesPage
        });
    }
);

test(
    'LPS-185429 Assert CRUD with created standard object using Salesforce storage type',
    {tag: '@LPS-185429'},
    async ({apiHelpers, page, viewObjectEntriesPage}) => {
        const objectFields = generateObjectFields({
            objectFieldBusinessTypes: [
                { businessType: 'Text', externalReferenceCode: 'Email', label: { en_US: 'Email' }, name: 'email' },
                { businessType: 'Text', externalReferenceCode: 'FirstName', label: { en_US: 'First Name' }, name: 'firstName' },
                { businessType: 'Text', externalReferenceCode: 'LastName', label: { en_US: 'Last Name' }, name: 'lastName', required: true },
                { businessType: 'Text', externalReferenceCode: 'Phone', label: { en_US: 'Phone' }, name: 'phone' },
            ],
        });

        await runSalesforceCRUDTest({
            apiHelpers,
            page,
            fieldIndex: 2,
			objectConfig: {
				active: true,
				externalReferenceCode: "Contact",
				label: { en_US: "Contact" },
				name: "Contact",
				objectFields,
				panelCategoryKey: 'control_panel.object',
				pluralLabel: { en_US: "Contacts" },
				portlet: true,
				scope: 'company',
				status: { code: 0 },
				storageType: 'salesforce',
			},
            viewObjectEntriesPage,
            isStandardObject: true
        });
    }
);

test(
	'LPD-78504 Assert CRUD with form container using Salesforce storage type',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site, viewObjectEntriesPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Text',
					externalReferenceCode: 'Title__c',
					label: {en_US: 'Title'},
					name: 'title',
				},
			],
		});

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				enableFormContainer: true,
				externalReferenceCode: 'Poshi_Test__c',
				label: {en_US: 'Poshi Test'},
				name: 'PoshiTest' + getRandomInt(),
				objectFields,
				panelCategoryKey: 'control_panel.object',
				pluralLabel: {en_US: 'Poshi Tests'},
				portlet: true,
				scope: 'company',
				status: {code: 0},
				storageType: 'salesforce',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Create a content page with a form container fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({id: formId});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Map the form container to the Salesforce object

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.mapFormFragment(
			formId,
			objectDefinition.label['en_US'],
			['Title']
		);

		await pageEditorPage.publishPage();

		// Navigate to the published page and create an entry via the form

		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const entryValue = 'Entry added on form container';

		await page.getByRole('textbox', {name: 'Title'}).fill(entryValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Thank you. Your information was successfully received.'
			)
		).toBeVisible();

		// Verify the entry exists in the object admin

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('cell', {name: entryValue})
		).toBeVisible();

		// Delete the entry and verify it is removed

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await viewObjectEntriesPage.deletionConfirmationModal
			.getByRole('button', {name: 'Delete'})
			.click();

		await expect(
			page.getByRole('cell', {name: entryValue})
		).toBeAttached({attached: false});
	}
);