package com.ddm.playwire.viewmodel.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends ViewModel {

    private ReviewRepository reviewRepository;
    private MutableLiveData<Review> currentReviewLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String[]>> favouriteReviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String[]>> unfavouriteReviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String[]>> rankReviewLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> userReviewCount = new MutableLiveData<>();

    public ReviewViewModel(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public LiveData<List<Review>> getAllReviewLive() {
        loadAllReviews();
        return reviewsLiveData;
    }

    public LiveData<List<String[]>> getFavouriteReviewsLive(User user){
        loadFavouriteGameReview(user.getUserId());
        return favouriteReviewsLiveData;
    }

    public LiveData<List<String[]>> getUnfavouriteReviewsLive(User user){
        loadUnfavouriteGameReview(user.getUserId());
        return unfavouriteReviewsLiveData;
    }

    public LiveData<Integer> getCountReviewByUserLive(User user) {
        loadCountReviewByUser(user.getUserId());
        return userReviewCount;
    }

    public LiveData<List<String[]>> getGameRankLive(){
        loadRankReview();
        return rankReviewLiveData;
    }

    public void insertReview(String gameTitle, String reviewDescription, String feedback, User user){
        reviewRepository.insertReview(new Review(gameTitle, reviewDescription, feedback, user));
        loadAllReviews();
        loadFavouriteGameReview(user.getUserId());
        loadUnfavouriteGameReview(user.getUserId());
    }

    public void deleteReview(Review review){
        reviewRepository.deleteReview(review);
        loadAllReviews();
        loadFavouriteGameReview(review.getUser().getUserId());
        loadUnfavouriteGameReview(review.getUser().getUserId());
    }

    private void loadRankReview() {
        List<String[]> rankReview = reviewRepository.listReviewRank();
        rankReviewLiveData.setValue(rankReview);
    }

    private void loadCountReviewByUser(int userId) {
        int userCountReview = reviewRepository.getCountReviewByUser(userId);
        userReviewCount.setValue(userCountReview);
    }

    private void loadAllReviews() {
        List<Review> reviews = reviewRepository.listAllReviews();
        reviewsLiveData.setValue(reviews);
    }

    private void loadFavouriteGameReview(int userId){
        List<String[]> favoriteReviews = reviewRepository.listFavouriteGamesByUser(userId);
        favouriteReviewsLiveData.setValue(favoriteReviews);
    }

    private void loadUnfavouriteGameReview(int userId){
        List<String[]> favoriteReviews = reviewRepository.listUnfavouriteGamesByUser(userId);
        unfavouriteReviewsLiveData.setValue(favoriteReviews);
    }

    public LiveData<Review> getReview(int reviewId) {
        loadReview(reviewId);
        return currentReviewLiveData;
    }

    private void loadReview(int reviewId) {
        Review review = reviewRepository.loadReviewById(reviewId);
        currentReviewLiveData.setValue(review);
    }
}
