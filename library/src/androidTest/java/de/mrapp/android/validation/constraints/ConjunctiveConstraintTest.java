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
package de.mrapp.android.validation.constraints;

import junit.framework.Assert;
import junit.framework.TestCase;

import de.mrapp.android.validation.Constraint;

/**
 * Tests the functionality of the class {@link ConjunctiveConstraint}.
 *
 * @author Michael Rapp
 */
public class ConjunctiveConstraintTest extends TestCase {

    /**
     * An implementation of the interface {@link Constraint}, which is needed for test purposes.
     */
    private class ConstraintImplementation implements Constraint<Object> {

        /**
         * The result, which is returned by the constraint.
         */
        private final boolean result;

        /**
         * Creates a new class, which allows to verify, whether a type satisfies a constraint.
         *
         * @param result
         *         The result, which should be returned by the constraint
         */
        public ConstraintImplementation(final boolean result) {
            this.result = result;
        }

        @Override
        public boolean isSatisfied(final Object value) {
            return result;
        }

    }

    /**
     * Tests, if all properties are correctly initialized by the constructor.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructor() {
        Constraint<Object> constraint1 = new ConstraintImplementation(true);
        Constraint<Object> constraint2 = new ConstraintImplementation(true);
        Constraint<Object>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        ConjunctiveConstraint<Object> conjunctiveConstraint =
                new ConjunctiveConstraint<>(constraints);
        assertEquals(constraints, conjunctiveConstraint.getConstraints());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the constraints
     * are null.
     */
    public final void testConstructorThrowsNullPointerException() {
        try {
            new ConjunctiveConstraint<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, if the
     * constraints are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructorThrowsIllegalArgumentException() {
        try {
            new ConjunctiveConstraint<>(new Constraint[0]);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests, if all properties are correctly initialized by the factory method.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethod() {
        Constraint<Object> constraint1 = new ConstraintImplementation(true);
        Constraint<Object> constraint2 = new ConstraintImplementation(true);
        Constraint<Object>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        ConjunctiveConstraint<Object> conjunctiveConstraint =
                ConjunctiveConstraint.create(constraints);
        assertEquals(constraints, conjunctiveConstraint.getConstraints());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the factory method, if the
     * constraints are null.
     */
    public final void testFactoryMethodThrowsNullPointerException() {
        try {
            ConjunctiveConstraint.create(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the factory method, if the
     * constraints are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodThrowsIllegalArgumentException() {
        try {
            ConjunctiveConstraint.create(new Constraint[0]);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the constraints.
     */
    @SuppressWarnings("unchecked")
    public final void testSetConstraints() {
        Constraint<Object> constraint1 = new ConstraintImplementation(true);
        Constraint<Object> constraint2 = new ConstraintImplementation(true);
        Constraint<Object>[] constraints1 = new Constraint[1];
        constraints1[0] = constraint1;
        Constraint<Object>[] constraints2 = new Constraint[2];
        constraints2[0] = constraint1;
        constraints2[1] = constraint2;
        ConjunctiveConstraint<Object> conjunctiveConstraint =
                new ConjunctiveConstraint<>(constraints1);
        conjunctiveConstraint.setConstraints(constraints2);
        assertEquals(constraints2, conjunctiveConstraint.getConstraints());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * constraints, if the constraints are null.
     */
    @SuppressWarnings("unchecked")
    public final void testSetConstraintsThrowsNullPointerException() {
        try {
            Constraint<Object> constraint = new ConstraintImplementation(true);
            Constraint<Object>[] constraints1 = new Constraint[1];
            constraints1[0] = constraint;
            Constraint<Object>[] constraints2 = null;
            ConjunctiveConstraint<Object> conjunctiveConstraint =
                    new ConjunctiveConstraint<>(constraints1);
            conjunctiveConstraint.setConstraints(constraints2);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the constraints, if the constraints are null.
     */
    @SuppressWarnings("unchecked")
    public final void testSetConstraintsThrowsIllegalArgumentException() {
        try {
            Constraint<Object> constraint = new ConstraintImplementation(true);
            Constraint<Object>[] constraints1 = new Constraint[1];
            constraints1[0] = constraint;
            Constraint<Object>[] constraints2 = new Constraint[0];
            ConjunctiveConstraint<Object> conjunctiveConstraint =
                    new ConjunctiveConstraint<>(constraints1);
            conjunctiveConstraint.setConstraints(constraints2);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the isSatisfied-method, if all constraints are satisfied.
     */
    @SuppressWarnings("unchecked")
    public final void testIsSatisfiedWhenAllConstraintsAreSatisfied() {
        Constraint<Object> constraint1 = new ConstraintImplementation(true);
        Constraint<Object> constraint2 = new ConstraintImplementation(true);
        Constraint<Object>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        ConjunctiveConstraint<Object> conjunctiveConstraint =
                new ConjunctiveConstraint<>(constraints);
        assertTrue(conjunctiveConstraint.isSatisfied(new Object()));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if not all constraints are satisfied.
     */
    @SuppressWarnings("unchecked")
    public final void testIsSatisfiedWhenNotAllConstraintsAreSatisfied() {
        Constraint<Object> constraint1 = new ConstraintImplementation(true);
        Constraint<Object> constraint2 = new ConstraintImplementation(false);
        Constraint<Object>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        ConjunctiveConstraint<Object> conjunctiveConstraint =
                new ConjunctiveConstraint<>(constraints);
        assertFalse(conjunctiveConstraint.isSatisfied(new Object()));
    }

}