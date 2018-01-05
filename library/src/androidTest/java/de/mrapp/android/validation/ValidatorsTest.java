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

import android.test.AndroidTestCase;

import java.util.regex.Pattern;

import de.mrapp.android.validation.validators.text.Case;

/**
 * Tests the functionality of the class {@link Validators}.
 *
 * @author Michael Rapp
 */
public class ValidatorsTest extends AndroidTestCase {

    /**
     * Tests the functionality of the negate-method, which expects a char sequence as a parameter.
     */
    public final void testNegateWithCharSequenceParameter() {
        assertNotNull(Validators.negate("foo", Validators.notEmpty("foo")));
    }

    /**
     * Tests the functionality of the negate-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testNegateWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .negate(getContext(), android.R.string.cancel, Validators.notEmpty("foo")));
    }

    /**
     * Tests the functionality of the negate-method, which expects a context as a parameter.
     */
    public final void testNegateWithContextParameter() {
        assertNotNull(Validators.negate(getContext(), Validators.notEmpty("foo")));
    }

    /**
     * Tests the functionality of the conjunctive-method, which expects a char sequence as a
     * parameter.
     */
    public final void testConjunctiveWithCharSequenceParameter() {
        assertNotNull(Validators
                .conjunctive("foo", Validators.notEmpty("foo"), Validators.minLength("foo", 1)));
    }

    /**
     * Tests the functionality of the conjunctive-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testConjunctiveWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .conjunctive(getContext(), android.R.string.cancel, Validators.notEmpty("foo"),
                        Validators.minLength("foo", 1)));
    }

    /**
     * Tests the functionality of the conjunctive-method, which expects a context as a parameter.
     */
    public final void testConjunctiveWithContextParameter() {
        assertNotNull(Validators.conjunctive(getContext(), Validators.notEmpty("foo")));
    }

    /**
     * Tests the functionality of the disjunctive-method, which expects a char sequence as a
     * parameter.
     */
    public final void testDisjunctiveWithCharSequenceParameter() {
        assertNotNull(Validators
                .disjunctive("foo", Validators.notEmpty("foo"), Validators.minLength("foo", 1)));
    }

    /**
     * Tests the functionality of the disjunctive-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testDisjunctiveWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .disjunctive(getContext(), android.R.string.cancel, Validators.notEmpty("foo"),
                        Validators.minLength("foo", 1)));
    }

    /**
     * Tests the functionality of the disjunctive-method, which expects a context as a parameter.
     */
    public final void testDisjunctiveWithContextParameter() {
        assertNotNull(Validators.disjunctive(getContext(), Validators.notEmpty("foo")));
    }

    /**
     * Tests the functionality of the notNull-method, which expects a char sequence as a parameter.
     */
    public final void testNotNullWithCharSequenceParameter() {
        assertNotNull(Validators.notNull("foo"));
    }

    /**
     * Tests the functionality of the notNull-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testNotNullWithContextAndResourceIdParameters() {
        assertNotNull(Validators.notNull(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the notNull-method, which expects a context as a parameter.
     */
    public final void testNotNullWithContextParameter() {
        assertNotNull(Validators.notNull(getContext()));
    }

    /**
     * Tests the functionality of the regex-method, which expects a char sequence as a parameter.
     */
    public final void testRegexWithCharSequenceParameter() {
        assertNotNull(Validators.regex("foo", Pattern.compile(".")));
    }

    /**
     * Tests the functionality of the regex-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testRegexWithContextAndResourceIdParameters() {
        assertNotNull(
                Validators.regex(getContext(), android.R.string.cancel, Pattern.compile(".")));
    }

    /**
     * Tests the functionality of the regex-method, which expects a context as a parameter.
     */
    public final void testRegexWithContextParameter() {
        assertNotNull(Validators.regex(getContext(), Pattern.compile(".")));
    }

    /**
     * Tests the functionality of the notEmpty-method, which expects a char sequence as a
     * parameter.
     */
    public final void testNotEmptyWithCharSequenceParameter() {
        assertNotNull(Validators.notEmpty("foo"));
    }

    /**
     * Tests the functionality of the notEmpty-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testNotEmptyWithContextAndResourceIdParameters() {
        assertNotNull(Validators.notEmpty(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the notEmpty-method, which expects a context as a parameter.
     */
    public final void testNotEmptyWithContextParameter() {
        assertNotNull(Validators.notEmpty(getContext()));
    }

    /**
     * Tests the functionality of the minLength-method, which expects a char sequence as a
     * parameter.
     */
    public final void testMinLengthWithCharSequenceParameter() {
        assertNotNull(Validators.minLength("foo", 1));
    }

