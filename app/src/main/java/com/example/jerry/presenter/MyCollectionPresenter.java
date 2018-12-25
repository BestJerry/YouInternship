package com.example.jerry.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.beans.Position;
import com.example.jerry.constants.Constant;
import com.example.jerry.model.DatabaseHelper;
import com.example.jerry.model.DbCommand;
import com.example.jerry.view.MyCollectionActivity;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionPresenter extends BasePresenter<MyCollectionActivity> {
    public static final String TAG = "YouInternship";


    public MyCollectionPresenter(Context context, MyCollectionActivity View) {
        super(context, View);
    }

    public void fetchPositionData() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();


        new DbCommand<List<Position>>() {
            Cursor cursor1;
            Cursor cursor2;
            List<Position> list = new ArrayList<>();

            @Override
            protected List<Position> doInBackground() {
                Log.d(TAG, "doInBackground: 查询");

                try {
                    cursor1 = database.query(DatabaseHelper.TABLE_STU_COLLECT_POS, null,
                            "s_id = ?", new String[]{String.valueOf(Constant.STUDENT_ID)},
                            null, null, null);

                    while (cursor1.moveToNext()) {

                        Log.d(TAG, "doInBackground: 职位id " + cursor1.getInt(1));
                        cursor2 = database.query(DatabaseHelper.TABLE_POSITION, null,
                                "id = ?", new String[]{String.valueOf(cursor1.getInt(1))},
                                null, null, null);


                        while (cursor2.moveToNext()) {
                            Log.d(TAG, "doInBackground: 职位名称 " + cursor2.getString(2));
                            Position position = new Position();
                            position.setId(cursor2.getInt(0));
                            position.setE_id(cursor2.getInt(1));
                            position.setName(cursor2.getString(2));
                            position.setCompany_name(cursor2.getString(3));
                            position.setBase(cursor2.getString(4));
                            position.setWork_time(cursor2.getString(5));
                            position.setSalary(cursor2.getString(6));
                            position.setUpdate_date(cursor2.getString(7));
                            position.setDescription(cursor2.getString(8));
                            position.setRequire(cursor2.getString(9));
                            position.setDeadline(cursor2.getString(10));
                            position.setIs_out_of_date(cursor2.getInt(11));

                            list.add(position);
                        }
                    }


                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: " + e.getMessage());

                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Position> result) {
                if (cursor1 != null) {
                    cursor1.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }

                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }
}
