import {test} from '@playwright/test';

import { FeatureFlagPage } from '../pages/instanceSettings/featureFlag.page';

exports.test = test.extend({
    _featureFlagPage:  async ({page}, use) => {
		await use(new FeatureFlagPage(page));
	},
});