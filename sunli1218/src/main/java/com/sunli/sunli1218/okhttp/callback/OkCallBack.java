package com.sunli.sunli1218.okhttp.callback;

public interface OkCallBack<T> {
    void success(T data);
    void fail(Exception e);
}
