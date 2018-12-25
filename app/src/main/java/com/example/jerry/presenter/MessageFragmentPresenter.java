package com.example.jerry.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.beans.Message;
import com.example.jerry.constants.Constant;
import com.example.jerry.model.DatabaseHelper;
import com.example.jerry.model.DbCommand;
import com.example.jerry.view.MessageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/3/20.
 */

public class MessageFragmentPresenter extends BasePresenter<MessageFragment> {

    public static final String TAG = "YouInternship";


    public MessageFragmentPresenter(Context context, MessageFragment View) {
        super(context, View);
    }

    public void fetchInformationData() {

        Log.d(TAG, "fetchInformationData: " + " 获取通知消息");
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();


        new DbCommand<List<Message>>() {
            Cursor cursor1;
            Cursor cursor2;
            Cursor cursor3;
            List<Message> list = new ArrayList<>();

            @Override
            protected List<Message> doInBackground() {

                Log.d(TAG, "doInBackground: 查询");

                // 学生获取企业通知
                if (Constant.isStudentLogin) {

                    //获取e_id，p_id、通知信息、日期
                    cursor1 = database.query(DatabaseHelper.TABLE_ETP_INFO_STU, null, "s_id = ?",
                            new String[]{String.valueOf(Constant.STUDENT_ID)},
                            null, null, null);

                    while (cursor1.moveToNext()) {
                        Log.d(TAG, "doInBackground: cursor1 ok e_id: " + cursor1.getInt(0)
                        + " p_id " + cursor1.getInt(1) + " s_id " + cursor1.getInt(2) );

                        //获取企业名字
                        cursor2 = database.query(DatabaseHelper.TABLE_ENTERPRISE, new String[]{"name"},
                                "id = ?", new String[]{String.valueOf(cursor1.getInt(0))}
                                , null, null, null);

                        cursor2.moveToNext();

                        //获取职位名字
                        cursor3 = database.query(DatabaseHelper.TABLE_POSITION, new String[]{"name"},
                                "id = ?", new String[]{String.valueOf(cursor1.getInt(1))},
                                null, null, null);

                        while (cursor3.moveToNext()) {
                            Log.d(TAG, "doInBackground: e_name " + cursor2.getString(0));
                            Message message = new Message();
                            message.setDate(cursor1.getString(3));
                            message.setInformation(cursor1.getInt(4));
                            message.setE_name(cursor2.getString(0));
                            message.setP_name(cursor3.getString(0));


                            list.add(message);
                        }
                    }

                }

                // 企业获取学生通知
                else {

                    int i = 1;
                    try {
                        //获取当前企业发布的所有p_id
                        cursor1 = database.query(DatabaseHelper.TABLE_POSITION, null,
                                "e_id = ?", new String[]{String.valueOf(Constant.ENTERPRISE_ID)},
                                null, null, null);


                        while (cursor1.moveToNext()) {
                            Log.d(TAG, "doInBackground: cursor1 ok position id: " + cursor1.getInt(0) + i++);

                            //获取s_id和日期
                            cursor2 = database.query(DatabaseHelper.TABLE_STU_DELIVER_POS, null,
                                    "p_id = ?", new String[]{String.valueOf(cursor1.getInt(0))},
                                    null, null, null);


                            while (cursor2.moveToNext()) {
                                Log.d(TAG, "doInBackground: cursor2 ok student id: " + cursor2.getInt(0));

                                // 获取学生姓名
                                cursor3 = database.query(DatabaseHelper.TABLE_RESUME, null, "s_id = ?",
                                        new String[]{String.valueOf(cursor2.getInt(0))}, null,
                                        null, null);


                                cursor3.moveToNext();

                                Log.d(TAG, "doInBackground: cursor3 ok 学生姓名：" + cursor3.getString(2));
                                Message message = new Message();

                                message.setP_id(cursor1.getInt(cursor1.getColumnIndex("id")));
                                message.setP_name(cursor1.getString(cursor1.getColumnIndex("name")));
                                message.setDate(cursor2.getString(cursor2.getColumnIndex("deliver_date")));
                                message.setS_name(cursor3.getString(cursor3.getColumnIndex("name")));
                                message.setS_id(cursor2.getInt(0));

                                list.add(message);


                            }

                        }

                    } catch (Exception e) {

                        Log.d(TAG, "doInBackground: " + e.getMessage());

                    }
                }

                return list;

            }


            @Override
            protected void onPostExecute(List<Message> result) {
                if (cursor1 != null) {
                    cursor1.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (cursor3 != null) {
                    cursor3.close();
                }
                //database.close();
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x010, result);
            }
        }.execute();

    }
}
