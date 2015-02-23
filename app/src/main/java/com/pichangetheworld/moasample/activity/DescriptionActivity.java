package com.pichangetheworld.moasample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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

        // Setting the date on the article
        TextView date = (TextView) findViewById(R.id.article_date);
        int year = getIntent().getIntExtra("year", 2015);
        int month = getIntent().getIntExtra("month", 2) - 1; // Zero indexing
        int day = getIntent().getIntExtra("day", 15);
        String[] months = getResources().getStringArray(R.array.month_array);
        String strFormat = getResources().getString(R.string.formatted_date);
        String strMsg = String.format(strFormat, months[month], day, year);
        date.setText(strMsg);

        // Set the image for the article
        int drawable = getIntent().getIntExtra("drawable", R.drawable.index1);
        ((ImageView) findViewById(R.id.image)).setImageResource(drawable);

        // Show the list of related products. Clicking the product should let you buy it
        LinearLayout relatedProducts = (LinearLayout) findViewById(R.id.related_products);
        for (int i = 0; i < relatedProducts.getChildCount(); ++i) {
            relatedProducts.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DescriptionActivity.this, PurchaseActivity.class);
                    // add item info
                    startActivity(intent);
                }
            });
        }

        TextView fromMagazine = (TextView) findViewById(R.id.from_magazine);
        String magazineName = getIntent().getStringExtra("magazine_name");
        if (magazineName == null) {
            magazineName = "Magazine Name";
        }
        String strFromMagazine = String.format(getResources().getString(R.string.from_magazine_name), magazineName);
        fromMagazine.setText(strFromMagazine);
        fromMagazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, MagazineActivity.class);
                startActivity(intent);
            }
        });
    }
}
