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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import de.mrapp.util.Condition;
import de.mrapp.util.datastructure.ListenerList;

/**
 * An abstract base class for all views, whose value should be able to be validated according to the
 * pattern, which is suggested by the Material Design guidelines.
 *
 * @param <ViewType>
 *         The type of the view, whose value should be able to be validated
 * @param <ValueType>
 *         The type of the values, which should be validated
 * @author Michael Rapp
 * @since 1.0.0
 */
public abstract class AbstractValidateableView<ViewType extends View, ValueType>
        extends LinearLayout implements Validateable<ValueType> {

    /**
     * A data structure, which allows to save the internal state of an {@link
     * AbstractValidateableView}.
     */
    public static class SavedState extends BaseSavedState {

        /**
         * A creator, which allows to create instances of the class {@link AbstractValidateableView}
         * from parcels.
         */
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {

                    @Override
                    public SavedState createFromParcel(final Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(final int size) {
                        return new SavedState[size];
                    }

                };

        /**
         * True, if the view displays an error, false otherwise.
         */
        boolean validated;

        /**
         * True, if the view's value should be automatically validated, when the value has been
         * changed, false otherwise.
         */
        boolean validateOnValueChange;

        /**
         * True, if the view's value should be automatically validated, when the view has lost its
         * focus, false otherwise.
         */
        boolean validateOnFocusLost;

        /**
         * Creates a new data structure, which allows to store the internal state of a {@link
         * EditText}. This constructor is used when reading from a parcel. It reads the state of the
         * superclass.
         *
         * @param source
         *         The parcel to read read from as a instance of the class {@link Parcel}. The
         *         parcel may not be null
         */
        private SavedState(@NonNull final Parcel source) {
            super(source);
            validated = source.readInt() == 1;
            validateOnValueChange = source.readInt() == 1;
            validateOnFocusLost = source.readInt() == 1;
        }

        /**
         * Creates a new data structure, which allows to store the internal state of a {@link
         * EditText}. This constructor is called by derived classes when saving their states.
         *
         * @param superState
         *         The state of the superclass of this view, as an instance of the type {@link
         *         Parcelable}. The state may not be null
         */
        SavedState(@NonNull final Parcelable superState) {
            super(superState);
        }

        @Override
        public final void writeToParcel(final Parcel destination, final int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(validated ? 1 : 0);
            destination.writeInt(validateOnValueChange ? 1 : 0);
            destination.writeInt(validateOnFocusLost ? 1 : 0);
        }

    }

    /**
     * True, if the view's value should be automatically validated, when the value has been changed,
     * by default, false otherwise.
     */
    private static final boolean DEFAULT_VALIDATE_ON_VALUE_CHANGE = true;

    /**
     * True, if the view's value should be automatically validated, when the view has lost its
     * focus, by default, false otherwise.
     */
    private static final boolean DEFAULT_VALIDATE_ON_FOCUS_LOST = true;

    /**
     * The parent view of the view, whose value should be able to be validated.
     */
    private ViewGroup parentView;

    /**
     * The view, whose value should be able to be validated.
     */
    private ViewType view;

    /**
     * The text view, which may be used to show messages at the left edge of the view.
     */
    private TextView leftMessage;

    /**
     * The text view, which may be used to show messages at the right edge of the view.
     */
    private TextView rightMessage;

    /**
     * The helper text, which is shown, when no validation errors are currently shown at the left
     * edge of the view.
     */
    private CharSequence helperText;

    /**
     * The color, which is used to indicate validation errors.
     */
    private int errorColor;

    /**
     * The color of the helper text.
     */
    private int helperTextColor;

    /**
     * A set, which contains the validators, which should be used for validation.
     */
    private Set<Validator<ValueType>> validators;

    /**
     * True, if the view's value is automatically validated, when its value has been changed, false
     * otherwise.
     */
    private boolean validateOnValueChange;

    /**
     * True, if the view's value is automatically validated, when the view loses its focus, false
     * otherwise.
     */
    private boolean validateOnFocusLost;

    /**
     * A set, which contains the listeners, which should be notified, when the view has been
     * validated.
     */
    private ListenerList<ValidationListener<ValueType>> listeners;

    /**
     * Initializes the view.
     *
     * @param attributeSet
     *         The attribute set, the attributes should be obtained from, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes should be obtained
     */
    private void initialize(@Nullable final AttributeSet attributeSet) {
        validators = new LinkedHashSet<>();
        listeners = new ListenerList<>();
        setOrientation(VERTICAL);
        inflateView();
        inflateErrorMessageTextViews();
        obtainStyledAttributes(attributeSet);
        setLeftMessage(null, null);
        setRightMessage(null);
    }

    /**
     * Obtains all attributes from a specific attribute set.
     *
     * @param attributeSet
     *         The attribute set, the attributes should be obtained from, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes should be obtained
     */
    private void obtainStyledAttributes(@Nullable final AttributeSet attributeSet) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attributeSet, R.styleable.AbstractValidateableView);
        try {
            obtainHelperText(typedArray);
            obtainHelperTextColor(typedArray);
            obtainErrorColor(typedArray);
            obtainValidateOnValueChange(typedArray);
            obtainValidateOnFocusLost(typedArray);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Obtains the helper text from a specific typed array.
     *
     * @param typedArray
     *         The typed array, the helper text should be obtained from, as an instance of the class
     *         {@link TypedArray}. The typed array may not be null
     */
    private void obtainHelperText(@NonNull final TypedArray typedArray) {
        setHelperText(typedArray.getString(R.styleable.AbstractValidateableView_helperText));
    }

    /**
     * Obtains the color of the helper text from a specific typed array.
     *
     * @param typedArray
     *         The typed array, the color of the helper text should be obtained from, as an instance
     *         of the class {@link TypedArray}. The typed array may not be null
     */
    private void obtainHelperTextColor(@NonNull final TypedArray typedArray) {
        setHelperTextColor(typedArray.getColor(R.styleable.AbstractValidateableView_helperTextColor,
                ContextCompat.getColor(getContext(), R.color.default_helper_text_color)));
    }

    /**
     * Obtains the color, which is used to indicate validation errors, from a specific typed array.
     *
     * @param typedArray
     *         The typed array, the error color should be obtained from, as an instance of the class
     *         {@link TypedArray}. The typed array may not be null
     */
    private void obtainErrorColor(@NonNull final TypedArray typedArray) {
        setErrorColor(typedArray.getColor(R.styleable.AbstractValidateableView_errorColor,
                ContextCompat.getColor(getContext(), R.color.default_error_color)));
    }

    /**
     * Obtains, whether the value of the view should be validated, when its value has been changed,
     * or not, from a specific typed array.
     *
     * @param typedArray
     *         The typed array, it should be obtained from, whether the value of the view should be
     *         validated, when its value has been changed, or not, as an instance of the class
     *         {@link TypedArray}. The typed array may not be null
     */
    private void obtainValidateOnValueChange(@NonNull final TypedArray typedArray) {
        validateOnValueChange(typedArray
                .getBoolean(R.styleable.AbstractValidateableView_validateOnValueChange,
                        DEFAULT_VALIDATE_ON_VALUE_CHANGE));
    }

    /**
     * Obtains, whether the value of the view should be validated, when the view loses its focus, or
     * not, from a specific typed array.
     *
     * @param typedArray
     *         The typed array, it should be obtained from, whether the value of the view should be
     *         validated, when the view loses its focus, or not, as an instance of the class {@link
     *         TypedArray}. The typed array may not be null
     */
    private void obtainValidateOnFocusLost(@NonNull final TypedArray typedArray) {
        validateOnFocusLost(typedArray
                .getBoolean(R.styleable.AbstractValidateableView_validateOnFocusLost,
                        DEFAULT_VALIDATE_ON_FOCUS_LOST));
    }

    /**
     * Inflates the view, whose value should be able to be validated.
     */
    private void inflateView() {
        parentView = createParentView();
        view = createView();
        view.setOnFocusChangeListener(createFocusChangeListener());
        view.setBackgroundResource(R.drawable.validateable_view_background);
        setLineColor(getAccentColor());

        if (parentView != null) {
            parentView.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            addView(parentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        } else {
            addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Inflates the text views, which are used to show validation errors.
     */
    private void inflateErrorMessageTextViews() {
        View parent = View.inflate(getContext(), R.layout.error_messages, null);
        addView(parent, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        leftMessage = parent.findViewById(R.id.left_error_message);
        leftMessage.setTag(false);
        rightMessage = parent.findViewById(R.id.right_error_message);
        rightMessage.setTag(false);
    }

    /**
     * Creates and returns a listener, which allows to validate the value of the view, when the view
     * loses its focus.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnFocusChangeListener}
     */
    private OnFocusChangeListener createFocusChangeListener() {
        return new OnFocusChangeListener() {

            @Override
            public final void onFocusChange(final View view, final boolean hasFocus) {
                if (!hasFocus && isValidatedOnFocusLost()) {
                    validate();
                }
            }

        };
    }

    /**
     * Notifies all registered listeners, that a validation succeeded.
     */
    private void notifyOnValidationSuccess() {
        for (ValidationListener<ValueType> listener : listeners) {
            listener.onValidationSuccess(this);
        }
    }

    /**
     * Notifies all registered listeners, that a validation failed.
     *
     * @param validator
     *         The validator, which failed, as an instance of the type {@link Validator}. The
     *         validator may not be null
     */
    private void notifyOnValidationFailure(@NonNull final Validator<ValueType> validator) {
        for (ValidationListener<ValueType> listener : listeners) {
            listener.onValidationFailure(this, validator);
        }
    }

    /**
     * Validates the current value of the view in order to retrieve the error message and icon,
     * which should be shown at the left edge of the view, if a validation fails.
     *
     * @return The validator, which failed or null, if the validation succeeded
     */
    private Validator<ValueType> validateLeft() {
        Validator<ValueType> result = null;
        Collection<Validator<ValueType>> subValidators = onGetLeftErrorMessage();

        if (subValidators != null) {
            for (Validator<ValueType> validator : subValidators) {
                notifyOnValidationFailure(validator);

                if (result == null) {
                    result = validator;
                }
            }
        }

        for (Validator<ValueType> validator : validators) {
            if (!validator.validate(getValue())) {
                notifyOnValidationFailure(validator);

                if (result == null) {
                    result = validator;
                }
            }
        }

        return result;
    }

    /**
     * Validates the current value of the view in order to retrieve the error message and icon,
     * which should be shown at the right edge of the view, if a validation fails.
     *
     * @return The validator, which failed or null, if the validation succeeded
     */
    private Validator<ValueType> validateRight() {
        Validator<ValueType> result = null;
        Collection<Validator<ValueType>> subValidators = onGetRightErrorMessage();

        if (subValidators != null) {
            for (Validator<ValueType> validator : subValidators) {
                notifyOnValidationFailure(validator);

                if (result == null) {
                    result = validator;
                }
            }
        }

        return result;
    }

    /**
     * Returns the color of the theme attribute <code>android.R.attr.colorAccent</code>.
     *
     * @return The color of the theme attribute <code>android.R.attr.colorAccent</code>
     */
    private int getAccentColor() {
        TypedArray typedArray =
                getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorAccent});
        return typedArray.getColor(0, 0);
    }

    /**
     * Sets the color of the view's line.
     *
     * @param color
     *         The color, which should be set, as an {@link Integer} value
     */
    private void setLineColor(@ColorInt final int color) {
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Adapts the enable state of all children of a specific view group.
     *
     * @param viewGroup
     *         The view group, whose children's enabled states should be adapted, as an instance of
     *         the class {@link ViewGroup}. The view group may not be null
     * @param enabled
     *         True, if the children should be enabled, false otherwise
     */
    private void setEnabledOnViewGroup(@NonNull final ViewGroup viewGroup, final boolean enabled) {
        viewGroup.setEnabled(enabled);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof ViewGroup) {
                setEnabledOnViewGroup((ViewGroup) child, enabled);
            } else {
                child.setEnabled(enabled);
            }
        }
    }

    /**
     * Adapts the activated state of all children of a specific view group.
     *
     * @param viewGroup
     *         The view group, whose children's activated states should be adapted, as an instance
     *         of the class {@link ViewGroup}. The view group may not be null
     * @param activated
     *         True, if the children should be activated, false otherwise
     */
    private void setActivatedOnViewGroup(@NonNull final ViewGroup viewGroup,
                                         final boolean activated) {
        viewGroup.setActivated(activated);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof ViewGroup) {
                setActivatedOnViewGroup((ViewGroup) child, activated);
            } else {
                child.setActivated(activated);
            }
        }
    }

    /**
     * Returns the view, whose value should be able to be validated.
     *
     * @return The view, whose value should be able to be validated, as an instance of the generic
     * type ViewType
     */
    protected final ViewType getView() {
        return view;
    }

    /**
     * Shows a specific message, which is marked as an error, at the left edge of the view.
     *
     * @param message
     *         The message, which should be shown, as an instance of the type {@link CharSequence}
     *         or null, if no message should be shown
     * @param icon
     *         The icon, which should be shown, as an instance of the type {@link Drawable} or null,
     *         if no icon should be shown
     */
    protected final void setLeftMessage(@Nullable final CharSequence message,
                                        @Nullable final Drawable icon) {
        setLeftMessage(message, icon, true);
    }

    /**
     * Shows a specific message at the left edge of the view.
     *
     * @param message
     *         The message, which should be shown, as an instance of the type {@link CharSequence}
     *         or null, if no message should be shown
     * @param icon
     *         The icon, which should be shown, as an instance of the type {@link Drawable} or null,
     *         if no icon should be shown
     * @param error
     *         True, if the message should be highlighted as an error, false otherwise
     */
    protected final void setLeftMessage(@Nullable final CharSequence message,
                                        @Nullable final Drawable icon, final boolean error) {
        if (message != null) {
            leftMessage.setText(message);
            leftMessage.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            leftMessage.setTextColor(error ? getErrorColor() : getHelperTextColor());
            leftMessage.setTag(error);
            leftMessage.setVisibility(View.VISIBLE);
        } else if (getHelperText() != null) {
            setLeftMessage(getHelperText(), null, false);
        } else {
            leftMessage.setTag(false);
            leftMessage.setVisibility(View.GONE);
        }
    }

    /**
     * Shows a specific message, which is highlighted as an error, at the right edge of the view.
     *
     * @param message
     *         The message, which should be shown, as an instance of the type {@link CharSequence}
     *         or null, if no message should be shown
     */
    protected final void setRightMessage(@Nullable final CharSequence message) {
        setRightMessage(message, true);
    }

    /**
     * Shows a specific message at the right edge of the view.
     *
     * @param message
     *         The message, which should be shown, as an instance of the type {@link CharSequence}
     *         or null, if no message should be shown
     * @param error
     *         True, if the message should be highlighted as an error, false otherwise
     */
    protected final void setRightMessage(@Nullable final CharSequence message,
                                         final boolean error) {
        if (message != null) {
            rightMessage.setVisibility(View.VISIBLE);
            rightMessage.setText(message);
            rightMessage.setTextColor(error ? getErrorColor() : getHelperTextColor());
            rightMessage.setTag(error);
        } else {
            rightMessage.setTag(false);
            rightMessage.setVisibility(View.GONE);
        }
    }

    /**
     * The method, which is invoked in order to validate the current value of the view and to
     * retrieve the error message, which should be shown at the left edge of the view, if a
     * validation fails. This method may be overridden by subclasses in order to perform internal
     * validations.
     *
     * @return A collection, which contains the validators, which failed or null, if the validation
     * succeeded, as an instance of the type {@link Collection}
     */
    protected Collection<Validator<ValueType>> onGetLeftErrorMessage() {
        return null;
    }

    /**
     * The method, which is invoked in order to validate the current value of the view and to
     * retrieve the error message, which should be shown at the right edge of the view, if a
     * validation fails. This method may be overridden by subclasses in order to perform internal
     * validations.
     *
     * @return TA collection, which contains the validators, which failed or null, if the validation
     * succeeded, as an instance of the type {@link Collection}
     */
    protected Collection<Validator<ValueType>> onGetRightErrorMessage() {
        return null;
    }

    /**
     * The method, which is invoked when the value of the view has been validated. This method may
     * be overridden by subclasses in order to adapt the view depending on the validation result.
     *
     * @param valid
     *         True, if the validation succeeded, false otherwise
     */
    protected void onValidate(final boolean valid) {

    }

    /**
     * The method, which is invoked in order to create the view, whose value should be able to be
     * validated.
     *
     * @return The view, which has been created, as an instance of the generic type ViewType
     */
    protected abstract ViewType createView();

    /**
     * The method, which is invoked in order to create the parent view of the view, whose value
     * should be able to be validated.
     *
     * @return The parent view, which has been created, as an instance of the class {@link
     * ViewGroup} or null, if no parent view should be created
     */
    protected abstract ViewGroup createParentView();

    /**
     * The method, which is invoked in order to retrieve the current value of the view.
     *
     * @return The current value of the view as an instance of the generic type Type
     */
    protected abstract ValueType getValue();

    /**
     * Creates a new view, which allows to enter text.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     */
    public AbstractValidateableView(@NonNull final Context context) {
        super(context);
        initialize(null);
    }

    /**
     * Creates a new view, whose value should be able to be validated according to the pattern,
     * which is suggested by the Material Design guidelines.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     */
    public AbstractValidateableView(@NonNull final Context context,
                                    @Nullable final AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(attributeSet);
    }

    /**
     * Creates a new view, whose value should be able to be validated according to the pattern,
     * which is suggested by the Material Design guidelines.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AbstractValidateableView(@NonNull final Context context,
                                    @Nullable final AttributeSet attributeSet,
                                    final int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        initialize(attributeSet);
    }

    /**
     * Creates a new view, whose value should be able to be validated according to the pattern,
     * which is suggested by the Material Design guidelines.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     * @param defaultStyleResource
     *         A resource identifier of a style resource that supplies default values for the
     *         preference, used only if the default style is 0 or can not be found in the theme. Can
     *         be 0 to not look for defaults
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbstractValidateableView(@NonNull final Context context,
                                    @Nullable final AttributeSet attributeSet,
                                    final int defaultStyle, final int defaultStyleResource) {
        super(context, attributeSet, defaultStyle, defaultStyleResource);
        initialize(attributeSet);
    }

    @Override
    public final Collection<Validator<ValueType>> getValidators() {
        return validators;
    }

    @Override
    public final void addValidator(@NonNull final Validator<ValueType> validator) {
        Condition.INSTANCE.ensureNotNull(validator, "The validator may not be null");
        validators.add(validator);
    }

    @Override
    public final void addAllValidators(@NonNull final Collection<Validator<ValueType>> validators) {
        Condition.INSTANCE.ensureNotNull(validators, "The collection may not be null");

        for (Validator<ValueType> validator : validators) {
            addValidator(validator);
        }
    }

    @SafeVarargs
    @Override
    public final void addAllValidators(@NonNull final Validator<ValueType>... validators) {
        Condition.INSTANCE.ensureNotNull(validators, "The array may not be null");
        addAllValidators(Arrays.asList(validators));
    }

    @Override
    public final void removeValidator(@NonNull final Validator<ValueType> validator) {
        Condition.INSTANCE.ensureNotNull(validator, "The validator may not be null");
        validators.remove(validator);
    }

    @Override
    public final void removeAllValidators(
            @NonNull final Collection<Validator<ValueType>> validators) {
        Condition.INSTANCE.ensureNotNull(validators, "The collection may not be null");

        for (Validator<ValueType> validator : validators) {
            removeValidator(validator);
        }
    }

    @SafeVarargs
    @Override
    public final void removeAllValidators(@NonNull final Validator<ValueType>... validators) {
        Condition.INSTANCE.ensureNotNull(validators, "The array may not be null");
        removeAllValidators(Arrays.asList(validators));
    }

    @Override
    public final void removeAllValidators() {
        validators.clear();
    }

    /**
     * Returns the helper text, which is shown, when no validation error is currently shown.
     *
     * @return The helper text, which is shown, when no validation error is currently shown, as an
     * instance of the type {@link CharSequence} or null, if no helper text is shown
     */
    public final CharSequence getHelperText() {
        return helperText;
    }

    /**
     * Sets the helper text, which should be shown, when no validation error is currently shown.
     *
     * @param helperText
     *         The helper text, which should be set, as an instance of the type {@link CharSequence}
     *         or null, if no helper text should be shown
     */
    public final void setHelperText(@Nullable final CharSequence helperText) {
        this.helperText = helperText;

        if (getError() == null) {
            setLeftMessage(helperText, null, false);
        }
    }

    /**
     * Sets the helper text, which should be shown, when no validation error is currently shown.
     *
     * @param resourceId
     *         The resource ID of the string resource, which contains the helper text, which should
     *         be set, as an {@link Integer} value. The resource ID must correspond to a valid
     *         string resource
     */
    public final void setHelperText(@StringRes final int resourceId) {
        setHelperText(getContext().getText(resourceId));
    }

    /**
     * Returns the color, which is used to indicate validation errors.
     *
     * @return The color, which is used to indicate validation errors, as an {@link Integer} value
     */
    public final int getErrorColor() {
        return errorColor;
    }

    /**
     * Sets the color, which should be used to indicate validation errors.
     *
     * @param color
     *         The color, which should be set, as an {@link Integer} value
     */
    public final void setErrorColor(@ColorInt final int color) {
        this.errorColor = color;

        if ((Boolean) leftMessage.getTag()) {
            leftMessage.setTextColor(color);
        }

        if ((Boolean) rightMessage.getTag()) {
            rightMessage.setTextColor(color);
        }
    }

    /**
     * Returns the color of the helper text.
     *
     * @return The color of the helper text as an {@link Integer} value
     */
    public final int getHelperTextColor() {
        return helperTextColor;
    }

    /**
     * Sets the color of the helper text.
     *
     * @param color
     *         The color, which should be set, as an {@link Integer} value
     */
    public final void setHelperTextColor(@ColorInt final int color) {
        this.helperTextColor = color;

        if (!(Boolean) leftMessage.getTag()) {
            leftMessage.setTextColor(color);
        }

        if (!(Boolean) rightMessage.getTag()) {
            rightMessage.setTextColor(color);
        }
    }

    /**
     * Returns the error message, which has been previously set to be displayed.
     *
     * @return The error message, which has been previously set to be displayed, as an instance of
     * the type {@link CharSequence} or null, if no error has been set or if it has already been
     * cleared by the widget
     */
    public final CharSequence getError() {
        if (leftMessage.getVisibility() == View.VISIBLE && (Boolean) leftMessage.getTag()) {
            return leftMessage.getText();
        }

        return null;
    }

    /**
     * Sets an error message, which should be displayed.
     *
     * @param error
     *         The error message, which should be displayed, as an instance of the type {@link
     *         CharSequence} or null, if a previously set error message should be cleared be
     *         cleared
     */
    public final void setError(@Nullable final CharSequence error) {
        setError(error, null);
    }

    /**
     * Sets an error message and an icon, which should be displayed.
     *
     * @param error
     *         The error message, which should be displayed, as an instance of the type {@link
     *         CharSequence} or null, if a previously set error message should be cleared be
     *         cleared
     * @param icon
     *         The icon, which should be displayed,as an instance of the type {@link Drawable} or
     *         null, if no icon should be displayed
     */
    public void setError(@Nullable final CharSequence error, @Nullable final Drawable icon) {
        setLeftMessage(error, icon);
        setActivated(error != null);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);

        if (!enabled) {
            setError(null);
        }

        view.setEnabled(enabled);

        if (parentView != null) {
            setEnabledOnViewGroup(parentView, enabled);
        }
    }

    @Override
    public final void setActivated(final boolean activated) {
        super.setActivated(activated);
        view.setActivated(activated);

        if (parentView != null) {
            setActivatedOnViewGroup(parentView, activated);
        }
    }

    @Override
    public final boolean validate() {
        Validator<ValueType> leftValidator = validateLeft();
        Validator<ValueType> rightValidator = validateRight();
        setLeftMessage(leftValidator != null ? leftValidator.getErrorMessage() : null,
                leftValidator != null ? leftValidator.getIcon() : null);
        setRightMessage(rightValidator != null ? rightValidator.getErrorMessage() : null);

        if (leftValidator == null && rightValidator == null) {
            notifyOnValidationSuccess();
            onValidate(true);
            setActivated(false);
            setLineColor(getAccentColor());
            return true;
        }

        onValidate(false);
        setActivated(true);
        setLineColor(getErrorColor());
        return false;
    }

    @Override
    public final boolean isValidatedOnValueChange() {
        return validateOnValueChange;
    }

    @Override
    public final void validateOnValueChange(final boolean validateOnValueChange) {
        this.validateOnValueChange = validateOnValueChange;
    }

    @Override
    public final boolean isValidatedOnFocusLost() {
        return validateOnFocusLost;
    }

    @Override
    public final void validateOnFocusLost(final boolean validateOnFocusLost) {
        this.validateOnFocusLost = validateOnFocusLost;
    }

    @Override
    public final void addValidationListener(@NonNull final ValidationListener<ValueType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        listeners.add(listener);
    }

    @Override
    public final void removeValidationListener(
            @NonNull final ValidationListener<ValueType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        listeners.remove(listener);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        if (superState != null) {
            SavedState savedState = new SavedState(superState);
            savedState.validated = getError() != null;
            savedState.validateOnValueChange = isValidatedOnValueChange();
            savedState.validateOnFocusLost = isValidatedOnFocusLost();
            return savedState;
        }

        return null;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;

            if (savedState.validated) {
                validate();
            }

            validateOnValueChange(savedState.validateOnValueChange);
            validateOnFocusLost(savedState.validateOnFocusLost);
            super.onRestoreInstanceState(savedState.getSuperState());
        } else {
            super.onRestoreInstanceState(state);
        }
    }

}