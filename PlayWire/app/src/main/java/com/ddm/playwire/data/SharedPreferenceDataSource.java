package com.ddm.playwire.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceDataSource {

    private static SharedPreferenceDataSource instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static SharedPreferenceDataSource getInstance(){
        if(instance == null){
            instance = new SharedPreferenceDataSource();
        }
        return instance;
    }

    private SharedPreferenceDataSource(){}

    public void init(Context context){
        sharedPreferences = context.getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSessionUserId(int userId){
        editor.putInt("userId",userId);
        editor.commit();
    }

    public int getSessionUserId(){
        return sharedPreferences.getInt("userId", -1);
    }

    public void unsetSessionUserId(){
        editor.remove("userId");
        editor.apply();
    }
}
