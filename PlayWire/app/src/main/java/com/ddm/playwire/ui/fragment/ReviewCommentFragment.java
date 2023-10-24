package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.ui.activity.MenuActivity;
import com.ddm.playwire.ui.adapter.ReviewCommentAdapter;
import com.ddm.playwire.ui.adapter.ReviewRankAdapter;

import java.util.List;

public class ReviewCommentFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewDao reviewDao;
    private ReviewCommentDao reviewCommentDao;
    private ListView lvReviewComments;
    private EditText etReviewComment;
    private TextView tvReviewCommentTitle;
    private Button btnComment;
    private ReviewCommentAdapter reviewCommentAdapter;

    public ReviewCommentFragment(int reviewId) {
        this.reviewId = reviewId;
    }

    public static ReviewCommentFragment newInstance() {
        ReviewCommentFragment fragment = new ReviewCommentFragment(reviewId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_comment, container, false);
        MenuActivity activity = (MenuActivity) getActivity();

        reviewCommentDao = new ReviewCommentDao(getContext());

        reviewDao = new ReviewDao(getContext());
        Review review = reviewDao.loadByReviewId(reviewId);

        tvReviewCommentTitle = rootView.findViewById(R.id.tvReviewCommentTitle);
        tvReviewCommentTitle.setText("Comentários Análise N° " + review.getReviewId() + " - " + review.getUser().getUsername());

        etReviewComment = rootView.findViewById(R.id.etReviewComment);

        this.displayData(review.getReviewId());

        btnComment = rootView.findViewById(R.id.btnComment);

        btnComment.setOnClickListener(view -> {

            if(etReviewComment.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "O campo Comentário é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
            }
            else{
                ReviewComment reviewComment = new ReviewComment(review, activity.getSessionUser(), etReviewComment.getText().toString());
                reviewCommentDao.insert(reviewComment);
                reviewCommentAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void displayData(int reviewId) {

        lvReviewComments = rootView.findViewById(R.id.lvReviewComments);
        List<ReviewComment> reviewsComments = reviewCommentDao.listAllByReviewId(reviewId);

        reviewCommentAdapter = new ReviewCommentAdapter(getContext(), reviewsComments);
        lvReviewComments.setAdapter(reviewCommentAdapter);
    }
}