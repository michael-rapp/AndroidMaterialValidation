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