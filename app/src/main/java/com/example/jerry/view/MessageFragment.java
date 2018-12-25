package com.example.jerry.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jerry.R;
import com.example.jerry.adapter.EntMessageAdapter;
import com.example.jerry.adapter.StuMessageAdapter;
import com.example.jerry.basemvp.BaseFragment;
import com.example.jerry.basemvp.BaseView;
import com.example.jerry.beans.Message;
import com.example.jerry.constants.Constant;
import com.example.jerry.listeners.OnItemClickListener;
import com.example.jerry.presenter.MessageFragmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jerry on 2018/3/20.
 */

public class MessageFragment extends BaseFragment<MessageFragmentPresenter> implements BaseView {

    public static final String TAG = "YouInternship";

    protected EntMessageAdapter mEntMessageAdapter;

    protected StuMessageAdapter mStuMessageAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Unbinder unbinder;
    @Override
    public void setData(int state, Object object) {

        switch (state){
            case 0x010:
                if (Constant.isStudentLogin){
                    mStuMessageAdapter.addItems((List<Message>) object);

                }else{
                    mEntMessageAdapter.addItems((List<Message>) object);
                }

        }
    }

    @Override
    protected MessageFragmentPresenter createPresenter() {
        return new MessageFragmentPresenter(getActivity(), this);
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        initAdapter();
    }

    protected void initAdapter() {

        if(Constant.isStudentLogin){
            mStuMessageAdapter = new StuMessageAdapter();

            mStuMessageAdapter.setOnItemClickListener(new OnItemClickListener<Message>() {
                @Override
                public void onClick(Message item) {
                    return;
                }
            });
            mRecyclerView.setAdapter(mStuMessageAdapter);
        }

        else{

            mEntMessageAdapter = new EntMessageAdapter();

            mEntMessageAdapter.setOnItemClickListener(new OnItemClickListener<Message>() {
                @Override
                public void onClick(Message item) {

                    Intent intent = ResumeDetailActivity.newIntent(getActivity());
                    intent.putExtra("p_id",item.getP_id());
                    intent.putExtra("s_id",item.getS_id());

                    startActivity(intent);

                }
            });

            mRecyclerView.setAdapter(mEntMessageAdapter);

        }

        // 设置Adapter
        mPresenter.fetchInformationData();
    }

    @Override
    protected int getLayoutId() {

        return R.layout.recyclerview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
