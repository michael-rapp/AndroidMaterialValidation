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
 * Tests the functionality of the class {@link IPv6AddressValidator}.
 *
 * @author Michael Rapp
 */
public class IPv6AddressValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        IPv6AddressValidator iPv6AddressValidator = new IPv6AddressValidator(errorMessage);
        assertEquals(errorMessage, iPv6AddressValidator.getErrorMessage());
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        IPv6AddressValidator iPv6AddressValidator =
                new IPv6AddressValidator(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, iPv6AddressValidator.getErrorMessage());
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        IPv6AddressValidator iPv4AddressValidator = new IPv6AddressValidator("foo");
        assertTrue(iPv4AddressValidator.validate(""));
        assertTrue(iPv4AddressValidator.validate("FE80:0000:0000:0000:0202:B3FF:FE1E:8329"));
        assertTrue(iPv4AddressValidator.validate("FE80::0202:B3FF:FE1E:8329"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        IPv6AddressValidator iPv4AddressValidator = new IPv6AddressValidator("foo");
        assertFalse(iPv4AddressValidator.validate("FE80:0000:0000:0000:0202:B3XX:FE1E:8329"));
        assertFalse(iPv4AddressValidator.validate("FE80:0000:0000:0000:0202:B3FF:FE1E:8329:3492"));
    }

}