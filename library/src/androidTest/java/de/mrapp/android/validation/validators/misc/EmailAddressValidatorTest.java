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