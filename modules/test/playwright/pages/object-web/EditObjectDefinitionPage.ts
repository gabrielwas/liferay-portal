/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditObjectDefinitionPage {

    readonly objectActionsTab : Locator;
	readonly page: Page;
   
	constructor(page: Page) {
        this.objectActionsTab = page.getByRole('link', { name: 'Actions' });
	}

	async clickObjectActionsTab() {
		await this.objectActionsTab.click();
	}

}
