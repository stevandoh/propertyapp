package com.greenfield.propertyapp.utils.wizard.pages;

import android.support.v4.app.Fragment;

import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by root on 7/2/17.
 */

public class EditTextPage extends Page {
    protected EditTextPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return null;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }
}
