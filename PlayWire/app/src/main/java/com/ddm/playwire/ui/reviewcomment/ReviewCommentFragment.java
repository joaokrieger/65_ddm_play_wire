package com.ddm.playwire.ui.reviewcomment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewCommentDao;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewCommentRepository;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.ui.adapter.ReviewCommentAdapter;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;
import com.ddm.playwire.viewmodel.reviewcomment.ReviewCommentViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.reviewcomment.ReviewCommentViewModelFactory;
import com.ddm.playwire.viewmodel.user.UserViewModel;
import com.ddm.playwire.viewmodel.user.UserViewModelFactory;

import java.util.List;

public class ReviewCommentFragment extends Fragment {

    private static int reviewId;
    private View rootView;

    private ReviewViewModel reviewViewModel;
    private ReviewCommentViewModel reviewCommentViewModel;
    private UserViewModel userViewModel;
    private ReviewCommentAdapter reviewCommentAdapter;
    private User user;
    private Review review;

    public ReviewCommentFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);

        ReviewCommentRepository reviewCommentRepository = new ReviewCommentDao(getActivity().getApplicationContext());
        reviewCommentViewModel = new ViewModelProvider(this, new ReviewCommentViewModelFactory(reviewCommentRepository)).get(ReviewCommentViewModel.class);

        UserRepository userRepository = new UserDao(getActivity().getApplicationContext());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_comment, container, false);

        userViewModel.getCurrentUserLiveData().observe(getActivity(), user -> this.user = user);
        reviewViewModel.getReview(reviewId).observe(getActivity(), review -> {
            this.review = review;
            updateUi();
        });
        reviewCommentViewModel.getAllReviewsCommentLive(review).observe(getActivity(), reviewComments -> updateListView(reviewComments));
        
        initComponents();

        return rootView;
    }

    private void updateListView(List<ReviewComment> reviewComments) {
        if(reviewCommentAdapter == null) {
            setReviewCommentAdapter(reviewComments);
        }
        else {
            reviewCommentAdapter.getReviewComments().clear();
            reviewCommentAdapter.getReviewComments().addAll(reviewComments);
            reviewCommentAdapter.notifyDataSetChanged();
        }
    }

    private void initComponents() {
        Button btnComment = rootView.findViewById(R.id.btnComment);
        btnComment.setOnClickListener(view -> insertReviewComment());
    }

    private void insertReviewComment() {
        EditText etReviewComment = rootView.findViewById(R.id.etReviewComment);
        if(etReviewComment.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo de Comentário!", Toast.LENGTH_SHORT).show();
        }
        else{
            reviewCommentViewModel.insertReviewComment(review, user, etReviewComment.getText().toString());
            etReviewComment.setText("");
        }
    }

    private void setReviewCommentAdapter(List<ReviewComment> reviewsComments) {
        reviewCommentAdapter = new ReviewCommentAdapter(getContext(), reviewsComments);
        ListView lvReviewComments = rootView.findViewById(R.id.lvReviewComments);
        lvReviewComments.setAdapter(reviewCommentAdapter);
    }

    private void updateUi() {
        TextView tvReviewCommentTitle = rootView.findViewById(R.id.tvReviewCommentTitle);
        tvReviewCommentTitle.setText("Comentários Análise N° " + review.getReviewId() + " - " + review.getUser().getUsername());
    }
}