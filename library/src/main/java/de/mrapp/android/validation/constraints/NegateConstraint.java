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

import static de.mrapp.android.validation.util.Condition.ensureNotNull;

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