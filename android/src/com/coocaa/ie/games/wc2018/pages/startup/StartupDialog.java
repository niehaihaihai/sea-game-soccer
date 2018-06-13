package com.coocaa.ie.games.wc2018.pages.startup;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.games.wc2018.WC2018Config;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.launcher.impl.WC2018GameComponentController;
import com.coocaa.ie.games.wc2018.net.INet;
import com.coocaa.ie.games.wc2018.pages.WC2018PageBaseDialog;
import com.coocaa.ie.games.wc2018.pages.faileddialog.FailedDialog;
import com.coocaa.ie.http.base.HttpCallBack;
import com.coocaa.ie.http.wc2018.univers.Adinfo;
import com.coocaa.ie.http.wc2018.univers.AdinfoHttpMethod;
import com.coocaa.ie.http.wc2018.univers.GameInitData;
import com.coocaa.ie.http.wc2018.univers.GameInitHttpMethod;
import com.skyworth.framework.skysdk.util.SkyJSONUtil;
import com.skyworth.util.imageloader.ImageLoader;
import com.tianci.media.action.SkyAction;
import com.tianci.user.data.AccountUtils;
import com.tianci.user.data.UserCmdDefine;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.coocaa.ie.games.wc2018.WC2018GameController.GAME_ANSWER;
import static com.coocaa.ie.games.wc2018.WC2018GameController.GAME_PENALTY;
import static com.coocaa.ie.http.wc2018.univers.AdinfoHttpMethod._NET_ERROR;

public class StartupDialog extends WC2018PageBaseDialog implements WC2018Game.WC2018GameCallback {

    public static final String STATUSBAR_USER_CHANGED_ACTION = "com.statusbar.account.changed";
    private FrameLayout rootlayout;
    private Context mContext;
    private String curGame;
    private boolean loadResourceComplete = false;

    private View background;
    private DetailRule detailRule;
    private View ad1, ad2;
    private static final int MSG_REFRESH_BACKGROUNG = 1;
    private static final int MSG_SHOW_USERMSG = 2;
    private static final int MSG_SHOW_CENTER_VIEW = 3;
    private static final int MSG_REFRESH_AD = 4;
    private static final int MSG_REFRESF_USERMSG = 5;
    private static final int MSG_USER_CHANGE = 6;
    private static final int MSG_SHOw_EROOR_DIALOG = 7;
    private static final int MSG_INIT_RULE = 8;

    /**
     * 下一场游戏时间
     */
    private String nextGameTime;

    /**
     * 是否登录
     */
    private boolean hasLogin = false;
    private AccountInfo accountInfo;

    /**
     * 是否在游戏时间段
     */
    private boolean inGameTIme = true;

    /**
     * 是否还有剩余游戏次数
     */
    private boolean hasGameChance = false;
    private int chances;

    /**
     * 游戏已失效
     */
    private boolean gameIsOver = false;
    private boolean gameNotStart = false;

    private LinearLayout centerContent;
    private FrameLayout userContent_parent;

    private MyHandler mHandler;

    private String GAME_partKey = "gameBegin";

    private Music music;

    public StartupDialog(Context context, String game) {
        super(context, "启动页");
        this.mContext = context;
        this.curGame = game;
        this.mHandler = new MyHandler(StartupDialog.this);
        rootlayout = new FrameLayout(context);
        rootlayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootlayout);
        _init();

        if (GAME_PENALTY.equals(WC2018GameController.getController().getGameName())) {
            music = Gdx.audio.newMusic(Gdx.files.internal("id_wc2018_penalty_gamepage_voice_bgm.mp3"));
        } else {
            music = Gdx.audio.newMusic(Gdx.files.internal("games/wc2018/answer/audio/bg.mp3"));
        }
        music.setLooping(true);
        music.play();

