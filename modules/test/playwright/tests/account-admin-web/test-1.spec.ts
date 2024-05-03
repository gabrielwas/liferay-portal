import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByRole('button', { name: 'Sign In' }).click();
  await page.getByLabel('Email Address').click();
  await page.getByLabel('Email Address').click();
  await page.locator('div').filter({ hasText: 'Sign In- Loading Authentication failed. Please enable browser cookies and try ag' }).nth(1).click();
});