package com.coocaa.ie.games.wc2018.utils.web.ad;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.badlogic.gdx.Gdx;

import java.io.Serializable;

/**
 * Created by Sea on 2018/5/31.
 */

public class BaseAdData implements Serializable {

    public static final BaseAdData parse(String value) {
        BaseAdData data = null;
        try {
            data = JSONObject.parseObject(value, BaseAdData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null)
            data = new BaseAdData();
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
        object = JSONObject.parseArray(data, clazz);
//        object = JSONObject.parseObject(data, clazz);
        return (T) object;
    }

    public <T extends Serializable> T getObject() {
        if (object == null)
            return null;
        return (T) object;
    }
}
