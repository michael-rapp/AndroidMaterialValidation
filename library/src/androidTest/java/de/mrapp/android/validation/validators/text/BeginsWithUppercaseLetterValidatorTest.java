/*
 * Copyright 2015 - 2016 Michael Rapp
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

/**
 * Tests the functionality of the class {@link BeginsWithUppercaseLetterValidator}.
 *
 * @author Michael Rapp
 */
public class BeginsWithUppercaseLetterValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        BeginsWithUppercaseLetterValidator beginsWithUppercaseLetterValidator =
                new BeginsWithUppercaseLetterValidator(errorMessage);
        assertEquals(errorMessage, beginsWithUppercaseLetterValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        BeginsWithUppercaseLetterValidator beginsWithUppercaseLetterValidator =
                new BeginsWithUppercaseLetterValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, beginsWithUppercaseLetterValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        BeginsWithUppercaseLetterValidator beginsWithUppercaseLetterValidator =
                new BeginsWithUppercaseLetterValidator("foo");
        assertTrue(beginsWithUppercaseLetterValidator.validate(""));
        assertTrue(beginsWithUppercaseLetterValidator.validate("Abc123"));
        assertTrue(beginsWithUppercaseLetterValidator.validate("Übc123"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        BeginsWithUppercaseLetterValidator beginsWithUppercaseLetterValidator =
                new BeginsWithUppercaseLetterValidator("foo");
        assertFalse(beginsWithUppercaseLetterValidator.validate("abc123"));
        assertFalse(beginsWithUppercaseLetterValidator.validate("1Abc"));
        assertFalse(beginsWithUppercaseLetterValidator.validate("%Abc"));
    }

}
