package com.ddm.playwire.viewmodel.reviewcomment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ddm.playwire.dao.ReviewCommentDao;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;

import java.util.List;

public class ReviewCommentViewModel extends AndroidViewModel {

    private ReviewCommentDao reviewCommentDao;

    public ReviewCommentViewModel(@NonNull Application application) {
        super(application);
        reviewCommentDao = new ReviewCommentDao(application);
    }

    public MutableLiveData<List<ReviewComment>> getCommentsReviewLive(int reviewId) {
        MutableLiveData<List<ReviewComment>> reviewCommentsLiveData = new MutableLiveData<>();
        reviewCommentsLiveData.setValue(reviewCommentDao.listAllByReviewId(reviewId));
        return reviewCommentsLiveData;
    }

    public void insertReviewComment(ReviewComment reviewComment) {
        reviewCommentDao.insert(reviewComment);
    }
}
