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
package de.mrapp.android.validation.validators.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import de.mrapp.android.validation.EditText;
import de.mrapp.android.validation.validators.AbstractValidator;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A validator, which allows to validate texts to ensure, that they are equal to the text, which is
 * contained by an {@link EditText} widget.
 *
 * @author Michael Rapp
 */
public class EqualValidator extends AbstractValidator<CharSequence> {

    /**
     * The edit text widget, which contains the content, the texts should be equal to.
     */
    private EditText editText;

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they are equal to the
     * text, which is contained by an {@link EditText} widget.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param editText
     *         The edit text widget, which contains the content, the texts should be equal to, as an
     *         instance of the class {@link EditText}. The widget may not be null
     */
    public EqualValidator(@NonNull final CharSequence errorMessage,
                          @NonNull final EditText editText) {
        super(errorMessage);
        setEditText(editText);
    }

    /**
     * Creates a new validator, which allows to validate texts to ensure, that they are equal to the
     * text, which is contained by an {@link EditText} widget.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param editText
     *         The edit text widget, which contains the content, the texts should be equal to, as an
     *         instance of the class {@link EditText}. The widget may not be null
     */
    public EqualValidator(@NonNull final Context context, @StringRes final int resourceId,
                          @NonNull final EditText editText) {
        super(context, resourceId);
        setEditText(editText);
    }

    /**
     * Returns the edit text widget, which contains the content, the texts should be equal to.
     *
     * @return The edit text widget, which contains the content, the texts should be equal to, as an
     * instance of the class {@link EditText}
     */
    public final EditText getEditText() {
        return editText;
    }

    /**
     * Sets the edit text widget, which contains the content, the texts should be equal to.
     *
     * @param editText
     *         The edit text widget, which should be set, as an instance of the class {@link
     *         EditText}. The widget may not be null
     */
    public final void setEditText(@NonNull final EditText editText) {
        ensureNotNull(editText, "The edit text widget may not be null");
        this.editText = editText;
    }

    @Override
    public final boolean validate(final CharSequence value) {
        return TextUtils.equals(value, getEditText().getText());
    }

}