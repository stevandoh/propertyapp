package com.greenfield.propertyapp.utils.wizard.pages;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.model.ReviewItem;
import com.greenfield.propertyapp.utils.wizard.views.ContactInfoFragment;

import java.util.ArrayList;

/**
 * Created by root on 7/3/17.
 */

public class ContactInfoPage extends Page {

    public static final String PHONE_DATA_KEY = "phone";
    public static final String NETWORK_DATA_KEY = "network";
    public ContactInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }


    @Override
    public Fragment createFragment() {
        return ContactInfoFragment.create(getKey());
    }


    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Phone", mData.getString(PHONE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Network", mData.getString(NETWORK_DATA_KEY), getKey(), -1));

    }
    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(PHONE_DATA_KEY));
    }
}
