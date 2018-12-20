package com.sunli.sunli1218.mvp.callback;

public interface ICallBack<T> {
    void setData(T data);
    void fail(Exception e);
}
