package com.ddm.playwire.ui.review;

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
import com.ddm.playwire.data.dao.ReviewDao;
import com.ddm.playwire.data.dao.UserDao;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.ui.reviewcomment.ReviewCommentFragment;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;
import com.ddm.playwire.viewmodel.user.UserViewModel;
import com.ddm.playwire.viewmodel.user.UserViewModelFactory;

public class ReviewDetailFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewViewModel reviewViewModel;
    private UserViewModel userViewModel;
    private User user;

    public ReviewDetailFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);

        UserRepository userRepository = new UserDao(getActivity().getApplicationContext());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        userViewModel.getCurrentUserLiveData().observe(getActivity(), user -> this.user = user);
        reviewViewModel.getReview(reviewId).observe(getActivity(), review -> updateUi(review));

        initComponents();

        return rootView;
    }

    private void updateUi(Review review) {

        EditText etGameTitlePreview = rootView.findViewById(R.id.etGameTitlePreview);
        EditText etReviewDescriptionPreview = rootView.findViewById(R.id.etReviewDescriptionPreview);
        EditText etFeedbackPreview = rootView.findViewById(R.id.etFeedbackPreview);
        TextView tvReviewIdentifier = rootView.findViewById(R.id.tvReviewIdentifier);

        tvReviewIdentifier.setText("Análise N°" + review.getReviewId() + " - " + review.getUser().getUsername());
        etGameTitlePreview.setText(review.getGameTitle());
        etReviewDescriptionPreview.setText(review.getReviewDescription());
        etFeedbackPreview.setText(review.getFeedback());

        setUserPermission(review);
    }

    private void initComponents() {
        Button btnReviewComments = rootView.findViewById(R.id.btnReviewComments);
        btnReviewComments.setOnClickListener(view -> navigateToReviewComment());
    }

    private void setUserPermission(Review review) {
        Button btnRemove = rootView.findViewById(R.id.btnRemove);
        if(review.getUser().getUserId() != user.getUserId())
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