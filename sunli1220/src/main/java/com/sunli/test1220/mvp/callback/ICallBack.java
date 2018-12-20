package com.sunli.test1220.mvp.callback;

public interface ICallBack<T> {
    void setData(T data);
    void setDataFail(Exception e);
}
