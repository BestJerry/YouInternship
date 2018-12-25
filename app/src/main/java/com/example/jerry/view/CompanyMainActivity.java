package com.example.jerry.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.presenter.CompanyActivityPresenter;

import butterknife.BindView;

public class CompanyMainActivity extends BaseActivity<CompanyActivityPresenter> implements BaseView {

    protected FragmentManager mFragmentManager;

    
    @BindView(R.id.company_navigation)
    BottomNavigationView mCompanyNavigation;

    private Fragment mMyMainpageFragment;

    private Fragment mPositionListFragment;

    private Fragment mMessageFragment;


    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CompanyMainActivity.class);
        return intent;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.company_position:
                    if (mPositionListFragment == null){
                        mPositionListFragment = new PositionListFragment();
                    }
                    replaceFragment(mPositionListFragment);
                    return true;

                case R.id.company_message:

                    if(mMessageFragment == null){
                        mMessageFragment = new MessageFragment();
                    }
                    replaceFragment(mMessageFragment);

                    return true;

                case R.id.company_my:

                    if (mMyMainpageFragment == null) {
                        mMyMainpageFragment = new MyMainPageFragment();
                    }
                    replaceFragment(mMyMainpageFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected CompanyActivityPresenter createPresenter() {
        return new CompanyActivityPresenter(this, this);
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        mPositionListFragment = new PositionListFragment();
        addFragment(mPositionListFragment);

        mCompanyNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_company_main;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }


    protected void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void setData(int state, Object object) {

    }
}
