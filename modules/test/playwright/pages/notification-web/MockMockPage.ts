/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

export interface MockMockEmailMatcher {
	body?: string;
	from?: string;
	subject: string;
	to?: string;
}

export class MockMockPage {
	readonly deleteAllLink: Locator;
	readonly emptyHeading: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.deleteAllLink = page.locator(
			'a.delete[href="/mail/delete/all"]'
		);
		this.emptyHeading = page.getByRole('heading', {
			name: 'No emails in queue',
		});
		this.page = page;
	}

	async assertEmail(
		expected: MockMockEmailMatcher,
		{timeoutMs = 15_000}: {timeoutMs?: number} = {}
	) {
		await this.waitForSubject(expected.subject, timeoutMs);

		await this.page
			.getByRole('link', {name: expected.subject})
			.first()
			.click();

		await expect(
			this.page.getByRole('heading', {name: expected.subject})
		).toBeVisible();

		const addresses = this.page.locator('[name="addresses"]');

		if (expected.from !== undefined) {
			await expect(addresses).toContainText(expected.from);
		}

		if (expected.to !== undefined) {
			await expect(addresses).toContainText(expected.to);
		}

		if (expected.body !== undefined) {
			const body = this.page
				.locator(
					'[name="bodyPlainText"] .well, [name="bodyHTML_Unformatted"] .well'
				)
				.first();

			await expect(body).toContainText(expected.body);
		}
	}

	async deleteAllEmails() {
		await this.page.goto(`${this.url()}/mail/delete/all`);
	}

	async goto() {
		await this.page.goto(this.url());
	}

	private url(): string {
		return liferayConfig.environment.baseUrl.replace(
			/:([0-9]*)$/,
			':8282'
		);
	}

	private async waitForSubject(subject: string, timeoutMs: number) {
		const start = Date.now();

		while (true) {
			await this.goto();

			const link = this.page
				.getByRole('link', {name: subject})
				.first();

			if ((await link.count()) > 0) {
				return;
			}

			if (Date.now() - start >= timeoutMs) {
				throw new Error(
					`Timed out waiting for email with subject "${subject}" in MockMock inbox.`
				);
			}

			await this.page.waitForTimeout(500);
		}
	}
}
