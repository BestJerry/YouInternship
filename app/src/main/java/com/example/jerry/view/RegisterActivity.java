package com.example.jerry.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.RegisterActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterActivityPresenter> implements BaseView {

    @BindView(R.id.tv_register_title)
    TextView mTvRegisterTitle;
    @BindView(R.id.et_input_account)
    EditText mEtInputAccount;
    @BindView(R.id.et_input_password)
    EditText mEtInputPassword;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.tv_person_register)
    TextView mTvPersonRegister;
    @BindView(R.id.tv_company_register)
    TextView mTvCompanyRegister;
    @BindView(R.id.image_back)
    ImageView mImageBack;

    @Override
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter(this, this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Constant.isStudentLogin = true;
        Constant.isStudentRegister = true;
        mTvCompanyRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));
        mTvPersonRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x010:
                if ((Boolean) object) {
                    Intent intent = new Intent();
                    intent.putExtra("phone_number", mEtInputAccount.getText().toString());
                    intent.putExtra("password", mEtInputPassword.getText().toString());
                    setResult(RESULT_OK, intent);
                    RegisterActivity.this.finish();

                } else {

                    Toast.makeText(mContext, "该手机号已经被注册", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @OnClick({R.id.bt_register, R.id.tv_person_register, R.id.tv_company_register, R.id.image_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                mPresenter.check(Constant.isStudentLogin, mEtInputAccount.getText().toString(),
                        mEtInputPassword.getText().toString());
                break;
            case R.id.tv_person_register:
                Constant.isStudentRegister = true;
                Constant.isStudentLogin = true;
                mTvCompanyRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));
                mTvPersonRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));

                break;
            case R.id.tv_company_register:
                Constant.isStudentRegister = false;
                Constant.isStudentLogin = false;
                mTvCompanyRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));
                mTvPersonRegister.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));

                break;
            case R.id.image_back:

                setResult(RESULT_CANCELED);
                this.finish();
        }
    }


}
