package com.ddm.playwire.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.view.activity.MenuActivity;
import com.ddm.playwire.view.adapter.ReviewFeedAdapter;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.viewmodel.ReviewViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FeedFragment extends Fragment {

    private FloatingActionButton fabAddReview;
    private View rootView;
    private ListView lvFeed;
    private ReviewViewModel reviewViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        fabAddReview = rootView.findViewById(R.id.fabAddReview);
        fabAddReview.setOnClickListener(view -> navigateToReviewForm());
        lvFeed = rootView.findViewById(R.id.lvFeed);

        this.listReview();

        return rootView;
    }

    private void listReview(){
        reviewViewModel.getAllReviewLive().observe(requireActivity(), reviews -> setReviewFeedAdapter(reviews));
    }

    private void setReviewFeedAdapter(List<Review> reviews){
        ReviewFeedAdapter reviewFeedAdapter = new ReviewFeedAdapter(getContext(), reviews);
        lvFeed.setAdapter(reviewFeedAdapter);
        lvFeed.setOnItemClickListener((adapterView, view, i, l) -> navigateToReviewDetail(reviews, i));
    }

    private void navigateToReviewDetail(List<Review> reviews, int index){
        int reviewId = reviews.get(index).getReviewId();
        ((MenuActivity) getActivity()).replaceFragment(new ReviewDetailFragment(reviewId));
    }

    private void navigateToReviewForm(){
        ((MenuActivity) getActivity()).replaceFragment(new ReviewFormFragment());
    }
}