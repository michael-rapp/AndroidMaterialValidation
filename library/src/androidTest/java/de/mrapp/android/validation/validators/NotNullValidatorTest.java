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
package de.mrapp.android.validation.validators;

import android.test.AndroidTestCase;

import de.mrapp.android.validation.R;

/**
 * Tests the functionality of the class {@link NotNullValidator}.
 *
 * @author Michael Rapp
 */
public class NotNullValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        NotNullValidator notNullValidator = new NotNullValidator(errorMessage);
        assertEquals(errorMessage, notNullValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        NotNullValidator notNullValidator =
                new NotNullValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, notNullValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        NotNullValidator notNullValidator = new NotNullValidator("foo");
        assertTrue(notNullValidator.validate(new Object()));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        NotNullValidator notNullValidator = new NotNullValidator("foo");
        assertFalse(notNullValidator.validate(null));
    }

}