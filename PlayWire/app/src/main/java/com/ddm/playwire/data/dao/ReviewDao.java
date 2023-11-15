package com.ddm.playwire.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements ReviewRepository {

    private final SQLiteManager sqlLiteManager;

    private static final String TABLE_NAME = "review";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GAME_TITLE = "game_title";
    private static final String COLUMN_REVIEW_DESCRIPTION = "review_description";
    private static final String COLUMN_FEEDBACK = "feedback";
    private static final String COLUMN_USER_ID = "user_id";

    public ReviewDao(@Nullable Context context) {
        this.sqlLiteManager = new SQLiteManager(context);
    }

    @Override
    public void insertReview(Review review){
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TITLE, review.getGameTitle());
        contentValues.put(COLUMN_REVIEW_DESCRIPTION, review.getReviewDescription());
        contentValues.put(COLUMN_FEEDBACK, review.getFeedback());
        contentValues.put(COLUMN_USER_ID, review.getUser().getUserId());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(result);
    }

    @Override
    public void deleteReview(Review review) {
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        String query = "_id = ?";
        String[] whereArgs = { String.valueOf(review.getReviewId()) };

        long result = sqLiteDatabase.delete(TABLE_NAME, query, whereArgs);
        SQLiteManager.checkExecSql(result);
    }

    @Override
    public List<Review> listAllReviews(){
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY 1 DESC";
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        List<Review> reviews = new ArrayList<>();

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                int id = Integer.parseInt(cursor.getString(0));
                String gameTitle = cursor.getString(1);
                String reviewDescription = cursor.getString(2);
                String feedback = cursor.getString(3);

                UserDao userDao = new UserDao(sqlLiteManager.getContext());
                User user = userDao.loadUserById(Integer.parseInt(cursor.getString(4)));

                Review review = new Review(id, gameTitle, reviewDescription, feedback, user);
                reviews.add(review);
            }
        }
        return reviews;
    }

    @Override
    public int getCountReviewByUser(int userId){
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId;
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        int countReview = 0;

        if(sqLiteDatabase != null) {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while (cursor.moveToNext()) {
                countReview = Integer.parseInt(cursor.getString(0));
            }
        }

        return countReview;
    }

    @Override
    public Review loadReviewById(int reviewId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + reviewId;
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        Review review = null;

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                int id = Integer.parseInt(cursor.getString(0));
                String gameTitle = cursor.getString(1);
                String reviewDescription = cursor.getString(2);
                String feedback = cursor.getString(3);

                UserDao userDao = new UserDao(sqlLiteManager.getContext());
                User user = userDao.loadUserById(Integer.parseInt(cursor.getString(4)));

                review = new Review(id, gameTitle, reviewDescription, feedback, user);
            }
        }
        return review;
    }

    @Override
    public List<String[]> listReviewRank(){

        String query = "SELECT " + COLUMN_GAME_TITLE + ", COUNT(*) AS review_count, AVG(CASE " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Excelente' THEN 5 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Bom' THEN 4 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Regular' THEN 3 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Insatisfatório' THEN 2 " +
                "  ELSE 1 END) AS avg_feedback " +
                " FROM " + TABLE_NAME +
                " GROUP BY " + COLUMN_GAME_TITLE +
                " ORDER BY avg_feedback DESC " +
                " LIMIT 5";

        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        List<String[]> reviews = new ArrayList<>();
        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                String gameTitle = cursor.getString(0);
                int reviewCount = cursor.getInt(1);
                int feedbackAvg = cursor.getInt(2);

                String[] reviewData = {gameTitle, String.valueOf(reviewCount), String.valueOf(feedbackAvg)};
                reviews.add(reviewData);
            }
        }

        return reviews;
    }

    @Override
    public List<String[]> listFavouriteGamesByUser(int userId){
        return this.listReviewRankByUser(userId,"ASC");
    }

    @Override
    public List<String[]> listUnfavouriteGamesByUser(int userId){
        return this.listReviewRankByUser(userId,"DESC");
    }

    private List<String[]> listReviewRankByUser(int userId, String order){

        String subquery = "SELECT " + COLUMN_GAME_TITLE + ", COUNT(*) AS review_count, AVG(CASE " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Excelente' THEN 5 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Bom' THEN 4 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Regular' THEN 3 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Insatisfatório' THEN 2 " +
                "  ELSE 1 END) AS avg_feedback " +
                " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_ID + " = " + userId +
                " GROUP BY " + COLUMN_GAME_TITLE;

        String query = "SELECT " + COLUMN_GAME_TITLE + ", review_count, avg_feedback " +
                "FROM (" + subquery + ") AS subquery " +
                "WHERE avg_feedback " + (order.equals("ASC") ? ">= 3 " : "< 3 ") +
                "ORDER BY avg_feedback " + order +
                " LIMIT 5";

        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        List<String[]> reviews = new ArrayList<>();
        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                String gameTitle = cursor.getString(0);
                int reviewCount = cursor.getInt(1);
                int feedbackAvg = cursor.getInt(2);

                String[] reviewData = {gameTitle, String.valueOf(reviewCount), String.valueOf(feedbackAvg)};
                reviews.add(reviewData);
            }
        }
        return reviews;
    }
}
