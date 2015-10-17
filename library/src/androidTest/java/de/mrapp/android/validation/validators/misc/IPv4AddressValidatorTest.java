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
 * Tests the functionality of the class {@link IPv4AddressValidator}.
 *
 * @author Michael Rapp
 */
public class IPv4AddressValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        IPv4AddressValidator iPv4AddressValidator = new IPv4AddressValidator(errorMessage);
        assertEquals(errorMessage, iPv4AddressValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        IPv4AddressValidator iPv4AddressValidator =
                new IPv4AddressValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, iPv4AddressValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        IPv4AddressValidator iPv4AddressValidator = new IPv4AddressValidator("foo");
        assertTrue(iPv4AddressValidator.validate(""));
        assertTrue(iPv4AddressValidator.validate("1.1.1.1"));
        assertTrue(iPv4AddressValidator.validate("255.255.255.255"));
        assertTrue(iPv4AddressValidator.validate("192.168.1.1"));
        assertTrue(iPv4AddressValidator.validate("10.10.1.1"));
        assertTrue(iPv4AddressValidator.validate("132.254.111.10"));
        assertTrue(iPv4AddressValidator.validate("26.10.2.10"));
        assertTrue(iPv4AddressValidator.validate("127.0.0.1"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        IPv4AddressValidator iPv4AddressValidator = new IPv4AddressValidator("foo");
        assertFalse(iPv4AddressValidator.validate("10.10.10"));
        assertFalse(iPv4AddressValidator.validate("10.10"));
        assertFalse(iPv4AddressValidator.validate("10"));
        assertFalse(iPv4AddressValidator.validate("a.a.a.a"));
        assertFalse(iPv4AddressValidator.validate("10.0.0.a"));
        assertFalse(iPv4AddressValidator.validate("10.10.10.256"));
        assertFalse(iPv4AddressValidator.validate("222.222.2.999"));
        assertFalse(iPv4AddressValidator.validate("999.10.10.20"));
        assertFalse(iPv4AddressValidator.validate("2222.22.22.22"));
        assertFalse(iPv4AddressValidator.validate("22.2222.22.2"));
    }

}