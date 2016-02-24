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