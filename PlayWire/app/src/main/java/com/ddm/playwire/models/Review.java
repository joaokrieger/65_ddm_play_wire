package com.ddm.playwire.models;

public class Review {

    private int reviewId;
    private String gameTitle;
    private String reviewDescription;
    private String feedback;
    private User user;

    public Review(int reviewId, String gameTitle, String reviewDescription, String feedback, User user) {
        this.reviewId = reviewId;
        this.gameTitle = gameTitle;
        this.reviewDescription = reviewDescription;
        this.feedback = feedback;
        this.user = user;
    }

    public Review(String gameTitle, String reviewDescription, String feedback, User user) {
        this.gameTitle = gameTitle;
        this.reviewDescription = reviewDescription;
        this.feedback = feedback;
        this.user = user;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "gameTitle='" + gameTitle + '\'' +
                ", reviewDescription='" + reviewDescription + '\'' +
                ", feedback='" + feedback + '\'' +
                ", user=" + user +
                '}';
    }
}
