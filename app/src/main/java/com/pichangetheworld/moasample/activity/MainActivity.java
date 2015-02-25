package com.pichangetheworld.moasample.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.fragment.FeedFragment;
import com.pichangetheworld.moasample.fragment.SavedFragment;
import com.pichangetheworld.moasample.fragment.TabFragment;

/**
 * MAO
 * Author: pchan
 * Date: 18/02/2015
 */
public class MainActivity extends ActionBarActivity {
    final TabFragment FRAGMENTS[] = {
            new FeedFragment(),
            new SavedFragment()
    };

    final int[] TAB_VIEWS = {
            R.id.feed_tab,
            R.id.saved_tab
    };

    View[] tabs = new View[4];
    int curSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < TAB_VIEWS.length; ++i) {
            tabs[i] = findViewById(TAB_VIEWS[i]);
            tabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTab(v);
                }
            });
        }

        curSelected = savedInstanceState != null ? savedInstanceState.getInt("tab") : 0;
        selectTab(tabs[curSelected]);
    }

    private void selectTab(View v) {
        int index;
        for (index = 0; index < tabs.length; ++index) {
            if (tabs[index] == v) break;
        }

        tabs[curSelected].setEnabled(true);
        curSelected = index;
        tabs[curSelected].setEnabled(false);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, FRAGMENTS[curSelected]);
        ft.commit();
    }

    // Populate Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        // save the current tab selected
        outState.putInt("tab", curSelected);
        if (curSelected == 0) {
            outState.putString("feed", FRAGMENTS[curSelected].getCurrentTabTag());
        } else if (curSelected == 1) {
            outState.putString("saved", FRAGMENTS[curSelected].getCurrentTabTag());
        }
        super.onSaveInstanceState(outState);
    }

}
