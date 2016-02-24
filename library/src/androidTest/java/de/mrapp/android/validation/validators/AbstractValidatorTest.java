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
package de.mrapp.android.validation.validators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Tests the functionality of the class {@link AbstractValidator}.
 *
 * @author Michael Rapp
 */
public class AbstractValidatorTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link AbstractValidator}, which is needed for test
     * purposes.
     */
    private class AbstractValidatorImplementation extends AbstractValidator<Object> {

        /**
         * Creates a new validator, which should be able to validate values.
         *
         * @param errorMessage
         *         The error message, which should be shown, if the validation fails, as an instance
         *         of the type {@link CharSequence}. The error message may not be null
         */
        public AbstractValidatorImplementation(final CharSequence errorMessage) {
            super(errorMessage);
        }

        /**
         * Creates a new validator, which should be able to validate values.
         *
         * @param context
         *         The context, which should be used to retrieve the error message, as an instance
         *         of the class {@link Context}. The context may not be null
         * @param resourceId
         *         The resource ID of the string resource, which contains the error message, which
         *         should be set, as an {@link Integer} value. The resource ID must correspond to a
         *         valid string resource
         */
        public AbstractValidatorImplementation(final Context context, final int resourceId) {
            super(context, resourceId);
        }

        @Override
        public boolean validate(final Object value) {
            return false;
        }

    }

    ;

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation(errorMessage);
        assertEquals(errorMessage, abstractValidator.getErrorMessage());
        assertNull(abstractValidator.getIcon());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the char sequence is null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsNullPointerException() {
        try {
            new AbstractValidatorImplementation(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a char sequence as a parameter, if the char sequence is empty.
     */
    public final void testConstructorWithCharSequenceParameterThrowsIllegalArgumentException() {
        try {
            new AbstractValidatorImplementation("");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, abstractValidator.getErrorMessage());
        assertNull(abstractValidator.getIcon());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsException() {
        try {
            new AbstractValidatorImplementation(null, android.R.string.cancel);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the validator's error message by
     * passing a char sequence as a parameter.
     */
    public final void testSetErrorMessageWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation("foo");
        abstractValidator.setErrorMessage(errorMessage);
        assertEquals(errorMessage, abstractValidator.getErrorMessage());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * validator's error message by passing a char sequence as a parameter, if the error message is
     * null.
     */
    public final void testSetErrorMessageWithCharSequenceParameterThrowsNullPointerException() {
        try {
            AbstractValidatorImplementation abstractValidator =
                    new AbstractValidatorImplementation("foo");
            abstractValidator.setErrorMessage(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link IllegalArgumentException} is thrown by the method, which allows to set
     * the validator's error message by passing a char sequence as a parameter, if the error message
     * is empty.
     */
    public final void testSetErrorMessageWithCharSequenceParameterThrowsIllegalArgumentException() {
        try {
            AbstractValidatorImplementation abstractValidator =
                    new AbstractValidatorImplementation("foo");
            abstractValidator.setErrorMessage("");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the validator's error message by
     * passing a context and a resource ID as parameters.
     */
    public final void testSetErrorMessageWithContextAndResourceIdParameter() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation("foo");
        abstractValidator.setErrorMessage(getContext(), android.R.string.cancel);
        assertEquals(errorMessage, abstractValidator.getErrorMessage());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * validator's error message by passing a context and a resource ID as parameters, if the
     * context is null.
     */
    public final void testSetErrorMessageWithContextAndResourceIdParameterThrowsException() {
        try {
            AbstractValidatorImplementation abstractValidator =
                    new AbstractValidatorImplementation(getContext(), android.R.string.cancel);
            abstractValidator.setErrorMessage(null, android.R.string.cancel);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the validator's icon by passing a
     * drawable as a parameter.
     */
    @SuppressWarnings("deprecation")
    public final void testSetIconWithDrawableParameter() {
        Drawable icon = getContext().getResources().getDrawable(android.R.drawable.ic_dialog_info);
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation("foo");
        abstractValidator.setIcon(icon);
        assertEquals(icon, abstractValidator.getIcon());
    }

    /**
     * Tests the functionality of the method, which allows to set the validator's icon by passing a
     * context and a resource ID as parameters.
     */
    public final void testSetIconWithContextAndResourceIdParameters() {
        AbstractValidatorImplementation abstractValidator =
                new AbstractValidatorImplementation("foo");
        abstractValidator.setIcon(getContext(), android.R.drawable.ic_dialog_info);
        assertNotNull(abstractValidator.getIcon());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * validator's icon by passing a context and resource ID as a paramters, if the context is
     * null.
     */
    public final void testSetIconWithContextAndResourceIDParametersThrowsException() {
        try {
            AbstractValidatorImplementation abstractValidator =
                    new AbstractValidatorImplementation("foo");
            abstractValidator.setIcon(null, android.R.drawable.ic_dialog_info);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

}