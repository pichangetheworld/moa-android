package com.pichangetheworld.moasample.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.pichangetheworld.moasample.R;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class DescriptionActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        int drawable = getIntent().getIntExtra("drawable", R.drawable.index1);
        ((ImageView) findViewById(R.id.image)).setImageResource(drawable);
    }
}
