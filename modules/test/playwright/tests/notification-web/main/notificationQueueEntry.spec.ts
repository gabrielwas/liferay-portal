/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectActionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(apiHelpersTest, dataApiHelpersTest, loginTest());

test.describe('Notification queue entry', () => {
	test(
		'can delete a notification',
		{tag: '@LPD-78504'},
		async ({apiHelpers}) => {
			const templateName = 'Notification Template ' + getRandomInt();

			const notificationTemplate =
				await apiHelpers.notification.postRandomNotificationTemplate(
					templateName,
					'test@liferay.com'
				);

			apiHelpers.data.push({
				id: notificationTemplate.id,
				type: 'notificationTemplate',
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const objectActionAPIClient =
				await apiHelpers.buildRestClient(ObjectActionAPI);

			const objectAction =
				await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
					objectDefinition.externalReferenceCode,
					{
						active: true,
						label: {
							en_US: 'Custom Action',
						},
						name: 'CustomAction',
						objectActionExecutorKey: 'notification',
						objectActionTriggerKey: 'onAfterAdd',
						parameters: {
							notificationTemplateId: notificationTemplate.id,
							type: 'email',
						},
					}
				);

			apiHelpers.data.push({
				id: objectAction.body.id,
				type: 'objectAction',
			});

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Trigger'},
				applicationName
			);

			const notificationQueueEntries =
				await apiHelpers.notification.getNotificationQueueEntriesPage(
					objectDefinition.name
				);

			expect(notificationQueueEntries.items.length).toBeGreaterThan(0);

			const notificationQueueEntryId =
				notificationQueueEntries.items[0].id;

			await apiHelpers.notification.deleteNotificationQueueEntry(
				notificationQueueEntryId
			);

			const updatedNotificationQueueEntries =
				await apiHelpers.notification.getNotificationQueueEntriesPage(
					objectDefinition.name
				);

			const remainingIds = updatedNotificationQueueEntries.items.map(
				(item: any) => item.id
			);

			expect(remainingIds).not.toContain(notificationQueueEntryId);
		}
	);

	test(
		'can add email notification to queue via API',
		{tag: '@LPD-78504'},
		async () => {
			// This test requires a mock SMTP server which is not available
			// in the Playwright test infrastructure

			test.fixme();
		}
	);
});
