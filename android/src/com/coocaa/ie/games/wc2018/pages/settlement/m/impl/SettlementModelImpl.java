package com.coocaa.ie.games.wc2018.pages.settlement.m.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.badlogic.gdx.Gdx;
import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.ie.R;
import com.coocaa.ie.core.utils.MathRandom;
import com.coocaa.ie.core.utils.thread.CCThread;
import com.coocaa.ie.core.web.call.HttpCall;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.pages.settlement.m.SettlementModel;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;
import com.coocaa.ie.http.base.HttpCallBack;
import com.coocaa.ie.http.wc2018.univers.Adinfo;
import com.coocaa.ie.http.wc2018.univers.AdinfoHttpMethod;
import com.skyworth.framework.skysdk.ccos.CcosProperty;
import com.skyworth.framework.skysdk.util.MD5;
import com.tianci.user.data.AccountUtils;
import com.tianci.user.data.UserCmdDefine;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettlementModelImpl implements SettlementModel {
    public static class CanaryInfo implements Serializable {
        //        int	当前排名
        public int ranking;
        // long	本轮酷币
        public int coins;
        //long	本次游戏所得金币
        public int gameCoins;
    }

    public static class PartTime implements Serializable {
        //Integer	-1表示不在游戏时间段 0表示在游戏时间段
        public int state;
        //String	下一个时间段开始时间 时间毫秒值
        public String beginTime;
        //String	下一个时间段结束时间 时间毫秒值
        public String endTime;
    }

    public static class SSData implements Serializable {
        //还差xx球可得xx币
        public String toast;
        //Integer	当时游戏段 剩余游戏次数
        public int overNumber;
        //Integer	该时间段总的进球数
        public int allPartNumber;
        //Integer	当前答对题目的数量
        public int operateNumber;
        //Integer	击败多少人百分比 例如返回为60 就是 60%
//        public int rankPercent;
        //String	系统时间 毫秒值
        public long sysTime;
        //String	分享战绩url
        public String shareUrl;
        //        Integer	已经复活的次数
        public int reliveNumber;
        //Object	酷币信息和排名 （等待和财哥确认）
        public CanaryInfo canaryInfo;

        public PartTime nowPartTime;
        public PartTime nextPartTime;
    }

    private static final String HTTP_API = "%s/v2/lottery/%s/%s/submitData";

    private static final String generateToken(String openId,
                                              String cModel,
                                              String cChip,
                                              String lotteryCode,
                                              int operateNumber,
                                              long operateTime,
                                              String id,
                                              String name) {
        //        "openId=&cModel=&cChip=&lotteryCode=&operateNumber=&operateTime=&{id}&shooter"
        String _openId = "openId=" + openId;
        String _cModel = "cModel=" + cModel;
        String _cChip = "cChip=" + cChip;
        String _lotteryCode = "lotteryCode=" + lotteryCode;
        String _operateNumber = "operateNumber=" + operateNumber;
        String _operateTime = "operateTime=" + operateTime;
        String params = _openId + "&"
                + _cModel + "&"
                + _cChip + "&"
                + _lotteryCode + "&"
                + _operateNumber + "&"
                + _operateTime + "&"
                + id + "&"
                + name;
        Gdx.app.log("Sett", "pp:" + params);
        String token = MD5.md5s(params);
        Gdx.app.log("Sett", "token:" + token);
        return token;
    }

    private static final String generateParams(String openId,
                                               int operateNumber,
                                               long operateTime,
                                               String relived) {
        String id = WC2018GameController.getController().getGameId();
        String name = WC2018GameController.getController().getGameName();
        String code = WC2018GameController.getController().getCode();
        String model = CcosProperty.getCcosDeviceInfo().skytype;
        String chip = CcosProperty.getCcosDeviceInfo().skymodel;
        String _lotteryCode = "lotteryCode=" + code;
        String _operateNumber = "operateNumber=" + operateNumber;
        String _operateTime = "operateTime=" + operateTime;
        String _relived = "relived=" + relived;
        String _id = "id=" + id;
        String _token = "token=" + generateToken(openId, model, chip, code, operateNumber, operateTime, id, name);

        String params = "?" + _lotteryCode + "&"
                + _operateNumber + "&"
                + _operateTime + "&"
                + _relived + "&"
                + _id + "&"
                + _token;
        return params;
    }

    private static String generateShareUrl(String url, int score, int defeat) {
        String activeKey = WC2018GameController.getController().getGameName();
        String avatar = "";
        String name = "";
        String openId = "";
        try {
            Map<String, String> userInfo = CCConnecterManager.getManager().getCCConnecter().getAccoutInfomation();
            if (userInfo != null) {
                avatar = AccountUtils.getIconUrl(userInfo);
                name = AccountUtils.getAccountValue(userInfo, UserCmdDefine.UserKeyDefine.KEY_ACCOUNT_NICK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            openId = CCConnecterManager.getManager().getCCConnecter().getOpenID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //activeKey=&avatar=&name=&score=&defeat=
        String params = "activeKey=" + activeKey
                + "&avatar=" + avatar
                + "&name=" + name
                + "&score=" + score
                + "&defeat=" + defeat
                + "&openId=" + openId;
        String _url = url + "?" + params + "&token=" + MD5.md5s(params);
        return _url;
    }

    private ExecutorService executor;

    private static final String SP_NAME = "wc2018_settlement";
    private static final String SP_RANK_KEY = "rank_";
    private AdinfoHttpMethod adinfoHttpMethod;
    private SharedPreferences sp;
    private Context context;

    @Override
    public void create(Context context) {
        this.context = context;
        executor = Executors.newFixedThreadPool(2,
                CCThread.threadFactory
        );
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        adinfoHttpMethod = new AdinfoHttpMethod(context);
    }

    @Override
    public DefeatAndLevel calculateDefeatAndLevel(int score) {
        DefeatAndLevel dl = new DefeatAndLevel();
        if (WC2018GameController.getController().getGameName().equals(WC2018GameController.GAME_PENALTY)) {
            if (score >= 27)
                dl.defeat = MathRandom.random(98, 99);
            else if (score >= 25)
                dl.defeat = MathRandom.random(94, 97);
            else if (score >= 23)
                dl.defeat = MathRandom.random(89, 93);
            else if (score >= 21)
                dl.defeat = MathRandom.random(83, 88);
            else if (score >= 19)
                dl.defeat = MathRandom.random(77, 82);
            else if (score >= 17)
                dl.defeat = MathRandom.random(71, 76);
            else if (score >= 15)
                dl.defeat = MathRandom.random(64, 70);
            else if (score >= 13)
                dl.defeat = MathRandom.random(54, 63);
            else if (score >= 11)
                dl.defeat = MathRandom.random(45, 53);
            else if (score >= 9)
                dl.defeat = MathRandom.random(36, 44);
            else if (score >= 7)
                dl.defeat = MathRandom.random(24, 35);
            else if (score >= 5)
                dl.defeat = MathRandom.random(12, 23);
            else if (score >= 3)
                dl.defeat = MathRandom.random(5, 11);
            else if (score >= 1)
                dl.defeat = MathRandom.random(1, 4);
            else
                dl.defeat = 0;


            if (score > 25)
                dl.level = 1;
            else if (score >= 21)
                dl.level = 2;
            else if (score >= 16)
                dl.level = 3;
            else if (score >= 11)
                dl.level = 4;
            else if (score >= 6)
                dl.level = 5;
            else
                dl.level = 6;
        } else {
            if (score >= 25)
                dl.defeat = MathRandom.random(97, 99);
            else if (score >= 23)
                dl.defeat = MathRandom.random(91, 96);
            else if (score >= 21)
                dl.defeat = MathRandom.random(85, 90);
            else if (score >= 19)
                dl.defeat = MathRandom.random(79, 84);
            else if (score >= 17)
                dl.defeat = MathRandom.random(73, 78);
            else if (score >= 15)
                dl.defeat = MathRandom.random(64, 72);
            else if (score >= 13)
                dl.defeat = MathRandom.random(57, 63);
            else if (score >= 11)
                dl.defeat = MathRandom.random(51, 56);
            else if (score >= 9)
                dl.defeat = MathRandom.random(41, 50);
            else if (score >= 7)
                dl.defeat = MathRandom.random(25, 40);
            else if (score >= 5)
                dl.defeat = MathRandom.random(16, 24);
            else if (score >= 3)
                dl.defeat = MathRandom.random(6, 15);
            else if (score >= 1)
                dl.defeat = MathRandom.random(1, 5);
            else
                dl.defeat = 0;

            if (score > 25)
                dl.level = 1;
            else if (score >= 20)
                dl.level = 2;
            else if (score >= 15)
                dl.level = 3;
            else if (score >= 10)
                dl.level = 4;
            else if (score >= 4)
                dl.level = 5;
            else
                dl.level = 6;
        }
        return dl;
    }

    private static final int RETRY_TIMES = 5;

    @Override
    public void getSettlementInfo(final WC2018Game.WC2018GameComponent.Score score, final int defeat, final Callback<SettlementInfo> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (false) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SettlementInfo info = new SettlementInfo();
//                    info.coins = data.canaryInfo.gameCoins;//score.coins;
//                    info.score = data.operateNumber;//score.score;
                    info.thisCoins = score.coins + (int) (score.coins * Math.random()) + 10;
                    info.thisScore = score.score + (int) (score.score * Math.random()) + 3;
//                    info.defeat = data.rankPercent;// (int) (Math.random() * 50) + 50;
                    info.rank = (int) (Math.random() * 10000);
                    info.rankdiff = (int) ((0.5f - Math.random()) * 2000);
                    info.ticket = (int) (Math.random() * 29) + 1;
                    info.shareUrl = generateShareUrl("https://www.baidu.com", score.score, defeat);// "https://www.baidu.com";
//                    WC2018GameController.getController().getGame().getWC2018GameComponent().setRevivableValue(data.reliveNumber);
//                    if (info.rank >= 0)
//                        sp.edit().putInt(rankKey, info.rank).commit();

//                    SimpleDateFormat st = new SimpleDateFormat("MM.dd HH:mm");
//                    String d = st.format(1527841848000L);
//
//                    SimpleDateFormat end = new SimpleDateFormat("HH:mm");
//                    d += "-" + end.format(1527843648000L);
//                    info.tips = context.getResources().getString(R.string.wc2018_answer_settlement_main_tips, d);
                    callback.onCallback(info);
                } else {
                    String openId = null;
                    try {
                        openId = CCConnecterManager.getManager().getCCConnecter().getOpenID();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(openId)) {
                        callback.onCallback(null);
                        return;
                    }
                    Gdx.app.log("Sett", "openId:" + openId);
                    final String rankKey = SP_RANK_KEY + openId.hashCode();

                    String gameId = WC2018GameController.getController().getGameId();
                    String gameName = WC2018GameController.getController().getGameName();
                    String server;
                    server = String.format(HTTP_API, WC2018GameController.getController().getServer(), gameName, gameId);

                    server += generateParams(openId, score.score, score.duration, score.bRelived ? "1" : "0");

                    SettlementInfo info = null;
                    int retry = RETRY_TIMES;
                    while (retry-- > 0) {
                        try {
                            Map<String, String> headers = WC2018GameController.getController().getHeaderLoader().load();

                            Gdx.app.log("Sett", "server:" + server);
                            String body = HttpCall.create(server).withHeaders(headers).call();
                            Gdx.app.log("Sett", "body:" + body);
                            WC2018HttpBaseData bd = WC2018HttpBaseData.parse(body);
                            if (bd.code == 50100) {
                                bd.parseObject(SSData.class);
                                SSData data = bd.getObject();

                                int lastRank = sp.getInt(rankKey, -1);
                                info = new SettlementInfo();
//                    info.coins = data.canaryInfo.gameCoins;//score.coins;
//                    info.score = data.operateNumber;//score.score;
                                info.toast = data.toast;
                                info.thisCoins = data.canaryInfo.coins;////score.coins + (int) (score.coins * Math.random()) + 10;
                                info.thisScore = data.allPartNumber;// score.score + (int) (score.score * Math.random()) + 3;
//                    info.defeat = data.rankPercent;// (int) (Math.random() * 50) + 50;
                                info.rank = data.canaryInfo.ranking;// (int) (Math.random() * 10000);
                                if (lastRank >= 0)
                                    info.rankdiff = info.rank - lastRank;//(int) ((0.5f - Math.random()) * 2000);
                                info.ticket = data.overNumber;// (int) (Math.random() * 100);
                                info.shareUrl = generateShareUrl(data.shareUrl, score.score, defeat);// "https://www.baidu.com";
                                WC2018GameController.getController().getGame().getWC2018GameComponent().setRevivableValue(data.reliveNumber);

                                if (data.nowPartTime.state == -1 || info.ticket == 0) {
                                    if (data.nextPartTime != null) {
                                        SimpleDateFormat st = new SimpleDateFormat("MM.dd HH:mm");
                                        String d = st.format(Long.valueOf(data.nextPartTime.beginTime));

                                        SimpleDateFormat end = new SimpleDateFormat("HH:mm");
                                        d += "-" + end.format(Long.valueOf(data.nextPartTime.endTime));
                                        info.tips = context.getResources().getString(R.string.wc2018_answer_settlement_main_tips, d);
                                    }
                                }

                                if (info.rank >= 0)
                                    sp.edit().putInt(rankKey, info.rank).commit();
                            }
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    callback.onCallback(info);
                }
            }
        });
    }

    @Override
    public void getADInfo(final Callback<List<Adinfo>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                adinfoHttpMethod.getAdInfo(WC2018GameController.getController().getGameId(), "gameEnd", new HttpCallBack<List<Adinfo>>() {
                    @Override
                    public void callback(List<Adinfo> adinfos) {
                        callback.onCallback(adinfos);
                    }

                    @Override
                    public void error(int code) {

                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        executor.shutdown();
    }
}
