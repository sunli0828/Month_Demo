package com.sunli.sunli1218.mvp.model;

import com.sunli.sunli1218.mvp.callback.ICallBack;
import com.sunli.sunli1218.okhttp.callback.OkCallBack;
import com.sunli.sunli1218.okhttp.utils.OkHttpUtils;

public class IModelImpl implements IModel {
    @Override
    public void getResponseData(String urlStr, String params, Class clazz, final ICallBack iCallBack) {
        OkHttpUtils.getOkInstance().getEnqueue(urlStr, new OkCallBack() {
            @Override
            public void success(Object data) {
                iCallBack.setData(data);
            }

            @Override
            public void fail(Exception e) {
                iCallBack.fail(e);
            }
        }, clazz);
    }
}
