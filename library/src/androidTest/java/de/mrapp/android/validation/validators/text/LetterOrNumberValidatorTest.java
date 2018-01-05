/*
 * Copyright 2015 - 2018 Michael Rapp
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

import junit.framework.Assert;

/**
 * Tests the functionality of the class {@link LetterOrNumberValidator}.
 */
public class LetterOrNumberValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Case caseSensitivity = Case.CASE_INSENSITIVE;
        boolean allowSpaces = true;
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator(errorMessage, caseSensitivity, allowSpaces,
                        allowedCharacters);
        assertEquals(errorMessage, characterOrNumberValidator.getErrorMessage());
        assertEquals(caseSensitivity, characterOrNumberValidator.getCaseSensitivity());
        assertEquals(allowSpaces, characterOrNumberValidator.areSpacesAllowed());
        assertEquals(allowedCharacters, characterOrNumberValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the case sensitivity is null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsExceptionWhenCaseSensitivityIsNull() {
        try {
            new LetterOrNumberValidator("foo", null, true);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the allowed character are null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsExceptionWhenAllowedCharactersAreNull() {
        try {
            new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        Case caseSensitivity = Case.CASE_INSENSITIVE;
        boolean allowSpaces = true;
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator(getContext(), android.R.string.cancel, caseSensitivity,
                        allowSpaces, allowedCharacters);
        assertEquals(errorMessage, characterOrNumberValidator.getErrorMessage());
        assertEquals(caseSensitivity, characterOrNumberValidator.getCaseSensitivity());
        assertEquals(allowSpaces, characterOrNumberValidator.areSpacesAllowed());
        assertEquals(allowedCharacters, characterOrNumberValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the case sensitivity is null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsExceptionWhenCaseSensitivityIsNull() {
        try {
            new LetterOrNumberValidator(getContext(), android.R.string.cancel, null, true);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the allowed characters are null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsExceptionWhenAllowedCharactersAreNull() {
        try {
            new LetterOrNumberValidator(getContext(), android.R.string.cancel,
                    Case.CASE_INSENSITIVE, true, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the case sensitivity.
     */
    public final void testSetCaseSensitivity() {
        Case caseSensitivity = Case.UPPERCASE;
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true);
        characterOrNumberValidator.setCaseSensitivity(caseSensitivity);
        assertEquals(caseSensitivity, characterOrNumberValidator.getCaseSensitivity());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * case sensitivity, if the case sensitivity is null.
     */
    public final void testSetCaseSensitivityThrowsException() {
        try {
            LetterOrNumberValidator characterOrNumberValidator =
                    new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true);
            characterOrNumberValidator.setCaseSensitivity(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set, whether spaces should be allowed,
     * or not.
     */
    public final void testAllowSpaces() {
        boolean allowSpaces = false;
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true);
        characterOrNumberValidator.allowSpaces(allowSpaces);
        assertEquals(allowSpaces, characterOrNumberValidator.areSpacesAllowed());
    }

    /**
     * Tests the functionality of the method, which allows to set the allowed characters.
     */
    public final void testSetAllowedCharacters() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, false);
        characterOrNumberValidator.setAllowedCharacters(allowedCharacters);
        assertEquals(allowedCharacters, characterOrNumberValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * allowed characters, if the allowed characters are null.
     */
    public final void testSetAllowedCharactersThrowsException() {
        try {
            LetterOrNumberValidator characterOrNumberValidator =
                    new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, false);
            characterOrNumberValidator.setAllowedCharacters(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and allowing spaces,
     * if it succeeds.
     */
    public final void testValidateCaseInsensitiveAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("Ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and allowing spaces,
     * if it fails.
     */
    public final void testValidateCaseInsensitiveAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, true, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("Ab C&-"));
    }

    /**
     * Tests the functionality of the validate-method, using uppercase and allowing spaces, if it
     * succeeds.
     */
    public final void testValidateUppercaseAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.UPPERCASE, true, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("AB 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using upperase and allowing spaces, if it
     * fails.
     */
    public final void testValidateUppercaseAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.UPPERCASE, true, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("Ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and allowing spaces, if it
     * succeeds.
     */
    public final void testValidateLowercaseAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, true, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and allowing spaces, if it
     * fails.
     */
    public final void testValidateLowercaseAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, true, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("Ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and not allowing
     * spaces, if it succeeds.
     */
    public final void testValidateCaseInsensitiveNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, false, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("Ab1-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and not allowing
     * spaces, if it fails.
     */
    public final void testValidateCaseInsensitiveNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.CASE_INSENSITIVE, false, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("Ab 1&-"));
    }

    /**
     * Tests the functionality of the validate-method, using uppercase and not allowing spaces, if
     * it succeeds.
     */
    public final void testValidateUppercaseNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.UPPERCASE, false, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("AB1-"));
    }

    /**
     * Tests the functionality of the validate-method, using upperase and not allowing spaces, if it
     * fails.
     */
    public final void testValidateUppercaseNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.UPPERCASE, false, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("Ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and not allowing spaces, if
     * it succeeds.
     */
    public final void testValidateLowercaseNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("ab1-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and not allowing spaces, if
     * it fails.
     */
    public final void testValidateLowercaseNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertFalse(characterOrNumberValidator.validate("ab 1-"));
    }

    /**
     * Tests the functionality of the validate-method, when the text only contains allowed special
     * characters.
     */
    public final void testValidateContainsOnlyAllowedSpecialCharacters() {
        char[] allowedCharacters = new char[]{'-'};
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertTrue(characterOrNumberValidator.validate("----"));
    }

    /**
     * Tests the functionality of the validate-method, when the text is empty.
     */
    public final void testValidateWhenEmpty() {
        LetterOrNumberValidator characterOrNumberValidator =
                new LetterOrNumberValidator("foo", Case.LOWERCASE, false);
        assertTrue(characterOrNumberValidator.validate(""));
    }

}