package com.sunli.month_demo.mvp.callback;

public interface ICallBack<T> {
    void success(T data);
    void fail(Exception e);
}
