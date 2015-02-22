package com.pichangetheworld.moasample.view;

import android.content.Context;
import android.util.AttributeSet;
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

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawable(int d) {
        drawable = d;
        setImageResource(d);
    }

    public int getDrawableResource() { return drawable; }
}
