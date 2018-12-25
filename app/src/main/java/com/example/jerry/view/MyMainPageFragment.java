package com.example.jerry.view;

import android.media.midi.MidiManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.R;
import com.example.jerry.adapter.MenuAdapter;
import com.example.jerry.basemvp.BaseFragment;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.beans.MenuItem;
import com.example.jerry.constants.Constant;
import com.example.jerry.listeners.OnItemClickListener;
import com.example.jerry.presenter.MyMainPagePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jerry on 2018/3/20.
 */

public class MyMainPageFragment extends BaseFragment<MyMainPagePresenter> implements BaseView {



    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.recyclerview_my_mainpage_menu)
    RecyclerView mRecyclerviewMyMainpageMenu;
    Unbinder unbinder;
    @BindView(R.id.img_user_head_portrait)
    ImageView mImgUserHeadPortrait;

    @Override
    public void setData(int state, Object object) {

    }

    @Override
    protected MyMainPagePresenter createPresenter() {
        return new MyMainPagePresenter(getActivity(), this);
    }

    @Override
    protected void initView() {


        mRecyclerviewMyMainpageMenu.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // 初始化菜单Adapter
        MenuAdapter menuAdapter = new MenuAdapter();
        menuAdapter.addItems(prepareMenuItems());
        menuAdapter.setOnItemClickListener(new OnItemClickListener<MenuItem>() {
            @Override
            public void onClick(MenuItem item) {
                clickMenuItem(item);
            }
        });
        mRecyclerviewMyMainpageMenu.setAdapter(menuAdapter);
    }

    private void clickMenuItem(MenuItem item) {

        switch (item.getIconResId()) {
            case R.drawable.icon_resume:
                startActivity(CreateResumeActivity.newIntent(getActivity()));

                break;

            case R.drawable.icon_collection:
                startActivity(MyCollectionActivity.newIntent(getActivity()));
                break;

            case R.drawable.icon_post_job:
                if (Constant.isDetailComplete == 0) {
                    Toast.makeText(getActivity(), "请先完善信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(CreatePositionActivity.newIntent(getActivity()));
                break;

            case R.drawable.icon_complete_message:
                startActivity(CompanyInfoActivity.newIntent(getActivity()));
                break;

            case R.drawable.icon_exit:
                Constant.STUDENT_ID = -1;
                Constant.ENTERPRISE_ID = -1;
                getActivity().finish();

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isStudentLogin){
            mImgUserHeadPortrait.setImageResource(R.drawable.image_headpic);
            mTvUserName.setText("用户名");
        }else{
            mImgUserHeadPortrait.setImageResource(R.drawable.alibaba_logo);
            mTvUserName.setText("公司名");
        }
    }

    private List<MenuItem> prepareMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        if (Constant.isStudentLogin) {

            menuItems.add(new MenuItem(getString(R.string.my_resume), R.drawable.icon_resume));
            menuItems.add(new MenuItem(getString(R.string.my_collection), R.drawable.icon_collection));
            menuItems.add(new MenuItem(getString(R.string.exit), R.drawable.icon_exit));
        } else {
            menuItems.add(new MenuItem(getString(R.string.deliver_position), R.drawable.icon_post_job));
            menuItems.add(new MenuItem(getString(R.string.complete_message), R.drawable.icon_complete_message));
            menuItems.add(new MenuItem(getString(R.string.exit), R.drawable.icon_exit));

        }

        return menuItems;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_mainpage;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
