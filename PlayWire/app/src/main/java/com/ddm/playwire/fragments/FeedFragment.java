package com.ddm.playwire.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.activities.MenuActivity;
import com.ddm.playwire.adapter.ReviewAdapter;
import com.ddm.playwire.data.ReviewDatabaseHelper;
import com.ddm.playwire.models.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FeedFragment extends Fragment {

    private FloatingActionButton fabAddReview;
    private ReviewDatabaseHelper reviewDatabaseHelper;
    private View rootView;
    private ListView feed;

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

        rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        fabAddReview = rootView.findViewById(R.id.fabAddReview);

        fabAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity activity = (MenuActivity) getActivity();
                activity.replaceFragment(new ReviewFormFragment());
            }
        });

        displayData();

        return rootView;
    }

    public void displayData(){
        feed = rootView.findViewById(R.id.lvFeed);
        reviewDatabaseHelper = new ReviewDatabaseHelper(getContext());
        List<Review> reviews = reviewDatabaseHelper.listReview();

        ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), reviews);

        feed.setAdapter(reviewAdapter);

        feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuActivity activity = (MenuActivity) getActivity();
                int reviewId = reviews.get(i).getReviewId();
                activity.replaceFragment(new ReviewDetailFragment(reviewId));
            }
        });
    }
}