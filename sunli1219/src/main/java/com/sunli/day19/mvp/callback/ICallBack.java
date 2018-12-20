package com.sunli.day19.mvp.callback;

public interface ICallBack<T> {
    void setData(T data);
    void setDataFail(Exception e);
}
