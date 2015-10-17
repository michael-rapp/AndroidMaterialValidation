/*
 * AndroidMaterialValidation Copyright 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
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