package com.coocaa.ie.games.wc2018.pages.settlement.m;

import android.content.Context;

import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.http.wc2018.univers.Adinfo;

import java.util.List;

public interface SettlementModel {
    interface Callback<T> {
        void onCallback(T data);
    }

    class SettlementInfo {
        public int thisScore;
        public int thisCoins;
        public int rank, rankdiff;
        public int ticket;
        public String shareUrl;
        public String tips;
        public String toast;
    }

    class DefeatAndLevel {
        public int defeat, level;
    }


    void create(Context context);

    DefeatAndLevel calculateDefeatAndLevel(int score);

    void getSettlementInfo(WC2018Game.WC2018GameComponent.Score score, int defeat, Callback<SettlementInfo> callback);

    void getADInfo(Callback<List<Adinfo>> callback);

    void destroy();
}
