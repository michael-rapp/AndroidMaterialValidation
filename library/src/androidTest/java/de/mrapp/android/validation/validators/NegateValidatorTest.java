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
import android.test.AndroidTestCase;

import junit.framework.Assert;

import de.mrapp.android.validation.R;
import de.mrapp.android.validation.Validator;

/**
 * Tests the functionality of the class {@link NegateValidator}.
 *
 * @author Michael Rapp
 */
public class NegateValidatorTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link AbstractValidator}, which is needed for test
     * purposes.
     */
    private class AbstractValidatorImplementation extends AbstractValidator<Object> {

        /**
         * The result, which is returned by the validator.
         */
        private final boolean result;

        /**
         * Creates a new validator, which should be able to validate values.
         *
         * @param errorMessage
         *         The error message, which should be shown, if the validation fails, as an instance
         *         of the type {@link CharSequence}. The error message may not be null
         * @param result
         *         The result, which should be returned by the validator
         */
        public AbstractValidatorImplementation(final CharSequence errorMessage,
                                               final boolean result) {
            super(errorMessage);
            this.result = result;
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
         * @param result
         *         The result, which should be returned by the validator
         */
        public AbstractValidatorImplementation(final Context context, final int resourceId,
                                               final boolean result) {
            super(context, resourceId);
            this.result = result;
        }

        @Override
        public boolean validate(final Object value) {
            return result;
        }

    }

    ;

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a char
     * sequence as a parameter.
     */
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator = new NegateValidator<>(errorMessage, validator);
        assertEquals(errorMessage, negateValidator.getErrorMessage());
        assertEquals(validator, negateValidator.getValidator());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the validator is null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsException() {
        try {
            Validator<Object> validator = null;
            new NegateValidator<>("foo", validator);
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
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator =
                new NegateValidator<>(getContext(), android.R.string.cancel, validator);
        assertEquals(errorMessage, negateValidator.getErrorMessage());
        assertEquals(validator, negateValidator.getValidator());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the validators are null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsException() {
        try {
            Validator<Object> validator = null;
            new NegateValidator<>(getContext(), android.R.string.cancel, validator);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the factory method, which expects a
     * char sequence as a parameter.
     */
    public final void testFactoryMethodWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator = NegateValidator.create(errorMessage, validator);
        assertEquals(errorMessage, negateValidator.getErrorMessage());
        assertEquals(validator, negateValidator.getValidator());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the factory method, which expects a
     * char sequence as a parameter, if the validator is null.
     */
    public final void testFactoryMethodWithCharSequenceParameterThrowsException() {
        try {
            Validator<Object> validator = null;
            NegateValidator.create("foo", validator);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the factory method, which expects a
     * context and a resource ID as parameters.
     */
    public final void testFactoryMethodWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator =
                NegateValidator.create(getContext(), android.R.string.cancel, validator);
        assertEquals(errorMessage, negateValidator.getErrorMessage());
        assertEquals(validator, negateValidator.getValidator());
    }

    /**
     * Ensures, that a {@link NullPointerException} is throw by the factory method, which expects a
     * context and a resource ID as parameters, if the validator is null.
     */
    public final void testFactoryMethodWithContextAndResourceIdParametersThrowsException() {
        try {
            Validator<Object> validator = null;
            NegateValidator.create(getContext(), android.R.string.cancel, validator);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set a validator.
     */
    public final void testSetValidator() {
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator =
                new NegateValidator<>("foo", new AbstractValidatorImplementation("bar", false));
        negateValidator.setValidator(validator);
        assertEquals(validator, negateValidator.getValidator());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set a
     * validator, if the validator is null.
     */
    public final void testSetValidatorThrowsException() {
        try {
            NegateValidator<Object> negateValidator =
                    new NegateValidator<>("foo", new AbstractValidatorImplementation("bar", false));
            negateValidator.setValidator(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, if the validator succeeds.
     */
    public final void testValidateWhenValidatorSucceeds() {
        Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
        NegateValidator<Object> negateValidator = new NegateValidator<>("foo", validator);
        assertFalse(negateValidator.validate(new Object()));
    }

    /**
     * Tests the functionality of the validate-method, if the validator fails.
     */
    public final void testValidateWhenValidatorFails() {
        Validator<Object> validator = new AbstractValidatorImplementation("foo", false);
        NegateValidator<Object> negateValidator = new NegateValidator<>("foo", validator);
        assertTrue(negateValidator.validate(new Object()));
    }

}