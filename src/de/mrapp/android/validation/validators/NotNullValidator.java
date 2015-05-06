package de.mrapp.android.validation.validators;

import android.content.Context;

/**
 * A validator, which allows to ensure, that values are not null.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class NotNullValidator extends AbstractValidator<Object> {

	/**
	 * Creates a new validator, which allows to ensure, that values are not
	 * null.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 */
	public NotNullValidator(final CharSequence errorMessage) {
		super(errorMessage);
	}

	/**
	 * Creates a new validator, which allows to ensure, that values are not
	 * null.
	 * 
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 */
	public NotNullValidator(final Context context, final int resourceId) {
		super(context, resourceId);
	}

	@Override
	public final boolean validate(final Object value) {
		return value != null;
	}

}