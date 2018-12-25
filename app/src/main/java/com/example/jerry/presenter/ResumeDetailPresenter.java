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
import com.example.jerry.view.ResumeDetailActivity;

/**
 * Created by jerry on 2018/3/27.
 */

public class ResumeDetailPresenter extends BasePresenter<ResumeDetailActivity> {

    public static final String TAG = "YouInternship";


    public ResumeDetailPresenter(Context context, ResumeDetailActivity View) {
        super(context, View);
    }

    // 获取简历详细信息
    public void getResumeDetail(final int s_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<ContentValues>() {

            Cursor mCursor;
            @Override
            protected ContentValues doInBackground() {

                try {

                    Log.d(TAG, "doInBackground: s_id " + s_id);
                    mCursor = database.query(DatabaseHelper.TABLE_RESUME, null,
                            "s_id = ?", new String[]{String.valueOf(s_id)},
                            null, null, null);

                    if(mCursor.moveToNext()){
                        Log.d(TAG, "doInBackground: cursor 不为空");
                    }else{
                        Log.d(TAG, "doInBackground: cursor 为空");
                    }

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


                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 获取简历信息失败 " + e.getMessage());
                    return null;
                }

                return contentValues;
            }

            @Override
            protected void onPostExecute(ContentValues result) {
                if (mCursor != null){
                    mCursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }


    //发送不合适通知
    public void sendNoFitInfo(final int p_id, final int s_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<Boolean>() {

            Cursor mCursor;
            @Override
            protected Boolean doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_ETP_INFO_STU, null,
                            "p_id = ? and s_id = ?", new String[]{String.valueOf(p_id), String.valueOf(s_id)},
                            null, null, null);

                    if (mCursor.moveToNext()){
                        return false;
                    }

                    contentValues.put("e_id",Constant.ENTERPRISE_ID);
                    contentValues.put("p_id",p_id);
                    contentValues.put("s_id",s_id);
                    contentValues.put("date", DateUtil.getDate());
                    contentValues.put("is_pass",0);

                    database.insert(DatabaseHelper.TABLE_ETP_INFO_STU, null, contentValues);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 通知失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mCursor != null){
                    mCursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x011, result);
            }
        }.execute();
    }

    //发送合适通知
    public void sendAccessInfo(final int p_id, final int s_id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<Boolean>() {

            Cursor mCursor;
            @Override
            protected Boolean doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_ETP_INFO_STU, null,
                            "p_id = ? and s_id = ?", new String[]{String.valueOf(p_id), String.valueOf(s_id)},
                            null, null, null);

                    if (mCursor.moveToNext()){
                        return false;
                    }

                    contentValues.put("e_id",Constant.ENTERPRISE_ID);
                    contentValues.put("p_id",p_id);
                    contentValues.put("s_id",s_id);
                    contentValues.put("date", DateUtil.getDate());
                    contentValues.put("is_pass",1);

                    database.insert(DatabaseHelper.TABLE_ETP_INFO_STU, null, contentValues);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 通知失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mCursor != null){
                    mCursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x012, result);
            }
        }.execute();

    }


}
