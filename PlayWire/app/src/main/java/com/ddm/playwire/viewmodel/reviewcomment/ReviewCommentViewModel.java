package com.ddm.playwire.viewmodel.reviewcomment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewCommentRepository;

import java.util.List;

public class ReviewCommentViewModel extends ViewModel {

    private ReviewCommentRepository reviewCommentRepository;
    private MutableLiveData<List<ReviewComment>> reviewCommentsLiveData  = new MutableLiveData<>();;

    public ReviewCommentViewModel(ReviewCommentRepository reviewCommentRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
    }

    public MutableLiveData<List<ReviewComment>> getAllReviewsCommentLive(Review review){
        loadAllReviewComments(review.getReviewId());
        return reviewCommentsLiveData;
    }

    public void insertReviewComment(Review review, User user, String comment) {
        ReviewComment newReviewComment = new ReviewComment(review, user, comment);
        reviewCommentRepository.insertReviewComment(newReviewComment);
        loadAllReviewComments(newReviewComment.getReview().getReviewId());
    }

    private void loadAllReviewComments(int reviewId) {
        List<ReviewComment> reviewsComments = reviewCommentRepository.listAllReviewCommentsByReviewId(reviewId);
        reviewCommentsLiveData.setValue(reviewsComments);
    }

}
