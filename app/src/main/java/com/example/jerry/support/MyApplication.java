package com.example.jerry.support;

import android.app.Application;

import com.example.jerry.model.DatabaseHelper;
import com.lzy.okgo.OkGo;

/**
 * Created by jerry on 2018/3/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
       DatabaseHelper.init(this);
    }
}
