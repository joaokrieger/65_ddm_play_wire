package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.activity.MenuActivity;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.viewmodel.ReviewViewModel;

public class ReviewDetailFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewViewModel reviewViewModel;

    private EditText etGameTitlePreview, etReviewDescriptionPreview, etFeedbackPreview;
    private TextView tvReviewIdentifier;
    private Button btnRemove;
    private Button btnReviewComments;

    public ReviewDetailFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        btnRemove = rootView.findViewById(R.id.btnRemove);
        btnReviewComments = rootView.findViewById(R.id.btnReviewComments);
        etGameTitlePreview = rootView.findViewById(R.id.etGameTitlePreview);
        etReviewDescriptionPreview = rootView.findViewById(R.id.etReviewDescriptionPreview);
        etFeedbackPreview = rootView.findViewById(R.id.etFeedbackPreview);
        tvReviewIdentifier = rootView.findViewById(R.id.tvReviewIdentifier);

        loadReviewData();

        btnReviewComments.setOnClickListener(view -> navigateToReviewComment());

        return rootView;
    }

    private void loadReviewData() {

        Review review = reviewViewModel.loadReview(reviewId);
        tvReviewIdentifier.setText("Análise N°" + review.getReviewId() + " - " + review.getUser().getUsername());
        etGameTitlePreview.setText(review.getGameTitle());
        etReviewDescriptionPreview.setText(review.getReviewDescription());
        etFeedbackPreview.setText(review.getFeedback());

        setUserPermission(review);
    }

    private void setUserPermission(Review review) {
        if(review.getUser().getUserId() != ((MenuActivity) getActivity()).getSessionUser().getUserId())
            btnRemove.setVisibility(View.INVISIBLE);
        else
            btnRemove.setOnClickListener(view -> deleteReview(review));
    }

    private void deleteReview(Review review) {
        reviewViewModel.deleteReview(review);
        navigateToFeed();
    }

    private void navigateToFeed() {
        ((MenuActivity) getActivity()).replaceFragment(new FeedFragment());
    }

    private void navigateToReviewComment() {
        ((MenuActivity) getActivity()).replaceFragment(new ReviewCommentFragment(reviewId));
    }
}