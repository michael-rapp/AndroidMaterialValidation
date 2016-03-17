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
 * Tests the functionality of the class {@link NegateConstraint}.
 *
 * @author Michael Rapp
 */
public class NegateConstraintTest extends TestCase {

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
     * Tests, if all properties are set correctly be the constructor.
     */
    public final void testConstructor() {
        Constraint<Object> constraint = new ConstraintImplementation(true);
        NegateConstraint<Object> negateConstraint = new NegateConstraint<>(constraint);
        assertEquals(constraint, negateConstraint.getConstraint());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the constraint
     * is null.
     */
    public final void testConstructorThrowsException() {
        try {
            new NegateConstraint<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests, if all properties are set correctly by the factory method.
     */
    public final void testFactoryMethod() {
        Constraint<Object> constraint = new ConstraintImplementation(true);
        NegateConstraint<Object> negateConstraint = NegateConstraint.create(constraint);
        assertEquals(constraint, negateConstraint.getConstraint());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the factory method, if the
     * constraint is null.
     */
    public final void testFactoryMethodThrowsException() {
        try {
            NegateConstraint.create(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set a constraint.
     */
    public final void testSetConstraint() {
        Constraint<Object> constraint = new ConstraintImplementation(true);
        NegateConstraint<Object> negateConstraint =
                NegateConstraint.create(new ConstraintImplementation(false));
        negateConstraint.setConstraint(constraint);
        assertEquals(constraint, negateConstraint.getConstraint());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set a
     * constraint, if the constraint is null.
     */
    public final void testSetConstraintThrowsException() {
        try {
            NegateConstraint<Object> negateConstraint =
                    NegateConstraint.create(new ConstraintImplementation(false));
            negateConstraint.setConstraint(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the isSatisfied-method, if the constraint is satisfied.
     */
    public final void testIsSatisfiedWhenConstraintIsSatisfied() {
        Constraint<Object> constraint = new ConstraintImplementation(true);
        NegateConstraint<Object> negateConstraint = NegateConstraint.create(constraint);
        assertFalse(negateConstraint.isSatisfied(new Object()));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if the constraint is not satisfied.
     */
    public final void testIsSatisfiedWhenConstraintIsNotSatisfied() {
        Constraint<Object> constraint = new ConstraintImplementation(false);
        NegateConstraint<Object> negateConstraint = NegateConstraint.create(constraint);
        assertTrue(negateConstraint.isSatisfied(new Object()));
    }

}