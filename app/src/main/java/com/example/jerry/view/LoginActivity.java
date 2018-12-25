package com.example.jerry.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.LoginActivityPresenter;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements BaseView {

    public static final String TAG = "YouInternship";


    @BindView(R.id.bt_close)
    Button mBtClose;
    @BindView(R.id.tv_login_title)
    TextView mTvLoginTitle;
    @BindView(R.id.et_login_Account)
    EditText mEtLoginAccount;
    @BindView(R.id.et_login_Password)
    EditText mEtLoginPassword;
    @BindView(R.id.tv_goto_register)
    TextView mTvGotoRegister;
    @BindView(R.id.bt_immediately_login)
    Button mBtImmediatelyLogin;
    @BindView(R.id.tv_person_login)
    TextView mTvPersonLogin;
    @BindView(R.id.tv_company_login)
    TextView mTvCompanyLogin;

    private CustomProgressDialog mProgressDialog;

    private static Thread sThread;

    private MyHandler mHandler;


    private static class MyHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;

        public MyHandler(LoginActivity activity) {
            mActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (activity != null) {

                switch (msg.what) {
                    case 0x002:
                        activity.mProgressDialog.dismiss();
                        if (Constant.isStudentLogin) {
                            activity.startActivity(MainActivity.newIntent(activity));
                        } else {
                            activity.startActivity(CompanyMainActivity.newIntent(activity));
                        }
                        activity.mProgressDialog = null;
                        break;
                }
            }
        }
    }


    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter(this, this);
    }

    @Override
    protected void initView() {
        Constant.isStudentLogin = true;
        mTvCompanyLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));
        mTvPersonLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);

        /*try{
            DatabaseHelper.getInstance().getDatabase().execSQL("drop table "+ DatabaseHelper.TABLE_STUDENT);
            DatabaseHelper.getInstance().getDatabase().execSQL("drop table "+ DatabaseHelper.TABLE_ENTERPRISE);
            Log.d(TAG, "onCreate: 表删除成功");
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "onCreate: 表删除不成功" + e.getMessage());
        }*/


    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x010:
                if ((Boolean) object) {
                    Message message = new Message();
                    message.what = 0x002;
                    mHandler.sendMessage(message);

                } else {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                    Toast.makeText(mContext, "手机号或密码不正确", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }


    @OnClick({R.id.bt_close, R.id.tv_goto_register, R.id.bt_immediately_login, R.id.tv_person_login, R.id.tv_company_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_person_login:

                Constant.isStudentLogin = true;
                mTvCompanyLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));
                mTvPersonLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));


                break;
            case R.id.tv_company_login:

                Constant.isStudentLogin = false;
                mTvCompanyLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));
                mTvPersonLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));

                break;

            case R.id.bt_close:
                this.finish();
                break;
            case R.id.tv_goto_register:
                startActivityForResult(RegisterActivity.newIntent(LoginActivity.this),
                        0x001);
                break;
            case R.id.bt_immediately_login:
                mProgressDialog = new CustomProgressDialog(this);
                mProgressDialog.show();

                sThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                sThread.start();
                mPresenter.check(Constant.isStudentLogin, mEtLoginAccount.getText().toString(),
                        mEtLoginPassword.getText().toString());

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0x001:
                if (resultCode == RESULT_OK) {

                    if (Constant.isStudentRegister) {
                        Constant.isStudentLogin = true;
                    } else {
                        Constant.isStudentLogin = false;
                    }
                    mEtLoginAccount.setText(data.getStringExtra("phone_number"));
                    mEtLoginPassword.setText(data.getStringExtra("password"));
                    mProgressDialog = new CustomProgressDialog(this);
                    mProgressDialog.show();

                    sThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.currentThread().sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    sThread.start();

                    Message message = new Message();
                    message.what = 0x002;
                    mHandler.sendMessage(message);
                } else {
                    Constant.isStudentLogin = true;
                    mTvCompanyLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White_transparent));
                    mTvPersonLogin.setTextColor(ContextCompat.getColor(this, R.color.colorText_White));

                }
                break;

        }
    }
}

