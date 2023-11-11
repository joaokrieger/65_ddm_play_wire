package com.ddm.playwire.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewDao reviewDao;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewDao = new ReviewDao(application);
    }

    public LiveData<List<Review>> getAllReviewLive() {
        MutableLiveData<List<Review>> allReviewLiveData = new MutableLiveData<>();
        allReviewLiveData.setValue(reviewDao.listAll());
        return allReviewLiveData;
    }

    public LiveData<List<String[]>> getFavouriteGameLive(int userId) {
        MutableLiveData<List<String[]>>  favouriteGames = new MutableLiveData<>();
        favouriteGames.setValue(reviewDao.listFavouriteGamesByUser(userId));
        return favouriteGames;
    }

    public LiveData<List<String[]>> getUnfavouriteGameLive(int userId) {
        MutableLiveData<List<String[]>> unfavouriteGames = new MutableLiveData<>();
        unfavouriteGames.setValue(reviewDao.listUnfavouriteGamesByUser(userId));
        return unfavouriteGames;
    }

    public LiveData<Integer> getCountReviewByUserLive(int userId) {
        MutableLiveData<Integer> userReviewCount = new MutableLiveData<>();
        userReviewCount.setValue(reviewDao.getCountReviewByUser(userId));
        return userReviewCount;
    }

    public LiveData<List<String[]>> getGameRankLive() {
        MutableLiveData<List<String[]>> rankGames = new MutableLiveData<>();
        rankGames.setValue(reviewDao.listRank());
        return rankGames;
    }

    public Review loadReview(int reviewId) {
        return reviewDao.loadReviewById(reviewId);
    }

    public void deleteReview(Review review) {
        reviewDao.delete(review);
    }

    public void insertReview(Review review) {
        reviewDao.insert(review);
    }
}
