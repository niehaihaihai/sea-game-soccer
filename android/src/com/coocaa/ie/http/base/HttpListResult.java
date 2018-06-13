package com.coocaa.ie.http.base;

import java.util.List;

/**
 * Created by Sea on 2017/12/26.
 */

public class HttpListResult<T> {
    public int code = 0;
    public String msg = "";
    public List<T> data;
}