package com.pichangetheworld.moasample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.adapter.ImageAdapter;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class MagazineActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        GridView gridView = (GridView) findViewById(R.id.archived_articles);
        gridView.setAdapter(new ImageAdapter(this));

    }
}
