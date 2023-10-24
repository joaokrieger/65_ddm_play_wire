package com.ddm.playwire.model;

public class ReviewComment {

    private int reviewCommentId;
    private Review review;
    private User user;
    private String comment;

    public ReviewComment(Review review, User user, String comment) {
        this.review = review;
        this.user = user;
        this.comment = comment;
    }

    public ReviewComment(int id, Review review, User user, String comment) {
        this.reviewCommentId = id;
        this.review = review;
        this.user = user;
        this.comment = comment;
    }

    public int getReviewCommentId() {
        return reviewCommentId;
    }

    public void setReviewCommentId(int reviewCommentId) {
        this.reviewCommentId = reviewCommentId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
