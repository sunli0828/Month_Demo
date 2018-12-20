package com.sunli.month_demo.mvp.view;

public interface IView {
    void showResponseData(Object data);
    void showResponseFail(Exception e);
}
