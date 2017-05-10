package com.gionee.amisystem.yourpage.statistics.bi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.StatisticConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 11/2/15.
 */
public class StatisticDatabase {

    public static final String TAG = "StatisticDatabase";

    private DatabaseHelper mDatabaseHelper;

    private static final boolean DEBUG = true;

    public StatisticDatabase(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void insert(StatisticBiInfo info) {
        if (DEBUG) {
            LogUtils.d(TAG, "insert staticBiInfo info " + info);
        }
        if (info == null || info.isEmpty()) {
            LogUtils.w(TAG, "insert StatisticBiInfo is null or empty");
            return;
        }
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        if (database == null || database.isReadOnly()) {
            LogUtils.w(TAG, "insert StatisticBiInfo database is null or only read. database = " + database);
            return;
        }
        try {
            ContentValues values = info.toContentValues();
            database.insert(StatisticConstant.TABLE_NAME, null, values);
        } catch (Exception e) {
            LogUtils.e(TAG, "insert StatisticBiInfo error " + e);
        }
    }


    public List<StatisticBiInfo> getAllFailureInfo(int sourceForm) {
        if (DEBUG) {
            LogUtils.d(TAG, "get allFail info ");
        }
        List<StatisticBiInfo> result = new ArrayList<StatisticBiInfo>();
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        if ((database == null) || database.isReadOnly()) {
            LogUtils.w(TAG, "getAllFailureInfo database is null or only read. database = " + database);
            return result;
        }
        Cursor cursor = null;

        try {
            if (sourceForm == StatisticBiInfo.SOURCE_ALL) {
                cursor = database.query(StatisticConstant.TABLE_NAME, null,
                        null, null, null, null, null);
            } else {
                cursor = database.query(StatisticConstant.TABLE_NAME, null,
                        StatisticConstant.Column.SOURCE_FROM + "=" + sourceForm, null, null, null, null);
            }

            if ((null == cursor) || !cursor.moveToFirst()) {
                return result;
            }

            int idIndex = cursor.getColumnIndexOrThrow(StatisticConstant.Column._ID);
            int paramsIndex = cursor.getColumnIndexOrThrow(StatisticConstant.Column.STATISTIC_INFO);
            int urlIndex = cursor.getColumnIndexOrThrow(StatisticConstant.Column.URL);
            do {
                int id = cursor.getInt(idIndex);
                String params = cursor.getString(paramsIndex);
                String url = cursor.getString(urlIndex);

                StatisticBiInfo info = new StatisticBiInfo(url, params);
                info.setId(id);
                info.setSourceFrom(sourceForm);
                result.add(info);

            } while (cursor.moveToNext());

        } catch (Exception e) {
            LogUtils.e(TAG, "getAllFailureInfo e = " + e);
        } finally {
            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }
        }
        return result;

    }

    public void deleteById(long id) {
        if (DEBUG) {
            LogUtils.d(TAG, "delete staticBiInfo id " + id);
        }
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        if ((database == null) || database.isReadOnly()) {
            LogUtils.w(TAG, "delete staticInfo from db,  database is null or only read. database = " + database);
            return;
        }

        try {
            database.delete(StatisticConstant.TABLE_NAME, StatisticConstant.Column._ID + "=" + id, null);
        } catch (Exception e) {
            LogUtils.e(TAG, "remove Statistic info error " + e);
        }

    }

    public void closeDb() {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

}
