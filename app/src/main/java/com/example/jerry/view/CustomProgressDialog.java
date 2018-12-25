package com.example.jerry.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.RatingCompat;
import android.view.WindowManager;

import com.example.jerry.R;

/**
 * Created by jerry on 2018/3/18.
 */

public class CustomProgressDialog extends ProgressDialog {


    public CustomProgressDialog(Context context) {
        this(context, R.style.Theme_CustomProgressDialog);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_custom_loading);
        initView();

    }

    private void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);


    }





}
