package com.example.jerry.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jerry.R;
import com.example.jerry.adapter.PositionAdapter;
import com.example.jerry.basemvp.BaseActivity;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.beans.Position;
import com.example.jerry.listeners.OnItemClickListener;
import com.example.jerry.presenter.MyCollectionPresenter;

import java.util.List;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements BaseView {

    public static final String TAG = "YouInternship";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    protected PositionAdapter mPositionAdapter;


    protected static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        return intent;
    }

    @Override
    protected MyCollectionPresenter createPresenter() {
        return new MyCollectionPresenter(this, this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的收藏");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        initAdapter();
    }

    protected void initAdapter() {
        mPositionAdapter = new PositionAdapter();
        mPositionAdapter.setOnItemClickListener(new OnItemClickListener<Position>() {
            @Override
            public void onClick(Position item) {
                if (item != null) {
                    showPositionDetail(item.getId(), item.getE_id());
                }
            }
        });
        // 设置Adapter
        mRecyclerView.setAdapter(mPositionAdapter);
    }


    private void showPositionDetail(int p_id, int e_id) {

        Intent intent = DeliverPositionActivity.newIntent(this);
        intent.putExtra("p_id",p_id);
        intent.putExtra("e_id",e_id);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x010:
                mPositionAdapter.clear();
                mPositionAdapter.addItems((List<Position>) object);
                break;
        }
    }
    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.fetchPositionData();

    }
}
