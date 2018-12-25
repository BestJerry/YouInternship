package com.example.jerry.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.beans.Position;
import com.example.jerry.model.DatabaseHelper;
import com.example.jerry.model.DbCommand;
import com.example.jerry.view.PositionListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/3/20.
 */

public class PositionListPresenter extends BasePresenter<PositionListFragment> {

    public static final String TAG = "YouInternship";

    public PositionListPresenter(Context context, PositionListFragment View) {
        super(context, View);
    }



    public void fetchPositionData() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();


        new DbCommand<List<Position>>() {
            Cursor cursor;
            List<Position> list = new ArrayList<>();

            @Override
            protected List<Position> doInBackground() {
                Log.d(TAG, "doInBackground: 查询");

                try {
                    cursor = database.query(DatabaseHelper.TABLE_POSITION, null,
                            null, null, null, null, null);


                    while (cursor.moveToNext()) {
                        Position position = new Position();
                        position.setId(cursor.getInt(0));
                        position.setE_id(cursor.getInt(1));
                        position.setName(cursor.getString(2));
                        position.setCompany_name(cursor.getString(3));
                        position.setBase(cursor.getString(4));
                        position.setWork_time(cursor.getString(5));
                        position.setSalary(cursor.getString(6));
                        position.setUpdate_date(cursor.getString(7));
                        position.setDescription(cursor.getString(8));
                        position.setRequire(cursor.getString(9));
                        position.setDeadline(cursor.getString(10));
                        position.setIs_out_of_date(cursor.getInt(11));

                        list.add(position);
                    }

                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: " + e.getMessage());

                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Position> result) {
                if (cursor != null) {
                    cursor.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }
}

