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

import de.mrapp.android.validation.EditText;

/**
 * Tests the functionality of the class {@link EqualValidator}.
 *
 * @author Michael Rapp
 */
public class EqualValidatorTest extends AndroidTestCase {

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        EditText editText = new EditText(getContext());
        EqualValidator equalValidator = new EqualValidator(errorMessage, editText);
        assertEquals(errorMessage, equalValidator.getErrorMessage());
        assertEquals(editText, equalValidator.getEditText());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a char sequence as a parameter, if the edit text widget is null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsException() {
        try {
            new EqualValidator("foo", null);
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
        EditText editText = new EditText(getContext());
        EqualValidator equalValidator =
                new EqualValidator(getContext(), android.R.string.cancel, editText);
        assertEquals(errorMessage, equalValidator.getErrorMessage());
        assertEquals(editText, equalValidator.getEditText());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a context and a resource ID as parameters, if the edit text widget is null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsException() {
        try {
            new EqualValidator(getContext(), android.R.string.cancel, null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the edit text widget.
     */
    public final void testSetEditText() {
        EditText editText = new EditText(getContext());
        EqualValidator equalValidator = new EqualValidator("foo", new EditText(getContext()));
        equalValidator.setEditText(editText);
        assertEquals(editText, equalValidator.getEditText());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * edit text widget, if the edit text widget is null.
     */
    public final void testSetEditTextThrowsException() {
        try {
            EqualValidator equalValidator = new EqualValidator("foo", new EditText(getContext()));
            equalValidator.setEditText(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, if it succeeds.
     */
    public final void testValidateSucceeds() {
        EditText editText = new EditText(getContext());
        editText.setText("abc");
        EqualValidator equalValidator = new EqualValidator("foo", editText);
        assertTrue(equalValidator.validate("abc"));
    }

    /**
     * Tests the functionality of the validate-method, if it fails.
     */
    public final void testValidateFails() {
        EditText editText = new EditText(getContext());
        editText.setText("abc");
        EqualValidator equalValidator = new EqualValidator("foo", editText);
        assertFalse(equalValidator.validate("123"));
    }

}