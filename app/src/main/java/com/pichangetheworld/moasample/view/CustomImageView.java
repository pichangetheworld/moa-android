package com.pichangetheworld.moasample.view;

import android.content.Context;
import android.widget.ImageView;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class CustomImageView extends ImageView {
    int drawable;

    public CustomImageView(Context context) {
        super(context);
    }

    public void setDrawable(int d) {
        drawable = d;
        setImageResource(d);
    }

    public int getDrawableResource() { return drawable; }
}
