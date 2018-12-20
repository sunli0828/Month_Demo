package com.sunli.sunli1218.mvp.model;

import com.sunli.sunli1218.mvp.callback.ICallBack;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallBack iCallBack);
}
