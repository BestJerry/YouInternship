package com.example.jerry.presenter;

import android.content.Context;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.view.MainActivity;

/**
 * Created by jerry on 2018/3/16.
 */

public class MainActivityPresenter extends BasePresenter<MainActivity> {

    public MainActivityPresenter(Context context, MainActivity View) {
        super(context, View);
    }
}

