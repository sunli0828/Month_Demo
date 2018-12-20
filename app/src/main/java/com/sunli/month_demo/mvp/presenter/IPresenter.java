package com.sunli.month_demo.mvp.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String urlStr, String params, Class clazz);
}
