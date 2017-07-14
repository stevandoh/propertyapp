package com.greenfield.propertyapp.utils.wizard.pages;

import android.support.v4.app.Fragment;

import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.views.NumberFragment;

/**
 * Created by root on 7/4/17.
 */

public class NumberPage extends TextPage {

	public NumberPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return NumberFragment.create(getKey());
    }

}