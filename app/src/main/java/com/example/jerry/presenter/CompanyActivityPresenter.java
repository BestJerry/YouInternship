package com.example.jerry.presenter;

import android.content.Context;

import com.example.jerry.basemvp.BasePresenter;
import com.example.jerry.view.CompanyMainActivity;

/**
 * Created by jerry on 2018/3/24.
 */

public class CompanyActivityPresenter extends BasePresenter<CompanyMainActivity> {

    public CompanyActivityPresenter(Context context, CompanyMainActivity View) {
        super(context, View);
    }
}
