package com.example.jerry.presenter;

import android.content.Context;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.basemvp.BaseView;

/**
 * Created by jerry on 2018/3/19.
 */

/*public class MyMainPagePresenter extends BasePresenter<MyMainPageActivity> {


    public MyMainPagePresenter(Context context, MyMainPageActivity View) {
        super(context, View);
    }
}*/

public class MyMainPagePresenter extends BasePresenter<BaseView> {


    public MyMainPagePresenter(Context context, BaseView View) {
        super(context, View);
    }
}
