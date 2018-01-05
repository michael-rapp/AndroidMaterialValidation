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

import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link Constraints}.
 *
 * @author Michael Rapp
 */
public class ConstraintsTest extends TestCase {

    /**
     * Tests the functionality of the negate-method.
     */
    public final void testNegate() {
        assertNotNull(Constraints.negate(Constraints.minLength(1)));
    }

    /**
     * Tests the functionality of the conjunctive-method.
     */
    public final void testConjunctive() {
        assertNotNull(
                Constraints.conjunctive(Constraints.minLength(1), Constraints.containsLetter()));
    }

    /**
     * Tests the functionality of the disjunctive-method.
     */
    public final void testDisjunctive() {
        assertNotNull(
                Constraints.disjunctive(Constraints.minLength(1), Constraints.containsLetter()));
    }

    /**
     * Tests the functionality of the minLength-method.
     */
    public final void testMinLength() {
        assertNotNull(Constraints.minLength(1));
    }

    /**
     * Tests the functionality of the containsNumber-method.
     */
    public final void testContainsNumber() {
        assertNotNull(Constraints.containsNumber());
    }

    /**
     * Tests the functionality of the containsLetter-method.
     */
    public final void testContainsLetter() {
        assertNotNull(Constraints.containsLetter());
    }

    /**
     * Tests the functionality of the containsSymbol-method.
     */
    public final void testContainsSymbol() {
        assertNotNull(Constraints.containsSymbol());
    }

}