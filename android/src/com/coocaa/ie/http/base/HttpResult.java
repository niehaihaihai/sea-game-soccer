package com.coocaa.ie.http.base;

/**
 * Created by Sea on 2017/12/26.
 */

public class HttpResult<T> {
    public int code = 0;
    public String msg = "";
    public T data;
}