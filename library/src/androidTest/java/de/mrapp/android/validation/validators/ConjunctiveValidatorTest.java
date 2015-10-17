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
package de.mrapp.android.validation.validators;

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import de.mrapp.android.validation.Validator;

/**
 * Tests the functionality of the class {@link ConjunctiveValidator}.
 *
 * @author Michael Rapp
 */
public class ConjunctiveValidatorTest extends AndroidTestCase {

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
    @SuppressWarnings("unchecked")
    public final void testConstructorWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                new ConjunctiveValidator<>(errorMessage, validators);
        assertEquals(errorMessage, conjunctiveValidator.getErrorMessage());
        assertEquals(validators, conjunctiveValidator.getValidators());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * char sequence as a parameter, if the validators are null.
     */
    public final void testConstructorWithCharSequenceParameterThrowsNullPointerException() {
        try {
            Validator<Object>[] validators = null;
            new ConjunctiveValidator<>("foo", validators);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a char sequence as a parameter, if the validators are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructorWithCharSequenceParameterThrowsIllegalArgumentException() {
        try {
            Validator<Object>[] validators = new Validator[0];
            new ConjunctiveValidator<>("foo", validators);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the constructor, which expects a
     * context and a resource ID as parameters.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructorWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                new ConjunctiveValidator<>(getContext(), android.R.string.cancel, validators);
        assertEquals(errorMessage, conjunctiveValidator.getErrorMessage());
        assertEquals(validators, conjunctiveValidator.getValidators());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, which expects a
     * context and a resource ID as parameters, if the validators are null.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsNullPointerException() {
        try {
            Validator<Object>[] validators = null;
            new ConjunctiveValidator<>(getContext(), android.R.string.cancel, validators);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a context and a resource ID as parameters, if the validators are empty.
     */
    public final void testConstructorWithContextAndResourceIdParametersThrowsIllegalArgumentException() {
        try {
            @SuppressWarnings("unchecked") Validator<Object>[] validators = new Validator[0];
            new ConjunctiveValidator<>(getContext(), android.R.string.cancel, validators);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the factory method, which expects a
     * char sequence as a parameter.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodWithCharSequenceParameter() {
        CharSequence errorMessage = "errorMessage";
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                ConjunctiveValidator.create(errorMessage, validators);
        assertEquals(errorMessage, conjunctiveValidator.getErrorMessage());
        assertEquals(validators, conjunctiveValidator.getValidators());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the factory method, which expects a
     * char sequence as a parameter, if the validators are null.
     */
    public final void testFactoryMethodWithCharSequenceParameterThrowsNullPointerException() {
        try {
            Validator<Object>[] validators = null;
            ConjunctiveValidator.create("foo", validators);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the factory method, which
     * expects a char sequence as a parameter, if the validators are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodWithCharSequenceParameterThrowsIllegalArgumentException() {
        try {
            Validator<Object>[] validators = new Validator[0];
            ConjunctiveValidator.create("foo", validators);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests, if all properties are correctly initialized by the factory method, which expects a
     * context and a resource ID as parameters.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodWithContextAndResourceIdParameters() {
        CharSequence errorMessage = getContext().getText(android.R.string.cancel);
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                ConjunctiveValidator.create(getContext(), android.R.string.cancel, validators);
        assertEquals(errorMessage, conjunctiveValidator.getErrorMessage());
        assertEquals(validators, conjunctiveValidator.getValidators());
    }

    /**
     * Ensures, that a {@link NullPointerException} is throw by the factory method, which expects a
     * context and a resource ID as parameters, if the validators are null.
     */
    public final void testFactoryMethodWithContextAndResourceIdParametersThrowsNullPointerException() {
        try {
            Validator<Object>[] validators = null;
            ConjunctiveValidator.create(getContext(), android.R.string.cancel, validators);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is throw by the factory method, which
     * expects a context and a resource ID as parameters, if the validators are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodWithContextAndResourceIdParametersThrowsIllegalArgumentException() {
        try {
            Validator<Object>[] validators = new Validator[0];
            ConjunctiveValidator.create(getContext(), android.R.string.cancel, validators);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the validators.
     */
    @SuppressWarnings("unchecked")
    public final void testSetValidators() {
        CharSequence errorMessage = "errorMessage";
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators1 = new Validator[1];
        validators1[0] = validator1;
        Validator<Object>[] validators2 = new Validator[2];
        validators2[0] = validator1;
        validators2[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                new ConjunctiveValidator<>(errorMessage, validators1);
        conjunctiveValidator.setValidators(validators2);
        assertEquals(validators2, conjunctiveValidator.getValidators());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * validators, if the validators are null.
     */
    @SuppressWarnings("unchecked")
    public final void testSetValidatorsThrowsNullPointerException() {
        try {
            CharSequence errorMessage = "errorMessage";
            Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
            Validator<Object>[] validators1 = new Validator[1];
            validators1[0] = validator;
            ConjunctiveValidator<Object> conjunctiveValidator =
                    new ConjunctiveValidator<>(errorMessage, validators1);
            Validator<Object>[] validators2 = null;
            conjunctiveValidator.setValidators(validators2);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link IllegalArgumentException} is thrown by the method, which allows to set
     * the validators, if the validators are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testSetValidatorsThrowsIllegalArgumentException() {
        try {
            CharSequence errorMessage = "errorMessage";
            Validator<Object> validator = new AbstractValidatorImplementation("foo", true);
            Validator<Object>[] validators1 = new Validator[1];
            validators1[0] = validator;
            ConjunctiveValidator<Object> conjunctiveValidator =
                    new ConjunctiveValidator<>(errorMessage, validators1);
            Validator<Object>[] validators2 = new Validator[0];
            conjunctiveValidator.setValidators(validators2);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the validate-method, if all validators succeed.
     */
    @SuppressWarnings("unchecked")
    public final void testValidateWhenAllValidatorsSucceed() {
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", true);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                new ConjunctiveValidator<>("foo", validators);
        assertTrue(conjunctiveValidator.validate(new Object()));
    }

    /**
     * Tests the functionality of the validate-method, if not all validators succeed.
     */
    @SuppressWarnings("unchecked")
    public final void testValidateWhenNotAllValidatorsSucceed() {
        Validator<Object> validator1 = new AbstractValidatorImplementation("foo", true);
        Validator<Object> validator2 = new AbstractValidatorImplementation("bar", false);
        Validator<Object>[] validators = new Validator[2];
        validators[0] = validator1;
        validators[1] = validator2;
        ConjunctiveValidator<Object> conjunctiveValidator =
                new ConjunctiveValidator<>("foo", validators);
        assertFalse(conjunctiveValidator.validate(new Object()));
    }

}