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