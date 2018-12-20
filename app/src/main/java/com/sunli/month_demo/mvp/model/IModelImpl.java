package com.sunli.month_demo.mvp.model;

import com.sunli.month_demo.mvp.callback.ICallBack;
import com.sunli.month_demo.okhttp.callback.OkCallBack;
import com.sunli.month_demo.okhttp.utils.OkHttpUtils;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getResponseData(String urlStr, String params, Class clazz, final ICallBack iCallBack) {
        OkHttpUtils.getOkInstance().getEnqueue(urlStr, new OkCallBack() {
            @Override
            public void success(Object obj) {
                iCallBack.success(obj);
            }

            @Override
            public void failed(Exception e) {
                iCallBack.fail(e);
            }
        }, clazz);
    }
}
