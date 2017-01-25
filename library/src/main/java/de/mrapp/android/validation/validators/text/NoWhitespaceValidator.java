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
package de.mrapp.android.validation.validators.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import de.mrapp.android.validation.validators.AbstractValidator;

/**
 * A validator, which allows to validate texts to ensure, that they contain no whitespace. Empty
 * texts are also accepted.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class NoWhitespaceValidator extends AbstractValidator<CharSequence> {

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they contain no
     * whitespace.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     */
    public NoWhitespaceValidator(@NonNull final CharSequence errorMessage) {
        super(errorMessage);
    }

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they contain no
     * whitespace.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     */
    public NoWhitespaceValidator(@NonNull final Context context, @StringRes final int resourceId) {
        super(context, resourceId);
    }

    @Override
    public final boolean validate(final CharSequence value) {
        return !value.toString().contains(" ");
    }

}