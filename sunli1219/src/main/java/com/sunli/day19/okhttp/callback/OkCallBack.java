package com.sunli.day19.okhttp.callback;

public interface OkCallBack<T> {
    void success(T data);
    void fail(Exception e);
}
