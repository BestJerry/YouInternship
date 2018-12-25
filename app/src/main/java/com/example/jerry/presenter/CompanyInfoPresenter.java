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
import com.example.jerry.view.CompanyInfoActivity;

/**
 * Created by jerry on 2018/3/25.
 */

public class CompanyInfoPresenter extends BasePresenter<CompanyInfoActivity> {

    public static final String TAG = "YouInternship";


    public CompanyInfoPresenter(Context context, CompanyInfoActivity View) {
        super(context, View);
    }


    // 獲取公司信息
    public void getCompanyInfo() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        final ContentValues contentValues = new ContentValues();

        new DbCommand<ContentValues>() {

            Cursor mCursor;

            @Override
            protected ContentValues doInBackground() {

                try {

                    mCursor = database.query(DatabaseHelper.TABLE_ENTERPRISE, null,
                            "id = ?", new String[]{String.valueOf(Constant.ENTERPRISE_ID)},
                            null, null, null);

                    if (mCursor.moveToNext()) {

                        contentValues.put("name", mCursor.getString(3));
                        contentValues.put("homepage", mCursor.getString(4));
                        contentValues.put("address", mCursor.getString(5));
                        contentValues.put("introduction", mCursor.getString(6));
                        contentValues.put("icon_address", mCursor.getString(7));
                        contentValues.put("voucher_address", mCursor.getString(8));

                    }


                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: 获取公司信息失败 " + e.getMessage());

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

    public void updateDetail(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {

            Cursor mCursor;
            @Override
            protected Boolean doInBackground() {

                try {

                    database.update(DatabaseHelper.TABLE_ENTERPRISE, contentValues,
                            "id = ?", new String[]{String.valueOf(Constant.ENTERPRISE_ID)});

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 完善企业信息失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();
    }


}
