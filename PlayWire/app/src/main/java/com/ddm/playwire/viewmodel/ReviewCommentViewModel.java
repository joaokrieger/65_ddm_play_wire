package com.ddm.playwire.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ddm.playwire.dao.ReviewCommentDao;

public class ReviewCommentViewModel extends AndroidViewModel {

    private ReviewCommentDao reviewCommentDao;

    public ReviewCommentViewModel(@NonNull Application application) {
        super(application);
        reviewCommentDao = new ReviewCommentDao(application);
    }
}