        mContext.registerReceiver(broadcastReceiver, new IntentFilter(STATUSBAR_USER_CHANGED_ACTION));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mHandler == null)
                return;
            if (mHandler.hasMessages(MSG_USER_CHANGE))
                mHandler.removeMessages(MSG_USER_CHANGE);
            mHandler.sendEmptyMessageDelayed(MSG_USER_CHANGE, 1000);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (music != null) {
            music.dispose();
        }
        if (mContext != null && broadcastReceiver != null) {
            mContext.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void _init() {
        initBack();

        initRule();

        initGame();
    }

    private void initGame() {

        if (!INet.nManager.checkNetworkConnection(mContext))
            return;

        new Thread(new Runnable() {
            @Override
            public void run() {

                initUserInfo();

                if (!hasLogin && mHandler!=null) {
                    mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                    mHandler.sendEmptyMessage(MSG_SHOW_CENTER_VIEW);
                    return;
                }

                new GameInitHttpMethod(mContext).getGameInitInfo(new HttpCallBack<GameInitData>() {
                    @Override
                    public void callback(GameInitData gameInitData) {
                        if (gameInitData == null) {
                            Log.e("Sea-game", "get null init data!!");
                            return;
                        }

                        WC2018GameController.getController().getGame().getWC2018GameComponent().setRevivableValue(gameInitData.reliveNumber);

                        if (gameInitData.overNumber > 0) {
                            hasGameChance = true;
                            chances = gameInitData.overNumber;
                        } else {
                            hasGameChance = false;
                        }

                        if (gameInitData.nowPartTime != null) {
                            if (gameInitData.nowPartTime.state == 0) {
                                inGameTIme = true;
                            } else {
                                inGameTIme = false;
                            }
                        }

                        if (gameInitData.nextPartTime != null) {
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
                                long time = Long.parseLong(gameInitData.nextPartTime.beginTime);
                                String timeString = simpleDateFormat.format(new Date(time));
                                if (!TextUtils.isEmpty(timeString) && timeString.length() == 2) {
                                    if (gameInitData.nextPartTime.state == 0) {
                                        nextGameTime = timeString;
                                    } else {
                                        nextGameTime = "明天 " + timeString;
                                    }
                                    if(!TextUtils.isEmpty(gameInitData.sysTime) && !TextUtils.isEmpty(gameInitData.nextPartTime.beginTime)) {
                                        try {
                                            startTimer(Long.parseLong(gameInitData.sysTime), Long.parseLong(gameInitData.nextPartTime.beginTime));
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Sea-game", "simpleDateFormat error : " + e.toString());
                                e.printStackTrace();
                            }
                        }

                        if (gameInitData.canaryInfo != null) {
                            accountInfo.rank = gameInitData.canaryInfo.ranking;
                            accountInfo.totalCoins = gameInitData.canaryInfo.totalCoins;
                            accountInfo.coins = gameInitData.canaryInfo.coins;
                        }

                        if(mHandler!=null) {
                            mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                            mHandler.sendEmptyMessageDelayed(MSG_SHOW_CENTER_VIEW, 100);
                            mHandler.sendEmptyMessageDelayed(MSG_REFRESF_USERMSG, 300);
                            mHandler.sendEmptyMessageDelayed(MSG_INIT_RULE, 300);
                        }
                        Log.i("Sea-game", "inGameTIme = " + inGameTIme + "   hasGameChance = " + hasGameChance + "   gameIsOver  = " + gameIsOver + "  nextGameTime = " + nextGameTime);
                        if (!inGameTIme || !hasGameChance || gameIsOver) {//small logo
                            getAdData();
                        }
                    }

                    @Override
                    public void error(int code) {
                        dealError(code);
                        if(mHandler == null)
                            return;
                        if (code == 50003) {
                            gameIsOver = true;
                            mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                            mHandler.sendEmptyMessage(MSG_SHOW_CENTER_VIEW);
                            getAdData();
                        } else if(code == 50004) {
                            hasGameChance = false;
                            mHandler.sendEmptyMessage(MSG_REFRESF_USERMSG);
                            mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                            mHandler.sendEmptyMessage(MSG_SHOW_CENTER_VIEW);
                        } else if(code == 10001 || code == 50001 || code == 10003){
                            inGameTIme = false;
                            mHandler.sendEmptyMessage(MSG_REFRESF_USERMSG);
                            mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                            mHandler.sendEmptyMessage(MSG_SHOW_CENTER_VIEW);
                        } else if(code == 50002) {
                            inGameTIme = false;
                            gameNotStart = true;
                            mHandler.sendEmptyMessage(MSG_REFRESF_USERMSG);
                            mHandler.sendEmptyMessage(MSG_REFRESH_BACKGROUNG);
                            mHandler.sendEmptyMessage(MSG_SHOW_CENTER_VIEW);
                        } else if(code == _NET_ERROR){
                            mHandler.sendEmptyMessage(MSG_SHOw_EROOR_DIALOG);
                        }
                        getAdData();
                    }
                });
            }
        }, "get startpage getInitdata").start();
    }

    private void initUserInfo() {
        try {
            String open_id = CCConnecterManager.getManager().getCCConnecter().getOpenID();
            if(TextUtils.isEmpty(open_id)) {
                Log.i("Sea-game", "getOpenID null not login");
                hasLogin = false;
                return;
            }
            Log.i("Sea-game", "getOpenID suc login : "+open_id);
            Map<String, String> userInfo = CCConnecterManager.getManager().getCCConnecter().getAccoutInfomation();
            if (userInfo != null) {
                accountInfo = new AccountInfo();
                accountInfo.avatar = AccountUtils.getIconUrl(userInfo);
                accountInfo.open_id = AccountUtils.getAccountValue(userInfo, UserCmdDefine.UserKeyDefine.KEY_OPEN_ID);
                accountInfo.nick_name = AccountUtils.getAccountValue(userInfo, UserCmdDefine.UserKeyDefine.KEY_ACCOUNT_NICK);
                hasLogin = true;

                if(mHandler!=null)
                    mHandler.sendEmptyMessage(MSG_REFRESF_USERMSG);

                Log.i("Sea-game", "getAccountInfo : " + accountInfo.avatar + "; " + accountInfo.nick_name);
            } else {
                hasLogin = false;
            }
        } catch (Exception e) {
            Log.i("Sea-game", "get user login error" + e.toString());
            e.printStackTrace();
        }

    }

    private static class MyHandler extends Handler {
        private WeakReference<StartupDialog> weakReferenceStartpage;

        public MyHandler(StartupDialog startupDialog) {
            this.weakReferenceStartpage = new WeakReference<>(startupDialog);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReferenceStartpage != null && weakReferenceStartpage.get() != null) {

                switch (msg.what) {
                    case MSG_REFRESH_BACKGROUNG:
                        weakReferenceStartpage.get().refreshBack();
                        break;
                    case MSG_SHOW_USERMSG:
                        weakReferenceStartpage.get().initUserMsg();
                        break;
                    case MSG_REFRESF_USERMSG:
                        weakReferenceStartpage.get().refreshUserMsg();
                        break;
                    case MSG_SHOW_CENTER_VIEW:
                        weakReferenceStartpage.get().initCenterView();
                        break;
                    case MSG_REFRESH_AD:
                        Adinfo adinfo = (Adinfo) msg.obj;
                        weakReferenceStartpage.get().refreshAD(adinfo.getAdvertName(), adinfo.getAdvertImgUrl(), adinfo.getOnclick());
                        break;
                    case MSG_USER_CHANGE:
                        weakReferenceStartpage.get()._init();
                        break;
                    case MSG_SHOw_EROOR_DIALOG:
                        new FailedDialog(weakReferenceStartpage.get().mContext) {
                            @Override
                            public void onRefresBtnClick() {
                                weakReferenceStartpage.get()._init();
                                dismiss();
                            }
                        }.show();
                        Toast.makeText(weakReferenceStartpage.get().mContext, "unknow error!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }

    private void initCenterView() {
        if (centerContent != null) {
            centerContent.removeAllViews();
        }
        centerContent = new LinearLayout(mContext);
        centerContent.setOrientation(LinearLayout.VERTICAL);
        centerContent.setGravity(Gravity.CENTER_HORIZONTAL);
        FrameLayout.LayoutParams centerContent_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerContent_p.gravity = Gravity.CENTER_HORIZONTAL;
        rootlayout.addView(centerContent, centerContent_p);

        addLogo();
        addGameTip();
        addAd();
        addStartBtn();
        addNoLoginTip();
    }

    private void addNoLoginTip() {
        if (hasLogin)
            return;
        TextView noLoginTip = new TextView(mContext);
        noLoginTip.setTextSize(UI.dpi(30));
        noLoginTip.setGravity(Gravity.CENTER);
        noLoginTip.setTextColor(Color.parseColor("#fdd706"));
        noLoginTip.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_tip_back);
        LinearLayout.LayoutParams noLoginTip_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (GAME_PENALTY.equals(WC2018GameController.getController().getGameName())) {
            noLoginTip_p.topMargin = UI.div(-25);
        } else {
            noLoginTip_p.topMargin = UI.div(15);
        }
        noLoginTip.setText("累计酷币赢现金，单人最高可获1万元");

        centerContent.addView(noLoginTip, noLoginTip_p);
    }

    private FrameLayout startBtnContent_perent;
    private ImageView startLight;
    private boolean startBtnFocus = false;

    private void setStartLightVisible(int visibility) {
        if ((!startBtnFocus && visibility == View.VISIBLE) || startLight == null)
            return;
        startLight.setVisibility(visibility);
    }

    private void addStartBtn() {
        if (!inGameTIme || (hasLogin && !hasGameChance) || gameIsOver) {//small logo
            centerContent.removeView(startBtnContent_perent);
            return;
        }

        startBtnContent_perent = new FrameLayout(mContext);

        LinearLayout startBtnContent = new LinearLayout(mContext);
        if(GAME_ANSWER.equals(WC2018GameController.getController().getGameName()))
            startBtnContent.setPadding(0,UI.div(20),0,0);
        startBtnContent.setOrientation(LinearLayout.HORIZONTAL);
        startBtnContent.setGravity(Gravity.CENTER);
        if (GAME_PENALTY.equals(curGame)) {
            startBtnContent.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_startbtn_back_unfocus);
        } else if (GAME_ANSWER.equals(curGame)) {
            startBtnContent.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_startbtn_back_unfocus);
        }

        LinearLayout.LayoutParams startBtnContent_perent_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (GAME_PENALTY.equals(curGame)) {
            startBtnContent_perent_p.topMargin = UI.div(666);
        } else if (GAME_ANSWER.equals(curGame)) {
            startBtnContent_perent_p.topMargin = UI.div(10);
        }

        startBtnContent.setFocusable(true);
        startBtnContent.setClickable(true);
        startBtnContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                startBtnFocus = hasFocus;
                if (hasFocus) {
                    if (GAME_PENALTY.equals(curGame)) {
                        v.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_startbtn_back_focus);
                    } else if (GAME_ANSWER.equals(curGame)) {
                        v.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_startbtn_back_focus);
                    }
                } else {
                    setStartLightVisible(View.INVISIBLE);
                    if (GAME_PENALTY.equals(curGame)) {
                        v.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_startbtn_back_unfocus);
                    } else if (GAME_ANSWER.equals(curGame)) {
                        v.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_startbtn_back_unfocus);
                    }
                }
            }
        });
        startBtnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!INet.nManager.checkNetworkConnection(mContext))
                    return;
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("game_name", WC2018GameController.getController().getGameName());
                    params.put("page_name", "启动页");
                    params.put("page_status", "");
                    params.put("login_status", String.valueOf(hasLogin));
                    params.put("button_name", hasLogin ? "开始游戏" : "去登陆");
                    params.put("OPEN_ID", accountInfo!=null?accountInfo.open_id:"");
                    params.put("extra", String.valueOf(chances));
                    CommonDataer.submit(mContext, "game_start_page_click_event", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (hasLogin) {
                    WC2018GameController.getController().getGame().loadGame();
                    readyGo();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CCConnecterManager.getManager().getCCConnecter().startAccountSetting();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        startBtnContent_perent.addView(startBtnContent);
        centerContent.addView(startBtnContent_perent, startBtnContent_perent_p);
        startBtnContent.requestFocus();

        View startText = ImageLoader.getLoader().getView(mContext);
        LinearLayout.LayoutParams startText_p = new LinearLayout.LayoutParams(UI.div(209), UI.div(74));
        startBtnContent.addView(startText, startText_p);
        ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_text).into(startText);

        if (hasLogin && chances > 0) {
            View chanceView = ImageLoader.getLoader().getView(mContext);
            LinearLayout.LayoutParams chanceView_p = new LinearLayout.LayoutParams(UI.div(76), UI.div(59));
            startBtnContent.addView(chanceView, chanceView_p);
            if (chances == 1)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance1).into(chanceView);
            else if (chances == 2)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance2).into(chanceView);
            else if (chances == 3)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance3).into(chanceView);
            else if (chances == 4)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance4).into(chanceView);
            else if (chances == 5)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance5).into(chanceView);
            else if (chances == 6)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance6).into(chanceView);
            else if (chances == 7)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance7).into(chanceView);
            else if (chances == 8)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance8).into(chanceView);
            else if (chances == 9)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance9).into(chanceView);
            else if (chances == 10)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance10).into(chanceView);
            else if (chances == 11)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance11).into(chanceView);
            else if (chances == 12)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance12).into(chanceView);
            else if (chances == 13)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance13).into(chanceView);
            else if (chances == 14)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance14).into(chanceView);
            else if (chances == 15)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance15).into(chanceView);
            else if (chances == 16)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance16).into(chanceView);
            else if (chances == 17)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance17).into(chanceView);
            else if (chances == 18)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance18).into(chanceView);
            else if (chances == 19)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance19).into(chanceView);
            else if (chances == 20)
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_start_chance20).into(chanceView);
        }

        if (GAME_PENALTY.equals(WC2018GameController.getController().getGameName()))
            return;
        startLight = new ImageView(mContext);
        setStartLightVisible(View.INVISIBLE);
        startLight.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_startbtn_light);
        FrameLayout.LayoutParams startLight_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        startLight_p.topMargin = UI.div(48);
        startBtnContent_perent.addView(startLight, startLight_p);
        final ObjectAnimator translation = ObjectAnimator.ofFloat(startLight, "translationX", UI.div(60), UI.div(460));
        translation.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        translation.setStartDelay(2000);
        translation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setStartLightVisible(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setStartLightVisible(View.INVISIBLE);
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        translation.setDuration(1000);
        translation.start();
    }

    private StartLoadBar startLoadBar;

    private void readyGo() {
        if (startBtnContent_perent != null) {
            centerContent.removeView(startBtnContent_perent);
        }

        if (loadResourceComplete) {
            startGame();
        } else {
            startLoadBar = new StartLoadBar(mContext);
            LinearLayout.LayoutParams startLoadBar_p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (GAME_PENALTY.equals(curGame)) {
                startLoadBar_p.topMargin = UI.div(715 + 30);
            } else if (GAME_ANSWER.equals(curGame)) {
                startLoadBar_p.topMargin = UI.div(100 + 30);
            }
            centerContent.addView(startLoadBar, startLoadBar_p);
        }

    }

    private void startGame() {
        if(mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        WC2018GameComponentController.getController().startGame();
        stopTimer();
        dismiss();
    }

    private FrameLayout adContent;

    private void addAd() {

        if (!inGameTIme || (hasLogin && !hasGameChance) || gameIsOver) {//small logo

        } else {//big logo
            centerContent.removeView(adContent);
            return;
        }
        adContent = new FrameLayout(mContext);
        LinearLayout.LayoutParams adContent_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adContent_p.topMargin = UI.div(30);
        centerContent.addView(adContent, adContent_p);
        ad1 = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams ad1_p = new FrameLayout.LayoutParams(UI.div(472), UI.div(269));

        ImageView ad1Back = new ImageView(mContext);
        ad1Back.setBackgroundColor(Color.argb(26, 255, 255, 255));
        adContent.addView(ad1Back, ad1_p);

        adContent.addView(ad1, ad1_p);
        ad1.setFocusable(true);
        ad1.setClickable(true);
        ad1.setPadding(UI.div(3), UI.div(3), UI.div(3), UI.div(3));
        ad1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ad1.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_adfocus);
                } else {
                    ad1.setBackgroundResource(0);
                }
            }
        });
        ad1.setClickable(false);

        ad2 = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams ad2_p = new FrameLayout.LayoutParams(UI.div(472), UI.div(269));
        ad2_p.leftMargin = UI.div(472 + 30);
        adContent.addView(ad2, ad2_p);
        ad2.setFocusable(true);
        ad2.setClickable(true);
        ad2.setPadding(UI.div(3), UI.div(3), UI.div(3), UI.div(3));
        ad2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_adfocus);
                } else {
                    v.setBackgroundResource(0);
                }
            }
        });
        ad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WC2018GameController.getController().getGame().getWC2018GameComponent().loadWebUrl( WC2018Config.getTaskUrl());

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("game_name", WC2018GameController.getController().getGameName());
                    params.put("page_name", "启动页");
                    params.put("page_status", "");
                    params.put("login_status", String.valueOf(hasLogin));
                    params.put("button_name", "任务入口");
                    params.put("OPEN_ID", accountInfo.open_id);
                    CommonDataer.submit(mContext, "game_start_page_click_event", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageLoader.getLoader().with(mContext).setScaleType(ImageView.ScaleType.FIT_XY)
                .load(R.drawable.id_wc2018_penalty_startpage_addefault).into(ad2);

        ad1.requestFocus();
    }

    private void refreshAD(final String name, String url, final String action) {
        if(ad1 == null)
            return;
        if (!TextUtils.isEmpty(action)) {
            ad1.setClickable(true);
            ad1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("game_name", WC2018GameController.getController().getGameName());
                        params.put("page_name", "启动页");
                        params.put("page_status", "");
                        params.put("login_status", String.valueOf(hasLogin));
                        params.put("button_name", name);
                        params.put("OPEN_ID", accountInfo.open_id);
                        CommonDataer.submit(mContext, "game_start_page_click_event", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        SkyAction theAction = (SkyAction) SkyJSONUtil.getInstance().parse(action, SkyAction.class);
                        SkyAction.execAction(mContext, theAction);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            ad1.setClickable(false);
        }

        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getLoader().with(mContext).setScaleType(ImageView.ScaleType.FIT_XY)
                    .load(Uri.parse(url)).into(ad1);
        }
    }

    private TextView cannotgameReasonTip;
    private String cannotgameReasonTip_tip = "";

    private void addGameTip() {

        if (!inGameTIme || (hasLogin && !hasGameChance) || gameIsOver) {//small logo

        } else {//big logo
            centerContent.removeView(cannotgameReasonTip);
            return;
        }
        cannotgameReasonTip = new TextView(mContext);
        cannotgameReasonTip.setTextSize(UI.dpi(30));
        cannotgameReasonTip.setGravity(Gravity.CENTER);
        cannotgameReasonTip.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_tip_back);
        LinearLayout.LayoutParams cannotgameReasonTip_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (GAME_PENALTY.equals(curGame)) {
            cannotgameReasonTip_p.topMargin = UI.div(478);
            cannotgameReasonTip.setTextColor(Color.YELLOW);
        } else if (GAME_ANSWER.equals(curGame)) {
            cannotgameReasonTip_p.topMargin = UI.div(15);
            cannotgameReasonTip.setTextColor(Color.RED);
        }

        if (gameIsOver) {
            cannotgameReasonTip_tip = "游戏已结束 去看看别的内容吧";
        } else if (!inGameTIme) {
            if(gameNotStart) {
                cannotgameReasonTip_tip = "游戏尚未开始哦";
            }else {
                cannotgameReasonTip_tip = "当前非游戏时间 下一场游戏时间：" + nextGameTime + "点";
            }
        } else if (!hasGameChance) {
            cannotgameReasonTip_tip = "本时段内游戏机会已经用完 去看看别的吧";
        }
        cannotgameReasonTip.setText(cannotgameReasonTip_tip);
        centerContent.addView(cannotgameReasonTip, cannotgameReasonTip_p);
    }

    private void addLogo() {
        //设置logo
        View logo = ImageLoader.getLoader().getView(mContext);
        LinearLayout.LayoutParams logo_p;

        TextView gameTime = new TextView(mContext);
        gameTime.setTextColor(Color.parseColor("#fdd706"));
        gameTime.setTextSize(UI.dpi(30));
        gameTime.setText("游戏开启时间：6.22~6.28 每日10点-13点、19点-22点");
        LinearLayout.LayoutParams gameTime_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!inGameTIme || (hasLogin && !hasGameChance) || gameIsOver) {//small logo
            if (GAME_ANSWER.equals(curGame)) {
                logo_p = new LinearLayout.LayoutParams(UI.div(1093), UI.div(557));
                centerContent.addView(logo, logo_p);
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_logo).into(logo);
                gameTime_p.topMargin = UI.div(-140);
                centerContent.addView(gameTime, gameTime_p);
            } else if (GAME_PENALTY.equals(curGame)) {
                //TODO penalty no logo
            }
        } else {//big logo
            if (GAME_ANSWER.equals(curGame)) {
                logo_p = new LinearLayout.LayoutParams(UI.div(1323), UI.div(604));
                centerContent.addView(logo, logo_p);
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_logo).into(logo);
                gameTime_p.topMargin = UI.div(-120);
                centerContent.addView(gameTime, gameTime_p);
            } else if (GAME_PENALTY.equals(curGame)) {
                //TODO penalty no logo
            }
        }

    }

    private FrameLayout ruleContent;
    private void initRule() {
        if(ruleContent != null)
            ruleContent.removeAllViews();
        ruleContent = new FrameLayout(mContext);
        FrameLayout.LayoutParams ruleContent_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ruleContent_p.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        ruleContent_p.bottomMargin = UI.div(65);
        rootlayout.addView(ruleContent, ruleContent_p);

        View rule = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams rule_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (GAME_ANSWER.equals(curGame)) {
            if (CoocaaIEApplication.isSupportVC()) {
                rule_p = new FrameLayout.LayoutParams(UI.div(1838), UI.div(136));
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_rule).into(rule);
            }
            else {
                rule_p = new FrameLayout.LayoutParams(UI.div(1838), UI.div(80));
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_rule_no_vc).into(rule);
            }
        } else if (GAME_PENALTY.equals(curGame)) {
            rule_p = new FrameLayout.LayoutParams(UI.div(1271), UI.div(135));
            if (CoocaaIEApplication.isSupportVC()) {
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_rule).into(rule);
            }
            else {
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_rule_no_vc).into(rule);
            }
        }
        ruleContent.addView(rule, rule_p);

        final View rulrBtn = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams rulrBtn_p = new FrameLayout.LayoutParams(UI.div(137), UI.div(45));
        rulrBtn_p.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        if (CoocaaIEApplication.isSupportVC()) {
            rulrBtn_p.bottomMargin = UI.div(30);
        } else {
            rulrBtn_p.bottomMargin = UI.div(15);
        }
        rulrBtn_p.rightMargin = UI.div(30);
        ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_rule_btn_unfocus).into(rulrBtn);
        ruleContent.addView(rulrBtn, rulrBtn_p);
        rulrBtn.setClickable(true);
        rulrBtn.setFocusable(true);
        rulrBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_rule_btn_focus).into(rulrBtn);
                } else {
                    ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_rule_btn_unfocus).into(rulrBtn);
                }
            }
        });
        rulrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startLoadBar != null && startLoadBar.getVisibility() == View.VISIBLE)
                    return;
                if (detailRule.getVisibility() != View.VISIBLE) {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("game_name", WC2018GameController.getController().getGameName());
                        params.put("page_name", "启动页");
                        params.put("page_status", "");
                        params.put("login_status", String.valueOf(hasLogin));
                        params.put("button_name", "游戏规则");
                        params.put("OPEN_ID", accountInfo.open_id);
                        CommonDataer.submit(mContext, "game_start_page_click_event", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    detailRule.bringToFront();
                    detailRule.setVisibility(View.VISIBLE);
                } else {
                    detailRule.setVisibility(View.GONE);
                }
            }
        });

    }

    private View userIcon;
    private TextView userName;
    private TextView rank;
    private TextView coins;

    private void refreshUserMsg() {
        if (userContent_parent == null) {
            initUserMsg();
        }
        if (accountInfo == null)
            return;
        if (!TextUtils.isEmpty(accountInfo.nick_name) && userName != null) {
            userName.setText(accountInfo.nick_name);
        }
        if (!TextUtils.isEmpty(accountInfo.avatar) && userIcon != null) {
            int rat = 5;
            ImageLoader.getLoader().with(mContext).setScaleType(ImageView.ScaleType.FIT_XY)
                    .setRightBottomCorner(rat).setRightTopCorner(rat).setLeftBottomCorner(rat).setLeftTopCorner(rat)
                    .load(Uri.parse(accountInfo.avatar)).into(userIcon);
        }
        if (accountInfo.coins != -1 && coins != null) {
            coins.setText(accountInfo.coins + "");
        }
        if (accountInfo.rank != -1 && rank != null) {
            rank.setText(accountInfo.rank + "");
        }
    }

    private void initUserMsg() {
        if (!hasLogin) {
            rootlayout.removeView(userContent_parent);
            return;
        }
        if (userContent_parent != null) {
            userContent_parent.removeAllViews();
        }

        userContent_parent = new FrameLayout(mContext);
        rootlayout.addView(userContent_parent);

        View userBack = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams userBack_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (GAME_PENALTY.equals(curGame)) {
            userBack_p = new FrameLayout.LayoutParams(UI.div(644), UI.div(85));
            ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_usermsg_back).into(userBack);
        } else if (GAME_ANSWER.equals(curGame)) {
            userBack_p = new FrameLayout.LayoutParams(UI.div(593), UI.div(104));
            ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_usermsg_back).into(userBack);
        }
        userContent_parent.addView(userBack, userBack_p);

        LinearLayout userContent = new LinearLayout(mContext);
        userContent.setOrientation(LinearLayout.HORIZONTAL);
        userContent.setGravity(Gravity.CENTER_VERTICAL);
        if (GAME_PENALTY.equals(curGame)) {
            userContent.setPadding(UI.div(27), UI.div(10), 0, 0);
        } else if (GAME_ANSWER.equals(curGame)) {
            userContent.setPadding(UI.div(15), UI.div(10), 0, UI.div(16));
        }

        FrameLayout.LayoutParams userContent_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        userContent_p.gravity = Gravity.TOP | Gravity.LEFT;
        userContent_parent.addView(userContent, userContent_p);

        userIcon = ImageLoader.getLoader().getView(mContext);
        userIcon.setPadding(UI.div(2), UI.div(2), UI.div(2), UI.div(2));
        FrameLayout.LayoutParams userIcon_p;
        userIcon.setBackgroundResource(R.drawable.id_wc2018_answer_startpage_usermsg_icon_back);
        userIcon_p = new FrameLayout.LayoutParams(UI.div(50), UI.div(50));
        userContent.addView(userIcon, userIcon_p);

        userName = new TextView(mContext);
        userName.setTextSize(UI.dpi(30));
        userName.setTextColor(Color.WHITE);
        userName.setMaxWidth(UI.div(200));
        userName.setEllipsize(TextUtils.TruncateAt.END);
        userName.setTypeface(Typeface.DEFAULT_BOLD);
        userName.setPadding(UI.div(22), 0, 0, 0);
        userName.setSingleLine(true);
        userName.setGravity(Gravity.CENTER_VERTICAL);
        FrameLayout.LayoutParams userName_p = new FrameLayout.LayoutParams(UI.div(200), ViewGroup.LayoutParams.WRAP_CONTENT);
        userContent.addView(userName, userName_p);

        LinearLayout rankContent = new LinearLayout(mContext);
        rankContent.setPadding(UI.div(50), 0, 0, 0);
        rankContent.setOrientation(LinearLayout.VERTICAL);
        rankContent.setGravity(Gravity.CENTER_HORIZONTAL);
        userContent.addView(rankContent);
        TextView rankTitle = new TextView(mContext);
        rankTitle.setText("本轮排名");
        rankTitle.setTextSize(UI.dpi(24));
        rankTitle.setTextColor(Color.WHITE);
        rankTitle.setGravity(Gravity.CENTER);
        rankContent.addView(rankTitle);
        rank = new TextView(mContext);
        rank.setTextSize(UI.dpi(24));
        rank.setTextColor(Color.parseColor("#ffdc3b"));
        rank.setGravity(Gravity.CENTER);
        rankContent.addView(rank);

        LinearLayout coinsContent = new LinearLayout(mContext);
        coinsContent.setOrientation(LinearLayout.VERTICAL);
        coinsContent.setGravity(Gravity.CENTER_HORIZONTAL);
        coinsContent.setPadding(UI.div(38), 0, 0, 0);
        userContent.addView(coinsContent);
        TextView coninsTitle = new TextView(mContext);
        coninsTitle.setText("本轮酷币");
        coninsTitle.setTextSize(UI.dpi(24));
        coninsTitle.setTextColor(Color.WHITE);
        coninsTitle.setGravity(Gravity.CENTER);
        coinsContent.addView(coninsTitle);
        coins = new TextView(mContext);
        coins.setTextSize(UI.dpi(24));
        coins.setTextColor(Color.parseColor("#ffdc3b"));
        coins.setGravity(Gravity.CENTER);
        coinsContent.addView(coins);

        if (GAME_PENALTY.equals(curGame)) {
            rank.setTextColor(Color.parseColor("#fbc901"));
            coins.setTextColor(Color.parseColor("#fbc901"));
        }
    }

    private void initBack() {
        background = ImageLoader.getLoader().getView(mContext);
        FrameLayout.LayoutParams back_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootlayout.addView(background, back_p);

        if (GAME_PENALTY.equals(curGame)) {
            ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_back_big).into(background);
        } else if (GAME_ANSWER.equals(curGame)) {
            ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_back).into(background);
        }

        detailRule = new DetailRule(mContext, WC2018GameController.getController().getGameName());
        detailRule.setVisibility(View.GONE);
        FrameLayout.LayoutParams detailRule_p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootlayout.addView(detailRule, detailRule_p);
    }

    private void refreshBack() {
        if (GAME_PENALTY.equals(curGame)) {
            if (!inGameTIme || (hasLogin && !hasGameChance) || gameIsOver) {
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_back_small).into(background);
            } else {
                ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_penalty_startpage_back_big).into(background);
            }
        } else if (GAME_ANSWER.equals(curGame)) {
            ImageLoader.getLoader().with(mContext).load(R.drawable.id_wc2018_answer_startpage_back).into(background);
        }
    }

    private void getAdData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new AdinfoHttpMethod(mContext).getAdInfo(WC2018GameController.getController().getGameId(), GAME_partKey, new HttpCallBack<List<Adinfo>>() {
                    @Override
                    public void callback(final List<Adinfo> adinfos) {
                        if (adinfos != null && adinfos.get(0) != null && !TextUtils.isEmpty(adinfos.get(0).getAdvertImgUrl())) {
                            if(mHandler!=null) {
                                Message message = mHandler.obtainMessage(MSG_REFRESH_AD);
                                message.obj = adinfos.get(0);
                                mHandler.sendMessage(message);
                            }
                        } else {
                            Log.e("Sea-game", "null ad url");
                        }
                    }

                    @Override
                    public void error(int code) {
                        dealError(code);
                    }
                });
            }
        }, "initstartpage adinfo").start();

    }

    private void dealError(int code) {
        String errMsg = "";
        switch (code) {
            case 50001:
                errMsg = "current game not exist-----";
                break;
            case 50002:
                errMsg = "current game not start-----";
                break;
            case 50003:
                errMsg = "current game is end-----";
                break;
            case 50004:
                errMsg = "not enough game chance -----";
                break;
            case 10007:
                errMsg = "elements error-----";
                break;
        }
        Log.i("Sea-game", "getstartpage adinfo error : " + code + "   errorMsg : " + errMsg);
    }

    private ScheduledThreadPoolExecutor timer;
    private void stopTimer()
    {
        if (timer != null)
        {
            timer.shutdownNow();
            timer = null;
        }
    }

    private long remainTime = 0;
    private void startTimer(long curTime, long startTime)
    {
        Log.i("Sea-game", "start game time dec timer curTime="+curTime+"startTime="+startTime);
        stopTimer();
        remainTime = startTime - curTime;
        if(remainTime <= 0)
            return;
        try
        {
            timer = new ScheduledThreadPoolExecutor(1);
            timer.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    remainTime -= 1000;
                    String remainText = "";
                    int hours = (int) (remainTime / 1000 / 60 / 60);
                    int mins = (int) (remainTime / 1000 / 60);
                    int secs = (int) (remainTime / 1000);
                    if(hours > 0) {
                        remainText = "  (还有"+hours+"小时)";
                    }else if(mins > 0) {
                        remainText = "  (还有"+mins+"分钟)";
                    }else if(secs > 0) {
                        remainText = "  (还有"+secs+"秒)";
                    }else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(cannotgameReasonTip != null && cannotgameReasonTip.getVisibility() == View.VISIBLE) {

                                    if(cannotgameReasonTip_tip.contains("下一场游戏时间")) {
                                        cannotgameReasonTip.setText(cannotgameReasonTip_tip + "  (游戏已开始，请退出页面重新进入)");
                                    }
                                }
                            }
                        });
                        stopTimer();
                        return;
                    }
                    if(mHandler!=null) {
                        final String finalRemainText = remainText;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(cannotgameReasonTip != null && cannotgameReasonTip.getVisibility() == View.VISIBLE) {

                                    if(cannotgameReasonTip_tip.contains("下一场游戏时间")) {
                                        cannotgameReasonTip.setText(cannotgameReasonTip_tip + finalRemainText);
                                    }
                                }
                            }
                        });
                    }
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(WC2018Game game) {
        show();
    }

    @Override
    public void onLoading(WC2018Game game, int progress) {
        if (startLoadBar != null && startLoadBar.getVisibility() == View.VISIBLE) {
            startLoadBar.setProgress(progress);
        }
    }

    @Override
    public void onError(WC2018Game game, CCAssetManager.CCAssetManagerException exception) {
        if(mHandler!=null)
            mHandler.sendEmptyMessage(MSG_SHOw_EROOR_DIALOG);
    }

    @Override
    public void onLoadingComplete(WC2018Game game) {
        loadResourceComplete = true;
        Log.i("Sea-game", "loading resource complete ");
        if (startLoadBar != null && startLoadBar.getVisibility() == View.VISIBLE && mHandler!=null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 1000);
        }
    }

    @Override
    public void onDisposed(WC2018Game game) {
        stopTimer();
        dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ||
                keyCode == KeyEvent.KEYCODE_ESCAPE) {
            if (detailRule != null && detailRule.getVisibility() == View.VISIBLE) {
                detailRule.setVisibility(View.GONE);
                return true;
            }
        }else if(keyCode == KeyEvent.KEYCODE_MENU) {
            if(CoocaaIEApplication.isSupportVC() && detailRule != null && detailRule.getVisibility() == View.VISIBLE) {
                WC2018GameController.getController().getGame().getWC2018GameComponent().showVCHelper();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
