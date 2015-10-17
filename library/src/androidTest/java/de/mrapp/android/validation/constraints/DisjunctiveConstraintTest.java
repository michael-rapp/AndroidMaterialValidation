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

import junit.framework.Assert;
import junit.framework.TestCase;

import de.mrapp.android.validation.Constraint;

/**
 * Tests the functionality of the class {@link DisjunctiveConstraint}.
 *
 * @author Michael Rapp
 */
public class DisjunctiveConstraintTest extends TestCase {

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

    ;

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
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                new DisjunctiveConstraint<>(constraints);
        assertEquals(constraints, disjunctiveConstraint.getConstraints());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the constraints
     * are null.
     */
    public final void testConstructorThrowsNullPointerException() {
        try {
            new DisjunctiveConstraint<>(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, if the
     * constraints are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructorThrowsIllegalArgumentException() {
        try {
            new DisjunctiveConstraint<>(new Constraint[0]);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
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
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                DisjunctiveConstraint.create(constraints);
        assertEquals(constraints, disjunctiveConstraint.getConstraints());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the factory method, if the
     * constraints are null.
     */
    public final void testFactoryMethodThrowsNullPointerException() {
        try {
            DisjunctiveConstraint.create(null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the factory method, if the
     * constraints are empty.
     */
    @SuppressWarnings("unchecked")
    public final void testFactoryMethodThrowsIllegalArgumentException() {
        try {
            DisjunctiveConstraint.create(new Constraint[0]);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
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
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                new DisjunctiveConstraint<>(constraints1);
        disjunctiveConstraint.setConstraints(constraints2);
        assertEquals(constraints2, disjunctiveConstraint.getConstraints());
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
            DisjunctiveConstraint<Object> disjunctiveConstraint =
                    new DisjunctiveConstraint<>(constraints1);
            disjunctiveConstraint.setConstraints(constraints2);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
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
            DisjunctiveConstraint<Object> disjunctiveConstraint =
                    new DisjunctiveConstraint<>(constraints1);
            disjunctiveConstraint.setConstraints(constraints2);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
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
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                new DisjunctiveConstraint<>(constraints);
        assertTrue(disjunctiveConstraint.isSatisfied(new Object()));
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
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                new DisjunctiveConstraint<>(constraints);
        assertTrue(disjunctiveConstraint.isSatisfied(new Object()));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if no constraints are satisfied.
     */
    @SuppressWarnings("unchecked")
    public final void testIsSatisfiedWhenNoConstraintsAreSatisfied() {
        Constraint<Object> constraint1 = new ConstraintImplementation(false);
        Constraint<Object> constraint2 = new ConstraintImplementation(false);
        Constraint<Object>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        DisjunctiveConstraint<Object> disjunctiveConstraint =
                new DisjunctiveConstraint<>(constraints);
        assertFalse(disjunctiveConstraint.isSatisfied(new Object()));
    }

}