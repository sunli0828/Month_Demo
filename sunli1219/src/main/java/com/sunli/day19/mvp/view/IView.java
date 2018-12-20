package com.sunli.day19.mvp.view;

public interface IView {
    void showResponseData(Object data);
    void showResponseFail(Exception e);
}
