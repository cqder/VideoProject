package com.cq.videoproject.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created
 *
 * @author Administrator
 */

public class DBUtil {

    private static SQLiteDatabase db;

    public static SQLiteDatabase getDatabase(Context context) {

        if (db == null) {
            DBHelper dbHelper = new DBHelper(context, "video_list.db");
            db = dbHelper.getReadableDatabase();
        }
        return db;
    }


    /**
     * 添加数据
     *
     * @param context
     * @param tb_name
     * @param values
     * @return
     */
    public static boolean insert(Context context, String tb_name, ContentValues values) {
        SQLiteDatabase db = getDatabase(context);
        return db.insert(tb_name, null, values) > 0 ;
    }

    /**
     * 删除数据
     *
     * @param context
     * @param tb_name
     * @param where
     * @param whereArgs
     * @return
     */
    public static boolean delete(Context context, String tb_name, String where, String[] whereArgs) {
        SQLiteDatabase db = getDatabase(context);
        return db.delete(tb_name, where, whereArgs) > 0;
    }

    /**
     * 修改值
     *
     * @param context
     * @param tb_name
     * @param values
     * @param where
     * @param whereArgs
     * @return
     */
    public static boolean update(Context context, String tb_name, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = getDatabase(context);
        return db.update(tb_name, values, where, whereArgs) > 0 ;
    }

    /**
     * 获取数据
     *
     * @param context
     * @param tb_name
     * @param select
     * @param selectArgs
     * @return
     */
    public static Cursor query(Context context, String tb_name, String select, String[] selectArgs) {
        SQLiteDatabase db = getDatabase(context);
        Cursor cursor = db.query(true,tb_name,null, select, selectArgs, null, null, null, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }
}
