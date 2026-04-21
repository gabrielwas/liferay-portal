/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AIAssistantChat,
	ChatContext,
} from '@liferay/ai-hub-cell-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {config} from '../../../app/config/index';

const HEADLESS_ADMIN_SITE_V1 = '/o/headless-admin-site/v1.0';

export default function AIAssistantSidebar() {
	const currentPageRef = useRef<unknown>(null);
	const [ready, setReady] = useState(false);

	const {layoutExternalReferenceCode, siteExternalReferenceCode} = config;

	useEffect(() => {
		let cancelled = false;

		async function loadCurrentPage() {
			try {
				const response = await fetch(
					`${HEADLESS_ADMIN_SITE_V1}/sites/${encodeURIComponent(
						siteExternalReferenceCode
					)}/site-pages/${encodeURIComponent(
						layoutExternalReferenceCode
					)}?nestedFields=pageSpecifications`
				);

				if (!response.ok) {
					throw new Error(
						`Failed to fetch current page: ${response.statusText}`
					);
				}

				const data = await response.json();

				if (!cancelled) {
					currentPageRef.current = data;
					setReady(true);
				}
			}
			catch (error) {
				console.warn(
					`AIAssistantSidebar: ${(error as Error).message}`
				);

				if (!cancelled) {
					setReady(true);
				}
			}
		}

		if (siteExternalReferenceCode && layoutExternalReferenceCode) {
			loadCurrentPage();
		}
		else {
			setReady(true);
		}

		return () => {
			cancelled = true;
		};
	}, [layoutExternalReferenceCode, siteExternalReferenceCode]);

	function getContext(): ChatContext {
		return {
			context: {
				current_page: currentPageRef.current,
			},
			instructionDefinitionScope: 'pageEditor',
		};
	}

	if (!ready) {
		return null;
	}

	return (
		<div className="p-3">
			<AIAssistantChat getContext={getContext} />
		</div>
	);
}
