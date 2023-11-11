package com.ddm.playwire.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PlayWire.db";
    public static final int DATABASE_VERSION = 3;

    private static Context context;

    public SQLiteManager(@Nullable Context context) {
        super(context, SQLiteManager.DATABASE_NAME, null, SQLiteManager.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user ( _id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE review ( _id INTEGER PRIMARY KEY AUTOINCREMENT, game_title TEXT, review_description TEXT, feedback TEXT, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user(_id))");
        sqLiteDatabase.execSQL("CREATE TABLE review_comment ( _id INTEGER PRIMARY KEY AUTOINCREMENT, id_review INTEGER, id_user INTEGER, comment TEXT, FOREIGN KEY (id_user) REFERENCES user(_id), FOREIGN KEY (id_review) REFERENCES review(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS review");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS review_comment");
        onCreate(sqLiteDatabase);
    }

    public Context getContext() {
        return context;
    }

    public static void checkExecSql(long result){
        if(result == -1){
            Toast.makeText(context, "Erro ao executar SQL!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}