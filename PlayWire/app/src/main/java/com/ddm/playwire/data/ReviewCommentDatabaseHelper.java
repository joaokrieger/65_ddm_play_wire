package com.ddm.playwire.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReviewCommentDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String TABLE_NAME = "review_comment";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_REVIEW = "id_review";
    private static final String COLUMN_USER = "id_user";
    private static final String COLUMN_COMMENT = "comment";

    public ReviewCommentDatabaseHelper(@Nullable Context context) {
        super(context, SQLiteManager.DATABASE_NAME, null, SQLiteManager.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_REVIEW + " INTEGER, "
                + COLUMN_USER + " INTEGER, "
                + COLUMN_COMMENT + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_REVIEW + ") REFERENCES review(_id), "
                + "FOREIGN KEY (" + COLUMN_USER + ") REFERENCES user(_id))";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
