package com.coocaa.ie.games.wc2018.pages.settlement.p.impl;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.games.wc2018.WC2018Config;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.launcher.impl.WC2018GameComponentController;
import com.coocaa.ie.games.wc2018.net.INet;
import com.coocaa.ie.games.wc2018.pages.faileddialog.FailedDialog;
import com.coocaa.ie.games.wc2018.pages.settlement.QRCodeDialog;
import com.coocaa.ie.games.wc2018.pages.settlement.m.SettlementModel;
import com.coocaa.ie.games.wc2018.pages.settlement.p.SettlementPresenter;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;
import com.coocaa.ie.http.wc2018.univers.Adinfo;
import com.skyworth.framework.skysdk.util.SkyJSONUtil;
import com.tianci.media.action.SkyAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettlementPresenterImpl implements SettlementPresenter {
    private static final String PAGE_NAME = "结束页";
    private WC2018Game.WC2018GameComponent.Score score;
    private Dialog dialog;
    private Context context;
    private SettlementView view;
    private SettlementModel model;

    private SettlementModel.SettlementInfo info;

    @Override
    public void create(Dialog dialog, SettlementView view, SettlementModel model, WC2018Game.WC2018GameComponent.Score score) {
        this.context = dialog.getContext();
        this.dialog = dialog;
        this.view = view;
        this.model = model;
        this.score = score;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        final SettlementModel.DefeatAndLevel defeatAndLevel = model.calculateDefeatAndLevel(score.score);
//        view.update(score.coins, score.score, defeat);
        model.getSettlementInfo(score, defeatAndLevel.defeat, new SettlementModel.Callback<SettlementModel.SettlementInfo>() {
            @Override
            public void onCallback(SettlementModel.SettlementInfo info) {
                if (info == null) {
                    GameApplicatoin.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new FailedDialog(context) {
                                @Override
                                public void onRefresBtnClick() {
                                    if (!INet.nManager.checkNetworkConnection(context))
                                        return;
                                    start();
                                    dismiss();
                                }
                            }.show();
                            Toast.makeText(context, "unknow error!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    SettlementPresenterImpl.this.info = info;
                    SettlementView.SettlementViewData _data = new SettlementView.SettlementViewData();
                    _data.thisCoins = info.thisCoins;
                    _data.thisScore = info.thisScore;
                    _data.rank = info.rank;
                    _data.rankdiff = info.rankdiff;
                    _data.ticket = info.ticket;
                    _data.toast = info.toast;
                    if (!TextUtils.isEmpty(info.tips)) {
                        _data.tips = info.tips;
                        _data.button1Type = SettlementView.SettlementViewData.BUTTON1_TYPE_CHECK_OTHERS;
                        _data.button1Action = new Runnable() {
                            @Override
                            public void run() {
                                startTaskMain();
                            }
                        };
                    } else {
                        if (_data.ticket > 0) {
                            _data.button1Type = SettlementView.SettlementViewData.BUTTON1_TYPE_PLAY_AGAIN;
                            _data.button1Action = new Runnable() {
                                @Override
                                public void run() {
                                    if (!INet.nManager.checkNetworkConnection(context))
                                        return;
                                    loggerClickEvent("再玩一局");
                                    view.disableButton1();
                                    playAgain();
                                }
                            };
                        } else {
                            _data.button1Type = SettlementView.SettlementViewData.BUTTON1_TYPE_CHECK_OTHERS;
                            _data.button1Action = new Runnable() {
                                @Override
                                public void run() {
                                    startTaskMain();
                                }
                            };
                        }
                    }
                    view.update(score.coins, score.score, defeatAndLevel.level, defeatAndLevel.defeat, _data);

                    {
//                        游戏数据	结束页	当游戏结束时，提交本局游戏数据	游戏数据提交事件	game_data_event	"
//                        1、游戏名称
//                        2、页面名称
//                        3、进球/答题数
//                        4、获得酷币数
//                        5、难度等级
//                        6、备注信息
//                        8、OPEN ID
//                        9、当前所属游戏时段"	"1、game_name
//                        2、page_name
//                        3、game_score
//                        4、game_coin
//                        5、difficulty_level
//                        6、extra
//                        8、OPEN_ID
//                        9、game_time"	"字段取值：
//                        2、页面名称：结束页
//                        3、进球/答题数： 根据不同的游戏，提交本局进球数 或 答题数量
//                        4、获得酷币数：提交本局实际获得的酷币数量
//                        5、难度等级：本局结束时，所到达的游戏难度等级，Tn
//
//                        6、备注信息：
//                        若为射门游戏，则在本字段提交用户本局的射门次数
//                        若为答题游戏，则在本字段提交本局游戏使用语音查答案的次数"
                        Map<String, String> params = new HashMap<>();
                        params.put("page_name", PAGE_NAME);
                        params.put("game_score", String.valueOf(score.score));
                        params.put("game_coin", String.valueOf(score.coins));
                        params.put("extra", String.valueOf(WC2018GameController.getController().getGame().getWC2018GameComponent().getLoggerExtra()));


                        CommonDataer.submit(context, "game_data_event", params);
                    }
                }
            }
        });
        model.getADInfo(new SettlementModel.Callback<List<Adinfo>>() {
            @Override
            public void onCallback(List<Adinfo> data) {
                List<SettlementView.ADViewData> viewData = new ArrayList<>();
                for (final Adinfo info : data) {
                    SettlementView.ADViewData _viewData = new SettlementView.ADViewData();
                    _viewData.source = info.getAdvertImgUrl();
                    try {
//                        String json = SkyJSONUtil.getInstance().compile(info.getOnclick());
                        final SkyAction theAction = (SkyAction) SkyJSONUtil.getInstance().parse(info.getOnclick(), SkyAction.class);
                        _viewData.onclick = new Runnable() {
                            @Override
                            public void run() {
                                loggerClickEvent(info.getAdvertName());
                                SkyAction.execAction(context, theAction);
                            }
                        };
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewData.add(_viewData);
                }
                view.updateAD(viewData);
            }
        });
    }

    private void startTaskMain() {
        if (!INet.nManager.checkNetworkConnection(context))
            return;
        loggerClickEvent("查看其他任务");
//        try {
//            Intent in = new Intent();
//            in.setPackage("com.coocaa.app_browser");
//            in.setAction("coocaa.intent.action.browser.no_trans");
//            in.putExtra("url", WC2018Config.getTaskUrl());
//            if (!(context instanceof Activity))
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        WC2018GameController.getController().getGame().getWC2018GameComponent().loadWebUrl(WC2018Config.getTaskUrl());
    }

    @Override
    public void playAgain() {
        WC2018GameComponentController.getController().reloadGame();
    }

    @Override
    public void share() {
        Gdx.app.log("share", "url:" + info.shareUrl);
        loggerClickEvent("分享战绩");
        if (info != null && !TextUtils.isEmpty(info.shareUrl)) {
            QRCodeDialog qrcode = new QRCodeDialog(context, info.shareUrl);
            qrcode.show();
        }
    }

    @Override
    public void exit() {
        CoocaaIEApplication.quit();
    }

    @Override
    public void onCreate(WC2018Game game) {

    }

    @Override
    public void onLoading(WC2018Game game, int progress) {
        view.updateGameLoading(progress);
    }

    @Override
    public void onError(WC2018Game game, CCAssetManager.CCAssetManagerException exception) {
        GameApplicatoin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new FailedDialog(context) {
                    @Override
                    public void onRefresBtnClick() {
                        WC2018GameComponentController.getController().reloadGame();
                        dismiss();
                    }
                }.show();
            }
        });
    }

    @Override
    public void onLoadingComplete(WC2018Game game) {
        GameApplicatoin.runOnUiThread(1000, new Runnable() {
            @Override
            public void run() {
                WC2018GameComponentController.getController().startGame();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDisposed(WC2018Game game) {
        exit();
    }

    private void loggerClickEvent(String buttonName) {
        Map<String, String> params = new HashMap<>();
        params.put("page_name", PAGE_NAME);
        params.put("button_name", buttonName);
        CommonDataer.submit(context, "game_end_page_click_event", params);
    }
}
