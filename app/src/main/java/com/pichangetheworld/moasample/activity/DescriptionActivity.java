package com.pichangetheworld.moasample.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        TextView date = (TextView) findViewById(R.id.article_date);
        int year = getIntent().getIntExtra("year", 2015);
        int month = getIntent().getIntExtra("month", 2) - 1; // Zero indexing
        int day = getIntent().getIntExtra("day", 15);
        String[] months = getResources().getStringArray(R.array.month_array);
        String strFormat = getResources().getString(R.string.formatted_date);
        String strMsg = String.format(strFormat, months[month], day, year);
        date.setText(strMsg);

        int drawable = getIntent().getIntExtra("drawable", R.drawable.index1);
        ((ImageView) findViewById(R.id.image)).setImageResource(drawable);

        LinearLayout relatedProducts = (LinearLayout) findViewById(R.id.related_products);

    }
}
