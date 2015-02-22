package com.pichangetheworld.moasample.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TabHost;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.adapter.PagerAdapter;
import com.pichangetheworld.moasample.fragment.StylesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * MAO
 * Author: pchan
 * Date: 18/02/2015
 */
public class MainActivity extends ActionBarActivity
        implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private TabHost mTabHost;
    private ViewPager mViewPager;

    /**
     * A simple factory that returns dummy views to the TabHost
     *
     * @author mwho
     */
    class TabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        /**
         * @param context context for the factory
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.activity_main);

        // Initialise the TabHost
        this.initialiseTabHost();
        if (savedInstanceState != null) {
            // Set the tab as per the saved state
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        // Initialise ViewPager
        this.initialiseViewPager();
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
        // save the current tab selected
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private static final int NUM_FRAGMENTS = 3;
    private void initialiseViewPager() {
        List<Fragment> fragments = new ArrayList<>(NUM_FRAGMENTS);
        for (int i = 0; i < NUM_FRAGMENTS; ++i) {
            fragments.add(Fragment.instantiate(this, StylesFragment.class.getName()));
        }

        PagerAdapter mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("New").setIndicator("New"));
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Top").setIndicator("Top"));
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Trending").setIndicator("Trending"));

        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * Add Tab content to the TabHost
     *
     * @param activity the host fragment
     * @param tabHost the tab host
     * @param tabSpec any specs about the tab
     */
    private static void AddTab(MainActivity activity,
                               TabHost tabHost, TabHost.TabSpec tabSpec) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    /**
     * (non-Javadoc)
     *
     * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
     */
    public void onTabChanged(String tag) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {}

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    /* (non-Javadoc)
         * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
         */
    @Override
    public void onPageScrollStateChanged(int state) {}
}
