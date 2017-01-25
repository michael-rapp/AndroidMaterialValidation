/*
 * Copyright 2015 - 2017 Michael Rapp
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
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import de.mrapp.android.validation.Validator;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A validator, which allows to negate the result of an other validator.
 *
 * @param <Type>
 *         The type of the values, which should be validated
 * @author Michael Rapp
 * @since 1.0.0
 */
public class NegateValidator<Type> extends AbstractValidator<Type> {

    /**
     * The validator, whose result is negated.
     */
    private Validator<Type> validator;

    /**
     * Creates a new validator, which allows to negate the result of an other validator.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     */
    public NegateValidator(@NonNull final CharSequence errorMessage,
                           @NonNull final Validator<Type> validator) {
        super(errorMessage);
        setValidator(validator);
    }

    /**
     * Creates and returns a validator, which allows to negate the result of an other validator.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     * @return The validator, which has been created, as an instance of the class {@link
     * NegateValidator}
     */
    public static <Type> NegateValidator<Type> create(@NonNull final CharSequence errorMessage,
                                                      @NonNull final Validator<Type> validator) {
        return new NegateValidator<>(errorMessage, validator);
    }

    /**
     * Creates and returns a validator, which allows to negate the result of an other validator.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     * @return The validator, which has been created, as an instance of the class {@link
     * NegateValidator}
     */
    public static <Type> NegateValidator<Type> create(@NonNull final Context context,
                                                      @StringRes final int resourceId,
                                                      @NonNull final Validator<Type> validator) {
        return new NegateValidator<>(context, resourceId, validator);
    }

    /**
     * Creates a new validator, which allows to negate the result of an other validator.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     */
    public NegateValidator(@NonNull final Context context, @StringRes final int resourceId,
                           @NonNull final Validator<Type> validator) {
        super(context, resourceId);
        setValidator(validator);
    }

    /**
     * Returns the validator, whose result is negated.
     *
     * @return The validator, whose result is negated, as an instance of the type {@link Validator}
     */
    public final Validator<Type> getValidator() {
        return validator;
    }

    /**
     * Sets the validator, whose result should be negated.
     *
     * @param validator
     *         The validator, which should be set, as an instance of the type {@link Validator}. The
     *         validator may not be null
     */
    public final void setValidator(@NonNull final Validator<Type> validator) {
        ensureNotNull(validator, "The validator may not be null");
        this.validator = validator;
    }

    @Override
    public final boolean validate(final Type value) {
        return !getValidator().validate(value);
    }

}