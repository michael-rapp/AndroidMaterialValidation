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
package de.mrapp.android.validation.constraints.text;

import de.mrapp.android.validation.Constraint;
import de.mrapp.util.Condition;

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
        Condition.INSTANCE.ensureAtLeast(minLength, 1, "The minimum length must be at least 1");
        this.minLength = minLength;
    }

    @Override
    public final boolean isSatisfied(final CharSequence value) {
        return value.length() >= getMinLength();
    }

}