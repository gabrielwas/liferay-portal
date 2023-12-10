import {HomePage} from '../home.page';

import {expect} from '@playwright/test';

export class FeatureFlagPage {
	constructor(page) {
		this.homePage = new HomePage(page);

		this.page = page;

		this.featureFlagLink = page.getByRole('link', {name: 'Feature Flags'});

		this.searchFor = page.getByPlaceholder('Search for');

		this.searchButton = page.getByLabel('Search', {exact: true});
	}

	async goto() {
		await this.homePage.goToInstanceSettings();
		await this.featureFlagLink.click();
	}

	async toggleFeatureFlag(featureFlag) {
		await this.goto();

		await expect(
			this.page.getByRole('heading', {name: 'Feature Flags'})
		).toBeVisible();

		await this.searchFor.click();
		await this.searchFor.fill(featureFlag);
		await this.searchButton.click();

		const labelFFTextContent = await this.page
			.locator('label')
			.textContent();

		if (labelFFTextContent.includes('Disable')) {
			await this.page
				.getByRole('listitem')
				.filter({hasText: featureFlag})
				.getByRole('switch')
				.click();
		}
	}
}
