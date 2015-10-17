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

import junit.framework.Assert;

/**
 * Tests the functionality of the class {@link MinLengthValidator}.
 *
 * @author Michael Rapp
 */
public class MinLengthValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        int minLength = 2;
        MinLengthValidator minLengthValidator = new MinLengthValidator(errorMessage, minLength);
        assertEquals(errorMessage, minLengthValidator.getErrorMessage());
        assertEquals(minLength, minLengthValidator.getMinLength());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a char sequence as a parameter, if the minimum length is less than 1.
     */
    public final void testConstructorWithCharSequenceParameterThrowsException() {
        try {
            new MinLengthValidator("foo", 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        int minLength = 2;
        MinLengthValidator minLengthValidator =
                new MinLengthValidator(getContext(), android.R.string.cancel, minLength);
        assertEquals(errorMessage, minLengthValidator.getErrorMessage());
        assertEquals(minLength, minLengthValidator.getMinLength());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a context and a resource ID as parameters, if the minimum value is less than 1.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsException() {
        try {
            new MinLengthValidator(getContext(), android.R.string.cancel, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the minimum length.
     */
    public final void testSetMinLength() {
        int minLength = 2;
        MinLengthValidator minLengthValidator = new MinLengthValidator("foo", 1);
        minLengthValidator.setMinLength(minLength);
        assertEquals(minLength, minLengthValidator.getMinLength());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * minimum length, if the length is less than 1.
     */
    public final void testSetMinLengthThrowsException() {
        try {
            MinLengthValidator minLengthValidator = new MinLengthValidator("foo", 1);
            minLengthValidator.setMinLength(0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        MinLengthValidator minLengthValidator = new MinLengthValidator("foo", 2);
        assertTrue(minLengthValidator.validate("12"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        MinLengthValidator minLengthValidator = new MinLengthValidator("foo", 2);
        assertFalse(minLengthValidator.validate("a"));
    }

}