package com.ddm.playwire.repository;

import com.ddm.playwire.model.ReviewComment;

import java.util.List;

public interface ReviewCommentRepository {
    List<ReviewComment> listAllReviewCommentsByReviewId(int reviewId);
    void insertReviewComment(ReviewComment reviewComment);
}
