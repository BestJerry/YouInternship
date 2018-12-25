package com.example.jerry.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.constants.Constant;
import com.example.jerry.model.DatabaseHelper;
import com.example.jerry.model.DbCommand;
import com.example.jerry.view.LoginActivity;

/**
 * Created by jerry on 2018/3/16.
 */

public class LoginActivityPresenter extends BasePresenter<LoginActivity> {

    public static final String TAG = "YouInternship";

    public LoginActivityPresenter(Context context, LoginActivity View) {
        super(context, View);
    }

    public void check(final boolean isStudent, final String phone_number, final String password) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor1;

            Cursor cursor2;

            @Override
            protected Boolean doInBackground() {
                Log.d(TAG, "doInBackground: 查询");

                if (isStudent) {

                    cursor1 = database.query(DatabaseHelper.TABLE_STUDENT, null,
                            null, null, null, null, null);
                } else {
                    cursor1 = database.query(DatabaseHelper.TABLE_ENTERPRISE, null,
                            null, null, null, null, null);
                }


                while (cursor1.moveToNext()) {
                    Log.d(TAG, "doInBackground: 检查");
                    String ph = cursor1.getString(1);
                    String pa = cursor1.getString(2);

                    Log.d(TAG, "doInBackground: 密码：" + pa);

                    if (ph.equals(phone_number) && pa.equals(password)) {
                        if (isStudent) {
                            //Constant.ENTERPRISE_ID = -1;
                            cursor2 = database.query(DatabaseHelper.TABLE_RESUME, new String[]{"is_detail_complete"},
                                    "s_id = ?", new String[]{String.valueOf(cursor1.getInt(0))},
                                    null, null, null);
                            if (cursor2.moveToNext()){
                                Constant.isDetailComplete = cursor2.getInt(cursor2.getColumnIndex("is_detail_complete"));

                            }else{
                                Constant.isDetailComplete = 0;
                            }
                            Constant.STUDENT_ID = cursor1.getInt(0);


                        } else {
                            //Constant.STUDENT_ID = -1;
                            Constant.ENTERPRISE_ID = cursor1.getInt(0);
                            Constant.isDetailComplete = cursor1.getInt(cursor1.getColumnIndex("is_detail_complete"));
                        }

                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (cursor1 != null) {
                    cursor1.close();
                }
                if (cursor2!=null){
                    cursor2.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }

}
