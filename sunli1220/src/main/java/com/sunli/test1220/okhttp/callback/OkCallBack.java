package com.sunli.test1220.okhttp.callback;

public interface OkCallBack<T> {
    void success(T data);
    void fail(Exception e);
}
