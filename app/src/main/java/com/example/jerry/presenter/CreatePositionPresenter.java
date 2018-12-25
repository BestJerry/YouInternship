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
import com.example.jerry.view.CreatePositionActivity;

/**
 * Created by jerry on 2018/3/24.
 */

public class CreatePositionPresenter extends BasePresenter<CreatePositionActivity> {

    public static final String TAG = "YouInternship";

    public CreatePositionPresenter(Context context, CreatePositionActivity View) {
        super(context, View);
    }

    public void insertPosition(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();


        new DbCommand<Boolean>() {

            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {
                    mCursor = database.query(DatabaseHelper.TABLE_ENTERPRISE,
                            new String[]{"name"}, "id = ?",
                            new String[]{String.valueOf(Constant.ENTERPRISE_ID)},
                            null, null, null);

                    mCursor.moveToNext();

                    contentValues.put("company_name",mCursor.getString(0));

                    database.insert(DatabaseHelper.TABLE_POSITION, null, contentValues);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 新建职位失败 " + e.getMessage());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mCursor != null) {
                    mCursor = null;
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }
}
