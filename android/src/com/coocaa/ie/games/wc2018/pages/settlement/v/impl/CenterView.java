package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.badlogic.gdx.Game;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;

import java.util.List;

public class CenterView extends LinearLayout {
    public static final int HEIGHT = UI.div(372);
    public static final int WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;

    private InfoView infoView;
    private ADView adView;

    public CenterView(Context context) {
        super(context);
        setGravity(Gravity.BOTTOM);
        {
            LinearLayout.LayoutParams params = new LayoutParams(InfoView.WIDTH, InfoView.HEIGHT);
            infoView = new InfoView(context);
            addView(infoView, params);
        }
        {
            LinearLayout.LayoutParams params = new LayoutParams(ADView.WIDTH, ADView.HEIGHT);
            params.leftMargin = UI.div(20);
            adView = new ADView(context);
            addView(adView, params);
        }
    }

    public void updateBadge(int level) {
        infoView.updateBadge(level);
    }

    public void setValue1(final int value1) {
        infoView.setValue1(value1);
    }

    public void setValue2(final int value2) {
        infoView.setValue2(value2);
    }

    public void setDefeat(int value) {
        infoView.setDefeat(value);
    }

    public void setThisCoins(int coins) {
        infoView.setThisCoins(coins);
    }

    public void setRank(int rank, int rankdiff) {
        infoView.setRank(rank, rankdiff);
    }

    public void setTips1(String toast) {
        infoView.setTips1(toast);
    }

    public void updateAD(List<SettlementView.ADViewData> data) {
        adView.updateAD(data);
    }

    public void setShareOnclickListener(OnClickListener listener) {
        infoView.setShareOnclickListener(listener);
    }
}