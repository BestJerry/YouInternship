package com.example.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.R;
import com.example.jerry.beans.MenuItem;

/**
 * Created by jerry on 2018/3/19.
 */

public class MenuAdapter extends RecyclerBaseAdapter<MenuItem, MenuAdapter.MenuViewHolder> {


    @Override
    protected void bindDataToItemView(MenuViewHolder viewHolder, MenuItem item) {
        viewHolder.menu_text_tv.setText(item.getText());
        viewHolder.menu_icon_imageview.setImageResource(item.getIconResId());
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(inflateItemView(parent, R.layout.recyclerview_meun_item));
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView menu_icon_imageview;
        public TextView menu_text_tv;
        public ImageView menu_arrow_imageview;

        public MenuViewHolder(View itemView) {
            super(itemView);
            menu_arrow_imageview = itemView.findViewById(R.id.image_item_arrow);
            menu_icon_imageview = itemView.findViewById(R.id.image_item_icon);
            menu_text_tv = itemView.findViewById(R.id.tv_item_describe);
        }
    }
}
