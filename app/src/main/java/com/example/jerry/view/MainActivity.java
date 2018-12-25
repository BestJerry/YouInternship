package com.example.jerry.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jerry.R;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.constants.Constant;
import com.example.jerry.presenter.MainActivityPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements BaseView {

    @BindView(R.id.sv_position)
    SearchView mSvPosition;

    private static final String TAG = "MainActivity";

    protected FragmentManager mFragmentManager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private Fragment mMyMainpageFragment;

    private Fragment mPositionListFragment;

    private Fragment mMessageFragment;

    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.chance:
                    if (mPositionListFragment == null){
                        mPositionListFragment = new PositionListFragment();
                    }
                    replaceFragment(mPositionListFragment);
                    break;

                case R.id.message:

                        if(mMessageFragment == null){
                            mMessageFragment = new MessageFragment();
                        }
                        replaceFragment(mMessageFragment);

                    break;

                case R.id.my:

                    if (mMyMainpageFragment == null) {
                        mMyMainpageFragment = new MyMainPageFragment();
                    }
                    replaceFragment(mMyMainpageFragment);
                    break;

            }
            return true;
        }
    };

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this, this);
    }

    @Override
    protected void initView() {
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mPositionListFragment = new PositionListFragment();
        addFragment(mPositionListFragment);
    }

    protected void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }


    protected void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void setData(int state, Object object) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
