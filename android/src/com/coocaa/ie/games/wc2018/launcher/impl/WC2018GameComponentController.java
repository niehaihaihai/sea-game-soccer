package com.coocaa.ie.games.wc2018.launcher.impl;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.components.webview.WebviewDialog;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.system.vc.VoiceMonitor;
import com.coocaa.ie.core.system.vc.VoiceObject;
import com.coocaa.ie.games.wc2018.WC2018Config;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.pages.quit.QuitDialog;
import com.coocaa.ie.games.wc2018.pages.resurgence.ReviveDialog;
import com.coocaa.ie.games.wc2018.pages.searchdialog.ISearchResult;
import com.coocaa.ie.games.wc2018.pages.settlement.SettlementDialog;
import com.coocaa.ie.games.wc2018.pages.startup.StartupDialog;
import com.coocaa.ie.system.vc.VC;
import com.tianci.media.api.Log;

import java.util.Map;

public class WC2018GameComponentController implements WC2018Game.WC2018GameComponent {
    static WC2018GameComponentController controller;

    public static final WC2018GameComponentController getController() {
        return controller;
    }


    public interface WC2018GameCallbackMonitor {
        void setCallback(WC2018Game.WC2018GameCallback callback);
    }

    private Context mContext;
    private WC2018GameCallbackMonitor monitor;
    private int revivableValue = 0;
    private boolean bReviveGame = false;
    private VC vc = VC.getVC();

    WC2018GameComponentController(Context context, WC2018GameCallbackMonitor monitor) {
        mContext = context;
        this.monitor = monitor;
    }

    @Override
    public void setRevivableValue(int value) {
        revivableValue = value;
    }

    @Override
    public void registerVCMonitor(VoiceMonitor monitor) {
        vc.registerVCMonitor(monitor);
    }

    @Override
    public void unRegisterVCMonitor(VoiceMonitor monitor) {
        vc.unRegisterVCMonitor(monitor);
    }

    @Override
    public void loadWebUrl(String url) {
        try {
            Intent in = new Intent();
            in.setPackage("com.coocaa.app_browser");
            in.setAction("coocaa.intent.action.browser.no_trans");
            in.putExtra("url", url);
            if (!(mContext instanceof Activity))
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(in);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent in = new Intent();
                in.setPackage("com.tianci.movieplatform");
                in.setAction("coocaa.intent.movie.webview");
                in.putExtra("url", url);
                if (!(mContext instanceof Activity))
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void showVCHelper() {
        if (CoocaaIEApplication.isSupportVC()) {
            loadWebUrl(WC2018Config.getVCHelperUrl());
            CommonDataer.submit(mContext, "call_voiice_course_event", null);
        }
//        GameApplicatoin.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                WebviewDialog dialog = new WebviewDialog(mContext, WC2018Config.getVCHelperUrl());
//                dialog.show();
//            }
//        });
    }

    @Override
    public void showVCAnswer(String id, String question, String answer) {
        Gdx.app.log("VC", "showVCAnswer id:" + id + "  question:" + question + "  answer:" + answer);
        ISearchResult.INSTANCE.startSearch(mContext, id, question, answer);
    }

    @Override
    public void hideVCAnswer() {
        ISearchResult.INSTANCE.hideSearchDialg();
    }

    @Override
    public void handleStartGame(final String game) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissQuitDialog();
                StartupDialog startupDialog = new StartupDialog(mContext, game);
                monitor.setCallback(startupDialog);
                startupDialog.show();
            }
        });
    }

    @Override
    public void handleGameover(final Score score, final Runnable runnable) {
        CoocaaIEApplication.runOnUiThread(2000, new Runnable() {
            @Override
            public void run() {
                if (revivableValue > 0 && CoocaaIEApplication.isSupportVC()) {
                    handleRevive(score);
                } else {
                    handleSettlement(score);
                }
                if (runnable != null)
                    runnable.run();
            }
        });
    }

    public void handleRevive(final Score score) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissQuitDialog();
                score.bRelived = bReviveGame;
                ReviveDialog reviveDialog = new ReviveDialog(mContext, score);
                reviveDialog.show();
            }
        });
    }

    public void handleSettlement(final Score score) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissQuitDialog();
                score.bRelived = bReviveGame;
                score.duration = WC2018GameController.getController().getGame().getPlayDuration() / 1000;//单位秒
                SettlementDialog settlementDialog = new SettlementDialog(mContext, score);
                monitor.setCallback(settlementDialog);
                settlementDialog.show();
            }
        });
    }

    QuitDialog quitDialog = null;

    private synchronized void dismissQuitDialog() {
        try {
            if (quitDialog != null) {
                quitDialog.dismiss();
                quitDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleQuitGame() {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissQuitDialog();
                quitDialog = new QuitDialog(mContext, new Runnable() {
                    @Override
                    public void run() {
                        quitDialog = null;
                    }
                });
                quitDialog.show();
            }
        });
    }

    private int loggerExtra = 0;

    @Override
    public void startGame() {
        loggerExtra = 0;
        bReviveGame = false;
        WC2018GameController.getController().getGame().startGame();
    }

    @Override
    public void reloadGame() {
        loggerExtra = 0;
        bReviveGame = false;
        WC2018GameController.getController().getGame().reloadGame();
    }

    @Override
    public void continueGame() {
        revivableValue--;
        bReviveGame = true;
        WC2018GameController.getController().getGame().continueGame();
    }

    @Override
    public void updateLoggerExtra(int value) {
        loggerExtra = value;
    }

    @Override
    public int getLoggerExtra() {
        return loggerExtra;
    }

    @Override
    public void logger(String eventID, Map<String, String> params) {
        CommonDataer.submit(mContext, eventID, params);
    }

    @Override
    public boolean isSupportVC() {
        return CoocaaIEApplication.isSupportVC();
    }
}
