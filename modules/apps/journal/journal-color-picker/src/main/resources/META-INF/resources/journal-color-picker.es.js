import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './journal-color-picker.soy';

/**
 * JournalColorPicker Component
 */
class JournalColorPicker extends Component {}

// Register component
Soy.register(JournalColorPicker, templates, 'render');

if (!window.DDMJournalColorPicker) {
	window.DDMJournalColorPicker = {

	};
}

window.DDMJournalColorPicker.render = JournalColorPicker;

export default JournalColorPicker;