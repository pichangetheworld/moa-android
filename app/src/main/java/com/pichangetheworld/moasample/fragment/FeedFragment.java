package com.pichangetheworld.moasample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class FeedFragment extends TabFragment implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        // Initialise the TabHost
        this.initialiseTabHost(v);
        if (savedInstanceState != null) {
            // Set the tab as per the saved state
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("feed"));
        }
        // Initialise ViewPager
        this.initialiseViewPager(v);

        return v;
    }

    private TabHost mTabHost;
    private ViewPager mViewPager;

    // Getter
    public String getCurrentTabTag() {
        return mTabHost.getCurrentTabTag();
    }

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
     * Initialise ViewPager
     */
    private static final int NUM_FRAGMENTS = 3;
    private void initialiseViewPager(View v) {
        List<Fragment> fragments = new ArrayList<>(NUM_FRAGMENTS);
        for (int i = 0; i < NUM_FRAGMENTS; ++i) {
            fragments.add(Fragment.instantiate(getActivity(), StylesSubFeedFragment.class.getName()));
        }

        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
        this.mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(View v) {
        Log.d("FeedFragment", "Initialising Tab Host");
        mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(this.mTabHost.newTabSpec("New").setIndicator("New")
                .setContent(new TabFactory(getActivity())));
        mTabHost.addTab(this.mTabHost.newTabSpec("Top").setIndicator("Top")
                .setContent(new TabFactory(getActivity())));
        mTabHost.addTab(this.mTabHost.newTabSpec("Trending").setIndicator("Trending")
                .setContent(new TabFactory(getActivity())));

        mTabHost.setOnTabChangedListener(this);
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
