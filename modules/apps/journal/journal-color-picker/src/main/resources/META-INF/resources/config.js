;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-journal-color-picker': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'journal-color-picker-form-field': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'journal-color-picker_field.js',
							requires: [ 'aui-timepicker',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/journal-color-picker/'
				}
			}
		}
	);
})();