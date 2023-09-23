package com.ddm.playwire.data;

import android.content.Context;
import android.widget.Toast;

public class SQLiteManager {

    public static final String DATABASE_NAME = "PlayWire.db";
    public static final int DATABASE_VERSION = 1;

    public static void checkExecSql(Context context, long result){
        if(result == -1){
            Toast.makeText(context, "Erro ao executar SQL!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}