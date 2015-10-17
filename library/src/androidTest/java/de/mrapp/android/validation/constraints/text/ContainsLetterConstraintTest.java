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
 * Tests the functionality of the class {@link ContainsLetterConstraint}.
 *
 * @author Michael Rapp
 */
public class ContainsLetterConstraintTest extends TestCase {

    /**
     * Tests the functionality of the isSatisfied-method, if it succeeds.
     */
    public final void testIsSatisfiedSucceeds() {
        ContainsLetterConstraint containsLetterConstraint = new ContainsLetterConstraint();
        assertTrue(containsLetterConstraint.isSatisfied("123a123"));
        assertTrue(containsLetterConstraint.isSatisfied("123A123"));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it fails.
     */
    public final void testIsSatisfiedFails() {
        ContainsLetterConstraint containsLetterConstraint = new ContainsLetterConstraint();
        assertFalse(containsLetterConstraint.isSatisfied("123123"));
    }

}