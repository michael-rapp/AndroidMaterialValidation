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