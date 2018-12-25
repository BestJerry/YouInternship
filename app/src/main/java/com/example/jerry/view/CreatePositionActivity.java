package com.example.jerry.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.CreatePositionPresenter;
import com.example.jerry.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CreatePositionActivity extends BaseActivity<CreatePositionPresenter> implements BaseView {

    public static final String TAG = "YouInternship";


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_position_name)
    TextView mTvPositionName;
    @BindView(R.id.et_position_name)
    EditText mEtPositionName;
    @BindView(R.id.tv_base)
    TextView mTvBase;
    @BindView(R.id.et_base)
    EditText mEtBase;
    @BindView(R.id.tv_work_time)
    TextView mTvWorkTime;
    @BindView(R.id.et_work_time)
    EditText mEtWorkTime;
    @BindView(R.id.tv_salary)
    TextView mTvSalary;
    @BindView(R.id.et_salary)
    EditText mEtSalary;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.tv_require)
    TextView mTvRequire;
    @BindView(R.id.et_require)
    EditText mEtRequire;
    @BindView(R.id.tv_deadline)
    TextView mTvDeadline;
    @BindView(R.id.et_deadline)
    EditText mEtDeadline;
    @BindView(R.id.bt_complete)
    Button mBtComplete;

    private ContentValues mContentValues;

    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreatePositionActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setData(int state, Object object) {

        switch (state) {
            case 0x010:
                if ((boolean) object) {
                    Toast.makeText(mContext, "新建职位成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "请先填写必要的信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected CreatePositionPresenter createPresenter() {
        return new CreatePositionPresenter(this, this);
    }

    @Override
    protected void initView() {

        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新建职位");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_position;
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

        if (mEtPositionName.getText().toString().equals("")||mEtBase.getText().toString().equals("")
                ||mEtWorkTime.getText().toString().equals("")||mEtSalary.getText().toString().equals("")){
            Toast.makeText(mContext, "必填信息不得为空", Toast.LENGTH_SHORT).show();
        }
        putData();
        mPresenter.insertPosition(mContentValues);
    }

    private void putData() {

        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();

        mContentValues.put("e_id", Constant.ENTERPRISE_ID);
        mContentValues.put("name", mEtPositionName.getText().toString());
        mContentValues.put("base", mEtBase.getText().toString());
        mContentValues.put("work_time", mEtWorkTime.getText().toString());
        mContentValues.put("salary", mEtSalary.getText().toString());
        mContentValues.put("description", mEtDescription.getText().toString());
        mContentValues.put("require", mEtRequire.getText().toString());
        mContentValues.put("deadline", mEtDeadline.getText().toString());
        mContentValues.put("update_date", DateUtil.getDate());

    }

}
