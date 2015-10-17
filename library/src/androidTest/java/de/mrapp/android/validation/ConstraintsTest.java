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