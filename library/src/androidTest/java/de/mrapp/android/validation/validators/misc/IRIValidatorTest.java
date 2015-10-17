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