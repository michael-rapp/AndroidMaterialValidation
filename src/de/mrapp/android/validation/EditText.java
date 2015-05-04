/*
 * AndroidMaterialValidation Copyright 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.validation;

import static de.mrapp.android.validation.util.Condition.ensureAtLeast;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.MovementMethod;
import android.text.method.TransformationMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.Scroller;
import android.widget.TextView.BufferType;
import android.widget.TextView.OnEditorActionListener;

/**
 * A view, which allows to enter text. The text may be validated according to
 * the pattern, which is suggested by the Material Design guidelines.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public class EditText extends
		AbstractValidateableView<android.widget.EditText, CharSequence> {

	/**
	 * A data structure, which allows to save the internal state of an
	 * {@link EditText}.
	 */
	public static class SavedState extends BaseSavedState {

		/**
		 * A creator, which allows to create instances of the class
		 * {@link EditText} from parcels.
		 */
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

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
		 * The internal state of the edit text.
		 */
		public Parcelable viewState;

		/**
		 * The maximum number of characters, the edit text is allowed to
		 * contain.
		 */
		public int maxNumberOfCharacters;

		/**
		 * Creates a new data structure, which allows to store the internal
		 * state of a {@link EditText}. This constructor is used when reading
		 * from a parcel. It reads the state of the superclass.
		 * 
		 * @param source
		 *            The parcel to read read from as a instance of the class
		 *            {@link Parcel}
		 */
		private SavedState(final Parcel source) {
			super(source);
			ClassLoader classLoader = Parcelable.class.getClassLoader();
			viewState = source.readParcelable(classLoader);
			maxNumberOfCharacters = source.readInt();
		}

		/**
		 * Creates a new data structure, which allows to store the internal
		 * state of a {@link EditText}. This constructor is called by derived
		 * classes when saving their states.
		 * 
		 * @param superState
		 *            The state of the superclass of this view, as an instance
		 *            of the type {@link Parcelable}
		 */
		public SavedState(final Parcelable superState) {
			super(superState);
		}

		@Override
		public final void writeToParcel(final Parcel destination,
				final int flags) {
			super.writeToParcel(destination, flags);
			destination.writeParcelable(viewState, flags);
			destination.writeInt(maxNumberOfCharacters);
		}

	};

	/**
	 * The maximum number of characters, the edit should be allowed to contain
	 * by default.
	 */
	private static final int DEFAULT_MAX_NUMBER_OF_CHARACTERS = -1;

	/**
	 * The value, which corresponds to the enum value
	 * <code>TruncateAt.START</code>.
	 */
	private static final int ELLIPSIZE_START_VALUE = 1;

	/**
	 * The value, which corresponds to the enum value
	 * <code>TruncateAt.MIDDLE</code>.
	 */
	private static final int ELLIPSIZE_MIDDLE_VALUE = 2;

	/**
	 * The value, which corresponds to the enum value
	 * <code>TruncateAt.END</code>.
	 */
	private static final int ELLIPSIZE_END_VALUE = 3;

	/**
	 * The value, which corresponds to the enum value
	 * <code>TruncateAt.MARQUEE</code>.
	 */
	private static final int ELLIPSIZE_MARQUEE_VALUE = 4;

	/**
	 * The value, which corresponds to the enum value
	 * <code>Typeface.SANS_SERIF</code>.
	 */
	private static final int TYPEFACE_SANS_SERIF_VALUE = 1;

	/**
	 * The value, which corresponds to the enum value
	 * <code>Typeface.SERIF</code>.
	 */
	private static final int TYPEFACE_SERIF_VALUE = 2;

	/**
	 * The value, which corresponds to the enum value
	 * <code>Typeface.MONOSPACE</code>.
	 */
	private static final int TYPEFACE_MONOSPACE_VALUE = 3;

	/**
	 * The maximum number of characters, the edit text is allowed to contain.
	 */
	private int maxNumberOfCharacters;

	/**
	 * Initializes the view.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void initialize(final AttributeSet attributeSet) {
		obtainStyledAttributes(attributeSet);
		setEditTextLineColor(getAccentColor());
	}

	/**
	 * Obtains all attributes from a specific attribute set.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void obtainStyledAttributes(final AttributeSet attributeSet) {
		TypedArray typedArray = getContext().obtainStyledAttributes(
				attributeSet, R.styleable.EditText);
		try {
			obtainMaxNumberOfCharacters(typedArray);
			obtainEditTextStyledAttributes(typedArray);
		} finally {
			typedArray.recycle();
		}
	}

	/**
	 * Obtains the maximum number of characters, the edit text should be allowed
	 * to contain, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, the maximum number of characters, the edit
	 *            text should be allowed to contain, should be obtained from, as
	 *            an instance of the class {@link TypedArray}
	 */
	private void obtainMaxNumberOfCharacters(final TypedArray typedArray) {
		setMaxNumberOfCharacters(typedArray.getInt(
				R.styleable.EditText_maxNumberOfCharacters,
				DEFAULT_MAX_NUMBER_OF_CHARACTERS));
	}

	/**
	 * Obtains all attributes, which are defined by an
	 * {@link android.widget.EditText} widget, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, the attributes should be obtained from, as an
	 *            instance of the class {@link TypedArray}
	 */
	@SuppressLint("NewApi")
	private void obtainEditTextStyledAttributes(final TypedArray typedArray) {
		Drawable drawableLeft = null;
		Drawable drawableTop = null;
		Drawable drawableRight = null;
		Drawable drawableBottom = null;
		CharSequence imeActionLabel = null;
		int imeActionId = getImeActionId();
		float lineSpacingExtra = getLineSpacingExtra();
		float lineSpacingMultiplier = getLineSpacingMultiplier();
		int shadowColor = 0;
		float shadowDx = 0;
		float shadowDy = 0;
		float shadowRadius = 0;

		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int index = typedArray.getIndex(i);

			if (index == R.styleable.EditText_android_autoLink) {
				setAutoLinkMask(typedArray.getInt(index, getAutoLinkMask()));
			} else if (index == R.styleable.EditText_android_cursorVisible) {
				setCursorVisible(typedArray
						.getBoolean(index, isCursorVisible()));
			} else if (index == R.styleable.EditText_android_drawableBottom) {
				drawableBottom = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_drawableEnd) {
				drawableRight = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_drawableLeft) {
				drawableLeft = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_drawablePadding) {
				setCompoundDrawablePadding(typedArray.getDimensionPixelSize(
						index, getCompoundDrawablePadding()));
			} else if (index == R.styleable.EditText_android_drawableRight) {
				drawableRight = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_drawableStart) {
				drawableLeft = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_drawableTop) {
				drawableTop = typedArray.getDrawable(index);
			} else if (index == R.styleable.EditText_android_elegantTextHeight) {
				setElegantTextHeight(typedArray.getBoolean(index, false));
			} else if (index == R.styleable.EditText_android_ellipsize) {
				int ellipsize = typedArray.getInt(index, -1);

				if (ellipsize != -1) {
					setEllipsize(parseEllipsize(ellipsize));
				}
			} else if (index == R.styleable.EditText_android_ems) {
				setEms(typedArray.getInt(index, -1));
			} else if (index == R.styleable.EditText_android_fontFeatureSettings) {
				setFontFeatureSettings(typedArray.getString(index));
			} else if (index == R.styleable.EditText_android_freezesText) {
				setFreezesText(typedArray.getBoolean(index, getFreezesText()));
			} else if (index == R.styleable.EditText_android_hint) {
				setHint(typedArray.getText(index));
			} else if (index == R.styleable.EditText_android_imeActionId) {
				imeActionId = typedArray.getInt(index, getImeActionId());
			} else if (index == R.styleable.EditText_android_imeActionLabel) {
				imeActionLabel = typedArray.getText(index);
			} else if (index == R.styleable.EditText_android_imeOptions) {
				setImeOptions(typedArray.getInt(index, getImeOptions()));
			} else if (index == R.styleable.EditText_android_includeFontPadding) {
				setIncludeFontPadding(typedArray.getBoolean(index,
						getIncludeFontPadding()));
			} else if (index == R.styleable.EditText_android_inputType) {
				setInputType(typedArray.getInt(index, getInputType()));
			} else if (index == R.styleable.EditText_android_letterSpacing) {
				setLetterSpacing(typedArray.getFloat(index, getLetterSpacing()));
			} else if (index == R.styleable.EditText_android_lines) {
				setLines(typedArray.getInt(index, -1));
			} else if (index == R.styleable.EditText_android_lineSpacingExtra) {
				lineSpacingExtra = typedArray.getDimensionPixelSize(index,
						(int) getLineSpacingExtra());
			} else if (index == R.styleable.EditText_android_lineSpacingMultiplier) {
				lineSpacingMultiplier = typedArray.getFloat(index,
						getLineSpacingMultiplier());
			} else if (index == R.styleable.EditText_android_linksClickable) {
				setLinksClickable(typedArray.getBoolean(index,
						getLinksClickable()));
			} else if (index == R.styleable.EditText_android_marqueeRepeatLimit) {
				setMarqueeRepeatLimit(typedArray.getInt(index,
						getMarqueeRepeatLimit()));
			} else if (index == R.styleable.EditText_android_maxEms) {
				setMaxEms(typedArray.getInt(index, getMaxEms()));
			} else if (index == R.styleable.EditText_android_maxLength) {
				int maxLength = typedArray.getInt(index, -1);

				if (maxLength >= 0) {
					setFilters(new InputFilter[] { new InputFilter.LengthFilter(
							maxLength) });
				} else {
					setFilters(new InputFilter[0]);
				}
			} else if (index == R.styleable.EditText_android_maxLines) {
				setMaxLines(typedArray.getInt(index, getMaxLines()));
			} else if (index == R.styleable.EditText_android_minEms) {
				setMinEms(typedArray.getInt(index, getMinEms()));
			} else if (index == R.styleable.EditText_android_minLines) {
				setMinLines(typedArray.getInt(index, getMinLines()));
			} else if (index == R.styleable.EditText_android_privateImeOptions) {
				setPrivateImeOptions(typedArray.getString(index));
			} else if (index == R.styleable.EditText_android_scrollHorizontally) {
				if (typedArray.getBoolean(index, false)) {
					setHorizontallyScrolling(true);
				}
			} else if (index == R.styleable.EditText_android_selectAllOnFocus) {
				setSelectAllOnFocus(typedArray.getBoolean(index, false));
			} else if (index == R.styleable.EditText_android_shadowColor) {
				shadowColor = typedArray.getInt(index, 0);
			} else if (index == R.styleable.EditText_android_shadowDx) {
				shadowDx = typedArray.getFloat(index, 0);
			} else if (index == R.styleable.EditText_android_shadowDy) {
				shadowDy = typedArray.getFloat(index, 0);
			} else if (index == R.styleable.EditText_android_shadowRadius) {
				shadowRadius = typedArray.getFloat(index, shadowRadius);
			} else if (index == R.styleable.EditText_android_singleLine) {
				setSingleLine(typedArray.getBoolean(index, false));
			} else if (index == R.styleable.EditText_android_text) {
				setText(typedArray.getText(index));
			} else if (index == R.styleable.EditText_android_textAllCaps) {
				setAllCaps(typedArray.getBoolean(index, false));
			} else if (index == R.styleable.EditText_android_textAppearance) {
				int resourceId = typedArray.getResourceId(index, -1);

				if (resourceId != -1
						&& resourceId != android.R.style.TextAppearance_Material) {
					setTextAppearance(getContext(), resourceId);
				}
			} else if (index == R.styleable.EditText_android_textColor) {
				ColorStateList textColor = typedArray.getColorStateList(index);

				if (textColor != null) {
					setTextColor(textColor);
				}
			} else if (index == R.styleable.EditText_android_textColorHighlight) {
				setHighlightColor(typedArray.getColor(index,
						getHighlightColor()));
			} else if (index == R.styleable.EditText_android_textColorHint) {
				setHintTextColor(typedArray.getColorStateList(index));
			} else if (index == R.styleable.EditText_android_textColorLink) {
				setLinkTextColor(typedArray.getColorStateList(index));
			} else if (index == R.styleable.EditText_android_textIsSelectable) {
				setTextIsSelectable(typedArray.getBoolean(index,
						isTextSelectable()));
			} else if (index == R.styleable.EditText_android_textScaleX) {
				setTextScaleX(typedArray.getFloat(index, getTextScaleX()));
			} else if (index == R.styleable.EditText_android_textSize) {
				setTextSize(typedArray.getFloat(index, getTextSize()));
			} else if (index == R.styleable.EditText_android_typeface) {
				int typeface = typedArray.getInt(index, -1);

				if (typeface != -1) {
					setTypeface(parseTypeface(typeface));
				}
			}
		}

		setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
				drawableRight, drawableBottom);
		setImeActionLabel(imeActionLabel, imeActionId);
		setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);

		if (shadowColor != 0) {
			setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
		}
	}

	/**
	 * Returns the value of the enum {@link TruncateAt}, which corresponds to a
	 * specific value.
	 * 
	 * @param value
	 *            The value
	 * @return The value of the enum {@link TruncateAt}, which corresponds to
	 *         the given value
	 */
	private TruncateAt parseEllipsize(final int value) {
		switch (value) {
		case ELLIPSIZE_START_VALUE:
			return TruncateAt.START;
		case ELLIPSIZE_MIDDLE_VALUE:
			return TruncateAt.MIDDLE;
		case ELLIPSIZE_END_VALUE:
			return TruncateAt.END;
		case ELLIPSIZE_MARQUEE_VALUE:
			return TruncateAt.MARQUEE;
		default:
			return TruncateAt.END;
		}
	}

	/**
	 * Returns the value of the enum {@link Typeface}, which corresponds to a
	 * specific value.
	 * 
	 * @param value
	 *            The value
	 * @return The value of the enum {@link Typeface}, which corresponds to the
	 *         given value
	 */
	private Typeface parseTypeface(final int value) {
		switch (value) {
		case TYPEFACE_SANS_SERIF_VALUE:
			return Typeface.SANS_SERIF;
		case TYPEFACE_SERIF_VALUE:
			return Typeface.SERIF;
		case TYPEFACE_MONOSPACE_VALUE:
			return Typeface.MONOSPACE;
		default:
			return Typeface.DEFAULT;
		}
	}

	/**
	 * Creates and returns a listener, which allows to validate the value of the
	 * view, when its text has been changed.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link TextWatcher}
	 */
	private TextWatcher createTextChangeListener() {
		return new TextWatcher() {

			@Override
			public final void beforeTextChanged(final CharSequence s,
					final int start, final int count, final int after) {
				return;
			}

			@Override
			public final void onTextChanged(final CharSequence s,
					final int start, final int before, final int count) {
				return;
			}

			@Override
			public final void afterTextChanged(final Editable s) {
				if (isValidatedOnValueChange()) {
					validate();
				}

				adaptMaxNumberOfCharactersMessage();
			}

		};
	}

	/**
	 * Returns the color of the theme attribute
	 * <code>android.R.attr.colorAccent</code>.
	 * 
	 * @return The color of the theme attribute
	 *         <code>android.R.attr.colorAccent</code>
	 */
	private int getAccentColor() {
		TypedValue typedValue = new TypedValue();
		TypedArray typedArray = getContext().obtainStyledAttributes(
				typedValue.data, new int[] { android.R.attr.colorAccent });
		int color = typedArray.getColor(0, 0);
		typedArray.recycle();
		return color;
	}

	/**
	 * Sets the color of the line of the edit text, which allows to enter a
	 * text.
	 * 
	 * @param color
	 *            The color, which should be set, as an {@link Integer} value
	 */
	private void setEditTextLineColor(final int color) {
		getView().getBackground().setColorFilter(color,
				PorterDuff.Mode.SRC_ATOP);
	}

	/**
	 * Returns the message, which shows how many characters, in relation to the
	 * maximum number of characters, the edit text is allowed to contain, have
	 * already been entered.
	 * 
	 * @return The message, which shows how many characters, in relation to the
	 *         maximum number of characters, the edit text is allowed to
	 *         contain, have already been typed, as an instance of the type
	 *         {@link CharSequence}
	 */
	private CharSequence getMaxNumberOfCharactersMessage() {
		int maxLength = getMaxNumberOfCharacters();
		int currentLength = getView().length();
		return String.format(
				getResources().getString(
						R.string.edit_text_size_violation_error_message),
				currentLength, maxLength);
	}

	/**
	 * Adapts the text view, which shows the message, which shows how many
	 * characters, in relation ot the maximum number of characters, the edit
	 * text is allowed to contain, have already been entered.
	 */
	private void adaptMaxNumberOfCharactersMessage() {
		if (getMaxNumberOfCharacters() != -1) {
			setRightMessage(getMaxNumberOfCharactersMessage(), getView()
					.length() > getMaxNumberOfCharacters());
		} else {
			setRightMessage(null);
		}
	}

	@Override
	protected final Collection<Validator<CharSequence>> onGetRightErrorMessage() {
		CharSequence errorMessage = getMaxNumberOfCharactersMessage();

		if (getMaxNumberOfCharacters() != -1) {
			Validator<CharSequence> validator = Validators.maxLength(
					errorMessage, getMaxNumberOfCharacters());

			if (!validator.validate(getValue())) {
				Collection<Validator<CharSequence>> result = new LinkedList<>();
				result.add(validator);
				return result;
			}
		}

		return null;
	}

	@Override
	protected final void onValidate(final boolean valid) {
		if (valid) {
			setEditTextLineColor(getAccentColor());
			getView().setActivated(false);
		} else {
			setEditTextLineColor(getResources().getColor(
					R.color.default_error_color));
			getView().setActivated(true);
		}

		adaptMaxNumberOfCharactersMessage();
	}

	@Override
	protected final android.widget.EditText createView() {
		android.widget.EditText editText = new android.widget.EditText(
				getContext());
		editText.setBackgroundResource(R.drawable.edit_text);
		editText.addTextChangedListener(createTextChangeListener());
		return editText;
	}

	@Override
	protected final CharSequence getValue() {
		return getView().getText();
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 */
	public EditText(final Context context) {
		super(context);
		initialize(null);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 */
	public EditText(final Context context, final AttributeSet attributeSet) {
		super(context, attributeSet);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public EditText(final Context context, final AttributeSet attributeSet,
			final int defaultStyle) {
		super(context, attributeSet, defaultStyle);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 * @param defaultStyleResource
	 *            A resource identifier of a style resource that supplies
	 *            default values for the preference, used only if the default
	 *            style is 0 or can not be found in the theme. Can be 0 to not
	 *            look for defaults
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public EditText(final Context context, final AttributeSet attributeSet,
			final int defaultStyle, final int defaultStyleResource) {
		super(context, attributeSet, defaultStyle, defaultStyleResource);
		initialize(attributeSet);
	}

	/**
	 * Returns the maximum number of characters, the edit text is allowed to
	 * contain.
	 * 
	 * @return The maximum number of characters, the edit text is allowed to
	 *         contain, as an {@link Integer} value or -1, if the number of
	 *         characters is not restricted
	 */
	public final int getMaxNumberOfCharacters() {
		return maxNumberOfCharacters;
	}

	/**
	 * Sets the maximum number of characters, the edit text should be allowed to
	 * contain.
	 * 
	 * @param maxNumberOfCharacters
	 *            The maximum number of characters, which should be set, as an
	 *            {@link Integer} value. The maximum number of characters must
	 *            be at least 1 or -1, if the number of characters should not be
	 *            restricted
	 */
	public final void setMaxNumberOfCharacters(final int maxNumberOfCharacters) {
		if (maxNumberOfCharacters != -1) {
			ensureAtLeast(maxNumberOfCharacters, 1,
					"The maximum number of characters must be at least 1");
		}

		this.maxNumberOfCharacters = maxNumberOfCharacters;
		adaptMaxNumberOfCharactersMessage();
	}

	@Override
	public final void setError(final CharSequence error, final Drawable icon) {
		super.setError(error, icon);

		if (error == null) {
			setEditTextLineColor(getAccentColor());
			getView().setActivated(false);
		}
	}

	// ------------- Methods of the class android.widget.EditText -------------

	// CHECKSTYLE:OFF

	/**
	 * Gets the autolink mask of the text. See
	 * {@link android.text.util.Linkify#ALL Linkify.ALL} and peers for possible
	 * values.
	 *
	 * @attr ref android.R.styleable#TextView_autoLink
	 */
	public final int getAutoLinkMask() {
		return getView().getAutoLinkMask();
	}

	/**
	 * Sets the autolink mask of the text. See
	 * {@link android.text.util.Linkify#ALL Linkify.ALL} and peers for possible
	 * values.
	 *
	 * @attr ref android.R.styleable#TextView_autoLink
	 */
	public final void setAutoLinkMask(final int mask) {
		getView().setAutoLinkMask(mask);
	}

	/**
	 * @return whether or not the cursor is visible (assuming this TextView is
	 *         editable)
	 *
	 * @see #setCursorVisible(boolean)
	 *
	 * @attr ref android.R.styleable#TextView_cursorVisible
	 */
	public final boolean isCursorVisible() {
		return getView().isCursorVisible();
	}

	/**
	 * Set whether the cursor is visible. The default is true. Note that this
	 * property only makes sense for editable TextView.
	 *
	 * @see #isCursorVisible()
	 *
	 * @attr ref android.R.styleable#TextView_cursorVisible
	 */
	public final void setCursorVisible(final boolean visible) {
		getView().setCursorVisible(visible);
	}

	/**
	 * Returns the padding between the compound drawables and the text.
	 *
	 * @attr ref android.R.styleable#TextView_drawablePadding
	 */
	public final int getCompoundDrawablePadding() {
		return getView().getCompoundDrawablePadding();
	}

	/**
	 * Sets the size of the padding between the compound drawables and the text.
	 *
	 * @attr ref android.R.styleable#TextView_drawablePadding
	 */
	public final void setCompoundDrawablePadding(final int pad) {
		getView().setCompoundDrawablePadding(pad);
	}

	/**
	 * Returns the top padding of the view, plus space for the top Drawable if
	 * any.
	 */
	public final int getCompoundPaddingTop() {
		return getView().getCompoundPaddingTop();
	}

	/**
	 * Returns the bottom padding of the view, plus space for the bottom
	 * Drawable if any.
	 */
	public final int getCompoundPaddingBottom() {
		return getView().getCompoundPaddingBottom();
	}

	/**
	 * Returns the left padding of the view, plus space for the left Drawable if
	 * any.
	 */
	public final int getCompoundPaddingLeft() {
		return getView().getCompoundPaddingLeft();
	}

	/**
	 * Returns the right padding of the view, plus space for the right Drawable
	 * if any.
	 */
	public final int getCompoundPaddingRight() {
		return getView().getCompoundPaddingRight();
	}

	/**
	 * Returns the start padding of the view, plus space for the start Drawable
	 * if any.
	 */
	public final int getCompoundPaddingStart() {
		return getView().getCompoundPaddingStart();
	}

	/**
	 * Returns the end padding of the view, plus space for the end Drawable if
	 * any.
	 */
	public final int getCompoundPaddingEnd() {
		return getView().getCompoundPaddingEnd();
	}

	/**
	 * Returns the extended top padding of the view, including both the top
	 * Drawable if any and any extra space to keep more than maxLines of text
	 * from showing. It is only valid to call this after measuring.
	 */
	public final int getExtendedPaddingTop() {
		return getView().getExtendedPaddingTop();
	}

	/**
	 * Returns the extended bottom padding of the view, including both the
	 * bottom Drawable if any and any extra space to keep more than maxLines of
	 * text from showing. It is only valid to call this after measuring.
	 */
	public final int getExtendedPaddingBottom() {
		return getView().getExtendedPaddingBottom();
	}

	/**
	 * Returns the total left padding of the view, including the left Drawable
	 * if any.
	 */
	public final int getTotalPaddingLeft() {
		return getView().getTotalPaddingLeft();
	}

	/**
	 * Returns the total right padding of the view, including the right Drawable
	 * if any.
	 */
	public final int getTotalPaddingRight() {
		return getView().getTotalPaddingRight();
	}

	/**
	 * Returns the total start padding of the view, including the start Drawable
	 * if any.
	 */
	public final int getTotalPaddingStart() {
		return getView().getTotalPaddingStart();
	}

	/**
	 * Returns the total end padding of the view, including the end Drawable if
	 * any.
	 */
	public final int getTotalPaddingEnd() {
		return getView().getTotalPaddingEnd();
	}

	/**
	 * Returns the total top padding of the view, including the top Drawable if
	 * any, the extra space to keep more than maxLines from showing, and the
	 * vertical offset for gravity, if any.
	 */
	public final int getTotalPaddingTop() {
		return getView().getTotalPaddingTop();
	}

	/**
	 * Returns the total bottom padding of the view, including the bottom
	 * Drawable if any, the extra space to keep more than maxLines from showing,
	 * and the vertical offset for gravity, if any.
	 */
	public final int getTotalPaddingBottom() {
		return getView().getTotalPaddingBottom();
	}

	/**
	 * Sets the Drawables (if any) to appear to the left of, above, to the right
	 * of, and below the text. Use {@code null} if you do not want a Drawable
	 * there. The Drawables must already have had {@link Drawable#setBounds}
	 * called.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawablesRelative} or related methods.
	 *
	 * @attr ref android.R.styleable#TextView_drawableLeft
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableRight
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawables(final Drawable left,
			final Drawable top, final Drawable right, final Drawable bottom) {
		getView().setCompoundDrawables(left, top, right, bottom);
	}

	/**
	 * Sets the Drawables (if any) to appear to the left of, above, to the right
	 * of, and below the text. Use 0 if you do not want a Drawable there. The
	 * Drawables' bounds will be set to their intrinsic bounds.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawablesRelative} or related methods.
	 *
	 * @param left
	 *            Resource identifier of the left Drawable.
	 * @param top
	 *            Resource identifier of the top Drawable.
	 * @param right
	 *            Resource identifier of the right Drawable.
	 * @param bottom
	 *            Resource identifier of the bottom Drawable.
	 *
	 * @attr ref android.R.styleable#TextView_drawableLeft
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableRight
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawablesWithIntrinsicBounds(final int left,
			final int top, final int right, final int bottom) {
		getView().setCompoundDrawablesWithIntrinsicBounds(left, top, right,
				bottom);
	}

	/**
	 * Sets the Drawables (if any) to appear to the left of, above, to the right
	 * of, and below the text. Use {@code null} if you do not want a Drawable
	 * there. The Drawables' bounds will be set to their intrinsic bounds.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawablesRelative} or related methods.
	 *
	 * @attr ref android.R.styleable#TextView_drawableLeft
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableRight
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawablesWithIntrinsicBounds(
			final Drawable left, final Drawable top, final Drawable right,
			final Drawable bottom) {
		getView().setCompoundDrawablesWithIntrinsicBounds(left, top, right,
				bottom);
	}

	/**
	 * Sets the Drawables (if any) to appear to the start of, above, to the end
	 * of, and below the text. Use {@code null} if you do not want a Drawable
	 * there. The Drawables must already have had {@link Drawable#setBounds}
	 * called.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawables} or related methods.
	 *
	 * @attr ref android.R.styleable#TextView_drawableStart
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableEnd
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawablesRelative(final Drawable start,
			final Drawable top, final Drawable end, final Drawable bottom) {
		setCompoundDrawablesRelative(start, top, end, bottom);
	}

	/**
	 * Sets the Drawables (if any) to appear to the start of, above, to the end
	 * of, and below the text. Use 0 if you do not want a Drawable there. The
	 * Drawables' bounds will be set to their intrinsic bounds.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawables} or related methods.
	 *
	 * @param start
	 *            Resource identifier of the start Drawable.
	 * @param top
	 *            Resource identifier of the top Drawable.
	 * @param end
	 *            Resource identifier of the end Drawable.
	 * @param bottom
	 *            Resource identifier of the bottom Drawable.
	 *
	 * @attr ref android.R.styleable#TextView_drawableStart
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableEnd
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawablesRelativeWithIntrinsicBounds(
			final int start, final int top, final int end, final int bottom) {
		getView().setCompoundDrawablesRelativeWithIntrinsicBounds(start, top,
				end, bottom);
	}

	/**
	 * Sets the Drawables (if any) to appear to the start of, above, to the end
	 * of, and below the text. Use {@code null} if you do not want a Drawable
	 * there. The Drawables' bounds will be set to their intrinsic bounds.
	 * <p>
	 * Calling this method will overwrite any Drawables previously set using
	 * {@link #setCompoundDrawables} or related methods.
	 *
	 * @attr ref android.R.styleable#TextView_drawableStart
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableEnd
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final void setCompoundDrawablesRelativeWithIntrinsicBounds(
			final Drawable start, final Drawable top, final Drawable end,
			final Drawable bottom) {
		getView().setCompoundDrawablesRelativeWithIntrinsicBounds(start, top,
				end, bottom);
	}

	/**
	 * Returns drawables for the left, top, right, and bottom borders.
	 *
	 * @attr ref android.R.styleable#TextView_drawableLeft
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableRight
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final Drawable[] getCompoundDrawables() {
		return getView().getCompoundDrawables();
	}

	/**
	 * Returns drawables for the start, top, end, and bottom borders.
	 *
	 * @attr ref android.R.styleable#TextView_drawableStart
	 * @attr ref android.R.styleable#TextView_drawableTop
	 * @attr ref android.R.styleable#TextView_drawableEnd
	 * @attr ref android.R.styleable#TextView_drawableBottom
	 */
	public final Drawable[] getCompoundDrawablesRelative() {
		return getView().getCompoundDrawablesRelative();
	}

	/**
	 * Set the TextView's elegant height metrics flag. This setting selects font
	 * variants that have not been compacted to fit Latin-based vertical
	 * metrics, and also increases top and bottom bounds to provide more space.
	 *
	 * @param elegant
	 *            set the paint's elegant metrics flag.
	 *
	 * @attr ref android.R.styleable#TextView_elegantTextHeight
	 */
	public final void setElegantTextHeight(final boolean elegant) {
		getView().setElegantTextHeight(elegant);
	}

	/**
	 * Returns where, if anywhere, words that are longer than the view is wide
	 * should be ellipsized.
	 */
	public final TextUtils.TruncateAt getEllipsize() {
		return getView().getEllipsize();
	}

	/**
	 * Causes words in the text that are longer than the view is wide to be
	 * ellipsized instead of broken in the middle. You may also want to
	 * {@link #setSingleLine} or {@link #setHorizontallyScrolling} to constrain
	 * the text to a single line. Use <code>null</code> to turn off ellipsizing.
	 *
	 * If {@link #setMaxLines} has been used to set two or more lines, only
	 * {@link android.text.TextUtils.TruncateAt#END} and
	 * {@link android.text.TextUtils.TruncateAt#MARQUEE} are supported (other
	 * ellipsizing types will not do anything).
	 *
	 * @attr ref android.R.styleable#TextView_ellipsize
	 */
	public final void setEllipsize(final TextUtils.TruncateAt where) {
		getView().setEllipsize(where);
	}

	/**
	 * Makes the TextView exactly this many ems wide
	 *
	 * @see #setMaxEms(int)
	 * @see #setMinEms(int)
	 * @see #getMinEms()
	 * @see #getMaxEms()
	 *
	 * @attr ref android.R.styleable#TextView_ems
	 */
	public final void setEms(final int ems) {
		getView().setEms(ems);
	}

	/**
	 * @return the currently set font feature settings. Default is null.
	 *
	 * @see #setFontFeatureSettings(String)
	 * @see Paint#setFontFeatureSettings
	 */
	public final String getFontFeatureSettings() {
		return getView().getFontFeatureSettings();
	}

	/**
	 * Sets font feature settings. The format is the same as the CSS
	 * font-feature-settings attribute:
	 * http://dev.w3.org/csswg/css-fonts/#propdef-font-feature-settings
	 *
	 * @param fontFeatureSettings
	 *            font feature settings represented as CSS compatible string
	 * @see #getFontFeatureSettings()
	 * @see Paint#getFontFeatureSettings
	 *
	 * @attr ref android.R.styleable#TextView_fontFeatureSettings
	 */
	public final void setFontFeatureSettings(final String fontFeatureSettings) {
		getView().setFontFeatureSettings(fontFeatureSettings);
	}

	/**
	 * Return whether this text view is including its entire text contents in
	 * frozen icicles.
	 *
	 * @return Returns true if text is included, false if it isn't.
	 *
	 * @see #setFreezesText
	 */
	public final boolean getFreezesText() {
		return getView().getFreezesText();
	}

	/**
	 * Control whether this text view saves its entire text contents when
	 * freezing to an icicle, in addition to dynamic state such as cursor
	 * position. By default this is false, not saving the text. Set to true if
	 * the text in the text view is not being saved somewhere else in persistent
	 * storage (such as in a content provider) so that if the view is later
	 * thawed the user will not lose their data.
	 *
	 * @param freezesText
	 *            Controls whether a frozen icicle should include the entire
	 *            text data: true to include it, false to not.
	 *
	 * @attr ref android.R.styleable#TextView_freezesText
	 */
	public final void setFreezesText(final boolean freezesText) {
		getView().setFreezesText(freezesText);
	}

	/**
	 * Returns the hint that is displayed when the text of the TextView is
	 * empty.
	 *
	 * @attr ref android.R.styleable#TextView_hint
	 */
	public final CharSequence getHint() {
		return getView().getHint();
	}

	/**
	 * Sets the text to be displayed when the text of the TextView is empty.
	 * Null means to use the normal empty text. The hint does not currently
	 * participate in determining the size of the view.
	 *
	 * @attr ref android.R.styleable#TextView_hint
	 */
	public final void setHint(final CharSequence hint) {
		getView().setHint(hint);
	}

	/**
	 * Sets the text to be displayed when the text of the TextView is empty,
	 * from a resource.
	 *
	 * @attr ref android.R.styleable#TextView_hint
	 */
	public final void setHint(final int resid) {
		getView().setHint(resid);
	}

	/**
	 * Change the editor type integer associated with the text view, which will
	 * be reported to an IME with {@link EditorInfo#imeOptions} when it has
	 * focus.
	 * 
	 * @see #getImeOptions
	 * @see android.view.inputmethod.EditorInfo
	 * @attr ref android.R.styleable#TextView_imeOptions
	 */
	public final void setImeOptions(final int imeOptions) {
		getView().setImeOptions(imeOptions);
	}

	/**
	 * Get the type of the IME editor.
	 *
	 * @see #setImeOptions(int)
	 * @see android.view.inputmethod.EditorInfo
	 */
	public final int getImeOptions() {
		return getView().getImeOptions();
	}

	/**
	 * Change the custom IME action associated with the text view, which will be
	 * reported to an IME with {@link EditorInfo#actionLabel} and
	 * {@link EditorInfo#actionId} when it has focus.
	 * 
	 * @see #getImeActionLabel
	 * @see #getImeActionId
	 * @see android.view.inputmethod.EditorInfo
	 * @attr ref android.R.styleable#TextView_imeActionLabel
	 * @attr ref android.R.styleable#TextView_imeActionId
	 */
	public final void setImeActionLabel(final CharSequence label,
			final int actionId) {
		getView().setImeActionLabel(label, actionId);
	}

	/**
	 * Get the IME action label previous set with {@link #setImeActionLabel}.
	 *
	 * @see #setImeActionLabel
	 * @see android.view.inputmethod.EditorInfo
	 */
	public final CharSequence getImeActionLabel() {
		return getView().getImeActionLabel();
	}

	/**
	 * Get the IME action ID previous set with {@link #setImeActionLabel}.
	 *
	 * @see #setImeActionLabel
	 * @see android.view.inputmethod.EditorInfo
	 */
	public final int getImeActionId() {
		return getView().getImeActionId();
	}

	/**
	 * Set whether the TextView includes extra top and bottom padding to make
	 * room for accents that go above the normal ascent and descent. The default
	 * is true.
	 *
	 * @see #getIncludeFontPadding()
	 *
	 * @attr ref android.R.styleable#TextView_includeFontPadding
	 */
	public final void setIncludeFontPadding(final boolean includepad) {
		getView().setIncludeFontPadding(includepad);
	}

	/**
	 * Gets whether the TextView includes extra top and bottom padding to make
	 * room for accents that go above the normal ascent and descent.
	 *
	 * @see #setIncludeFontPadding(boolean)
	 *
	 * @attr ref android.R.styleable#TextView_includeFontPadding
	 */
	public final boolean getIncludeFontPadding() {
		return getView().getIncludeFontPadding();
	}

	/**
	 * Get the type of the editable content.
	 *
	 * @see #setInputType(int)
	 * @see android.text.InputType
	 */
	public final int getInputType() {
		return getView().getInputType();
	}

	/**
	 * Set the type of the content with a constant as defined for
	 * {@link EditorInfo#inputType}. This will take care of changing the key
	 * listener, by calling {@link #setKeyListener(KeyListener)}, to match the
	 * given content type. If the given content type is
	 * {@link EditorInfo#TYPE_NULL} then a soft keyboard will not be displayed
	 * for this text view.
	 *
	 * Note that the maximum number of displayed lines (see
	 * {@link #setMaxLines(int)}) will be modified if you change the
	 * {@link EditorInfo#TYPE_TEXT_FLAG_MULTI_LINE} flag of the input type.
	 *
	 * @see #getInputType()
	 * @see #setRawInputType(int)
	 * @see android.text.InputType
	 * @attr ref android.R.styleable#TextView_inputType
	 */
	public final void setInputType(final int type) {
		getView().setInputType(type);
	}

	/**
	 * @return the extent by which text is currently being letter-spaced. This
	 *         will normally be 0.
	 *
	 * @see #setLetterSpacing(float)
	 * @see Paint#setLetterSpacing
	 */
	public final float getLetterSpacing() {
		return getView().getLetterSpacing();
	}

	/**
	 * Sets text letter-spacing. The value is in 'EM' units. Typical values for
	 * slight expansion will be around 0.05. Negative values tighten text.
	 *
	 * @see #getLetterSpacing()
	 * @see Paint#getLetterSpacing
	 *
	 * @attr ref android.R.styleable#TextView_letterSpacing
	 */
	public final void setLetterSpacing(final float letterSpacing) {
		getView().setLetterSpacing(letterSpacing);
	}

	/**
	 * Makes the TextView exactly this many lines tall.
	 *
	 * Note that setting this value overrides any other (minimum / maximum)
	 * number of lines or height setting. A single line TextView will set this
	 * value to 1.
	 *
	 * @attr ref android.R.styleable#TextView_lines
	 */
	public final void setLines(final int lines) {
		getView().setLines(lines);
	}

	/**
	 * Sets line spacing for this TextView. Each line will have its height
	 * multiplied by <code>mult</code> and have <code>add</code> added to it.
	 *
	 * @attr ref android.R.styleable#TextView_lineSpacingExtra
	 * @attr ref android.R.styleable#TextView_lineSpacingMultiplier
	 */
	public final void setLineSpacing(final float add, final float mult) {
		getView().setLineSpacing(add, mult);
	}

	/**
	 * Gets the line spacing multiplier
	 *
	 * @return the value by which each line's height is multiplied to get its
	 *         actual height.
	 *
	 * @see #setLineSpacing(float, float)
	 * @see #getLineSpacingExtra()
	 *
	 * @attr ref android.R.styleable#TextView_lineSpacingMultiplier
	 */
	public final float getLineSpacingMultiplier() {
		return getView().getLineSpacingMultiplier();
	}

	/**
	 * Gets the line spacing extra space
	 *
	 * @return the extra space that is added to the height of each lines of this
	 *         TextView.
	 *
	 * @see #setLineSpacing(float, float)
	 * @see #getLineSpacingMultiplier()
	 *
	 * @attr ref android.R.styleable#TextView_lineSpacingExtra
	 */
	public final float getLineSpacingExtra() {
		return getView().getLineSpacingExtra();
	}

	/**
	 * Sets whether the movement method will automatically be set to
	 * {@link LinkMovementMethod} if {@link #setAutoLinkMask} has been set to
	 * nonzero and links are detected in {@link #setText}. The default is true.
	 *
	 * @attr ref android.R.styleable#TextView_linksClickable
	 */
	public final void setLinksClickable(final boolean whether) {
		getView().setLinksClickable(whether);
	}

	/**
	 * Returns whether the movement method will automatically be set to
	 * {@link LinkMovementMethod} if {@link #setAutoLinkMask} has been set to
	 * nonzero and links are detected in {@link #setText}. The default is true.
	 *
	 * @attr ref android.R.styleable#TextView_linksClickable
	 */
	public final boolean getLinksClickable() {
		return getView().getLinksClickable();
	}

	/**
	 * Sets how many times to repeat the marquee animation. Only applied if the
	 * TextView has marquee enabled. Set to -1 to repeat indefinitely.
	 *
	 * @see #getMarqueeRepeatLimit()
	 *
	 * @attr ref android.R.styleable#TextView_marqueeRepeatLimit
	 */
	public final void setMarqueeRepeatLimit(final int marqueeLimit) {
		getView().setMarqueeRepeatLimit(marqueeLimit);
	}

	/**
	 * Gets the number of times the marquee animation is repeated. Only
	 * meaningful if the TextView has marquee enabled.
	 *
	 * @return the number of times the marquee animation is repeated. -1 if the
	 *         animation repeats indefinitely
	 *
	 * @see #setMarqueeRepeatLimit(int)
	 *
	 * @attr ref android.R.styleable#TextView_marqueeRepeatLimit
	 */
	public final int getMarqueeRepeatLimit() {
		return getView().getMarqueeRepeatLimit();
	}

	/**
	 * Makes the TextView at most this many ems wide
	 *
	 * @attr ref android.R.styleable#TextView_maxEms
	 */
	public final void setMaxEms(final int maxems) {
		getView().setMaxEms(maxems);
	}

	/**
	 * @return the maximum width of the TextView, expressed in ems or -1 if the
	 *         maximum width was set in pixels instead (using
	 *         {@link #setMaxWidth(int)} or {@link #setWidth(int)}).
	 *
	 * @see #setMaxEms(int)
	 * @see #setEms(int)
	 *
	 * @attr ref android.R.styleable#TextView_maxEms
	 */
	public final int getMaxEms() {
		return getView().getMaxEms();
	}

	/**
	 * Sets the list of input filters that will be used if the buffer is
	 * Editable. Has no effect otherwise.
	 *
	 * @attr ref android.R.styleable#TextView_maxLength
	 */
	public final void setFilters(final InputFilter[] filters) {
		getView().setFilters(filters);
	}

	/**
	 * Returns the current list of input filters.
	 *
	 * @attr ref android.R.styleable#TextView_maxLength
	 */
	public final InputFilter[] getFilters() {
		return getView().getFilters();
	}

	/**
	 * Makes the TextView at most this many lines tall.
	 *
	 * Setting this value overrides any other (maximum) height setting.
	 *
	 * @attr ref android.R.styleable#TextView_maxLines
	 */
	public final void setMaxLines(final int maxlines) {
		getView().setMaxLines(maxlines);
	}

	/**
	 * @return the maximum number of lines displayed in this TextView, or -1 if
	 *         the maximum height was set in pixels instead using
	 *         {@link #setMaxHeight(int) or #setHeight(int)}.
	 *
	 * @see #setMaxLines(int)
	 *
	 * @attr ref android.R.styleable#TextView_maxLines
	 */
	public final int getMaxLines() {
		return getView().getMaxLines();
	}

	/**
	 * Makes the TextView at least this many ems wide
	 *
	 * @attr ref android.R.styleable#TextView_minEms
	 */
	public final void setMinEms(final int minems) {
		getView().setMinEms(minems);
	}

	/**
	 * @return the minimum width of the TextView, expressed in ems or -1 if the
	 *         minimum width was set in pixels instead (using
	 *         {@link #setMinWidth(int)} or {@link #setWidth(int)}).
	 *
	 * @see #setMinEms(int)
	 * @see #setEms(int)
	 *
	 * @attr ref android.R.styleable#TextView_minEms
	 */
	public final int getMinEms() {
		return getView().getMinEms();
	}

	/**
	 * Makes the TextView at least this many lines tall.
	 *
	 * Setting this value overrides any other (minimum) height setting. A single
	 * line TextView will set this value to 1.
	 *
	 * @see #getMinLines()
	 *
	 * @attr ref android.R.styleable#TextView_minLines
	 */
	public final void setMinLines(final int minlines) {
		getView().setMinLines(minlines);
	}

	/**
	 * @return the minimum number of lines displayed in this TextView, or -1 if
	 *         the minimum height was set in pixels instead using
	 *         {@link #setMinHeight(int) or #setHeight(int)}.
	 *
	 * @see #setMinLines(int)
	 *
	 * @attr ref android.R.styleable#TextView_minLines
	 */
	public final int getMinLines() {
		return getView().getMinLines();
	}

	/**
	 * Set the private content type of the text, which is the
	 * {@link EditorInfo#privateImeOptions EditorInfo.privateImeOptions} field
	 * that will be filled in when creating an input connection.
	 *
	 * @see #getPrivateImeOptions()
	 * @see EditorInfo#privateImeOptions
	 * @attr ref android.R.styleable#TextView_privateImeOptions
	 */
	public final void setPrivateImeOptions(final String type) {
		getView().setPrivateImeOptions(type);
	}

	/**
	 * Get the private type of the content.
	 *
	 * @see #setPrivateImeOptions(String)
	 * @see EditorInfo#privateImeOptions
	 */
	public final String getPrivateImeOptions() {
		return getView().getPrivateImeOptions();
	}

	/**
	 * Set the TextView so that when it takes focus, all the text is selected.
	 *
	 * @attr ref android.R.styleable#TextView_selectAllOnFocus
	 */
	public final void setSelectAllOnFocus(final boolean selectAllOnFocus) {
		getView().setSelectAllOnFocus(selectAllOnFocus);
	}

	/**
	 * Gives the text a shadow of the specified blur radius and color, the
	 * specified distance from its drawn position.
	 * <p>
	 * The text shadow produced does not interact with the properties on view
	 * that are responsible for real time shadows, {@link View#getElevation()
	 * elevation} and {@link View#getTranslationZ() translationZ}.
	 *
	 * @see Paint#setShadowLayer(float, float, float, int)
	 *
	 * @attr ref android.R.styleable#TextView_shadowColor
	 * @attr ref android.R.styleable#TextView_shadowDx
	 * @attr ref android.R.styleable#TextView_shadowDy
	 * @attr ref android.R.styleable#TextView_shadowRadius
	 */
	public final void setShadowLayer(final float radius, final float dx,
			final float dy, final int color) {
		getView().setShadowLayer(radius, dx, dy, color);
	}

	/**
	 * Gets the radius of the shadow layer.
	 *
	 * @return the radius of the shadow layer. If 0, the shadow layer is not
	 *         visible
	 *
	 * @see #setShadowLayer(float, float, float, int)
	 *
	 * @attr ref android.R.styleable#TextView_shadowRadius
	 */
	public final float getShadowRadius() {
		return getView().getShadowRadius();
	}

	/**
	 * @return the horizontal offset of the shadow layer
	 *
	 * @see #setShadowLayer(float, float, float, int)
	 *
	 * @attr ref android.R.styleable#TextView_shadowDx
	 */
	public final float getShadowDx() {
		return getView().getShadowDx();
	}

	/**
	 * @return the vertical offset of the shadow layer
	 *
	 * @see #setShadowLayer(float, float, float, int)
	 *
	 * @attr ref android.R.styleable#TextView_shadowDy
	 */
	public final float getShadowDy() {
		return getView().getShadowDy();
	}

	/**
	 * @return the color of the shadow layer
	 *
	 * @see #setShadowLayer(float, float, float, int)
	 *
	 * @attr ref android.R.styleable#TextView_shadowColor
	 */
	public final int getShadowColor() {
		return getView().getShadowColor();
	}

	/**
	 * Sets the properties of this field (lines, horizontally scrolling,
	 * transformation method) to be for a single-line input.
	 *
	 * @attr ref android.R.styleable#TextView_singleLine
	 */
	public final void setSingleLine() {
		getView().setSingleLine();
	}

	/**
	 * If true, sets the properties of this field (number of lines, horizontally
	 * scrolling, transformation method) to be for a single-line input; if
	 * false, restores these to the default conditions.
	 *
	 * Note that the default conditions are not necessarily those that were in
	 * effect prior this method, and you may want to reset these properties to
	 * your custom values.
	 *
	 * @attr ref android.R.styleable#TextView_singleLine
	 */
	public final void setSingleLine(final boolean singleLine) {
		getView().setSingleLine(singleLine);
	}

	/**
	 * Sets the string value of the TextView. TextView <em>does not</em> accept
	 * HTML-like formatting, which you can do with text strings in XML resource
	 * files. To style your strings, attach android.text.style.* objects to a
	 * {@link android.text.SpannableString SpannableString}, or see the <a
	 * href="{@docRoot}
	 * guide/topics/resources/available-resources.html#stringresources">
	 * Available Resource Types</a> documentation for an example of setting
	 * formatted text in the XML resource file.
	 *
	 * @attr ref android.R.styleable#TextView_text
	 */
	public final void setText(final CharSequence text) {
		getView().setText(text);
	}

	/**
	 * Like {@link #setText(CharSequence)}, except that the cursor position (if
	 * any) is retained in the new text.
	 *
	 * @param text
	 *            The new text to place in the text view.
	 *
	 * @see #setText(CharSequence)
	 */
	public final void setTextKeepState(final CharSequence text) {
		getView().setTextKeepState(text);
	}

	/**
	 * Sets the text that this TextView is to display (see
	 * {@link #setText(CharSequence)}) and also sets whether it is stored in a
	 * styleable/spannable buffer and whether it is editable.
	 *
	 * @attr ref android.R.styleable#TextView_text
	 * @attr ref android.R.styleable#TextView_bufferType
	 */
	public final void setText(final CharSequence text, final BufferType type) {
		getView().setText(text, type);
	}

	/**
	 * Sets the TextView to display the specified slice of the specified char
	 * array. You must promise that you will not change the contents of the
	 * array except for right before another call to setText(), since the
	 * TextView has no way to know that the text has changed and that it needs
	 * to invalidate and re-layout.
	 */
	public final void setText(final char[] text, final int start, final int len) {
		getView().setText(text, start, len);
	}

	/**
	 * Like {@link #setText(CharSequence, android.widget.TextView.BufferType)},
	 * except that the cursor position (if any) is retained in the new text.
	 *
	 * @see #setText(CharSequence, android.widget.TextView.BufferType)
	 */
	public final void setTextKeepState(final CharSequence text,
			final BufferType type) {
		getView().setTextKeepState(text, type);
	}

	public final void setText(final int resid) {
		getView().setText(resid);
	}

	public final void setText(final int resid, final BufferType type) {
		getView().setText(resid, type);
	}

	/**
	 * Sets the properties of this field to transform input to ALL CAPS display.
	 * This may use a "small caps" formatting if available. This setting will be
	 * ignored if this field is editable or selectable.
	 *
	 * This call replaces the current transformation method. Disabling this will
	 * not necessarily restore the previous behavior from before this was
	 * enabled.
	 *
	 * @see #setTransformationMethod(TransformationMethod)
	 * @attr ref android.R.styleable#TextView_textAllCaps
	 */
	public final void setAllCaps(final boolean allCaps) {
		getView().setAllCaps(allCaps);
	}

	/**
	 * Sets the text color, size, style, hint color, and highlight color from
	 * the specified TextAppearance resource.
	 */
	public final void setTextAppearance(final Context context, final int resid) {
		getView().setTextAppearance(context, resid);
	}

	/**
	 * Sets the text color for all the states (normal, selected, focused) to be
	 * this color.
	 *
	 * @see #setTextColor(ColorStateList)
	 * @see #getTextColors()
	 *
	 * @attr ref android.R.styleable#TextView_textColor
	 */
	public final void setTextColor(final int color) {
		getView().setTextColor(color);
	}

	/**
	 * Sets the text color.
	 *
	 * @see #setTextColor(int)
	 * @see #getTextColors()
	 * @see #setHintTextColor(ColorStateList)
	 * @see #setLinkTextColor(ColorStateList)
	 *
	 * @attr ref android.R.styleable#TextView_textColor
	 */
	public final void setTextColor(final ColorStateList colors) {
		getView().setTextColor(colors);
	}

	/**
	 * Gets the text colors for the different states (normal, selected, focused)
	 * of the TextView.
	 *
	 * @see #setTextColor(ColorStateList)
	 * @see #setTextColor(int)
	 *
	 * @attr ref android.R.styleable#TextView_textColor
	 */
	public final ColorStateList getTextColors() {
		return getView().getTextColors();
	}

	/**
	 * <p>
	 * Return the current color selected for normal text.
	 * </p>
	 *
	 * @return Returns the current text color.
	 */
	public final int getCurrentTextColor() {
		return getView().getCurrentTextColor();
	}

	/**
	 * Sets the color used to display the selection highlight.
	 *
	 * @attr ref android.R.styleable#TextView_textColorHighlight
	 */
	public final void setHighlightColor(final int color) {
		getView().setHighlightColor(color);
	}

	/**
	 * @return the color used to display the selection highlight
	 *
	 * @see #setHighlightColor(int)
	 *
	 * @attr ref android.R.styleable#TextView_textColorHighlight
	 */
	public final int getHighlightColor() {
		return getView().getHighlightColor();
	}

	/**
	 * Sets whether the soft input method will be made visible when this
	 * TextView gets focused. The default is true.
	 */
	public final void setShowSoftInputOnFocus(final boolean show) {
		getView().setShowSoftInputOnFocus(show);
	}

	/**
	 * Returns whether the soft input method will be made visible when this
	 * TextView gets focused. The default is true.
	 */
	public final boolean getShowSoftInputOnFocus() {
		return getView().getShowSoftInputOnFocus();
	}

	/**
	 * Sets the color of the hint text for all the states (disabled, focussed,
	 * selected...) of this TextView.
	 *
	 * @see #setHintTextColor(ColorStateList)
	 * @see #getHintTextColors()
	 * @see #setTextColor(int)
	 *
	 * @attr ref android.R.styleable#TextView_textColorHint
	 */
	public final void setHintTextColor(final int color) {
		getView().setHintTextColor(color);
	}

	/**
	 * Sets the color of the hint text.
	 *
	 * @see #getHintTextColors()
	 * @see #setHintTextColor(int)
	 * @see #setTextColor(ColorStateList)
	 * @see #setLinkTextColor(ColorStateList)
	 *
	 * @attr ref android.R.styleable#TextView_textColorHint
	 */
	public final void setHintTextColor(final ColorStateList colors) {
		getView().setHintTextColor(colors);
	}

	/**
	 * @return the color of the hint text, for the different states of this
	 *         TextView.
	 *
	 * @see #setHintTextColor(ColorStateList)
	 * @see #setHintTextColor(int)
	 * @see #setTextColor(ColorStateList)
	 * @see #setLinkTextColor(ColorStateList)
	 *
	 * @attr ref android.R.styleable#TextView_textColorHint
	 */
	public final ColorStateList getHintTextColors() {
		return getView().getHintTextColors();
	}

	/**
	 * <p>
	 * Return the current color selected to paint the hint text.
	 * </p>
	 *
	 * @return Returns the current hint text color.
	 */
	public final int getCurrentHintTextColor() {
		return getView().getCurrentHintTextColor();
	}

	/**
	 * Sets the color of links in the text.
	 *
	 * @see #setLinkTextColor(ColorStateList)
	 * @see #getLinkTextColors()
	 *
	 * @attr ref android.R.styleable#TextView_textColorLink
	 */
	public final void setLinkTextColor(final int color) {
		getView().setLinkTextColor(color);
	}

	/**
	 * Sets the color of links in the text.
	 *
	 * @see #setLinkTextColor(int)
	 * @see #getLinkTextColors()
	 * @see #setTextColor(ColorStateList)
	 * @see #setHintTextColor(ColorStateList)
	 *
	 * @attr ref android.R.styleable#TextView_textColorLink
	 */
	public final void setLinkTextColor(final ColorStateList colors) {
		getView().setLinkTextColor(colors);
	}

	/**
	 * @return the list of colors used to paint the links in the text, for the
	 *         different states of this TextView
	 *
	 * @see #setLinkTextColor(ColorStateList)
	 * @see #setLinkTextColor(int)
	 *
	 * @attr ref android.R.styleable#TextView_textColorLink
	 */
	public final ColorStateList getLinkTextColors() {
		return getView().getLinkTextColors();
	}

	/**
	 * Sets whether the content of this view is selectable by the user. The
	 * default is {@code false}, meaning that the content is not selectable.
	 * <p>
	 * When you use a TextView to display a useful piece of information to the
	 * user (such as a contact's address), make it selectable, so that the user
	 * can select and copy its content. You can also use set the XML attribute
	 * {@link android.R.styleable#TextView_textIsSelectable} to "true".
	 * <p>
	 * When you call this method to set the value of {@code textIsSelectable},
	 * it sets the flags {@code focusable}, {@code focusableInTouchMode},
	 * {@code clickable}, and {@code longClickable} to the same value. These
	 * flags correspond to the attributes
	 * {@link android.R.styleable#View_focusable android:focusable},
	 * {@link android.R.styleable#View_focusableInTouchMode
	 * android:focusableInTouchMode}, {@link android.R.styleable#View_clickable
	 * android:clickable}, and {@link android.R.styleable#View_longClickable
	 * android:longClickable}. To restore any of these flags to a state you had
	 * set previously, call one or more of the following methods:
	 * {@link #setFocusable(boolean) setFocusable()},
	 * {@link #setFocusableInTouchMode(boolean) setFocusableInTouchMode()},
	 * {@link #setClickable(boolean) setClickable()} or
	 * {@link #setLongClickable(boolean) setLongClickable()}.
	 *
	 * @param selectable
	 *            Whether the content of this TextView should be selectable.
	 */
	public final void setTextIsSelectable(final boolean selectable) {
		getView().setTextIsSelectable(selectable);
	}

	/**
	 *
	 * Returns the state of the {@code textIsSelectable} flag (See
	 * {@link #setTextIsSelectable setTextIsSelectable()}). Although you have to
	 * set this flag to allow users to select and copy text in a non-editable
	 * TextView, the content of an {@link EditText} can always be selected,
	 * independently of the value of this flag.
	 * <p>
	 *
	 * @return True if the text displayed in this TextView can be selected by
	 *         the user.
	 *
	 * @attr ref android.R.styleable#TextView_textIsSelectable
	 */
	public final boolean isTextSelectable() {
		return getView().isTextSelectable();
	}

	/**
	 * @return the extent by which text is currently being stretched
	 *         horizontally. This will usually be 1.
	 */
	public final float getTextScaleX() {
		return getView().getTextScaleX();
	}

	/**
	 * Sets the extent by which text should be stretched horizontally.
	 *
	 * @attr ref android.R.styleable#TextView_textScaleX
	 */
	public final void setTextScaleX(final float size) {
		getView().setTextScaleX(size);
	}

	/**
	 * Set the default text size to the given value, interpreted as "scaled
	 * pixel" units. This size is adjusted based on the current density and user
	 * font size preference.
	 *
	 * @param size
	 *            The scaled pixel size.
	 *
	 * @attr ref android.R.styleable#TextView_textSize
	 */
	public final void setTextSize(final float size) {
		getView().setTextSize(size);
	}

	/**
	 * Set the default text size to a given unit and value. See
	 * {@link TypedValue} for the possible dimension units.
	 *
	 * @param unit
	 *            The desired dimension unit.
	 * @param size
	 *            The desired size in the given units.
	 *
	 * @attr ref android.R.styleable#TextView_textSize
	 */
	public final void setTextSize(final int unit, final float size) {
		getView().setTextSize(size);
	}

	/**
	 * @return the size (in pixels) of the default text size in this TextView.
	 */
	public final float getTextSize() {
		return getView().getTextSize();
	}

	/**
	 * Sets the typeface and style in which the text should be displayed, and
	 * turns on the fake bold and italic bits in the Paint if the Typeface that
	 * you provided does not have all the bits in the style that you specified.
	 *
	 * @attr ref android.R.styleable#TextView_typeface
	 * @attr ref android.R.styleable#TextView_textStyle
	 */
	public final void setTypeface(final Typeface tf, final int style) {
		getView().setTypeface(tf, style);
	}

	/**
	 * Sets the typeface and style in which the text should be displayed. Note
	 * that not all Typeface families actually have bold and italic variants, so
	 * you may need to use {@link #setTypeface(Typeface, int)} to get the
	 * appearance that you actually want.
	 *
	 * @see #getTypeface()
	 *
	 * @attr ref android.R.styleable#TextView_fontFamily
	 * @attr ref android.R.styleable#TextView_typeface
	 * @attr ref android.R.styleable#TextView_textStyle
	 */
	public final void setTypeface(final Typeface tf) {
		getView().setTypeface(tf);
	}

	/**
	 * @return the current typeface and style in which the text is being
	 *         displayed.
	 *
	 * @see #setTypeface(Typeface)
	 *
	 * @attr ref android.R.styleable#TextView_fontFamily
	 * @attr ref android.R.styleable#TextView_typeface
	 * @attr ref android.R.styleable#TextView_textStyle
	 */
	public final Typeface getTypeface() {
		return getView().getTypeface();
	}

	/**
	 * Returns the length, in characters, of the text managed by this TextView
	 */
	public final int length() {
		return getView().length();
	}

	/**
	 * Return the text the TextView is displaying as an Editable object. If the
	 * text is not editable, null is returned.
	 *
	 * @see #getText
	 */
	public final Editable getEditableText() {
		return getView().getEditableText();
	}

	/**
	 * @return the height of one standard line in pixels. Note that markup
	 *         within the text can cause individual lines to be taller or
	 *         shorter than this height, and the layout may contain additional
	 *         first- or last-line padding.
	 */
	public final int getLineHeight() {
		return getView().getLineHeight();
	}

	/**
	 * @return the Layout that is currently being used to display the text. This
	 *         can be null if the text or width has recently changes.
	 */
	public final Layout getLayout() {
		return getView().getLayout();
	}

	/**
	 * @return the current key listener for this TextView. This will frequently
	 *         be null for non-EditText TextViews.
	 *
	 * @attr ref android.R.styleable#TextView_numeric
	 * @attr ref android.R.styleable#TextView_digits
	 * @attr ref android.R.styleable#TextView_phoneNumber
	 * @attr ref android.R.styleable#TextView_inputMethod
	 * @attr ref android.R.styleable#TextView_capitalize
	 * @attr ref android.R.styleable#TextView_autoText
	 */
	public final KeyListener getKeyListener() {
		return getView().getKeyListener();
	}

	/**
	 * Sets the key listener to be used with this TextView. This can be null to
	 * disallow user input. Note that this method has significant and subtle
	 * interactions with soft keyboards and other input method: see
	 * {@link KeyListener#getInputType() KeyListener.getContentType()} for
	 * important details. Calling this method will replace the current content
	 * type of the text view with the content type returned by the key listener.
	 * <p>
	 * Be warned that if you want a TextView with a key listener or movement
	 * method not to be focusable, or if you want a TextView without a key
	 * listener or movement method to be focusable, you must call
	 * {@link #setFocusable} again after calling this to get the focusability
	 * back the way you want it.
	 *
	 * @attr ref android.R.styleable#TextView_numeric
	 * @attr ref android.R.styleable#TextView_digits
	 * @attr ref android.R.styleable#TextView_phoneNumber
	 * @attr ref android.R.styleable#TextView_inputMethod
	 * @attr ref android.R.styleable#TextView_capitalize
	 * @attr ref android.R.styleable#TextView_autoText
	 */
	public final void setKeyListener(final KeyListener input) {
		getView().setKeyListener(input);
	}

	/**
	 * @return the movement method being used for this TextView. This will
	 *         frequently be null for non-EditText TextViews.
	 */
	public final MovementMethod getMovementMethod() {
		return getView().getMovementMethod();
	}

	/**
	 * Sets the movement method (arrow key handler) to be used for this
	 * TextView. This can be null to disallow using the arrow keys to move the
	 * cursor or scroll the view.
	 * <p>
	 * Be warned that if you want a TextView with a key listener or movement
	 * method not to be focusable, or if you want a TextView without a key
	 * listener or movement method to be focusable, you must call
	 * {@link #setFocusable} again after calling this to get the focusability
	 * back the way you want it.
	 */
	public final void setMovementMethod(final MovementMethod movement) {
		getView().setMovementMethod(movement);
	}

	/**
	 * @return the current transformation method for this TextView. This will
	 *         frequently be null except for single-line and password fields.
	 *
	 * @attr ref android.R.styleable#TextView_password
	 * @attr ref android.R.styleable#TextView_singleLine
	 */
	public final TransformationMethod getTransformationMethod() {
		return getView().getTransformationMethod();
	}

	/**
	 * Sets the transformation that is applied to the text that this TextView is
	 * displaying.
	 *
	 * @attr ref android.R.styleable#TextView_password
	 * @attr ref android.R.styleable#TextView_singleLine
	 */
	public final void setTransformationMethod(final TransformationMethod method) {
		getView().setTransformationMethod(method);
	}

	/**
	 * Get the default {@link Locale} of the text in this TextView.
	 * 
	 * @return the default {@link Locale} of the text in this TextView.
	 */
	public final Locale getTextLocale() {
		return getView().getTextLocale();
	}

	/**
	 * Set the default {@link Locale} of the text in this TextView to the given
	 * value. This value is used to choose appropriate typefaces for ambiguous
	 * characters. Typically used for CJK locales to disambiguate
	 * Hanzi/Kanji/Hanja characters.
	 *
	 * @param locale
	 *            the {@link Locale} for drawing text, must not be null.
	 *
	 * @see Paint#setTextLocale
	 */
	public final void setTextLocale(final Locale locale) {
		getView().setTextLocale(locale);
	}

	/**
	 * @return the base paint used for the text. Please use this only to consult
	 *         the Paint's properties and not to change them.
	 */
	public final TextPaint getPaint() {
		return getView().getPaint();
	}

	/**
	 * Returns the list of URLSpans attached to the text (by {@link Linkify} or
	 * otherwise) if any. You can call {@link URLSpan#getURL} on them to find
	 * where they link to or use {@link Spanned#getSpanStart} and
	 * {@link Spanned#getSpanEnd} to find the region of the text they are
	 * attached to.
	 */
	public final URLSpan[] getUrls() {
		return getView().getUrls();
	}

	/**
	 * Sets the horizontal alignment of the text and the vertical gravity that
	 * will be used when there is extra space in the TextView beyond what is
	 * required for the text itself.
	 *
	 * @see android.view.Gravity
	 * @attr ref android.R.styleable#TextView_gravity
	 */
	public final void setTextGravity(final int gravity) {
		getView().setGravity(gravity);
	}

	/**
	 * Returns the horizontal and vertical alignment of this TextView.
	 *
	 * @see android.view.Gravity
	 * @attr ref android.R.styleable#TextView_gravity
	 */
	public final int getTextGravity() {
		return getView().getGravity();
	}

	/**
	 * @return the flags on the Paint being used to display the text.
	 * @see Paint#getFlags
	 */
	public final int getPaintFlags() {
		return getView().getPaintFlags();
	}

	/**
	 * Sets flags on the Paint being used to display the text and reflows the
	 * text if they are different from the old flags.
	 * 
	 * @see Paint#setFlags
	 */
	public final void setPaintFlags(final int flags) {
		getView().setPaintFlags(flags);
	}

	/**
	 * Sets whether the text should be allowed to be wider than the View is. If
	 * false, it will be wrapped to the width of the View.
	 *
	 * @attr ref android.R.styleable#TextView_scrollHorizontally
	 */
	public final void setHorizontallyScrolling(final boolean whether) {
		getView().setHorizontallyScrolling(whether);
	}

	/**
	 * Convenience method: Append the specified text to the TextView's display
	 * buffer, upgrading it to BufferType.EDITABLE if it was not already
	 * editable.
	 */
	public final void append(final CharSequence text) {
		getView().append(text);
	}

	/**
	 * Convenience method: Append the specified text slice to the TextView's
	 * display buffer, upgrading it to BufferType.EDITABLE if it was not already
	 * editable.
	 */
	public final void append(final CharSequence text, final int start,
			final int end) {
		getView().append(text, start, end);
	}

	/**
	 * Sets the Factory used to create new Editables.
	 */
	public final void setEditableFactory(final Editable.Factory factory) {
		getView().setEditableFactory(factory);
	}

	/**
	 * Sets the Factory used to create new Spannables.
	 */
	public final void setSpannableFactory(final Spannable.Factory factory) {
		getView().setSpannableFactory(factory);
	}

	/**
	 * Directly change the content type integer of the text view, without
	 * modifying any other state.
	 * 
	 * @see #setInputType(int)
	 * @see android.text.InputType
	 * @attr ref android.R.styleable#TextView_inputType
	 */
	public final void setRawInputType(final int type) {
		getView().setRawInputType(type);
	}

	/**
	 * Set a special listener to be called when an action is performed on the
	 * text view. This will be called when the enter key is pressed, or when an
	 * action supplied to the IME is selected by the user. Setting this means
	 * that the normal hard key event will not insert a newline into the text
	 * view, even if it is multi-line; holding down the ALT modifier will,
	 * however, allow the user to insert a newline character.
	 */
	public final void setOnEditorActionListener(final OnEditorActionListener l) {
		getView().setOnEditorActionListener(l);
	}

	/**
	 * Set the extra input data of the text, which is the
	 * {@link EditorInfo#extras TextBoxAttribute.extras} Bundle that will be
	 * filled in when creating an input connection. The given integer is the
	 * resource ID of an XML resource holding an
	 * {@link android.R.styleable#InputExtras &lt;input-extras&gt;} XML tree.
	 *
	 * @see #getInputExtras(boolean)
	 * @see EditorInfo#extras
	 * @attr ref android.R.styleable#TextView_editorExtras
	 */
	public final void setInputExtras(final int xmlResId)
			throws XmlPullParserException, IOException {
		getView().setInputExtras(xmlResId);
	}

	/**
	 * Retrieve the input extras currently associated with the text view, which
	 * can be viewed as well as modified.
	 *
	 * @param create
	 *            If true, the extras will be created if they don't already
	 *            exist. Otherwise, null will be returned if none have been
	 *            created.
	 * @see #setInputExtras(int)
	 * @see EditorInfo#extras
	 * @attr ref android.R.styleable#TextView_editorExtras
	 */
	public final Bundle getInputExtras(final boolean create) {
		return getView().getInputExtras(create);
	}

	/**
	 * Return the number of lines of text, or 0 if the internal Layout has not
	 * been built.
	 */
	public final int getLineCount() {
		return getView().getLineCount();
	}

	/**
	 * Return the baseline for the specified line (0...getLineCount() - 1) If
	 * bounds is not null, return the top, left, right, bottom extents of the
	 * specified line in it. If the internal Layout has not been built, return 0
	 * and set bounds to (0, 0, 0, 0)
	 * 
	 * @param line
	 *            which line to examine (0..getLineCount() - 1)
	 * @param bounds
	 *            Optional. If not null, it returns the extent of the line
	 * @return the Y-coordinate of the baseline
	 */
	public final int getLineBounds(final int line, final Rect bounds) {
		return getView().getLineBounds(line, bounds);
	}

	/**
	 * If this TextView contains editable content, extract a portion of it based
	 * on the information in <var>request</var> in to <var>outText</var>.
	 * 
	 * @return Returns true if the text was successfully extracted, else false.
	 */
	public final boolean extractText(final ExtractedTextRequest request,
			final ExtractedText outText) {
		return getView().extractText(request, outText);
	}

	/**
	 * Apply to this text view the given extracted text, as previously returned
	 * by {@link #extractText(ExtractedTextRequest, ExtractedText)}.
	 */
	public final void setExtractedText(final ExtractedText text) {
		getView().setExtractedText(text);
	}

	public final void beginBatchEdit() {
		getView().beginBatchEdit();
	}

	public final void endBatchEdit() {
		getView().endBatchEdit();
	}

	/**
	 * Move the point, specified by the offset, into the view if it is needed.
	 * This has to be called after layout. Returns true if anything changed.
	 */
	public final boolean bringPointIntoView(final int offset) {
		return getView().bringPointIntoView(offset);
	}

	/**
	 * Move the cursor, if needed, so that it is at an offset that is visible to
	 * the user. This will not move the cursor if it represents more than one
	 * character (a selection range). This will only work if the TextView
	 * contains spannable text; otherwise it will do nothing.
	 *
	 * @return True if the cursor was actually moved, false otherwise.
	 */
	public final boolean moveCursorToVisibleOffset() {
		return getView().moveCursorToVisibleOffset();
	}

	/**
	 * Convenience for {@link Selection#getSelectionStart}.
	 */
	public final int getSelectionStart() {
		return getView().getSelectionStart();
	}

	/**
	 * Convenience for {@link Selection#getSelectionEnd}.
	 */
	public final int getSelectionEnd() {
		return getView().getSelectionEnd();
	}

	/**
	 * Return true iff there is a selection inside this text view.
	 */
	public final boolean hasSelection() {
		return getView().hasSelection();
	}

	/**
	 * Adds a TextWatcher to the list of those whose methods are called whenever
	 * this TextView's text changes.
	 * <p>
	 * In 1.0, the {@link TextWatcher#afterTextChanged} method was erroneously
	 * not called after {@link #setText} calls. Now, doing {@link #setText} if
	 * there are any text changed listeners forces the buffer type to Editable
	 * if it would not otherwise be and does call this method.
	 */
	public final void addTextChangedListener(final TextWatcher watcher) {
		getView().addTextChangedListener(watcher);
	}

	/**
	 * Removes the specified TextWatcher from the list of those whose methods
	 * are called whenever this TextView's text changes.
	 */
	public final void removeTextChangedListener(final TextWatcher watcher) {
		getView().removeTextChangedListener(watcher);
	}

	/**
	 * Use {@link BaseInputConnection#removeComposingSpans
	 * BaseInputConnection.removeComposingSpans()} to remove any IME composing
	 * state from this text view.
	 */
	public final void clearComposingText() {
		getView().clearComposingText();
	}

	/**
	 * Returns true, only while processing a touch gesture, if the initial touch
	 * down event caused focus to move to the text view and as a result its
	 * selection changed. Only valid while processing the touch gesture of
	 * interest, in an editable text view.
	 */
	public final boolean didTouchFocusSelect() {
		return getView().didTouchFocusSelect();
	}

	public final void setScroller(final Scroller s) {
		getView().setScroller(s);
	}

	/**
	 * Returns whether this text view is a current input method target. The
	 * default implementation just checks with {@link InputMethodManager}.
	 */
	public final boolean isInputMethodTarget() {
		return getView().isInputMethodTarget();
	}

	/**
	 * Return whether or not suggestions are enabled on this TextView. The
	 * suggestions are generated by the IME or by the spell checker as the user
	 * types. This is done by adding {@link SuggestionSpan}s to the text.
	 *
	 * When suggestions are enabled (default), this list of suggestions will be
	 * displayed when the user asks for them on these parts of the text. This
	 * value depends on the inputType of this TextView.
	 *
	 * The class of the input type must be {@link InputType#TYPE_CLASS_TEXT}.
	 *
	 * In addition, the type variation must be one of
	 * {@link InputType#TYPE_TEXT_VARIATION_NORMAL},
	 * {@link InputType#TYPE_TEXT_VARIATION_EMAIL_SUBJECT},
	 * {@link InputType#TYPE_TEXT_VARIATION_LONG_MESSAGE},
	 * {@link InputType#TYPE_TEXT_VARIATION_SHORT_MESSAGE} or
	 * {@link InputType#TYPE_TEXT_VARIATION_WEB_EDIT_TEXT}.
	 *
	 * And finally, the {@link InputType#TYPE_TEXT_FLAG_NO_SUGGESTIONS} flag
	 * must <i>not</i> be set.
	 *
	 * @return true if the suggestions popup window is enabled, based on the
	 *         inputType.
	 */
	public final boolean isSuggestionsEnabled() {
		return getView().isSuggestionsEnabled();
	}

	/**
	 * If provided, this ActionMode.Callback will be used to create the
	 * ActionMode when text selection is initiated in this View.
	 *
	 * The standard implementation populates the menu with a subset of Select
	 * All, Cut, Copy and Paste actions, depending on what this View supports.
	 *
	 * A custom implementation can add new entries in the default menu in its
	 * {@link android.view.ActionMode.Callback#onPrepareActionMode(ActionMode, Menu)}
	 * method. The default actions can also be removed from the menu using
	 * {@link Menu#removeItem(int)} and passing {@link android.R.id#selectAll},
	 * {@link android.R.id#cut}, {@link android.R.id#copy} or
	 * {@link android.R.id#paste} ids as parameters.
	 *
	 * Returning false from
	 * {@link android.view.ActionMode.Callback#onCreateActionMode(ActionMode, Menu)}
	 * will prevent the action mode from being started.
	 *
	 * Action click events should be handled by the custom implementation of
	 * {@link android.view.ActionMode.Callback#onActionItemClicked(ActionMode, MenuItem)}
	 * .
	 *
	 * Note that text selection mode is not started when a TextView receives
	 * focus and the {@link android.R.attr#selectAllOnFocus} flag has been set.
	 * The content is highlighted in that case, to allow for quick replacement.
	 */
	public final void setCustomSelectionActionModeCallback(
			final ActionMode.Callback actionModeCallback) {
		getView().setCustomSelectionActionModeCallback(actionModeCallback);
	}

	/**
	 * Retrieves the value set in {@link #setCustomSelectionActionModeCallback}.
	 * Default is null.
	 *
	 * @return The current custom selection callback.
	 */
	public final ActionMode.Callback getCustomSelectionActionModeCallback() {
		return getView().getCustomSelectionActionModeCallback();
	}

	/**
	 * Get the character offset closest to the specified absolute position. A
	 * typical use case is to pass the result of {@link MotionEvent#getX()} and
	 * {@link MotionEvent#getY()} to this method.
	 *
	 * @param x
	 *            The horizontal absolute position of a point on screen
	 * @param y
	 *            The vertical absolute position of a point on screen
	 * @return the character offset for the character whose position is closest
	 *         to the specified position. Returns -1 if there is no layout.
	 */
	public final int getOffsetForPosition(final float x, final float y) {
		return getView().getOffsetForPosition(x, y);
	}

	/**
	 * Return the text the TextView is displaying. If setText() was called with
	 * an argument of BufferType.SPANNABLE or BufferType.EDITABLE, you can cast
	 * the return value from this method to Spannable or Editable, respectively.
	 *
	 * Note: The content of the return value should not be modified. If you want
	 * a modifiable one, you should make your own copy first.
	 *
	 * @attr ref android.R.styleable#TextView_text
	 */
	public final Editable getText() {
		return getView().getText();
	}

	/**
	 * Convenience for {@link Selection#setSelection(Spannable, int, int)}.
	 */
	public final void setSelection(final int start, final int stop) {
		Selection.setSelection(getText(), start, stop);
	}

	/**
	 * Convenience for {@link Selection#setSelection(Spannable, int)}.
	 */
	public final void setSelection(final int index) {
		Selection.setSelection(getText(), index);
	}

	/**
	 * Convenience for {@link Selection#selectAll}.
	 */
	public final void selectAll() {
		Selection.selectAll(getText());
	}

	/**
	 * Convenience for {@link Selection#extendSelection}.
	 */
	public final void extendSelection(final int index) {
		Selection.extendSelection(getText(), index);
	}

	// CHECKSTYLE:ON

	@Override
	protected final Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.viewState = getView().onSaveInstanceState();
		savedState.maxNumberOfCharacters = getMaxNumberOfCharacters();
		return savedState;
	}

	@Override
	protected final void onRestoreInstanceState(final Parcelable state) {
		if (state != null && state instanceof SavedState) {
			SavedState savedState = (SavedState) state;
			getView().onRestoreInstanceState(savedState.viewState);
			setMaxNumberOfCharacters(savedState.maxNumberOfCharacters);
			super.onRestoreInstanceState(savedState.getSuperState());
		} else {
			super.onRestoreInstanceState(state);
		}
	}

}