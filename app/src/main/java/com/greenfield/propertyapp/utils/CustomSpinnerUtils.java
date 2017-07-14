package com.greenfield.propertyapp.utils;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by SOVAVY on 5/7/2017.
 */

public class CustomSpinnerUtils extends android.support.v7.widget.AppCompatSpinner {
    public CustomSpinnerUtils(Context context)
    { super(context); }

    public CustomSpinnerUtils(Context context, AttributeSet attrs)
    { super(context, attrs); }

    public CustomSpinnerUtils(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }

    @Override
    public void
    setSelection(int position, boolean animate)
    {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void
    setSelection(int position)
    {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }
}
