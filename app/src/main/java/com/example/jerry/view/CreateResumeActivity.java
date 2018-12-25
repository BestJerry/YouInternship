package com.example.jerry.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.CreateResumePresenter;
import com.example.jerry.utils.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateResumeActivity extends BaseActivity<CreateResumePresenter> implements BaseView {

    public static final String TAG = "YouInternship";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_student_name)
    EditText mEtStudentName;
    @BindView(R.id.et_sex)
    EditText mEtSex;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_school)
    EditText mEtSchool;
    @BindView(R.id.et_major)
    EditText mEtMajor;
    @BindView(R.id.et_intention)
    EditText mEtIntention;
    @BindView(R.id.et_project_exp)
    EditText mEtProjectExp;
    @BindView(R.id.et_internship_exp)
    EditText mEtInternshipExp;
    @BindView(R.id.et_volunteer_exp)
    EditText mEtVolunteerExp;
    @BindView(R.id.et_honorary)
    EditText mEtHonorary;
    @BindView(R.id.et_hobby)
    EditText mEtHobby;
    @BindView(R.id.et_self_evaluation)
    EditText mEtSelfEvaluation;
    @BindView(R.id.bt_complete)
    Button mBtComplete;


    private ContentValues mContentValues;

    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreateResumeActivity.class);
        return intent;
    }

    @Override
    protected CreateResumePresenter createPresenter() {
        return new CreateResumePresenter(this, this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的简历");

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSelfResume();

    }

    @Override
    public void setData(int state, Object object) {

        switch (state) {
            case 0x010:
                if ((boolean) object) {
                    Toast.makeText(mContext, "完善简历成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "请先填写必要的信息", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0x011:
                if (object != null) {
                    setContent((ContentValues) object);
                }
        }

    }

    private void setContent(ContentValues contentValues) {

        mEtStudentName.setText(contentValues.getAsString("name"));
        mEtSex.setText(contentValues.getAsString("sex"));
        mEtAge.setText(contentValues.getAsString("age"));
        mEtPhone.setText(contentValues.getAsString("phone"));
        mEtEmail.setText(contentValues.getAsString("email"));
        mEtSchool.setText(contentValues.getAsString("school"));
        mEtMajor.setText(contentValues.getAsString("major"));
        mEtIntention.setText(contentValues.getAsString("intention"));
        mEtProjectExp.setText(contentValues.getAsString("project_exp"));
        mEtInternshipExp.setText(contentValues.getAsString("internship_exp"));
        mEtVolunteerExp.setText(contentValues.getAsString("volunteer_exp"));
        mEtHonorary.setText(contentValues.getAsString("honorary"));
        mEtHobby.setText(contentValues.getAsString("hobby"));
        mEtSelfEvaluation.setText(contentValues.getAsString("self_evaluation"));

    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.bt_complete)
    public void onViewClicked() {
        if (mEtStudentName.getText().toString().equals("")) {
            Toast.makeText(mContext, "姓名不得为空", Toast.LENGTH_SHORT).show();
            return;
        }
        putData();
        mPresenter.insertOrUpdateResume(mContentValues);
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();

        mContentValues.put("s_id", Constant.STUDENT_ID );
        mContentValues.put("name", mEtStudentName.getText().toString());
        mContentValues.put("sex", mEtSex.getText().toString());
        mContentValues.put("age", mEtAge.getText().toString());
        mContentValues.put("phone", mEtPhone.getText().toString());
        mContentValues.put("email", mEtEmail.getText().toString());
        mContentValues.put("school", mEtSchool.getText().toString());
        mContentValues.put("major", mEtMajor.getText().toString());
        mContentValues.put("intention", mEtIntention.getText().toString());
        mContentValues.put("project_exp", mEtProjectExp.getText().toString());
        mContentValues.put("internship_exp", mEtInternshipExp.getText().toString());
        mContentValues.put("volunteer_exp", mEtVolunteerExp.getText().toString());
        mContentValues.put("honorary", mEtHonorary.getText().toString());
        mContentValues.put("hobby", mEtHobby.getText().toString());
        mContentValues.put("self_evaluation", mEtSelfEvaluation.getText().toString());
        mContentValues.put("make_time", DateUtil.getDate());
        mContentValues.put("is_detail_complete",1);

    }
}
