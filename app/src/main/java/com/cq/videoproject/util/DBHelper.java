package com.cq.videoproject.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator
 * 数据库的 helper
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int VERSION =1;

    private static final String CREAT_TABLE_LIST="create table if not exists video_list ("
            +"id INTEGER primary key autoincrement, "
            +"uri VARCHAR,"
            +"name VARCHAR,"
            +"path VARCHAR)";
    private static final String CREAT_TABLE_OWN_LIST ="create table if not exists video_list_own ("
            +"id INTEGER primary key autoincrement, "
            +"uri VARCHAR,"
            +"name VARCHAR,"
            +"path VARCHAR)";


    DBHelper(Context context, String name){
        super(context,name,null,VERSION);
        this.context =context ;
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context =context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE_LIST);
        db.execSQL(CREAT_TABLE_OWN_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
