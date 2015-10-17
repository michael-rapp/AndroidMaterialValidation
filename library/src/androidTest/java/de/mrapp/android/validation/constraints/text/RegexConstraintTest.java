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
            return;
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
            return;
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