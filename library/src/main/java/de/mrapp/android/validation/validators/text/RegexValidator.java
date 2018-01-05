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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mrapp.android.validation.validators.AbstractValidator;

import static de.mrapp.android.util.Condition.ensureNotNull;

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