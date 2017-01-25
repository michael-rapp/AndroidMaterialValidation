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
 * Tests the functionality of the class {@link IRIValidator}.
 *
 * @author Michael Rapp
 */
public class IRIValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        IRIValidator iriValidator = new IRIValidator(errorMessage);
        assertEquals(errorMessage, iriValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        IRIValidator iriValidator = new IRIValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, iriValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        IRIValidator iriValidator = new IRIValidator("foo");
        assertTrue(iriValidator.validate(""));
        assertTrue(iriValidator.validate("http://www.foo.com"));
        assertTrue(iriValidator.validate("http://www.foo123.com"));
        assertTrue(iriValidator.validate("http://www.foo.com/bar/bar_2"));
        assertTrue(iriValidator.validate("http://www.foo123.com/bar/bar_2"));
        assertTrue(iriValidator.validate("http://foo.com"));
        assertTrue(iriValidator.validate("http://foo123.com"));
        assertTrue(iriValidator.validate("http://www.foo.com:8080"));
        assertTrue(iriValidator.validate("http://www.foo123.com:8080"));
        assertTrue(iriValidator.validate("http://www.foo.com.au"));
        assertTrue(iriValidator.validate("http://www.foo123.com.au"));
        assertTrue(iriValidator.validate("www.foo.com"));
        assertTrue(iriValidator.validate("www.foo123.com"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        IRIValidator iriValidator = new IRIValidator("foo");
        assertFalse(iriValidator.validate("http:/www.foo.com"));
        assertFalse(iriValidator.validate("http//www.foo.com"));
        assertFalse(iriValidator.validate("http://www..foo.com"));
        assertFalse(iriValidator.validate("http:/www.foo.com."));
        assertFalse(iriValidator.validate(".http:/www.foo.com"));
        assertFalse(iriValidator.validate("http:/www foo.com"));
    }

}