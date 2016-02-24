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
package de.mrapp.android.validation.validators.misc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.regex.Pattern;

import de.mrapp.android.validation.validators.text.RegexValidator;

/**
 * A validator, which allows to validate texts to ensure, that they represent valid IPv6 addresses.
 * Empty texts are also accepted.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class IPv6AddressValidator extends RegexValidator {

    /**
     * The regular expression, which is used by the validator.
     */
    private static final Pattern REGEX =
            Pattern.compile("(^$)" + "|(^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$)" +
                    "|(^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$)");

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they represent valid
     * IPv6 addresses.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     */
    public IPv6AddressValidator(@NonNull final CharSequence errorMessage) {
        super(errorMessage, REGEX);
    }

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they represent valid
     * IPv6 addresses.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     */
    public IPv6AddressValidator(@NonNull final Context context, @StringRes final int resourceId) {
        super(context, resourceId, REGEX);
    }

}