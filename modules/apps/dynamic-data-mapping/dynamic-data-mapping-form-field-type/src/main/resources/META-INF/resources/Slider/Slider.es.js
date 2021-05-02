import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import React, {useEffect, useRef, useState} from 'react';

import {ClayInput} from '@clayui/form';

/**
 * Slider React Component
 */

const getValue = (value) => {
    if (!value) {
        return '';
    }

    if (typeof value === 'string') {
        return value;
    }

    return JSON.stringify(value);
};

const Slider = ({name, onChange, predefinedValue, value, id, onFocus, placeholder, editingLanguageId}) => {

    const [currentValue, setCurrentValue] = useSyncValue(
        value ? value : predefinedValue
    );

    useEffect(() => {

        const idText = document.getElementById("myRange");
        const autocomplete = new google.maps.places.Autocomplete(idText);

        autocomplete.setFields(['address_component', 'formatted_address']);

        autocomplete.addListener('place_changed', () => {

            const place = autocomplete.getPlace();

            if (place != null) {

                const addressComponents = place.address_components;

                const address = {
                    formatted_address: place.formatted_address,
                    administrative_area_level_1: 'short_name',
                    country: 'long_name',
                    locality: 'long_name',
                    postal_code: 'short_name',
                    route: 'long_name',
                    street_number: 'short_name',
                };

                for (let i = 0; i < addressComponents.length; i++) {
                    const addressType = addressComponents[i].types[0];

                    if (address[addressType]) {
                        address[addressType] =
                            addressComponents[i][address[addressType]];
                    }
                }

                setCurrentValue(address)

            }

        });

    }, []);

    return (
        <>
            <ClayInput
                className="ddm-field-text"
                disabled={false}
                id={"myRange"}
                lang={editingLanguageId}
                onChange={(event) => {
                    onChange(event);
                }}
                onFocus={onFocus}
                placeholder={placeholder}
                type="text"
                value={currentValue ? currentValue.formatted_address : ''}
            />

            <ClayInput
                id={id}
                name={name}
                placeholder={placeholder}
                type="hidden"
                value={currentValue}
            />
        </>
    );

    }

const Main = ({autocomplete,
                  autocompleteEnabled,
                  defaultLanguageId,
                  displayStyle = 'singleline',
                  editingLanguageId,
                  fieldName,
                  id,
                  localizable,
                  localizedValue = {},
                  name,
                  onBlur,
                  onChange,
                  onFocus,
                  options = [],
                  placeholder,
                  predefinedValue = '',
                  readOnly,
                  shouldUpdateValue = false,
                  syncDelay = true,
                  value,
                  ...otherProps}) =>{

    return <FieldBase
        {...otherProps}
        id={id}
        localizedValue={localizedValue}
        name={name}
        readOnly={readOnly}
      >
        <Slider 
            name={name}
            onChange={onChange}
            predefinedValue={predefinedValue}
            readOnly={readOnly}
            value={value ? value : predefinedValue}
            id={id}
            onFocus={onFocus}
            placeholder={placeholder}
            editingLanguageId={editingLanguageId}
        />
    </FieldBase>
}
Main.displayName = 'Slider';
export default Main;
