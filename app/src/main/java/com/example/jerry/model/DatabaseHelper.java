package com.example.jerry.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerry on 2018/3/5.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "YouInternship";

    public static final String DB_NAME = "YouInternship.db";

    public static final int DB_VERSION = 1;

    private SQLiteDatabase mDatabase;

    private static DatabaseHelper sDatabaseHelper;

    public static final String TABLE_STUDENT = "StudentInfo";

    public static final String TABLE_ENTERPRISE = "EnterpriseInfo";

    public static final String TABLE_POSITION = "PositionInfo";

    public static final String TABLE_RESUME = "ResumeInfo";

    //public static final String TABLE_STU_CREATE_RES = "Stu_Create_Res";

    public static final String TABLE_STU_DELIVER_POS = "Stu_Deliver_Pos";

    public static final String TABLE_STU_COLLECT_POS = "Stu_Collect_Pos";

    //public static final String TABLE_ETP_POST_POS = "Etp_Post_Pos";

    public static final String TABLE_ETP_INFO_STU = "Etp_Info_Stu";

    // 学生实体
    private static final String STUDENT_INFO_CREATE_TABLE_SQL = "create table "
            + TABLE_STUDENT + " ( "
            + "id integer primary key autoincrement unique not null,"
            + "phone_number varchar(20) not null unique,"
            + "password varchar(20) not null,"
            + "head_pic_address text"
            + ");";

    // 企业实体
    private static final String ENTERPRISE_INFO_CREATE_TABLE_SQL = "create table "
            + TABLE_ENTERPRISE + " ( "
            + "id integer primary key autoincrement unique, " //0
            + "phone_number varchar(20) not null unique, " //1
            + "password varchar(20) not null, " //2
            + "name text, " //3
            + "homepage text, " //4
            + "address text," //5
            + "introduction text, " //6
            + "icon_address text, " //7
            + "voucher_address text, " //8
            + "is_certificate integer default 0 ," //9
            + "is_detail_complete integer default 0" //10
            + ");";

    // 职位实体
    private static final String POSITION_INFO_CREATE_TABLE_SQL = "create table "
            + TABLE_POSITION + " ( "
            + "id integer primary key autoincrement unique not null,"// 0
            + "e_id integer not null," //1
            + "name varchar(30) not null," //2
            + "company_name text, " //3
            + "base varchar(50) not null," //4
            + "work_time text not null," //5
            + "salary varchar(20) not null," //6
            + "update_date text not null," //7
            + "description text," //8
            + "require text," //9
            + "deadline text," //10
            + "is_out_of_date integer default 0," //表示未过期 //11
            + "foreign key (e_id) references " + TABLE_ENTERPRISE + " (id) "
            + ");";

    // 简历实体
    private static final String RESUME_INFO_CREATE_TABLE_SQL = "create table "
            + TABLE_RESUME + " ( "
            + "id integer primary key autoincrement unique not null, " //0
            + "s_id integer, " //1
            + "name text, " //2
            + "sex text, " //3
            + "age text, " //4
            + "phone text," //5
            + "email text," //6
            + "school text," //7
            + "major text," //8
            + "intention text," //9
            + "project_exp text," //10
            + "internship_exp text," //11
            + "volunteer_exp text, " //12
            + "honorary text," //13
            + "hobby text," //14
            + "self_evaluation text," //15
            + "make_time text," //16
            + "is_detail_complete integer default 0," //17
            + "foreign key (s_id) references " + TABLE_STUDENT + "(id)"
            + ");";

    // 学生制作简历
    /*private static final String STU_MAKE_RES_CREATE_TABLE_SQL = "create table "
            + TABLE_STU_CREATE_RES + " ( "
            + "s_id integer primary key, "
            + "r_id integer, "
            + "make_time text, "
            + "foreign key (s_id) reference " + TABLE_STUDENT + " (id) "
            + "foreign key (r_id) reference " + TABLE_RESUME + " (id) "
            + ");";*/

    // 学生投递职位
    private static final String STU_DELIVER_POS_CREATE_TABLE_SQL = "create table "
            + TABLE_STU_DELIVER_POS + " ( "
            + "s_id integer, "
            + "p_id integer, "
            + "deliver_date text, "
            + "primary key (s_id,p_id), "
            + "foreign key (s_id) references " + TABLE_STUDENT + " (id), "
            + "foreign key (p_id) references " + TABLE_POSITION + " (id) "
            + "on delete cascade"
            + ");";

    // 学生收藏职位
    private static final String STU_COLLECT_POS_CREATE_TABLE_SQL = "create table "
            + TABLE_STU_COLLECT_POS + " ( "
            + "s_id integer, "
            + "p_id integer, "
            + "collect_date text, "
            + "primary key (s_id,p_id), "
            + "foreign key (s_id) references " + TABLE_STUDENT + " (id), "
            + "foreign key (p_id) references " + TABLE_POSITION + " (id) "
            + "on delete cascade"
            + ");";

    // 企业发布职位
    /*private static final String ETP_POST_POS_CREATE_TABLE_SQL = "create table "
            + TABLE_ETP_POST_POS + " ( "
            + "e_id integer, "
            + "p_id integer, "
            + "primary key (p_id), "
            + "foreign key (e_id) references " + TABLE_ENTERPRISE + " (id), "
            + "foreign key (p_id) references " + TABLE_POSITION + " (id) "
            + ");";*/

    // 企业通知学生
    private static final String ETP_INFO_STU_CREATE_TABLE_SQL = "create table "
            + TABLE_ETP_INFO_STU + " ( "
            + "e_id integer, "
            + "p_id integer, "
            + "s_id integer, "
            + "date text,"
            + "is_pass integer," //0 表示未通过
            + "primary key (s_id,p_id), "
            + "foreign key (e_id) references " + TABLE_ENTERPRISE + " (id), "
            + "foreign key (p_id) references " + TABLE_POSITION + " (id) "
            + "on delete cascade, "
            + "foreign key (s_id) references " + TABLE_STUDENT + " (id) "
            + ");";

    private DatabaseHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
        mDatabase = getWritableDatabase();
    }

    public static void init(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getInstance() {
        if (sDatabaseHelper == null) {
            throw new NullPointerException("sDatabaseHelper is null, please call init " +
                    "method first.");
        }
        return sDatabaseHelper;
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        try {
            db.execSQL(STUDENT_INFO_CREATE_TABLE_SQL);
            db.execSQL(RESUME_INFO_CREATE_TABLE_SQL);
            db.execSQL(ENTERPRISE_INFO_CREATE_TABLE_SQL);
            db.execSQL(POSITION_INFO_CREATE_TABLE_SQL);

            db.execSQL(STU_COLLECT_POS_CREATE_TABLE_SQL);
            db.execSQL(STU_DELIVER_POS_CREATE_TABLE_SQL);
            db.execSQL(ETP_INFO_STU_CREATE_TABLE_SQL);
            Log.d(TAG, "onCreate: 表创建成功");
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                //upgradeFromVersion1(db);
                break;

            default:
                break;
        }
    }

    private void upgradeFromVersion1(SQLiteDatabase db) {
        try {
            db.execSQL(STUDENT_INFO_CREATE_TABLE_SQL);
            db.execSQL(ENTERPRISE_INFO_CREATE_TABLE_SQL);
            Log.d(TAG, "onCreate: 表创建成功");
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

            //或者这样写
            /*String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);*/
        }
    }
}
