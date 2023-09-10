package com.ddm.playwire.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddm.playwire.R;
import com.ddm.playwire.activities.MenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FeedFragment extends Fragment {

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        FloatingActionButton fabAddReview = rootView.findViewById(R.id.fabAddReview);

        fabAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity activity = (MenuActivity) getActivity();
                activity.replaceFragment(new ReviewFormFragment());
            }
        });

        return rootView;
    }
}