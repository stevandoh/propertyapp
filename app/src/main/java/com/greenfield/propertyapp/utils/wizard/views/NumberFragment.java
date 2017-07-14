package com.greenfield.propertyapp.utils.wizard.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;

/**
 * Created by root on 7/4/17.
 */

public class NumberFragment   extends TextFragment {
    public static NumberFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        NumberFragment f = new NumberFragment();
        f.setArguments(args);
        return f;
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void setInputType() {
        mEditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

}