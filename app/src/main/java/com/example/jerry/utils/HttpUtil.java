package com.example.jerry.utils;

import android.content.Context;

import com.example.jerry.listeners.DataListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by jerry on 2018/3/20.
 */

public class HttpUtil {

    public static void get(Context context, String reqUrl, final DataListener listener) {

        OkGo.<String>get(reqUrl)
                .tag(context)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        listener.onComplete(response.body());
                    }
                });
    }
}
