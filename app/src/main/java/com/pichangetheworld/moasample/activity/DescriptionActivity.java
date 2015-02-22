package com.pichangetheworld.moasample.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pichangetheworld.moasample.R;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class DescriptionActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        int drawable = getIntent().getIntExtra("drawable", R.drawable.index1);
        ((ImageView) findViewById(R.id.image)).setImageResource(drawable);

        LinearLayout relatedProducts = (LinearLayout) findViewById(R.id.related_products);

    }
}
