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
package de.mrapp.android.validation;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

import de.mrapp.android.validation.constraints.ConjunctiveConstraint;
import de.mrapp.android.validation.constraints.DisjunctiveConstraint;
import de.mrapp.android.validation.constraints.NegateConstraint;
import de.mrapp.android.validation.constraints.text.ContainsLetterConstraint;
import de.mrapp.android.validation.constraints.text.ContainsNumberConstraint;
import de.mrapp.android.validation.constraints.text.ContainsSymbolConstraint;
import de.mrapp.android.validation.constraints.text.MinLengthConstraint;
import de.mrapp.android.validation.constraints.text.RegexConstraint;

/**
 * An utility class, which provides factory methods, which allow to create various constraints.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public final class Constraints {

    /**
     * Creates a new utility class, which provides factory methods, which allows to create various
     * validators.
     */
    private Constraints() {

    }

    /**
     * Creates and returns a constraint, which allows to negate the result of an other constraint.
     *
     * @param <Type>
     *         The type of the values, which should be verified
     * @param constraint
     *         The constraint, whose result should be negated, as an instance of the type {@link
     *         Constraint}. The constraint may not be null
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static <Type> Constraint<Type> negate(@NonNull final Constraint<Type> constraint) {
        return NegateConstraint.create(constraint);
    }

    /**
     * Creates and returns a constraint, which allows to combine multiple constraints in a
     * conjunctive manner. Only if all single constraints are satisfied, the resulting constraint
     * will also be satisfied.
     *
     * @param <Type>
     *         The type of the values, which should be verified
     * @param constraints
     *         The single constraints, the constraint should consist of, as an array of the type
     *         {@link Constraint}. The constraints may neither be null, nor empty
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    @SafeVarargs
    public static <Type> Constraint<Type> conjunctive(
            @NonNull final Constraint<Type>... constraints) {
        return ConjunctiveConstraint.create(constraints);
    }

    /**
     * Creates and returns a constraint, which allows to combine multiple constraints in a
     * disjunctive manner. If at least one constraint is satisfied, the resulting constraint will
     * also be satisfied.
     *
     * @param <Type>
     *         The type of the values, which should be verified
     * @param constraints
     *         The single constraints, the constraint should consist of, as an array of the type
     *         {@link Constraint}. The constraints may neither be null, nor empty
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    @SafeVarargs
    public static <Type> Constraint<Type> disjunctive(
            @NonNull final Constraint<Type>... constraints) {
        return DisjunctiveConstraint.create(constraints);
    }

    /**
     * Creates and returns a constraint, which allows to verify texts in order to check, if the
     * match a certain regular expression.
     *
     * @param regex
     *         The regular expression, which should be used to verify the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static Constraint<CharSequence> regex(@NonNull final Pattern regex) {
        return new RegexConstraint(regex);
    }

    /**
     * Creates and returns a constraint, which allows to verify texts in order to check, if they
     * have at least a specific length.
     *
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static Constraint<CharSequence> minLength(final int minLength) {
        return new MinLengthConstraint(minLength);
    }

    /**
     * Creates and returns a constraint, which allows to verify texts in order to check, if they
     * contain at least one number.
     *
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static Constraint<CharSequence> containsNumber() {
        return new ContainsNumberConstraint();
    }

    /**
     * Creates and returns a constraint, which allows to verify texts in order to check, if they
     * contain at least one letter.
     *
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static Constraint<CharSequence> containsLetter() {
        return new ContainsLetterConstraint();
    }

    /**
     * Creates and returns a constraint, which allows to verify texts in order to check, if they
     * contain at least one symbol. Symbols are considered to be all characters except lower and
     * uppercase letters from A to Z and numbers.
     *
     * @return The constraint, which has been created, as an instance of the type {@link Constraint}
     */
    public static Constraint<CharSequence> containsSymbol() {
        return new ContainsSymbolConstraint();
    }

}