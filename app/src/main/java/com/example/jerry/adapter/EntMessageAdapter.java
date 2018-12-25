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
 * Created by jerry on 2018/3/27.
 */

public class EntMessageAdapter extends RecyclerBaseAdapter<Message, EntMessageAdapter.EntMessageViewHolder> {



    @SuppressLint("ResourceAsColor")
    @Override
    protected void bindDataToItemView(EntMessageViewHolder viewHolder, Message item) {


        viewHolder.mTvStudentName.setText(item.getS_name());
        viewHolder.mTvPositionName.setText(item.getP_name());
        viewHolder.mTvDate.setText(item.getDate());

    }

    @Override
    public EntMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;


        itemView = inflateItemView(parent, R.layout.recyclerview_ent_message_item);


        return new EntMessageViewHolder(itemView);

    }

    public static class EntMessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_student_name)
        TextView mTvStudentName;
        @BindView(R.id.tv_position_name)
        TextView mTvPositionName;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public EntMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
