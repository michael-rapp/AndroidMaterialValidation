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
package de.mrapp.android.validation.constraints;

import android.support.annotation.NonNull;

import de.mrapp.android.validation.Constraint;

import static de.mrapp.android.util.Condition.ensureAtLeast;
import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A validator, which allows to combine multiple constraints in a conjunctive manner. Only if all
 * single constraints are satisfied, the resulting constraint will also be satisfied.
 *
 * @param <Type>
 *         The type of the values, which should be verified
 * @author Michael Rapp
 * @since 1.0.0
 */
public class ConjunctiveConstraint<Type> implements Constraint<Type> {

    /**
     * A array, which contains the single constraints, the constraint consists of.
     */
    private Constraint<Type>[] constraints;

    /**
     * Creates a new constraint, which allows to combine multiple constraints in a conjunctive
     * manner.
     *
     * @param constraints
     *         The single constraints, the constraint should consist of, as an array of the type
     *         {@link Constraint}. The constraints may neither be null, nor empty
     */
    public ConjunctiveConstraint(@NonNull final Constraint<Type>[] constraints) {
        setConstraints(constraints);
    }

    /**
     * Creates and returns a constraint, which allows to combine multiple constraints in a
     * conjunctive manner.
     *
     * @param <Type>
     *         The type of the values, which should be verified
     * @param constraints
     *         The single constraints, the constraint should consist of, as an array of the type
     *         {@link Constraint}. The constraints may neither be null, nor empty
     * @return The constraint, which has been created, as an instance of the class {@link
     * ConjunctiveConstraint}
     */
    public static <Type> ConjunctiveConstraint<Type> create(
            @NonNull final Constraint<Type>[] constraints) {
        return new ConjunctiveConstraint<>(constraints);
    }

    /**
     * Returns the single constraints, the constraint consists of.
     *
     * @return The single constraints, the constraint consists of, as an array of the type {@link
     * Constraint}
     */
    public final Constraint<Type>[] getConstraints() {
        return constraints;
    }

    /**
     * Sets the single constraints, the constraint should consist of.
     *
     * @param constraints
     *         The single constraints, which should be set, as an array of the type {@link
     *         Constraint}. The constraints may neither be null, nor empty
     */
    public final void setConstraints(@NonNull final Constraint<Type>[] constraints) {
        ensureNotNull(constraints, "The constraints may not be null");
        ensureAtLeast(constraints.length, 1, "The constraints may not be empty");
        this.constraints = constraints;
    }

    @Override
    public final boolean isSatisfied(final Type value) {
        for (Constraint<Type> constraint : constraints) {
            if (!constraint.isSatisfied(value)) {
                return false;
            }
        }

        return true;
    }

}