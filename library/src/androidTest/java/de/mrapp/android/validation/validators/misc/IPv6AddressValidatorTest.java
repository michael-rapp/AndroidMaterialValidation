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