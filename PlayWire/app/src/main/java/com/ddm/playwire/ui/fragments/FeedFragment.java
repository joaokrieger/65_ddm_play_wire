package com.ddm.playwire.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.activities.MenuActivity;
import com.ddm.playwire.adapter.ReviewFeedAdapter;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FeedFragment extends Fragment {

    private FloatingActionButton fabAddReview;
    private ReviewDao reviewDao;
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
        reviewDao = new ReviewDao(getContext());
        List<Review> reviews = reviewDao.listReview();

        ReviewFeedAdapter reviewFeedAdapter = new ReviewFeedAdapter(getContext(), reviews);

        feed.setAdapter(reviewFeedAdapter);

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