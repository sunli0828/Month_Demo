package com.sunli.month_demo.mvp.model;

import com.sunli.month_demo.mvp.callback.ICallBack;

import java.util.Map;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallBack iCallBack);
}
