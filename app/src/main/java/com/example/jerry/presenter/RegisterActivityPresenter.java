package com.example.jerry.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.constants.Constant;
import com.example.jerry.model.DatabaseHelper;
import com.example.jerry.model.DbCommand;
import com.example.jerry.view.RegisterActivity;

/**
 * Created by jerry on 2018/3/16.
 */

public class RegisterActivityPresenter extends BasePresenter<RegisterActivity> {

    public static final String TAG = "YouInternship";

    public RegisterActivityPresenter(Context context, RegisterActivity View) {
        super(context, View);
    }

    public void check(final boolean isStudent, final String phone_number, final String password) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;
            ContentValues mContentValues = new ContentValues();

            @Override
            protected Boolean doInBackground() {
                try {
                    // 学生登录
                    if (isStudent) {
                        mContentValues.put("phone_number", phone_number);
                        mContentValues.put("password", password);
                        database.insertWithOnConflict(DatabaseHelper.TABLE_STUDENT, null,
                                mContentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

                        cursor = database.query(DatabaseHelper.TABLE_STUDENT, new String[]{"id"}, "phone_number = ?",
                                new String[]{phone_number}, null, null, null);

                        // 这里要先将游标移动一位，游标初始的索引是-1，会报错。
                        cursor.moveToNext();
                        Constant.STUDENT_ID = cursor.getInt(0);
                        //Constant.ENTERPRISE_ID = -1;

                    }
                    // 企业登录
                    else {
                        mContentValues.put("phone_number", phone_number);
                        mContentValues.put("password", password);

                        database.insertWithOnConflict(DatabaseHelper.TABLE_ENTERPRISE, null,
                                mContentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

                        cursor = database.query(DatabaseHelper.TABLE_ENTERPRISE, new String[]{"id"}, "phone_number = ?",
                                new String[]{phone_number}, null, null, null);

                        cursor.moveToNext();
                        Constant.ENTERPRISE_ID = cursor.getInt(0);
                        //Constant.STUDENT_ID = -1;

                    }
                    Log.d(TAG, "doInBackground: 插入成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 插入失败" + e.getMessage());

                    return false;
                }

                return true;

            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (cursor != null) {
                    cursor.close();
                }
                // database.close();
                mView.setData(0x010, result);
            }
        }.execute();

    }
}
