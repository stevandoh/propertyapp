package com.greenfield.propertyapp.utils.wizard.pages;

import android.support.v4.app.Fragment;

import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.views.GeoFragment;

/**
 * Created by root on 7/4/17.
 */

public class GeoPage extends TextPage {

    public GeoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return GeoFragment.create(getKey());
    }

    public GeoPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}
