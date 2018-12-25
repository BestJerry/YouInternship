package com.example.jerry.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.RegisterActivityPresenter;
import com.example.jerry.presenter.ResumeDetailPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ResumeDetailActivity extends BaseActivity<ResumeDetailPresenter> implements BaseView {

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
    @BindView(R.id.bt_not_fit)
    Button mBtNotFit;
    @BindView(R.id.bt_access)
    Button mBtAccess;


    private int p_id;

    private int s_id;



    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ResumeDetailActivity.class);
        return intent;
    }

    @Override
    protected ResumeDetailPresenter createPresenter() {
        return new ResumeDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("简历详情");

    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resume_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p_id = mIntent.getIntExtra("p_id", 1);
        s_id = mIntent.getIntExtra("s_id", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getResumeDetail(s_id);

    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x010:
                if (object != null) {
                    setContent((ContentValues) object);

                }
                break;
            case 0x011:
                if ((boolean) object) {
                    Toast.makeText(mContext, "通知已发送", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "您已通知过该学生", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0x012:
                if ((boolean) object) {
                    Toast.makeText(mContext, "通知已发送", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "您已通知过该学生", Toast.LENGTH_SHORT).show();
                }
                break;
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


    @OnClick({R.id.bt_not_fit, R.id.bt_access})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_not_fit:
                mPresenter.sendNoFitInfo(p_id, s_id);
                break;
            case R.id.bt_access:
                mPresenter.sendAccessInfo(p_id, s_id);
                break;
        }
    }

}
