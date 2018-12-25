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
import com.example.jerry.utils.DateUtil;
import com.example.jerry.view.DeliverPositionActivity;

/**
 * Created by jerry on 2018/3/26.
 */

public class DeliverPositionPresenter extends BasePresenter<DeliverPositionActivity> {

    public static final String TAG = "YouInternship";


    public DeliverPositionPresenter(Context context, DeliverPositionActivity View) {
        super(context, View);
    }


    // 获取职位信息
    public void fetchPositionData(final int p_id, final int e_id) {

        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<ContentValues>() {
            Cursor cursor;

            @Override
            protected ContentValues doInBackground() {
                Log.d(TAG, "doInBackground: 查询");

                try {
                    cursor = database.query(DatabaseHelper.TABLE_POSITION, null,
                            "id = ?", new String[]{String.valueOf(p_id)}, null, null, null);


                    while (cursor.moveToNext()) {

                        contentValues.put("p_id", cursor.getInt(0));
                        contentValues.put("p_name", cursor.getString(2));
                        contentValues.put("p_base", cursor.getString(4));
                        contentValues.put("p_work_time", cursor.getString(5));
                        contentValues.put("p_salary", cursor.getString(6));
                        contentValues.put("p_description", cursor.getString(8));
                        contentValues.put("p_require", cursor.getString(9));
                        contentValues.put("p_deadline", cursor.getString(10));

                    }

                    cursor = database.query(DatabaseHelper.TABLE_ENTERPRISE, null,
                            "id = ?", new String[]{String.valueOf(e_id)}, null, null, null);


                    while (cursor.moveToNext()) {

                        contentValues.put("e_id", cursor.getString(0));
                        contentValues.put("e_name", cursor.getString(3));
                        contentValues.put("e_homepage", cursor.getString(4));
                        contentValues.put("e_address", cursor.getString(5));
                        contentValues.put("e_introduction", cursor.getString(6));

                    }

                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: " + e.getMessage());

                }
                return contentValues;
            }

            @Override
            protected void onPostExecute(ContentValues result) {
                if (cursor != null) {
                    cursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }

    //收藏
    public void collectPosition(final int p_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_STU_COLLECT_POS, null, "s_id = ? and p_id = ?",
                            new String[]{String.valueOf(Constant.STUDENT_ID), String.valueOf(p_id)},
                            null, null, null);

                    if (mCursor.moveToNext()) {
                        return false;
                    }

                    Log.d(TAG, "doInBackground: 收藏职位简历");

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("s_id", Constant.STUDENT_ID);
                    contentValues.put("p_id", p_id);
                    contentValues.put("collect_date", DateUtil.getDate());

                    database.insert(DatabaseHelper.TABLE_STU_COLLECT_POS, null, contentValues);

                    Log.d(TAG, "doInBackground: 收藏职位成功");

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 收藏职位失败 " + e.getMessage());
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
                mView.setData(0x011, result);
            }
        }.execute();

    }

    //投递
    public void deliverPosition(final int p_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_STU_DELIVER_POS, null,
                            "s_id = ? and p_id = ?",
                            new String[]{String.valueOf(Constant.STUDENT_ID), String.valueOf(p_id)},
                            null, null, null);

                    if (mCursor.moveToNext()) {
                        return false;
                    }

                    Log.d(TAG, "doInBackground: 投递职位");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("s_id", Constant.STUDENT_ID);
                    contentValues.put("p_id", p_id);
                    contentValues.put("deliver_date", DateUtil.getDate());

                    database.insert(DatabaseHelper.TABLE_STU_DELIVER_POS, null, contentValues);

                    Log.d(TAG, "doInBackground: 投递职位成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 投递职位失败 " + e.getMessage());
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
                mView.setData(0x012, result);
            }
        }.execute();

    }

    public void deletePosition(final int p_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {

            @Override
            protected Boolean doInBackground() {

                try {

                    database.delete(DatabaseHelper.TABLE_POSITION,"id = ?",
                            new String[]{String.valueOf(p_id)});

                    Log.d(TAG, "doInBackground: 删除职位成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 删除职位失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x013, result);
            }
        }.execute();


    }
}
