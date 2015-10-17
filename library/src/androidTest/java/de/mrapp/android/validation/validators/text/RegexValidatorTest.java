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
package de.mrapp.android.validation.validators.text;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.regex.Pattern;

/**
 * Tests the functionality of the class {@link RegexValidator}.
 *
 * @author Michael Rapp
 */
public class RegexValidatorTest extends AndroidTestCase {

    /**
     * The pattern, which is used for test purposes.
     */
    private static final Pattern REGEX = Pattern.compile("[0-9]+");

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        RegexValidator regexValidator = new RegexValidator(errorMessage, REGEX);
        assertEquals(errorMessage, regexValidator.getErrorMessage());
        assertEquals(REGEX, regexValidator.getRegex());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if a regular expression, which is null, is passed.
     */
    public final void testConstructorWithCharSequenceParameterThrowsExeption() {
        try {
            new RegexValidator("foo", null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        RegexValidator regexValidator =
                new RegexValidator(getContext(), android.R.string.cancel, REGEX);
        assertEquals(errorMessage, regexValidator.getErrorMessage());
        assertEquals(REGEX, regexValidator.getRegex());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if a regular expression, which is null, is passed.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsException() {
        try {
            new RegexValidator(getContext(), android.R.string.cancel, null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set a regular expression.
     */
    public final void testSetRegex() {
        RegexValidator regexValidator = new RegexValidator("foo", Pattern.compile("."));
        regexValidator.setRegex(REGEX);
        assertEquals(REGEX, regexValidator.getRegex());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the regular expression is set to
     * null.
     */
    public final void testSetRegexThrowsException() {
        try {
            RegexValidator regexValidator = new RegexValidator("foo", Pattern.compile("."));
            regexValidator.setRegex(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        RegexValidator regexValidator = new RegexValidator("foo", REGEX);
        assertTrue(regexValidator.validate("0123456789"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        RegexValidator regexValidator = new RegexValidator("foo", REGEX);
        assertFalse(regexValidator.validate("abcdefghijkl"));
    }

}