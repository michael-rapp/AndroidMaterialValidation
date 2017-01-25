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
package de.mrapp.android.validation.validators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import de.mrapp.android.validation.Validator;

import static de.mrapp.android.util.Condition.ensureNotEmpty;
import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all validators, which should be able to validate values of a specific
 * type.
 *
 * @param <Type>
 *         The type of the values, which should be validated
 * @author Michael Rapp
 * @since 1.0.0
 */
public abstract class AbstractValidator<Type> implements Validator<Type> {

    /**
     * The error message, which should be shown, if the validation fails.
     */
    private CharSequence errorMessage;

    /**
     * The icon, which should be shown, if the validation fails.
     */
    private Drawable icon;

    /**
     * Creates a new validator, which should be able to validate values of a specific type.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     */
    public AbstractValidator(@NonNull final CharSequence errorMessage) {
        setErrorMessage(errorMessage);
    }

    /**
     * Creates a new validator, which should be able to validate values of a specific type.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     */
    public AbstractValidator(@NonNull final Context context, @StringRes final int resourceId) {
        setErrorMessage(context, resourceId);
    }

    /**
     * Sets the error message, which should be shown, if the validation fails.
     *
     * @param errorMessage
     *         The error message, which should be set, as an instance of the type {@link
     *         CharSequence}. The error message may not be null
     */
    public final void setErrorMessage(@NonNull final CharSequence errorMessage) {
        ensureNotNull(errorMessage, "The error message may not be null");
        ensureNotEmpty(errorMessage, "The error message may not be empty");
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the error message, which should be shown, if the validation fails.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     */
    public final void setErrorMessage(@NonNull final Context context,
                                      @StringRes final int resourceId) {
        ensureNotNull(context, "The context may not be null");
        this.errorMessage = context.getText(resourceId);
    }

    /**
     * Sets the icon, which should be shown, if the validation fails.
     *
     * @param icon
     *         The icon, which should be set, as an instance of the class {@link Drawable} or null,
     *         if no icon should be shown
     */
    public final void setIcon(@Nullable final Drawable icon) {
        this.icon = icon;
    }

    /**
     * Sets the icon, which should be shown, if the validation fails.
     *
     * @param context
     *         The context, which should be used to retrieve the icon, as an instance of the class
     *         {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the drawable resource, which contains the icon, which should be
     *         set, as an {@link Integer} value. The resource ID must correspond to a valid drawable
     *         resource
     */
    public final void setIcon(@NonNull final Context context, @DrawableRes final int resourceId) {
        ensureNotNull(context, "The context may not be null");
        this.icon = ContextCompat.getDrawable(context, resourceId);
    }

    @Override
    public final CharSequence getErrorMessage() {
        return errorMessage;
    }

    @Override
    public final Drawable getIcon() {
        return icon;
    }

}