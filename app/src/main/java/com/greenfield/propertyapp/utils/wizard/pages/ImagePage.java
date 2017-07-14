package com.greenfield.propertyapp.utils.wizard.pages;

import android.support.v4.app.Fragment;

import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.views.ImageFragment;


/**
 * Created by root on 7/4/17.
 */

public class ImagePage extends TextPage {

    public ImagePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ImageFragment.create(getKey());
    }

    public ImagePage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}