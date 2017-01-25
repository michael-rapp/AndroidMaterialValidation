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
package de.mrapp.android.validation.constraints;

import android.support.annotation.NonNull;

import de.mrapp.android.validation.Constraint;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A constraint, which allows to negate the result of an other constraint.
 *
 * @param <Type>
 *         The type of the values, which should be verified
 * @author Michael Rapp
 * @since 1.0.0
 */
public class NegateConstraint<Type> implements Constraint<Type> {

    /**
     * The constraint, whose result is negated.
     */
    private Constraint<Type> constraint;

    /**
     * Creates a new constraint, which allows to negate the result of an other constraint.
     *
     * @param constraint
     *         The constraint, whose result should be negated, as an instance of the type {@link
     *         Constraint}. The constraint may not be null
     */
    public NegateConstraint(@NonNull final Constraint<Type> constraint) {
        setConstraint(constraint);
    }

    /**
     * Creates and returns a constraint, which allows to negate the result of an other constraint.
     *
     * @param <Type>
     *         The type of the values, which should be verified
     * @param constraint
     *         The constraint, whose result should be negated, as an instance of the type {@link
     *         Constraint}. The constraint may not be null
     * @return The constraint, which has been created, as an instance of the class {@link
     * NegateConstraint}
     */
    public static final <Type> NegateConstraint<Type> create(
            @NonNull final Constraint<Type> constraint) {
        return new NegateConstraint<>(constraint);
    }

    /**
     * Returns the constraint, whose result is negated.
     *
     * @return The constraint, whose result is negated, as an instance of the type {@link
     * Constraint}
     */
    public final Constraint<Type> getConstraint() {
        return constraint;
    }

    /**
     * Sets the constraint, whose result should be negated.
     *
     * @param constraint
     *         The constraint, which should be set, as an instance of the type {@link Constraint}.
     *         The constraint may not be null
     */
    public final void setConstraint(@NonNull final Constraint<Type> constraint) {
        ensureNotNull(constraint, "The constraint may not be null");
        this.constraint = constraint;
    }

    @Override
    public final boolean isSatisfied(final Type value) {
        return !getConstraint().isSatisfied(value);
    }

}