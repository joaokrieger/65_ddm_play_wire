package com.ddm.playwire.repository;

import com.ddm.playwire.model.Review;

import java.util.List;

public interface ReviewRepository {

    void insertReview(Review review);
    void deleteReview(Review review);
    List<Review> listAllReviews();
    List<String[]> listReviewRank();
    List<String[]> listFavouriteGamesByUser(int userId);
    List<String[]> listUnfavouriteGamesByUser(int userId);
    int getCountReviewByUser(int userId);
    Review loadReviewById(int reviewId);
}
