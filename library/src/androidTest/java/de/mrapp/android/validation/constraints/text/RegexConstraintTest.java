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

import java.util.regex.Pattern;

/**
 * Tests the functionality of the class {@link RegexConstraint}.
 *
 * @author Michael Rapp
 */
public class RegexConstraintTest extends TestCase {

    /**
     * The pattern, which is used for test purposes.
     */
    private static final Pattern REGEX = Pattern.compile("[0-9]+");

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        RegexConstraint regexConstraint = new RegexConstraint(REGEX);
        assertEquals(REGEX, regexConstraint.getRegex());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the regular
     * expression is null.
     */
    public final void testConstructorThrowsException() {
        try {
            new RegexConstraint(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the regular expression.
     */
    public final void testSetRegex() {
        RegexConstraint regexConstraint = new RegexConstraint(Pattern.compile("."));
        regexConstraint.setRegex(REGEX);
        assertEquals(REGEX, regexConstraint.getRegex());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * regular expression, if the regular expression is null.
     */
    public final void testSetRegexThrowsException() {
        try {
            RegexConstraint regexConstraint = new RegexConstraint(Pattern.compile("."));
            regexConstraint.setRegex(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it succeeds.
     */
    public final void testIsSatisfiedSucceeds() {
        RegexConstraint regexConstraint = new RegexConstraint(REGEX);
        assertTrue(regexConstraint.isSatisfied("0123456789"));
    }

    /**
     * Tests the functionality of the isSatisfied-method, if it fails.
     */
    public final void testIsSatisfiedFails() {
        RegexConstraint regexConstraint = new RegexConstraint(REGEX);
        assertFalse(regexConstraint.isSatisfied("abcdefghijkl"));
    }

}