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
 * Tests the functionality of the class {@link EmailAddressValidator}.
 *
 * @author Michael Rapp
 */
public class EmailAddressValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        EmailAddressValidator emailAddressValidator = new EmailAddressValidator(errorMessage);
        assertEquals(errorMessage, emailAddressValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        EmailAddressValidator emailAddressValidator =
                new EmailAddressValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, emailAddressValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        EmailAddressValidator emailAddressValidator = new EmailAddressValidator("foo");
        assertTrue(emailAddressValidator.validate(""));
        assertTrue(emailAddressValidator.validate("foo@bar.com"));
        assertTrue(emailAddressValidator.validate("foo-100@bar.com"));
        assertTrue(emailAddressValidator.validate("foo.100@bar.com"));
        assertTrue(emailAddressValidator.validate("foo100@bar.com"));
        assertTrue(emailAddressValidator.validate("foo-100@bar.net"));
        assertTrue(emailAddressValidator.validate("foo.100@bar.com.au"));
        assertTrue(emailAddressValidator.validate("foo@123.com"));
        assertTrue(emailAddressValidator.validate("foo@bar.com.com"));
        assertTrue(emailAddressValidator.validate("foo+100@bar.com"));
        assertTrue(emailAddressValidator.validate("foo-100@bar-test.com"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        EmailAddressValidator emailAddressValidator = new EmailAddressValidator("foo");
        assertFalse(emailAddressValidator.validate("foo"));
        assertFalse(emailAddressValidator.validate("foo@.com.my"));
        assertFalse(emailAddressValidator.validate("foo123@.com"));
        assertFalse(emailAddressValidator.validate("foo123@.com.com"));
        assertFalse(emailAddressValidator.validate("foo()*@bar.com"));
        assertFalse(emailAddressValidator.validate("foo@%*.com"));
        assertFalse(emailAddressValidator.validate("foo@bar@test.com"));
    }

}