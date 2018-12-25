package com.example.jerry.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.DeliverPositionPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jerry on 2018/3/26.
 */

public class DeliverPositionActivity extends BaseActivity<DeliverPositionPresenter> implements BaseView {

    public static final String TAG = "YouInternship";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_position_name)
    TextView mTvPositionName;
    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.tv_base)
    TextView mTvBase;
    @BindView(R.id.tv_work_time)
    TextView mTvWorkTime;
    @BindView(R.id.tv_salary)
    TextView mTvSalary;
    @BindView(R.id.tv_position_description)
    TextView mTvPositionDescription;
    @BindView(R.id.tv_position_require)
    TextView mTvPositionRequire;
    @BindView(R.id.tv_deadline)
    TextView mTvDeadline;
    @BindView(R.id.tv_company_name_2)
    TextView mTvCompanyName2;
    @BindView(R.id.tv_homepage)
    TextView mTvHomepage;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_introduction)
    TextView mTvIntroduction;
    @BindView(R.id.bt_collect)
    Button mBtCollect;
    @BindView(R.id.bt_deliver)
    Button mBtDeliver;
    @BindView(R.id.bt_delete)
    Button mBtDelete;


    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DeliverPositionActivity.class);

        return intent;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x010:
                setContent(object);
                break;

            case 0x011:
                if ((Boolean) object) {
                    Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(mContext, "您已收藏过该职位", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0x012:

                if ((Boolean) object) {
                    Toast.makeText(mContext, "投递成功", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(mContext, "您已投递过该职位", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0x013:

                if ((Boolean) object) {
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "系统出了点小问题", Toast.LENGTH_SHORT).show();
                }
                this.finish();
                break;
        }
    }

    private void setContent(Object object) {
        ContentValues contentValues = (ContentValues) object;

        mTvPositionName.setText(contentValues.getAsString("p_name"));
        mTvCompanyName.setText(contentValues.getAsString("e_name"));
        mTvBase.setText(contentValues.getAsString("p_base"));
        mTvWorkTime.setText(contentValues.getAsString("p_work_time"));
        mTvSalary.setText(contentValues.getAsString("p_salary"));
        mTvPositionDescription.setText(contentValues.getAsString("p_description"));
        mTvPositionRequire.setText(contentValues.getAsString("p_require"));
        mTvDeadline.setText(contentValues.getAsString("p_deadline"));
        mTvCompanyName2.setText(contentValues.getAsString("e_name"));
        mTvHomepage.setText(contentValues.getAsString("e_homepage"));
        mTvAddress.setText(contentValues.getAsString("e_address"));
        mTvIntroduction.setText(contentValues.getAsString("e_introduction"));

        if (!Constant.isStudentLogin) {
            mBtCollect.setVisibility(View.GONE);
            mBtDeliver.setVisibility(View.GONE);
            mBtDelete.setVisibility(View.VISIBLE);
        } else {
            mBtCollect.setVisibility(View.VISIBLE);
            mBtDeliver.setVisibility(View.VISIBLE);
            mBtDelete.setVisibility(View.GONE);
        }

    }

    @Override
    protected DeliverPositionPresenter createPresenter() {
        return new DeliverPositionPresenter(this, this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("职位详情");

        Log.d(TAG, "initView: " + mIntent.getIntExtra("p_id", 10));
        Log.d(TAG, "initView: " + mIntent.getIntExtra("e_id", 10));

        mPresenter.fetchPositionData(mIntent.getIntExtra("p_id", 1),
                mIntent.getIntExtra("e_id", 1));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deliver_position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation


    }

    @OnClick({R.id.bt_collect, R.id.bt_deliver, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_collect:

                mPresenter.collectPosition(mIntent.getIntExtra("p_id", 1));
                break;
            case R.id.bt_deliver:
                mPresenter.deliverPosition(mIntent.getIntExtra("p_id", 1));
                break;

            case R.id.bt_delete:
                mPresenter.deletePosition(mIntent.getIntExtra("p_id", 1));
        }
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

}
