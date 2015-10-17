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
package de.mrapp.android.validation.constraints.text;

import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link ContainsNumberConstraint}.
 *
 * @author Michael Rapp
 */
public class ContainsNumberConstraintTest extends TestCase {

    /**
     * Tests the functionality of the isSatisfied-method, if it succeeds.
     */
    public final void testIsSatisfiedSucceeds() {
        ContainsNumberConstraint containsNumberConstraint = new ContainsNumberConstraint();
        assertTrue(containsNumberConstraint.isSatisfied("abc1abc"));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it fails.
     */
    public final void testIsSatisfiedFails() {
        ContainsNumberConstraint containsNumberConstraint = new ContainsNumberConstraint();
        assertFalse(containsNumberConstraint.isSatisfied("abcabc"));
    }

}