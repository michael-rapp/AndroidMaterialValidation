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
package de.mrapp.android.validation.validators.text;

import android.test.AndroidTestCase;

/**
 * Tests the functionality of the class {@link NotEmptyValidator}.
 *
 * @author Michael Rapp
 */
public class NotEmptyValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        NotEmptyValidator notEmptyValidator = new NotEmptyValidator(errorMessage);
        assertEquals(errorMessage, notEmptyValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        NotEmptyValidator notEmptyValidator =
                new NotEmptyValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, notEmptyValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        NotEmptyValidator notEmptyValidator = new NotEmptyValidator("foo");
        assertTrue(notEmptyValidator.validate("abc"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        NotEmptyValidator notEmptyValidator = new NotEmptyValidator("foo");
        assertFalse(notEmptyValidator.validate(""));
    }

}