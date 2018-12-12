AUI.add(
	'journal-color-picker-form-field',
	function(A) {
		var JournalColorPickerField = A.Component.create(
			{
				ATTRS: {
					mask: {
				        value: '%I:%M %p'
				    },
				    placeholder: {
				        value: ''
				    },
					type: {
						value: 'journal-color-picker-form-field'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'journal-color-picker-form-field',

				prototype: {
					render: function() {
						var instance = this;
				
						JournalColorPickerField.superclass.render.apply(instance, arguments);
				
						instance.timePicker = new A.TimePicker(
							{
								trigger: instance.getInputSelector(),
								mask: instance.get('mask'),
								popover: {
									zIndex: 1
								},
								after: {
									selectionChange: A.bind('afterSelectionChange', instance)
								}
							}
						);
					},
					
					getTemplateContext: function() {
					    var instance = this;

					    return A.merge(
					    		JournalColorPickerField.superclass.getTemplateContext.apply(instance, arguments),
					        {
					            placeholder: instance.get('placeholder')
					        }
					    );
					},
				
					afterSelectionChange: function(event) {
						var instance = this;
				
						var time = event.newSelection;
				
						instance.set('value', time);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').JournalColorPicker = JournalColorPickerField;
	},
	'',
	{
		requires: ['aui-timepicker', 'liferay-ddm-form-renderer-field']
	}
);