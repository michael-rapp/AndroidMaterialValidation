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

import de.mrapp.android.validation.Constraint;

import static de.mrapp.android.util.Condition.ensureAtLeast;

/**
 * A constraint, which allows to verify texts in order to check, if they have at least a specific
 * length.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class MinLengthConstraint implements Constraint<CharSequence> {

    /**
     * The minimum length a text must have.
     */
    private int minLength;

    /**
     * Creates a new constraint, which allows to verify texts in order to check, if they have at
     * least a specific length.
     *
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     */
    public MinLengthConstraint(final int minLength) {
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
        ensureAtLeast(minLength, 1, "The minimum length must be at least 1");
        this.minLength = minLength;
    }

    @Override
    public final boolean isSatisfied(final CharSequence value) {
        return value.length() >= getMinLength();
    }

}