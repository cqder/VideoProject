package com.cq.videoproject.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cqueWalt on 2017\10\19 0019.
 * 数据库的什么什么的
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String CREAT_TABLE_LIST="create table if not exists video_list ("
            +"uri text primary key autoincrement, "
            +"path text, "
            +"name integer)";
    public static final String CREAT_TABLE_OWN_LIST ="create table if not exists video_list_own ("
            +"uri text primary key autoincrement, "
            +"path text, "
            +"name integer)";

    public DBHelper(Context context, String name){
        super(context,name,null,1);
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
        Log.i("test"," 创建表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
