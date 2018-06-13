package com.coocaa.ie.games.wc2018.pages.settlement.p;

import android.app.Dialog;

import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.pages.settlement.m.SettlementModel;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;

public interface SettlementPresenter extends WC2018Game.WC2018GameCallback {

    void create(Dialog dialog, SettlementView view, SettlementModel model, WC2018Game.WC2018GameComponent.Score score);

    void destroy();

    void start();

    void playAgain();

    void share();

    void exit();
}
