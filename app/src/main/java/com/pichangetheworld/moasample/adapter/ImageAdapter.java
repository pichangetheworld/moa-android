package com.pichangetheworld.moasample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.view.CustomImageView;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class ImageAdapter extends BaseAdapter {
    private final int[] DRAWABLES = {
            R.drawable.index1,
            R.drawable.index2,
            R.drawable.index3,
            R.drawable.index4,
            R.drawable.index5,
            R.drawable.index6,
            R.drawable.index7,
            R.drawable.index8,
            R.drawable.index9,
            R.drawable.index10,
            R.drawable.index11,
            R.drawable.index12
    };

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomImageView imageView;
        if (convertView == null) {
            imageView = new CustomImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    GridView.AUTO_FIT,
                    450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (CustomImageView) convertView;
        }

        imageView.setDrawable(DRAWABLES[position]);
        return imageView;
    }
}
