package com.sunli.test1220.mvp.model;

import com.sunli.test1220.mvp.callback.ICallBack;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallBack iCallBack);
}
