package com.example.jerry.beans;

/**
 * Created by jerry on 2018/3/19.
 */

public class MenuItem {

    private int iconResId;
    private String text;

    public MenuItem(String text, int iconResId) {
        this.iconResId = iconResId;
        this.text = text;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
