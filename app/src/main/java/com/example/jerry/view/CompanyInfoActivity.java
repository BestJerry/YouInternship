package com.example.jerry.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.CompanyInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyInfoActivity extends BaseActivity<CompanyInfoPresenter> implements BaseView {

    public static final String TAG = "YouInternship";


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_homepage)
    EditText mEtHomepage;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.et_introduction)
    EditText mEtIntroduction;
    @BindView(R.id.image_voucher)
    ImageView mImageVoucher;
    @BindView(R.id.bt_complete)
    Button mBtComplete;

    private ContentValues mContentValues;


    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CompanyInfoActivity.class);
        return intent;
    }

    @Override
    protected CompanyInfoPresenter createPresenter() {
        return new CompanyInfoPresenter(this, this);
    }

    @Override
    protected void initView() {

        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("完善信息");

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getCompanyInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData(int state, Object object) {

        switch (state) {
            case 0x010:
                if ((boolean) object) {
                    Constant.isDetailComplete = 1;
                    Toast.makeText(mContext, "完善信息成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "出了点小错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case 0x011:
                if (object != null) {
                    setContent((ContentValues) object);
                }
        }
    }

    private void setContent(ContentValues contentValues) {

        mEtName.setText(contentValues.getAsString("name"));
        mEtHomepage.setText(contentValues.getAsString("homepage"));
        mEtAddress.setText(contentValues.getAsString("address"));
        mEtIntroduction.setText(contentValues.getAsString("introduction"));

    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }


    private void putData() {

        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }

        mContentValues.clear();

        mContentValues.put("name", mEtName.getText().toString());
        mContentValues.put("homepage", mEtHomepage.getText().toString());
        mContentValues.put("address", mEtAddress.getText().toString());
        mContentValues.put("introduction", mEtIntroduction.getText().toString());
        mContentValues.put("icon_address", "");
        mContentValues.put("voucher_address", "");
        mContentValues.put("is_detail_complete", 1);

    }


    @OnClick({R.id.image_voucher, R.id.bt_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_voucher:
                break;
            case R.id.bt_complete:
                if (mEtName.getText().toString().equals("")) {
                    Toast.makeText(mContext, "公司名称不得为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                putData();
                mPresenter.updateDetail(mContentValues);
                break;
        }
    }
}
