package com.pichangetheworld.moasample.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.pichangetheworld.moasample.R;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class PurchaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        TextView itemPriceView = (TextView) findViewById(R.id.item_price);
        String itemPrice = String.format(getResources().getString(R.string.sample_price), 123, 45);
        itemPriceView.setText(itemPrice);
    }
}
