package com.gionee.amisystem.yourpage.statistics.bi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.StatisticConstant;


/**
 * Created by lilt on 15-9-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";
    public static final String DB_NAME = "statistic.db";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.d(TAG, "on create db");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + StatisticConstant.TABLE_NAME + "(" +
                StatisticConstant.Column._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StatisticConstant.Column.SOURCE_FROM + " INTEGER," +
                StatisticConstant.Column.STATISTIC_INFO + " TEXT," +
                StatisticConstant.Column.URL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        try {
            SQLiteDatabase database = super.getWritableDatabase();
            return database;
        } catch (Exception e) {
            LogUtils.e(TAG, "getWritableDatabase error " + e);
        }
        return null;
    }
}
