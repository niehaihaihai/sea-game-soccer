package com.coocaa.ie.http.wc2018.univers;

import java.io.Serializable;

/**
 * Created by Sea on 2017/12/26.
 */

public class Adinfo implements Serializable{

    private static final long serialVersionUID = -2867585872763455897L;

    private int advertId;//广告id

    private int activeId;//活动id

    private String advertName;//广告名称

    private String advertImgUrl;//广告海报地址

    private String partKey;//位置信息

    private String onclick;//启动方式json串

    private String type;//类型 content为内容 advert 为广告

    public int getAdvertId() {
        return advertId;
    }

    public void setAdvertId(int advertId) {
        this.advertId = advertId;
    }

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvertImgUrl() {
        return advertImgUrl;
    }

    public void setAdvertImgUrl(String advertImgUrl) {
        this.advertImgUrl = advertImgUrl;
    }

    public String getPartKey() {
        return partKey;
    }

    public void setPartKey(String partKey) {
        this.partKey = partKey;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
