package com.cq.videoproject.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *  数据库的实例化以及增删改查操作
 *
 * @author Administrator
 */

public class DBUtil {

    private static SQLiteDatabase db;

    /**
     *实例化数据库
     *
     * @param context  调用的context
     * @return 数据库对象
     */
    private static SQLiteDatabase getDatabase(Context context) {

        if (db == null) {
            DBHelper dbHelper = new DBHelper(context, "video_list.db");
            db = dbHelper.getReadableDatabase();
        }
        return db;
    }


    /**
     * 添加数据
     *
     * @param context 调用的context对象
     * @param tbName 数据表的名字
     * @param contentValues 添加的数据
     * @return 添加的结果
     */
    public static boolean insert(Context context, String tbName, ContentValues contentValues) {
        SQLiteDatabase db = getDatabase(context);
        return db.insert(tbName, null, contentValues) > 0;
    }

    /**
     * 删除数据
     *
     * @param context 调用的context对象
     * @param tbName 数据表的名字
     * @param where 删除的字段约束条件
     * @param whereArgs  条件对应的字段
     * @return 删除的结果
     */
    public static boolean delete(Context context, String tbName, String where, String[] whereArgs) {
        SQLiteDatabase db = getDatabase(context);
        return db.delete(tbName, where, whereArgs) > 0;
    }

    /**
     * 修改值
     *
     * @param context 调用的context对象
     * @param tbName 数据表的名字
     * @param contentValues 修改的内容
     * @param where 条件
     * @param whereArgs 条件约束的对象
     * @return 修改的结果
     */
    public static boolean update(Context context, String tbName, ContentValues contentValues, String where, String[] whereArgs) {
        SQLiteDatabase db = getDatabase(context);
        return db.update(tbName, contentValues, where, whereArgs) > 0;
    }

    /**
     * 获取数据
     *
     * @param context 调用的context对象
     * @param tbName 数据表的名字
     * @param select 条件
     * @param selectArgs 条件约束的字段
     * @return 获取的结果
     */
    public static Cursor query(Context context, String tbName, String select, String[] selectArgs) {
        SQLiteDatabase db = getDatabase(context);
        Cursor cursor = db.query(true, tbName, null, select, selectArgs, null, null, null, null);
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }
}
