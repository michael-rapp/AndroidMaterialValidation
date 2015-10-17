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