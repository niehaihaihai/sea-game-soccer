package com.coocaa.ie.core.system.vc;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

//{"command":28,"detail":{"original":"射手榜查询","parameter":"4"}}
public class VoiceObject implements Serializable {
    public static class Detail implements Serializable {
        public String original;
        public String parameter;

        @Override
        public String toString() {
            return "original:" + original + " parameter:" + parameter;
        }
    }

    public String command;
    public Detail detail;

    public String toString() {
        return "command:" + command + " detail:" + detail.toString();
    }

    public static VoiceObject parse(String json) {
        return JSONObject.parseObject(json, VoiceObject.class);
    }
}
