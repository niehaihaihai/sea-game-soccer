package com.coocaa.ie.games.wc2018.pages.resurgence;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.coocaa.ie.BuildConfig;
import com.coocaa.ie.R;
import com.coocaa.ie.core.system.vc.VoiceMonitor;
import com.coocaa.ie.core.system.vc.VoiceObject;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.launcher.impl.WC2018GameComponentController;
import com.coocaa.ie.games.wc2018.pages.basedialog.CommonAnswerDialog;
import com.coocaa.ie.games.wc2018.pages.basedialog.DialogResConfig;
import com.skyworth.util.imageloader.ImageLoader;
import com.skyworth.util.imageloader.fresco.CoocaaFresco;
import com.tianci.media.api.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class ReviveDialog extends CommonAnswerDialog implements VoiceMonitor {

    private WC2018Game.WC2018GameComponent.Score score;
    private final String VOICE_APP_PKG = "com.skyworth.lafite.srtnj.speechserver";
    private final String TAG = "YYMT";
    private Context mContext;
    private final static int REVIVE_DIALOG_SHOW_TOAST = 0x01;
    private MyHandler handler;
    private String currentVoiceNum;

    public ReviveDialog(Context context, WC2018Game.WC2018GameComponent.Score score) {
        super(context,
                context.getResources().getString(R.string.wc2018_revive_dialog_how_use_voice),
                context.getResources().getString(R.string.wc2018_revive_dialog_next_time),
                "", score, context.getResources().getString(R.string.wc2018_revive_dialog_name)
        );
        DialogResConfig.initViewConfig(WC2018GameController.getController().getGameName());

        this.mContext = context;
        this.score = score;
        handler = new MyHandler(this);
        initBtnUIAndBg();
        currentVoiceNum = DialogResConfig.resConfig().voiceTips.voiceNum;
    }

    private void initBtnUIAndBg() {
        View bgView = ImageLoader.getLoader().getView(mContext);
        bgView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        dialogContent.addView(bgView);
        ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(mContext, DialogResConfig.resConfig().reviveViewBg))).into(bgView);
        setBtnBgResource(DialogResConfig.resConfig().buttonReviveUnFocuse, DialogResConfig.resConfig().buttonReviveWrong, DialogResConfig.resConfig().buttonReviveFocuse);
        root.setBackgroundColor(mContext.getResources().getColor(DialogResConfig.resConfig().reviveViewBgColor));
        setVoiceTipsRes(DialogResConfig.resConfig().voiceTips.tipsRes);
    }

    @Override
    public void onFirstButtonClick() {
        super.onFirstButtonClick();
        Log.d(TAG, "ReviveDialog first btn click" + getFirstBtnState());
        if (!getFirstBtnState()) {//正确的按钮状态
            int voiceAppVersion = getVoiceAppVersion();
            Log.d(TAG, "VoiceAppVersion:" + voiceAppVersion);
            if (voiceAppVersion <= 400042) {//语音版本过低
                updateBtn1WrongState(mContext.getResources().getString(R.string.wc2018_revive_dialog_goto_update_voice_app), mContext.getResources().getString(R.string.wc2018_revive_dialog_remind_update_voice));
            } else {//语音版本正常，可以跳转到帮助页面
                Log.d(TAG, "voice app version is ok jump to help");
                WC2018GameController.getController().getGame().getWC2018GameComponent().showVCHelper();
            }
        } else {//错误的按钮状态，跳转到语音教程
            Log.d(TAG, "wrong state jump to help");
            dismiss();
            WC2018GameController.getController().getGame().getWC2018GameComponent().showVCHelper();
        }

    }


    @Override
    public void onSecondButtonClick() {
        super.onSecondButtonClick();
        Log.d(TAG, "ReviveDialog second btn click");
        dismiss();
        WC2018GameComponentController.getController().handleSettlement(score);
    }

    @Override
    public void show() {
        Log.d(TAG, "Register voice receiver--");
        WC2018GameController.getController().getGame().getWC2018GameComponent().registerVCMonitor(this);
        super.show();
    }

    @Override
    public void dismiss() {
        WC2018GameController.getController().getGame().getWC2018GameComponent().unRegisterVCMonitor(this);
        super.dismiss();
    }

    private void showReviveToast() {
        Log.d(TAG, "show Revive Toast--");
        Toast toast = new Toast(mContext);
        toast.setDuration(Toast.LENGTH_SHORT);

        ImageView reviveView = new ImageView(mContext);
        ViewGroup.LayoutParams rParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        reviveView.setLayoutParams(rParams);
        reviveView.setBackgroundResource(DialogResConfig.resConfig().reviveSuccess);
        toast.setGravity(DialogResConfig.resConfig().reviveToastGravity, 0, DialogResConfig.resConfig().reviveToastY);

        toast.setView(reviveView);
        toast.show();
    }

    private int getVoiceAppVersion() {
        int version = -1;
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(VOICE_APP_PKG, 0);
            version = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    @Override
    public void onReceive(VoiceObject obj) {
        Log.d(TAG, "currentVoiceNum--" + currentVoiceNum + "##onVoiceUpdateValue--" + obj);
        try {
            if (obj != null && obj.command != null && obj.detail != null && obj.command.equals("27")) {
                if (currentVoiceNum.equals(obj.detail.parameter)) {
                    revive();
                } else {
                    Log.d(TAG, "Other voice --" + obj.detail.parameter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void revive() {
        submitReviveEvent();
        dismiss();
        handler.sendEmptyMessage(REVIVE_DIALOG_SHOW_TOAST);
        WC2018GameComponentController.getController().continueGame();
    }

    static class MyHandler extends Handler {

        private WeakReference<ReviveDialog> refs;

        public MyHandler(ReviveDialog ref) {
            refs = new WeakReference<ReviveDialog>(ref);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REVIVE_DIALOG_SHOW_TOAST:
                    refs.get().showReviveToast();
                    break;
            }
        }

    }


    public void submitReviveEvent() {
        Map<String, String> params = new HashMap<>();
        params.put("game_time", score.duration + "");
        CommonDataer.submit(mContext, "revive_event", params);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG, "ReviveDialog KEYCDE BACK ");
            dismiss();
            WC2018GameComponentController.getController().handleSettlement(score);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (BuildConfig.DEBUG)
                revive();
        }
        return super.onKeyDown(keyCode, event);
    }
}
