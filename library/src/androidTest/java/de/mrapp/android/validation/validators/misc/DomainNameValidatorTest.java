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
 * Tests the functionality of the class {@link DomainNameValidator}.
 *
 * @author Michael Rapp
 */
public class DomainNameValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        DomainNameValidator domainNameValidator = new DomainNameValidator(errorMessage);
        assertEquals(errorMessage, domainNameValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        DomainNameValidator domainNameValidator =
                new DomainNameValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, domainNameValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        DomainNameValidator domainNameValidator = new DomainNameValidator("foo");
        assertTrue(domainNameValidator.validate(""));
        assertTrue(domainNameValidator.validate("www.foo.com"));
        assertTrue(domainNameValidator.validate("foo.com"));
        assertTrue(domainNameValidator.validate("foo123.com"));
        assertTrue(domainNameValidator.validate("foo-info.com"));
        assertTrue(domainNameValidator.validate("sub.foo.com"));
        assertTrue(domainNameValidator.validate("sub.foo-info.com"));
        assertTrue(domainNameValidator.validate("foo.com.au"));
        assertTrue(domainNameValidator.validate("sub.sub.foo.com"));
        assertTrue(domainNameValidator.validate("g.co"));
        assertTrue(domainNameValidator.validate("foo.t.t.co"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        DomainNameValidator domainNameValidator = new DomainNameValidator("foo");
        assertFalse(domainNameValidator.validate("foo.t.t.c"));
        assertFalse(domainNameValidator.validate("foo,com"));
        assertFalse(domainNameValidator.validate("foo"));
        assertFalse(domainNameValidator.validate("foo.123"));
        assertFalse(domainNameValidator.validate(".com"));
        assertFalse(domainNameValidator.validate("foo.a"));
        assertFalse(domainNameValidator.validate("foo.com/users"));
        assertFalse(domainNameValidator.validate("-foo.com"));
        assertFalse(domainNameValidator.validate("foo-.com"));
        assertFalse(domainNameValidator.validate("sub.-foo.com"));
        assertFalse(domainNameValidator.validate("sub.foo-.com"));
    }

}