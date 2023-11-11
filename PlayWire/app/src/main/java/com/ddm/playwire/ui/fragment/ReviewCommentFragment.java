package com.ddm.playwire.ui.fragment;

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
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.model.User;
import com.ddm.playwire.ui.activity.MenuActivity;
import com.ddm.playwire.ui.adapter.ReviewCommentAdapter;
import com.ddm.playwire.viewmodel.ReviewCommentViewModel;
import com.ddm.playwire.viewmodel.ReviewViewModel;

import java.util.List;

public class ReviewCommentFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewViewModel reviewViewModel;
    private ReviewCommentViewModel reviewCommentViewModel;

    private ListView lvReviewComments;
    private EditText etReviewComment;
    private TextView tvReviewCommentTitle;
    private Button btnComment;
    private ReviewCommentAdapter reviewCommentAdapter;

    public ReviewCommentFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewCommentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewCommentViewModel.class);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_comment, container, false);
        tvReviewCommentTitle = rootView.findViewById(R.id.tvReviewCommentTitle);
        etReviewComment = rootView.findViewById(R.id.etReviewComment);
        lvReviewComments = rootView.findViewById(R.id.lvReviewComments);
        btnComment = rootView.findViewById(R.id.btnComment);

        Review review = reviewViewModel.loadReview(reviewId);
        tvReviewCommentTitle.setText("Comentários Análise N° " + review.getReviewId() + " - " + review.getUser().getUsername());

        this.listReviewComments(review.getReviewId());

        btnComment.setOnClickListener(view -> insertReviewComment(review, ((MenuActivity) getActivity()).getSessionUser()));

        return rootView;
    }

    private void insertReviewComment(Review review, User user) {
        if(validateFormFields()){
            reviewCommentViewModel.insertReviewComment(new ReviewComment(review, user, etReviewComment.getText().toString()));
            listReviewComments(review.getReviewId());
            etReviewComment.setText("");
        }
    }

    private boolean validateFormFields() {
        if(etReviewComment.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo de Comentário!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void listReviewComments(int reviewId) {
        reviewCommentViewModel.getCommentsReviewLive(reviewId).observe(requireActivity(), reviewsComments -> setReviewCommentAdapter(reviewsComments));
    }

    private void setReviewCommentAdapter(List<ReviewComment> reviewsComments) {
        reviewCommentAdapter = new ReviewCommentAdapter(getContext(), reviewsComments);
        lvReviewComments.setAdapter(reviewCommentAdapter);
        reviewCommentAdapter.notifyDataSetChanged();
    }
}