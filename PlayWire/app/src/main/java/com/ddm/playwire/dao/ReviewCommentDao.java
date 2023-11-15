package com.ddm.playwire.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewCommentRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewCommentDao implements ReviewCommentRepository {

    private final SQLiteManager sqlLiteManager;

    private static final String TABLE_NAME = "review_comment";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_REVIEW = "id_review";
    private static final String COLUMN_USER = "id_user";
    private static final String COLUMN_COMMENT = "comment";

    public ReviewCommentDao(@Nullable Context context) {
        this.sqlLiteManager = new SQLiteManager(context);
    }

    @Override
    public List<ReviewComment> listAllReviewCommentsByReviewId(int reviewId){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_REVIEW + " = " + reviewId + " ORDER BY 1 DESC";
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();

        List<ReviewComment> reviewComments = new ArrayList<>();

        if(sqLiteDatabase != null) {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while (cursor.moveToNext()) {

                int id = Integer.parseInt(cursor.getString(0));
                ReviewDao reviewDao = new ReviewDao(sqlLiteManager.getContext());
                Review review = reviewDao.loadReviewById(Integer.parseInt(cursor.getString(1)));

                UserDao userDao = new UserDao(sqlLiteManager.getContext());
                User user = userDao.loadUserById(Integer.parseInt(cursor.getString(2)));

                String comment = cursor.getString(3);

                ReviewComment reviewComment = new ReviewComment(id, review, user, comment);
                reviewComments.add(reviewComment);
            }
        }

        return reviewComments;
    }

    @Override
    public void insertReviewComment(ReviewComment reviewComment){
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER, reviewComment.getUser().getUserId());
        contentValues.put(COLUMN_REVIEW, reviewComment.getReview().getReviewId());
        contentValues.put(COLUMN_COMMENT, reviewComment.getComment());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(result);
    }
}
