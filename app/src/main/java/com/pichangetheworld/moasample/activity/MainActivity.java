package com.pichangetheworld.moasample.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.fragment.FeedFragment;

/**
 * MAO
 * Author: pchan
 * Date: 18/02/2015
 */
public class MainActivity extends ActionBarActivity {
    final Fragment FRAGMENTS[] = {
            new FeedFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, FRAGMENTS[0]);
        ft.commit();
    }

    // Populate Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        Log.d("MainActivity", "Searching in menu for menuview " +
                menu.findItem(R.id.action_search).getActionView());
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        // if (FeedFragment is showing) {
            // save the current tab selected
            outState.putString("tab",
                    ((FeedFragment) FRAGMENTS[0]).getCurrentTabTag());
        // }
        super.onSaveInstanceState(outState);
    }

}
