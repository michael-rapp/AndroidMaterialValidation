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

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link MinLengthConstraint}.
 *
 * @author Michael Rapp
 */
public class MinLengthConstraintTest extends TestCase {

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        int minLength = 2;
        MinLengthConstraint minLengthConstraint = new MinLengthConstraint(minLength);
        assertEquals(minLength, minLengthConstraint.getMinLength());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, if the
     * minimum length is not at least 1.
     */
    public final void testConstructorThrowsException() {
        try {
            new MinLengthConstraint(0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the minimum length.
     */
    public final void testSetMinLength() {
        int minLength = 2;
        MinLengthConstraint minLengthConstraint = new MinLengthConstraint(1);
        minLengthConstraint.setMinLength(minLength);
        assertEquals(minLength, minLengthConstraint.getMinLength());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the minimum length, if the minimum length is not at least 1.
     */
    public final void testSetMinLengthThrowsException() {
        try {
            MinLengthConstraint minLengthConstraint = new MinLengthConstraint(1);
            minLengthConstraint.setMinLength(0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it succeeds.
     */
    public final void testIsSatisfiedSucceeds() {
        MinLengthConstraint minLengthConstraint = new MinLengthConstraint(2);
        assertTrue(minLengthConstraint.isSatisfied("12"));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it fails.
     */
    public final void testIsSatisfiedFails() {
        MinLengthConstraint minLengthConstraint = new MinLengthConstraint(2);
        assertFalse(minLengthConstraint.isSatisfied("1"));
    }

}