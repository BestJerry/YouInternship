package com.example.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.R;
import com.example.jerry.beans.Position;
import com.example.jerry.listeners.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/26.
 */

public class PositionAdapter extends RecyclerBaseAdapter<Position, PositionAdapter.PositionViewHolder> {


    @Override
    protected void bindDataToItemView(PositionViewHolder viewHolder, Position item) {
        viewHolder.mImgCompanyLogo.setImageResource(R.drawable.alibaba_logo);
        viewHolder.mTvPositionName.setText(item.getName());
        viewHolder.mTvCompanyName.setText(item.getCompany_name());
        viewHolder.mTvCompanyLocation.setText(item.getBase());
        viewHolder.mTvUpdateDate.setText(item.getUpdate_date()+" 更新");
        viewHolder.mTvSalary.setText(item.getSalary());
        viewHolder.mTvWorkTime.setText(item.getWork_time());
    }

    @Override
    public PositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.recyclerview_position_item);
        return new PositionViewHolder(itemView);
    }

    public static class PositionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_company_logo)
        ImageView mImgCompanyLogo;
        @BindView(R.id.tv_position_name)
        TextView mTvPositionName;
        @BindView(R.id.tv_company_name)
        TextView mTvCompanyName;
        @BindView(R.id.tv_company_location)
        TextView mTvCompanyLocation;
        @BindView(R.id.tv_update_date)
        TextView mTvUpdateDate;
        @BindView(R.id.tv_salary)
        TextView mTvSalary;
        @BindView(R.id.tv_work_time)
        TextView mTvWorkTime;

        public PositionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
