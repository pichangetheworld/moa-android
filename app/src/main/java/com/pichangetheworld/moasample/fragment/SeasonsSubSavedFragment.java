package com.pichangetheworld.moasample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pichangetheworld.moasample.R;
import com.pichangetheworld.moasample.activity.DescriptionActivity;
import com.pichangetheworld.moasample.adapter.ImageAdapter;
import com.pichangetheworld.moasample.view.CustomImageView;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 18/02/2015
 */
public class SeasonsSubSavedFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_tab_spring, container, false);

        GridView gridView = (GridView) v.findViewById(R.id.grid_layout);
        gridView.setAdapter(new ImageAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), DescriptionActivity.class);
                i.putExtra("drawable", ((CustomImageView) v).getDrawableResource());
                startActivity(i);
            }
        });

        return v;
    }
}