    /**
     * Tests the functionality of the minLength-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testMinLengthWithContextAndResourceIdParameters() {
        assertNotNull(Validators.minLength(getContext(), android.R.string.cancel, 1));
    }

    /**
     * Tests the functionality of the minLength-method, which expects a context as a parameter.
     */
    public final void testMinLengthWithContextParameter() {
        assertNotNull(Validators.minLength(getContext(), 1));
    }

    /**
     * Tests the functionality of the maxLength-method, which expects a char sequence as a
     * parameter.
     */
    public final void testMaxLengthWithCharSequenceParameter() {
        assertNotNull(Validators.maxLength("foo", 1));
    }

    /**
     * Tests the functionality of the maxLength-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testMaxLengthWithContextAndResourceIdParameters() {
        assertNotNull(Validators.maxLength(getContext(), android.R.string.cancel, 1));
    }

    /**
     * Tests the functionality of the maxLength-method, which expects a context as a parameter.
     */
    public final void testMaxLengthWithContextParameter() {
        assertNotNull(Validators.maxLength(getContext(), 1));
    }

    /**
     * Tests the functionality of the noWhitespace-method, which expects a char sequence as a
     * parameter.
     */
    public final void testNoWhitespaceWithCharSequenceParameter() {
        assertNotNull(Validators.noWhitespace("foo"));
    }

    /**
     * Tests the functionality of the noWhitespace-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testNoWhitespaceWithContextAndResourceIdParameters() {
        assertNotNull(Validators.noWhitespace(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the noWhitespace-method, which expects a context as a parameter.
     */
    public final void testNoWhitespaceWithContextParameter() {
        assertNotNull(Validators.noWhitespace(getContext()));
    }

    /**
     * Tests the functionality of the number-method, which expects a char sequence as a parameter.
     */
    public final void testNumberWithCharSequenceParameter() {
        assertNotNull(Validators.number("foo"));
    }

    /**
     * Tests the functionality of the number-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testNumberWithContextAndResourceIdParameters() {
        assertNotNull(Validators.number(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the number-method, which expects a context as a parameter.
     */
    public final void testNumberWithContextParameter() {
        assertNotNull(Validators.number(getContext()));
    }

    /**
     * Tests the functionality of the letter-method, which expects a char sequence as a parameter.
     */
    public final void testLetterWithCharSequenceParameter() {
        assertNotNull(Validators.letter("foo", Case.CASE_INSENSITIVE, true, new char[]{'-'}));
    }

    /**
     * Tests the functionality of the letter-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testLetterWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .letter(getContext(), android.R.string.cancel, Case.CASE_INSENSITIVE, true,
                        new char[]{'-'}));
    }

    /**
     * Tests the functionality of the letter-method, which expects a context as a parameter.
     */
    public final void testLetterWithContextParameter() {
        assertNotNull(
                Validators.letter(getContext(), Case.CASE_INSENSITIVE, true, new char[]{'-'}));
    }

    /**
     * Tests the functionality of the letterOrNumber-method, which expects a char sequence as a
     * parameter.
     */
    public final void testLetterOrNumberWithCharSequenceParameter() {
        assertNotNull(
                Validators.letterOrNumber("foo", Case.CASE_INSENSITIVE, true, new char[]{'-'}));
    }

