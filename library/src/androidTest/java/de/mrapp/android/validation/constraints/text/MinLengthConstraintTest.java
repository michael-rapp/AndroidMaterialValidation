/*
 * Copyright 2015 - 2017 Michael Rapp
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