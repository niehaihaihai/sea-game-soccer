package com.coocaa.ie.games.wc2018.penalty.service;

/**
 * Created by Sea on 2018/5/7.
 */

public class PenaltyData {//extends BaseData {

    /**
     * 金币系数
     */
    private int goldenCoefficient;

    private String adLeft;

    private String adRight;

    public int getGoldenCoefficient() {
        return goldenCoefficient;
    }

    public void setGoldenCoefficient(int goldenCoefficient) {
        this.goldenCoefficient = goldenCoefficient;
    }

    public String getAdLeft() {
        return adLeft;
    }

    public void setAdLeft(String adLeft) {
        this.adLeft = adLeft;
    }

    public String getAdRight() {
        return adRight;
    }

    public void setAdRight(String adRight) {
        this.adRight = adRight;
    }

}
