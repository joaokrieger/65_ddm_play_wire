package com.ddm.playwire.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.User;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao{

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

    public void insert(Review review){
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TITLE, review.getGameTitle());
        contentValues.put(COLUMN_REVIEW_DESCRIPTION, review.getReviewDescription());
        contentValues.put(COLUMN_FEEDBACK, review.getFeedback());
        contentValues.put(COLUMN_USER_ID, review.getUser().getUserId());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(result);
    }

    public void delete(Review review) {
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        String query = "_id = ?";
        String[] whereArgs = { String.valueOf(review.getReviewId()) };

        long result = sqLiteDatabase.delete(TABLE_NAME, query, whereArgs);
        SQLiteManager.checkExecSql(result);
    }

    public List<Review> listAll(){
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
                User user = userDao.loadByUserId(Integer.parseInt(cursor.getString(4)));

                Review review = new Review(id, gameTitle, reviewDescription, feedback, user);
                reviews.add(review);
            }
        }

        return reviews;
    }

    public List<String[]> listRank(){

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

    public List<String[]> listRankByUser(int userId, String order){

        String query = "SELECT " + COLUMN_GAME_TITLE + ", COUNT(*) AS review_count, AVG(CASE " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Excelente' THEN 5 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Bom' THEN 4 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Regular' THEN 3 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Insatisfatório' THEN 2 " +
                "  ELSE 1 END) AS avg_feedback " +
                " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_ID + " = " + userId +
                " GROUP BY " + COLUMN_GAME_TITLE +
                " ORDER BY avg_feedback " + order +
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

    public int getCountReviewByUser(int userId){
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
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

    public Review loadByReviewId(int reviewId) {
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
                User user = userDao.loadByUserId(Integer.parseInt(cursor.getString(4)));

                review = new Review(id, gameTitle, reviewDescription, feedback, user);
            }
        }

        return review;
    }


}
