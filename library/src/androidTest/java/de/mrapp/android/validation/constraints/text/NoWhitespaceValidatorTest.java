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
package de.mrapp.android.validation.constraints.text;

import android.test.AndroidTestCase;

import de.mrapp.android.validation.validators.text.NoWhitespaceValidator;

/**
 * Tests the functionality of the class {@link NoWhitespaceValidator}.
 *
 * @author Michael Rapp
 */
public class NoWhitespaceValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        NoWhitespaceValidator noWhitespaceValidator = new NoWhitespaceValidator(errorMessage);
        assertEquals(errorMessage, noWhitespaceValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        NoWhitespaceValidator noWhitespaceValidator =
                new NoWhitespaceValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, noWhitespaceValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        NoWhitespaceValidator noWhitespaceValidator = new NoWhitespaceValidator("foo");
        assertTrue(noWhitespaceValidator.validate("abcabc"));
        assertTrue(noWhitespaceValidator.validate(""));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        NoWhitespaceValidator noWhitespaceValidator = new NoWhitespaceValidator("foo");
        assertFalse(noWhitespaceValidator.validate("abc abc"));
        assertFalse(noWhitespaceValidator.validate("abc "));
        assertFalse(noWhitespaceValidator.validate(" abc"));
        assertFalse(noWhitespaceValidator.validate("abc    abc"));
    }

}