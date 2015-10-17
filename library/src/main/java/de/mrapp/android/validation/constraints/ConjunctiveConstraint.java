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