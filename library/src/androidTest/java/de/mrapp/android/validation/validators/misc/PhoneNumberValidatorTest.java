/*
 * Copyright 2015 - 2017 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.validation.validators.misc;

import android.test.AndroidTestCase;

/**
 * Tests the functionality of the class {@link PhoneNumberValidator}.
 *
 * @author Michael Rapp
 */
public class PhoneNumberValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator(errorMessage);
        assertEquals(errorMessage, phoneNumberValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        PhoneNumberValidator phoneNumberValidator =
                new PhoneNumberValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, phoneNumberValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator("foo");
        assertTrue(phoneNumberValidator.validate(""));
        assertTrue(phoneNumberValidator.validate("123456"));
        assertTrue(phoneNumberValidator.validate("12345678901234"));
        assertTrue(phoneNumberValidator.validate("+1234567890123"));
        assertTrue(phoneNumberValidator.validate("+1 1234567890123"));
        assertTrue(phoneNumberValidator.validate("+12 123456789"));
        assertTrue(phoneNumberValidator.validate("+123 123456"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator("foo");
        assertFalse(phoneNumberValidator.validate("12345"));
        assertFalse(phoneNumberValidator.validate("123456789012345"));
        assertFalse(phoneNumberValidator.validate("abc"));
        assertFalse(phoneNumberValidator.validate("123abc"));
        assertFalse(phoneNumberValidator.validate("++1 12345667"));
        assertFalse(phoneNumberValidator.validate("123+123456"));
        assertFalse(phoneNumberValidator.validate("123 123456"));
    }

}