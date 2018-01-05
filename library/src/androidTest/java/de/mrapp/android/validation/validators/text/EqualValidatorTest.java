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