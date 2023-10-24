package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class ReviewDetailFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewDao reviewDao;
    private EditText etGameTitlePreview, etReviewDescriptionPreview, etFeedbackPreview;
    private TextView tvReviewIdentifier;
    private Button btnRemove;
    private Button btnReviewComments;

    public ReviewDetailFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    public static ReviewDetailFragment newInstance() {
        ReviewDetailFragment fragment = new ReviewDetailFragment(reviewId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MenuActivity activity = (MenuActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);
        reviewDao = new ReviewDao(getContext());
        Review review = reviewDao.loadByReviewId(reviewId);

        btnRemove = rootView.findViewById(R.id.btnRemove);

        if(review.getUser().getUserId() != activity.getSessionUser().getUserId()){
            btnRemove.setVisibility(View.INVISIBLE);
        }
        else{
            btnRemove.setOnClickListener(view -> {
                ReviewDao reviewDao = new ReviewDao(getContext());
                reviewDao.delete(review);
                activity.replaceFragment(new FeedFragment());
            });
        }

        etGameTitlePreview = rootView.findViewById(R.id.etGameTitlePreview);
        etReviewDescriptionPreview = rootView.findViewById(R.id.etReviewDescriptionPreview);
        etFeedbackPreview = rootView.findViewById(R.id.etFeedbackPreview);
        tvReviewIdentifier = rootView.findViewById(R.id.tvReviewIdentifier);

        if(review != null){
            tvReviewIdentifier.setText("Análise N°" + review.getReviewId() + " - " + review.getUser().getUsername());
            etGameTitlePreview.setText(review.getGameTitle());
            etReviewDescriptionPreview.setText(review.getReviewDescription());
            etFeedbackPreview.setText(review.getFeedback());

            btnReviewComments = rootView.findViewById(R.id.btnReviewComments);
            btnReviewComments.setOnClickListener(view -> {
                activity.replaceFragment(new ReviewCommentFragment(reviewId));
            });
        }

        return rootView;
    }
}