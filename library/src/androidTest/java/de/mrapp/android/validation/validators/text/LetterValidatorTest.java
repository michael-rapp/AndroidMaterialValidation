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

/**
 * Tests the functionality of the class {@link LetterValidator}.
 */
public class LetterValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Case caseSensitivity = Case.CASE_INSENSITIVE;
        boolean allowSpaces = true;
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator(errorMessage, caseSensitivity, allowSpaces, allowedCharacters);
        assertEquals(errorMessage, characterValidator.getErrorMessage());
        assertEquals(caseSensitivity, characterValidator.getCaseSensitivity());
        assertEquals(allowSpaces, characterValidator.areSpacesAllowed());
        assertEquals(allowedCharacters, characterValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the case sensitivity is null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsExceptionWhenCaseSensitivityIsNull() {
        try {
            new LetterValidator("foo", null, true);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the allowed characters are null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsExceptionWhenAllowedCharactersAreNull() {
        try {
            new LetterValidator("foo", Case.CASE_INSENSITIVE, true, null);
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
        Case caseSensitivity = Case.CASE_INSENSITIVE;
        boolean allowSpaces = true;
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator(getContext(), android.R.string.cancel, caseSensitivity,
                        allowSpaces, allowedCharacters);
        assertEquals(errorMessage, characterValidator.getErrorMessage());
        assertEquals(caseSensitivity, characterValidator.getCaseSensitivity());
        assertEquals(allowSpaces, characterValidator.areSpacesAllowed());
        assertEquals(allowedCharacters, characterValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the case sensitivity is null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsExceptionWhenCaseSensitivityIsNull() {
        try {
            new LetterValidator(getContext(), android.R.string.cancel, null, true);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the allowed characters are is null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsExceptionWhenAllowedCharactersAreNull() {
        try {
            new LetterValidator(getContext(), android.R.string.cancel, Case.CASE_INSENSITIVE, true,
                    null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the case sensitivity.
     */
    public final void testSetCaseSensitivity() {
        Case caseSensitivity = Case.UPPERCASE;
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, true);
        characterValidator.setCaseSensitivity(caseSensitivity);
        assertEquals(caseSensitivity, characterValidator.getCaseSensitivity());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * case sensitivity, if the case sensitivity is null.
     */
    public final void testSetCaseSensitivityThrowsException() {
        try {
            LetterValidator characterValidator =
                    new LetterValidator("foo", Case.CASE_INSENSITIVE, true);
            characterValidator.setCaseSensitivity(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set, whether spaces should be allowed,
     * or not.
     */
    public final void testAllowSpaces() {
        boolean allowSpaces = false;
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, true);
        characterValidator.allowSpaces(allowSpaces);
        assertEquals(allowSpaces, characterValidator.areSpacesAllowed());
    }

    /**
     * Tests the functionality of the method, which allows to set the allowed characters.
     */
    public final void testSetAllowedCharacters() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, false);
        characterValidator.setAllowedCharacters(allowedCharacters);
        assertEquals(allowedCharacters, characterValidator.getAllowedCharacters());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * allowed characters, if the allowed characters are null.
     */
    public final void testSetAllowedCharactersThrowsException() {
        try {
            LetterValidator characterValidator =
                    new LetterValidator("foo", Case.CASE_INSENSITIVE, false);
            characterValidator.setAllowedCharacters(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and allowing spaces,
     * if it succeeds.
     */
    public final void testValidateCaseInsensitiveAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, true, allowedCharacters);
        assertTrue(characterValidator.validate("Ab C-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and allowing spaces,
     * if it fails.
     */
    public final void testValidateCaseInsensitiveAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, true, allowedCharacters);
        assertFalse(characterValidator.validate("Ab C2-"));
    }

    /**
     * Tests the functionality of the validate-method, using uppercase and allowing spaces, if it
     * succeeds.
     */
    public final void testValidateUppercaseAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.UPPERCASE, true, allowedCharacters);
        assertTrue(characterValidator.validate("AB C-"));
    }

    /**
     * Tests the functionality of the validate-method, using upperase and allowing spaces, if it
     * fails.
     */
    public final void testValidateUppercaseAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.UPPERCASE, true, allowedCharacters);
        assertFalse(characterValidator.validate("Ab C-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and allowing spaces, if it
     * succeeds.
     */
    public final void testValidateLowercaseAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.LOWERCASE, true, allowedCharacters);
        assertTrue(characterValidator.validate("ab c-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and allowing spaces, if it
     * fails.
     */
    public final void testValidateLowercaseAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.LOWERCASE, true, allowedCharacters);
        assertFalse(characterValidator.validate("Ab c-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and not allowing
     * spaces, if it succeeds.
     */
    public final void testValidateCaseInsensitiveNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, false, allowedCharacters);
        assertTrue(characterValidator.validate("AbC-"));
    }

    /**
     * Tests the functionality of the validate-method, using case insensitivity and not allowing
     * spaces, if it fails.
     */
    public final void testValidateCaseInsensitiveNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.CASE_INSENSITIVE, false, allowedCharacters);
        assertFalse(characterValidator.validate("Ab C2-"));
    }

    /**
     * Tests the functionality of the validate-method, using uppercase and not allowing spaces, if
     * it succeeds.
     */
    public final void testValidateUppercaseNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.UPPERCASE, false, allowedCharacters);
        assertTrue(characterValidator.validate("ABC-"));
    }

    /**
     * Tests the functionality of the validate-method, using upperase and not allowing spaces, if it
     * fails.
     */
    public final void testValidateUppercaseNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.UPPERCASE, false, allowedCharacters);
        assertFalse(characterValidator.validate("Ab C-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and not allowing spaces, if
     * it succeeds.
     */
    public final void testValidateLowercaseNotAllowingSpacesSucceeds() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertTrue(characterValidator.validate("abc-"));
    }

    /**
     * Tests the functionality of the validate-method, using lowercase and not allowing spaces, if
     * it fails.
     */
    public final void testValidateLowercaseNotAllowingSpacesFails() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertFalse(characterValidator.validate("ab c-"));
    }

    /**
     * Tests the functionality of the validate-method, when the text only contains allowed special
     * characters.
     */
    public final void testValidateContainsOnlyAllowedSpecialCharacters() {
        char[] allowedCharacters = new char[]{'-'};
        LetterValidator characterValidator =
                new LetterValidator("foo", Case.LOWERCASE, false, allowedCharacters);
        assertTrue(characterValidator.validate("----"));
    }

    /**
     * Tests the functionality of the validate-method, when the text is empty.
     */
    public final void testValidateWhenEmpty() {
        LetterValidator characterValidator = new LetterValidator("foo", Case.LOWERCASE, false);
        assertTrue(characterValidator.validate(""));
    }

}