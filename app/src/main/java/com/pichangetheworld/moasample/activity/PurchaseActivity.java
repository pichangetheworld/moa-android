package com.pichangetheworld.moasample.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        // Set price of item
        TextView itemPriceView = (TextView) findViewById(R.id.item_price);
        String itemPrice = String.format(getResources().getString(R.string.sample_price), 123, 45);
        itemPriceView.setText(itemPrice);

        // When buy button is clicked, forward to third party
        findViewById(R.id.buy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PurchaseActivity.this,
                        "Thank you for purchasing!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
