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