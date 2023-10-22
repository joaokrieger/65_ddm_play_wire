package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewCommentDao;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.ui.adapter.ReviewCommentAdapter;
import com.ddm.playwire.ui.adapter.ReviewRankAdapter;

import java.util.List;

public class ReviewCommentFragment extends Fragment {

    private static int reviewId;
    private View rootView;
    private ReviewDao reviewDao;
    private ReviewCommentDao reviewCommentDao;
    private ListView lvReviewComments;

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
        reviewDao = new ReviewDao(getContext());
        Review review = reviewDao.loadByReviewId(reviewId);

        TextView tvReviewCommentTitle = rootView.findViewById(R.id.tvReviewCommentTitle);
        tvReviewCommentTitle.setText("Comentários Análise N° " + review.getReviewId() + " - " + review.getUser().getUsername());

        this.displayData(review.getReviewId());

        return rootView;
    }

    private void displayData(int reviewId) {

        lvReviewComments = rootView.findViewById(R.id.lvReviewComments);
        reviewCommentDao = new ReviewCommentDao(getContext());
        List<ReviewComment> reviewsComments = reviewCommentDao.listAllByReviewId(reviewId);

        ReviewCommentAdapter reviewCommentAdapter = new ReviewCommentAdapter(getContext(), reviewsComments);
        lvReviewComments.setAdapter(reviewCommentAdapter);
    }
}