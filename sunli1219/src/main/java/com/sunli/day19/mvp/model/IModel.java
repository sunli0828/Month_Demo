package com.sunli.day19.mvp.model;

import com.sunli.day19.mvp.callback.ICallBack;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallBack iCallBack);
}
