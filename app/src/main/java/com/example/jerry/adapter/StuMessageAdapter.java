package com.example.jerry.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jerry.R;
import com.example.jerry.beans.Message;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/30.
 */

public class StuMessageAdapter extends RecyclerBaseAdapter<Message, StuMessageAdapter.StuMessageViewHolder> {




    @SuppressLint("ResourceAsColor")
    @Override
    protected void bindDataToItemView(StuMessageViewHolder viewHolder, Message item) {
        viewHolder.mTvCompanyName.setText(item.getE_name());
        viewHolder.mTvPositionName.setText(item.getP_name());
        viewHolder.mTvDate.setText(item.getDate());
        if (item.getInformation() == 0) {
            viewHolder.mTvInformation.setText("不合适");
            viewHolder.mTvInformation.setTextColor(R.color.colorText_red);

        } else {
            viewHolder.mTvInformation.setText("简历已通过，待沟通");
            viewHolder.mTvInformation.setTextColor(R.color.colorBackground_Green);
        }
    }

    @Override
    public StuMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = inflateItemView(parent, R.layout.recyclerview_stu_message_item);

        return new StuMessageViewHolder(itemView);

    }


    public static class StuMessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_company_name)
        TextView mTvCompanyName;
        @BindView(R.id.tv_position_name)
        TextView mTvPositionName;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_information)
        TextView mTvInformation;

        public StuMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