    /**
     * Tests the functionality of the letterOrNumber-method, which expects a context and a resource
     * id as parameters.
     */
    public final void testLetterOrNumberWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .letterOrNumber(getContext(), android.R.string.cancel, Case.CASE_INSENSITIVE, true,
                        new char[]{'-'}));
    }

    /**
     * Tests the functionality of the letterOrNumber-method, which expects a context as a
     * parameter.
     */
    public final void testLetterOrNumberWithContextParameter() {
        assertNotNull(Validators
                .letterOrNumber(getContext(), Case.CASE_INSENSITIVE, true, new char[]{'-'}));
    }

    /**
     * Tests the functionality of the beginsWithUppercaseLetter-method, which expects a char
     * sequence as a parameter.
     */
    public final void testBeginsWithUppercaseLetterWithCharSequenceParameter() {
        assertNotNull(Validators.beginsWithUppercaseLetter("foo"));
    }

    /**
     * Tests the functionality of the beginsWithUppercaseLetter-method, which expects a context and
     * a resource id as parameters.
     */
    public final void testBeginsWithUppercaseLetterWithContextAndResourceIdParameters() {
        assertNotNull(Validators.beginsWithUppercaseLetter(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the beginsWithUppercaseLetter-method, which expects a context as a
     * parameter.
     */
    public final void testBeginsWithUppercaseLetterWithContextParameter() {
        assertNotNull(Validators.beginsWithUppercaseLetter(getContext()));
    }

    /**
     * Tests the functionality of the equal-method, which expects a char sequence as a parameter.
     */
    public final void testEqualWithCharSequenceParameter() {
        assertNotNull(Validators.equal("foo", new EditText(getContext())));
    }

    /**
     * Tests the functionality of the equal-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testEqualWithContextAndResourceIdParameters() {
        assertNotNull(Validators
                .equal(getContext(), android.R.string.cancel, new EditText(getContext())));
    }

    /**
     * Tests the functionality of the equal-method, which expects a context as a parameter.
     */
    public final void testEqualWithContextParameter() {
        assertNotNull(Validators.equal(getContext(), new EditText(getContext())));
    }

    /**
     * Tests the functionality of the iPv4Address-method, which expects a char sequence as a
     * parameter.
     */
    public final void testIPv4AddressWithCharSequenceParameter() {
        assertNotNull(Validators.iPv4Address("foo"));
    }

    /**
     * Tests the functionality of the iPv4Address-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testIPv4AddressWithContextAndResourceIdParameters() {
        assertNotNull(Validators.iPv4Address(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the iPv4Address-method, which expects a context as a parameter.
     */
    public final void testIPv4AddressWithContextParameter() {
        assertNotNull(Validators.iPv4Address(getContext()));
    }

    /**
     * Tests the functionality of the iPv6Address-method, which expects a char sequence as a
     * parameter.
     */
    public final void testIPv6AddressWithCharSequenceParameter() {
        assertNotNull(Validators.iPv6Address("foo"));
    }

    /**
     * Tests the functionality of the iPv6Address-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testIPv6AddressWithContextAndResourceIdParameters() {
        assertNotNull(Validators.iPv6Address(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the iPv6Address-method, which expects a context as a parameter.
     */
    public final void testIPv6AddressWithContextParameter() {
        assertNotNull(Validators.iPv6Address(getContext()));
    }

    /**
     * Tests the functionality of the domainName-method, which expects a char sequence as a
     * parameter.
     */
    public final void testDomainNameWithCharSequenceParameter() {
        assertNotNull(Validators.domainName("foo"));
    }

    /**
     * Tests the functionality of the domainName-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testDomainNameWithContextAndResourceIdParameters() {
        assertNotNull(Validators.domainName(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the domainName-method, which expects a context as a parameter.
     */
    public final void testDomainNameWithContextParameter() {
        assertNotNull(Validators.domainName(getContext()));
    }

    /**
     * Tests the functionality of the emailAddress-method, which expects a char sequence as a
     * parameter.
     */
    public final void testEmailAddressWithCharSequenceParameter() {
        assertNotNull(Validators.emailAddress("foo"));
    }

    /**
     * Tests the functionality of the emailAddress-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testEmailAddressWithContextAndResourceIdParameters() {
        assertNotNull(Validators.emailAddress(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the emailAddress-method, which expects a context as a parameter.
     */
    public final void testEmailAddressWithContextParameter() {
        assertNotNull(Validators.emailAddress(getContext()));
    }

    /**
     * Tests the functionality of the iri-method, which expects a char sequence as a parameter.
     */
    public final void testIriWithCharSequenceParameter() {
        assertNotNull(Validators.iri("foo"));
    }

    /**
     * Tests the functionality of the iri-method, which expects a context and a resource id as
     * parameters.
     */
    public final void testIriWithContextAndResourceIdParameters() {
        assertNotNull(Validators.iri(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the iri-method, which expects a context as a parameter.
     */
    public final void testIriWithContextParameter() {
        assertNotNull(Validators.iri(getContext()));
    }

    /**
     * Tests the functionality of the phoneNumber-method, which expects a char sequence as a
     * parameter.
     */
    public final void testPhoneNumberWithCharSequenceParameter() {
        assertNotNull(Validators.phoneNumber("foo"));
    }

    /**
     * Tests the functionality of the phoneNumber-method, which expects a context and a resource id
     * as parameters.
     */
    public final void testPhoneNumberWithContextAndResourceIdParameters() {
        assertNotNull(Validators.phoneNumber(getContext(), android.R.string.cancel));
    }

    /**
     * Tests the functionality of the phoneNumber-method, which expects a context as a parameter.
     */
    public final void testPhoneNumberWithContextParameter() {
        assertNotNull(Validators.phoneNumber(getContext()));
    }

}