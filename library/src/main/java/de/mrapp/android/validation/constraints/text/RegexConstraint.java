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
package de.mrapp.android.validation.constraints.text;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mrapp.android.validation.Constraint;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A constraint, which allows to verify a text in order to check, if it matches a certain regular
 * expression.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class RegexConstraint implements Constraint<CharSequence> {

    /**
     * The regular expression, which is used to verify the texts.
     */
    private Pattern regex;

    /**
     * Creates a new constraint, which allows to verify a text in order to check, if it matches a
     * certain regular expression.
     *
     * @param regex
     *         The regular expression, which should be used to verify the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     */
    public RegexConstraint(@NonNull final Pattern regex) {
        setRegex(regex);
    }

    /**
     * Returns the regular expression, which is used to verify the texts.
     *
     * @return The regular expression, which is used to verify the texts, as an instance of the
     * class {@link Pattern}
     */
    public final Pattern getRegex() {
        return regex;
    }

    /**
     * Sets the regular expression, which should be used to verify the texts.
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
    public final boolean isSatisfied(final CharSequence value) {
        Matcher matcher = getRegex().matcher(value);
        return matcher.matches();
    }

}