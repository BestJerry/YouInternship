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
import com.example.jerry.view.CreateResumeActivity;

/**
 * Created by jerry on 2018/3/24.
 */

public class CreateResumePresenter extends BasePresenter<CreateResumeActivity> {

    public static final String TAG = "YouInternship";


    public CreateResumePresenter(Context context, CreateResumeActivity View) {
        super(context, View);
    }

    public void insertOrUpdateResume(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {

            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {

                    mCursor = database.query(DatabaseHelper.TABLE_RESUME, null, "s_id = ?",
                            new String[]{String.valueOf(Constant.STUDENT_ID)}, null, null, null);

                    if (mCursor.moveToNext()) {
                        Log.d(TAG, "doInBackground: 更新简历 ");
                        database.updateWithOnConflict(DatabaseHelper.TABLE_RESUME, contentValues,
                                "s_id = ?",
                                new String[]{String.valueOf(Constant.STUDENT_ID)}, SQLiteDatabase.CONFLICT_ROLLBACK);
                    } else {

                        Log.d(TAG, "doInBackground: 新建简历");
                        database.insertWithOnConflict(DatabaseHelper.TABLE_RESUME,
                                null, contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

                    }

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 完善简历失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mCursor != null) {
                    mCursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();


    }

    public void getSelfResume() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<ContentValues>() {

            Cursor mCursor;

            @Override
            protected ContentValues doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_RESUME, null,
                            "s_id = ?", new String[]{String.valueOf(Constant.STUDENT_ID)},
                            null, null, null);

                    if (mCursor.moveToNext()) {
                        contentValues.put("name", mCursor.getString(2));
                        contentValues.put("sex", mCursor.getString(3));
                        contentValues.put("age", mCursor.getString(4));
                        contentValues.put("phone", mCursor.getString(5));
                        contentValues.put("email", mCursor.getString(6));
                        contentValues.put("school", mCursor.getString(7));
                        contentValues.put("major", mCursor.getString(8));
                        contentValues.put("intention", mCursor.getString(9));
                        contentValues.put("project_exp", mCursor.getString(10));
                        contentValues.put("internship_exp", mCursor.getString(11));
                        contentValues.put("volunteer_exp", mCursor.getString(12));
                        contentValues.put("honorary", mCursor.getString(13));
                        contentValues.put("hobby", mCursor.getString(14));
                        contentValues.put("self_evaluation", mCursor.getString(15));
                    }


                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 获取简历信息失败 " + e.getMessage());

                }

                return contentValues;
            }

            @Override
            protected void onPostExecute(ContentValues result) {
                if (mCursor != null) {
                    mCursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x011, result);
            }
        }.execute();

    }
}
