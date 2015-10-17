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
package de.mrapp.android.validation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * An implementation of the class {@link AbstractValidateableView}, which is needed for test
 * purposes.
 *
 * @author Michael Rapp
 */
public class AbstractValidateableViewImplementation
        extends AbstractValidateableView<EditText, CharSequence> {

    /**
     * Creates a new implementation of the class {@link AbstractValidateableView}.
     *
     * @param context
     *         The context, which should be used, as an instance of the class {@link Context}
     */
    public AbstractValidateableViewImplementation(final Context context) {
        super(context);
    }

    /**
     * Creates a new implementation of the class {@link AbstractValidateableView}.
     *
     * @param context
     *         The context, which should be used, as an instance of the class {@link Context}
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet}
     */
    public AbstractValidateableViewImplementation(final Context context,
                                                  final AttributeSet attributeSet) {
        super(context);
    }

    /**
     * Creates a new implementation of the class {@link AbstractValidateableView}.
     *
     * @param context
     *         The context, which should be used, as an instance of the class {@link Context}
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet}
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     */
    public AbstractValidateableViewImplementation(final Context context,
                                                  final AttributeSet attributeSet,
                                                  final int defaultStyle) {
        super(context);
    }

    /**
     * Creates a new implementation of the class {@link AbstractValidateableView}.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet}
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     * @param defaultStyleResource
     *         A resource identifier of a style resource that supplies default values for the
     *         preference, used only if the default style is 0 or can not be found in the theme. Can
     *         be 0 to not look for defaults
     */
    public AbstractValidateableViewImplementation(final Context context,
                                                  final AttributeSet attributeSet,
                                                  final int defaultStyle,
                                                  final int defaultStyleResource) {
        super(context);
    }

    @Override
    protected final EditText createView() {
        return new EditText(getContext());
    }

    @Override
    protected final ViewGroup createParentView() {
        return null;
    }

    @Override
    protected final CharSequence getValue() {
        return getView().getText();
    }

}