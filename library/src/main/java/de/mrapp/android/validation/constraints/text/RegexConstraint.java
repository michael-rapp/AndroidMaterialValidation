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
package de.mrapp.android.validation.constraints.text;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mrapp.android.validation.Constraint;

import static de.mrapp.android.validation.util.Condition.ensureNotNull;

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