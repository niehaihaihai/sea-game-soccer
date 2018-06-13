package com.coocaa.ie.games.wc2018.utils.web.call;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class WC2018HttpBaseData implements Serializable {

    public static final WC2018HttpBaseData parse(String value) {
        WC2018HttpBaseData data = null;
        try {
            data = JSONObject.parseObject(value, WC2018HttpBaseData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null)
            data = new WC2018HttpBaseData();
        data.source = value;
        return data;
    }


    public int code;
    public String msg;
    public String data;

    @JSONField(serialize = false)
    public String source;
    @JSONField(serialize = false)
    private Object object;

    public <T extends Serializable> T parseObject(Class<T> clazz) {
        object = JSONObject.parseObject(data, clazz);
        return (T) object;
    }

    public <T extends Serializable> T getObject() {
        if (object == null)
            return null;
        return (T) object;
    }
}
