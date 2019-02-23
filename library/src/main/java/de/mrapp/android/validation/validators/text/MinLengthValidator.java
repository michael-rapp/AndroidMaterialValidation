/*
 * Copyright 2015 - 2019 Michael Rapp
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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import de.mrapp.android.validation.validators.AbstractValidator;
import de.mrapp.util.Condition;

/**
 * A validator, which allows to validate texts to ensure, that they have at least a specific
 * length.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class MinLengthValidator extends AbstractValidator<CharSequence> {

    /**
     * The minimum length a text must have.
     */
    private int minLength;

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they have at least a
     * specific length.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     */
    public MinLengthValidator(@NonNull final CharSequence errorMessage, final int minLength) {
        super(errorMessage);
        setMinLength(minLength);
    }

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they have at least a
     * specific length.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     */
    public MinLengthValidator(@NonNull final Context context, @StringRes final int resourceId,
                              final int minLength) {
        super(context, resourceId);
        setMinLength(minLength);
    }

    /**
     * Returns the minimum length a text must have.
     *
     * @return The minimum length a text must have as an {@link Integer} value
     */
    public final int getMinLength() {
        return minLength;
    }

    /**
     * Sets the minimum length a text must have.
     *
     * @param minLength
     *         The minimum length, which should be set, as an {@link Integer} value. The minimum
     *         length must be at least 1
     */
    public final void setMinLength(final int minLength) {
        Condition.INSTANCE.ensureAtLeast(minLength, 1, "The minimum length must be at least 1");
        this.minLength = minLength;
    }

    @Override
    public final boolean validate(final CharSequence value) {
        return value.length() >= getMinLength();
    }

}