package com.example.jerry.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jerry.R;
import com.example.jerry.adapter.PositionAdapter;
import com.example.jerry.basemvp.BaseFragment;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.beans.Position;
import com.example.jerry.listeners.OnItemClickListener;
import com.example.jerry.presenter.PositionListPresenter;
import com.example.jerry.widgets.AutoLoadRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jerry on 2018/3/20.
 */

public class PositionListFragment extends BaseFragment<PositionListPresenter> implements BaseView{


    protected PositionAdapter mPositionAdapter;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerViewPosition;
    Unbinder unbinder;


    @Override
    protected PositionListPresenter createPresenter() {
        return new PositionListPresenter(getActivity(), this);
    }

    @Override
    protected void initView() {

        mRecyclerViewPosition.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        mRecyclerViewPosition.setHasFixedSize(true);
        mRecyclerViewPosition.setVisibility(View.VISIBLE);
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
        mRecyclerViewPosition.setAdapter(mPositionAdapter);
    }

    private void showPositionDetail(int p_id, int e_id) {

        Intent intent = DeliverPositionActivity.newIntent(getActivity());
        intent.putExtra("p_id",p_id);
        intent.putExtra("e_id",e_id);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchPositionData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
