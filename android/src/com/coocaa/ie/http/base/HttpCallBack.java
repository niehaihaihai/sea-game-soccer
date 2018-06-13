package com.coocaa.ie.http.base;

/**
 * Created by Sea on 2017/12/26.
 */

public interface HttpCallBack<T> {
    void callback(T t);
    void error(int code);
}
