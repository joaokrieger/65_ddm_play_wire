package com.ddm.playwire.ui.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.data.dao.ReviewDao;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.ui.adapter.ReviewFeedAdapter;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FeedFragment extends Fragment {

    private View rootView;
    private ReviewViewModel reviewViewModel;
    private ReviewFeedAdapter reviewFeedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        reviewViewModel.getAllReviewLive().observe(getActivity(), reviews -> updateListView(reviews));

        initComponents();

        return rootView;
    }

    private void updateListView(List<Review> reviews) {
        if(reviewFeedAdapter == null) {
            setReviewFeedAdapter(reviews);
        }
        else {
            reviewFeedAdapter.getReviews().clear();
            reviewFeedAdapter.getReviews().addAll(reviews);
            reviewFeedAdapter.notifyDataSetChanged();
        }
    }

    private void initComponents() {
        FloatingActionButton fabAddReview = rootView.findViewById(R.id.fabAddReview);
        fabAddReview.setOnClickListener(view -> navigateToReviewForm());
    }

    private void setReviewFeedAdapter(List<Review> reviews){
        reviewFeedAdapter = new ReviewFeedAdapter(getContext(), reviews);
        ListView lvFeed = rootView.findViewById(R.id.lvFeed);
        lvFeed.setAdapter(reviewFeedAdapter);
        lvFeed.setOnItemClickListener((adapterView, view, index, l) -> navigateToReviewDetail(reviews, index));
    }

    private void navigateToReviewDetail(List<Review> reviews, int index){
        int reviewId = reviews.get(index).getReviewId();
        ((MenuActivity) getActivity()).replaceFragment(new ReviewDetailFragment(reviewId));
    }

    private void navigateToReviewForm(){
        ((MenuActivity) getActivity()).replaceFragment(new ReviewFormFragment());
    }
}