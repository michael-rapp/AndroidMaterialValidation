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