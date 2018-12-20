package com.sunli.day19.mvp.model;

import com.sunli.day19.mvp.callback.ICallBack;
import com.sunli.day19.okhttp.callback.OkCallBack;
import com.sunli.day19.okhttp.utils.OkHttpUtils;

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
                iCallBack.setDataFail(e);
            }
        }, clazz);
    }
}
