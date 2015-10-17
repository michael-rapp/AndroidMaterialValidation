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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mrapp.android.validation.validators.AbstractValidator;

import static de.mrapp.android.validation.util.Condition.ensureNotNull;

/**
 * A validator, which allows to validate texts to ensure, that they match certain regular
 * expressions.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class RegexValidator extends AbstractValidator<CharSequence> {

    /**
     * The regular expression, which is used to validate the texts.
     */
    private Pattern regex;

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they only contain
     * numbers.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param regex
     *         The regular expression, which should be used to validate the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     */
    public RegexValidator(@NonNull final CharSequence errorMessage, @NonNull final Pattern regex) {
        super(errorMessage);
        setRegex(regex);
    }

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they only contain
     * numbers.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param regex
     *         The regular expression, which should be used to validate the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     */
    public RegexValidator(@NonNull final Context context, @StringRes final int resourceId,
                          @NonNull final Pattern regex) {
        super(context, resourceId);
        setRegex(regex);
    }

    /**
     * Returns the regular expression, which is used to validate the texts.
     *
     * @return The regular expression, which is used to validate the texts, as an instance of the
     * class {@link Pattern}
     */
    public final Pattern getRegex() {
        return regex;
    }

    /**
     * Sets the regular expression, which should be used to validate the texts.
     *
     * @param regex
     *         The regular expression, which should be set, as an instance of the class {@link
     *         Pattern}. The regular expression may not be null
     */
    public final void setRegex(@NonNull final Pattern regex) {
        ensureNotNull(regex, "The regular expression may not be null");
        this.regex = regex;
    }

    @Override
    public final boolean validate(final CharSequence value) {
        Matcher matcher = getRegex().matcher(value);
        return matcher.matches();
    }

}