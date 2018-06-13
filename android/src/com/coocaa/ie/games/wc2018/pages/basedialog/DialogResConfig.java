package com.coocaa.ie.games.wc2018.pages.basedialog;

import android.content.Context;
import android.view.Gravity;

import com.coocaa.ie.BuildConfig;
import com.coocaa.ie.R;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.tianci.media.api.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by YYM on 2018/5/31.
 */

public class DialogResConfig {
    public int reviveViewBg = -1;
    public int quiteViewBg = -1;

    public int reviveViewBgColor = -1;

    public int buttonReviveUnFocuse = -1;
    public int buttonReviveFocuse = -1;
    public int buttonReviveWrong = -1;

    public int reviveSuccess = -1;
    public int reviveToastGravity = -1;
    public int reviveToastY = -1;
    private static DialogResConfig resConfig;
    public VoiceTips voiceTips;
    public static int index = 0;


    private static DialogResConfig initAnswerResConfig(){
        DialogResConfig config = new DialogResConfig();
        config.reviveViewBg = R.drawable.id_wc2018_answer_revive_dialog_title;
        config.quiteViewBg = R.drawable.id_wc2018_answer_quite_dialog_exit_title;
        config.reviveViewBgColor = R.color.wc_revive_dialog_bg;

        config.buttonReviveUnFocuse = R.drawable.id_wc2018_dialog_btn_bg;
        config.buttonReviveFocuse = R.drawable.id_wc2018_dialog_btn_focus;
        config.buttonReviveWrong = R.drawable.id_wc2018_dialog_btn_wrong_bg;
        config.reviveSuccess = R.drawable.id_wc2018_answer_revive_dialog_success;
        config.reviveToastGravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        config.reviveToastY = UI.div(114);
//        if(BuildConfig.DEBUG){
//            config.voiceTips = getVoiceTips();
//        }else {
        config.voiceTips = getCurrentAnswerVoicTips();
//        }
        return config;
    }

    private static DialogResConfig initPenaltyResConfig() {
        DialogResConfig config = new DialogResConfig();
        config.reviveViewBg = R.drawable.id_wc2018_penalty_revive_dialog_title;
        config.quiteViewBg = R.drawable.id_wc2018_penalty_quite_dialog_exit_title;

        config.reviveViewBgColor = R.color.wc_penalty_dialog_bg;

        config.buttonReviveUnFocuse = R.drawable.id_wc2018_penalty_btn_unfocus_bg;
        config.buttonReviveFocuse = R.drawable.id_wc2018_penalty_btn_focus_bg;
        config.buttonReviveWrong = R.drawable.id_wc2018_penalty_btn_wrong_bg;
        config.reviveSuccess = R.drawable.id_wc2018_penalty_revive_dialog_success;

        config.reviveToastGravity = Gravity.CENTER;
        config.reviveToastY = 0;
//        if(BuildConfig.DEBUG){
//            config.voiceTips = getVoiceTips();
//        }else {
            config.voiceTips = getCurrentPenaltyVoicTips();
//        }
        return config;
    }

    public static final void initViewConfig(String game) {

        if (game.equals(WC2018GameController.GAME_ANSWER)) {
            resConfig = initAnswerResConfig();
        } else {
            resConfig = initPenaltyResConfig();
        }
    }

    public static final DialogResConfig resConfig(){
        return resConfig;
    }

    public static VoiceTips getCurrentAnswerVoicTips(){
        List<VoiceTips> voiceTipsList  = new ArrayList<>();
        VoiceTips iRevive = new VoiceTips();
        iRevive.startTime = getSetTimestamp(22,0,0, 0);
        iRevive.endTime = getSetTimestamp(23, 23, 59, 59);
        iRevive.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_i_revive);
        iRevive.voiceNum = "3";
        iRevive.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_i_revive;

        VoiceTips getCoin = new VoiceTips();
        getCoin.startTime = getSetTimestamp(24,0,0, 0);
        getCoin.endTime = getSetTimestamp(25, 23, 59, 59);
        getCoin.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_answer_voice_revive_answer_coin);
        getCoin.voiceNum = "4";
        getCoin.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_answer_coin;

        VoiceTips getMoney = new VoiceTips();
        getMoney.startTime = getSetTimestamp(26,0,0, 0);
        getMoney.endTime = getSetTimestamp(28, 23, 59, 59);
        getMoney.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_answer_voice_revive_get_money);
        getMoney.voiceNum = "5";
        getMoney.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_get_money;
        voiceTipsList.add(iRevive);
        voiceTipsList.add(getCoin);
        voiceTipsList.add(getMoney);
        Long currentTime = System.currentTimeMillis();
        for(VoiceTips tips:voiceTipsList){
            Log.d("YYMT", "Answer TipsName--"+tips.tipsStr+" startTime:"+tips.startTime+" endTime:"+tips.endTime+" currentTime:"+currentTime);
            if(currentTime>= tips.startTime&& currentTime <= tips.endTime){
                return tips;
            }
        }
        Log.d("YYMT", "Answer NOT IN RANG TIME ");
        return voiceTipsList.get(0);
    }

    public static VoiceTips getCurrentPenaltyVoicTips(){
        List<VoiceTips> voiceTipsList  = new ArrayList<>();
        VoiceTips loveworldcup = new VoiceTips();
        loveworldcup.startTime = getSetTimestamp(14,0,0, 0);
        loveworldcup.endTime = getSetTimestamp(15, 23, 59, 59);
        loveworldcup.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_world_cup);
        loveworldcup.voiceNum = "1";
        loveworldcup.tipsRes = R.drawable.id_wc2018_penalty_revive_love_world_cup;

        VoiceTips iRevive = new VoiceTips();
        iRevive.startTime = getSetTimestamp(16,0,0, 0);
        iRevive.endTime = getSetTimestamp(18, 23, 59, 59);
        iRevive.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_i_revive);
        iRevive.voiceNum = "3";
        iRevive.tipsRes = R.drawable.id_wc2018_penalty_i_revive;

        VoiceTips getCoin= new VoiceTips();
        getCoin.startTime = getSetTimestamp(19,0,0, 0);
        getCoin.endTime = getSetTimestamp(21, 23, 59, 59);
        getCoin.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_get_coin);
        getCoin.voiceNum = "2";
        getCoin.tipsRes = R.drawable.id_wc2018_penalty_revive_get_coocaa_coin;
        voiceTipsList.add(loveworldcup);
        voiceTipsList.add(iRevive);
        voiceTipsList.add(getCoin);
        Long currentTime = System.currentTimeMillis();
        for(VoiceTips tips:voiceTipsList){
            Log.d("YYMT", "penalty TipsName--"+tips.tipsStr+" startTime:"+tips.startTime+" endTime:"+tips.endTime+" currentTime:"+currentTime);
            if(currentTime>= tips.startTime&& currentTime <= tips.endTime){
                return tips;
            }
        }
        Log.d("YYMT", "Penalty NOT IN RANG TIME ");
        return voiceTipsList.get(0);
    }

    public static VoiceTips getVoiceTips(){
        Log.d("YYMT", "VoiceTips index--"+index);
        List<VoiceTips> voiceTipsList  = new ArrayList<>();
        VoiceTips iRevive = new VoiceTips();
        iRevive.startTime = getSetTimestamp(14,0,0, 0);
        iRevive.endTime = getSetTimestamp(15, 23, 59, 59);
        iRevive.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_i_revive);
        iRevive.voiceNum = "3";
        iRevive.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_i_revive;

        VoiceTips getCoin = new VoiceTips();
        getCoin.startTime = getSetTimestamp(16,0,0, 0);
        getCoin.endTime = getSetTimestamp(17, 23, 59, 59);
        getCoin.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_answer_voice_revive_answer_coin);
        getCoin.voiceNum = "4";
        getCoin.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_answer_coin;

        VoiceTips getMoney = new VoiceTips();
        getMoney.startTime = getSetTimestamp(18,0,0, 0);
        getMoney.endTime = getSetTimestamp(20, 23, 59, 59);
        getMoney.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_answer_voice_revive_get_money);
        getMoney.voiceNum = "5";
        getMoney.tipsRes = R.drawable.id_wc2018_answer_revive_voice_tips_get_money;
        voiceTipsList.add(iRevive);
        voiceTipsList.add(getCoin);
        voiceTipsList.add(getMoney);

        VoiceTips loveworldcup = new VoiceTips();
        loveworldcup.startTime = getSetTimestamp(14,0,0,0);
        loveworldcup.endTime = getSetTimestamp(15, 23, 59,59);
        loveworldcup.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_world_cup);
        loveworldcup.voiceNum = "1";
        loveworldcup.tipsRes = R.drawable.id_wc2018_penalty_revive_love_world_cup;

        VoiceTips siRevive = new VoiceTips();
        iRevive.startTime = getSetTimestamp(16,0,0,0);
        iRevive.endTime = getSetTimestamp(18, 23, 59, 59);
        iRevive.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_i_revive);
        iRevive.voiceNum = "3";
        iRevive.tipsRes = R.drawable.id_wc2018_penalty_i_revive;

        VoiceTips sgetCoin= new VoiceTips();
        getCoin.startTime = getSetTimestamp(19,0,0, 0);
        getCoin.endTime = getSetTimestamp(21, 23, 59, 59);
        getCoin.tipsStr = GameApplicatoin.getContext().getString(R.string.wc2018_penalty_voice_revive_get_coin);
        getCoin.voiceNum = "2";
        getCoin.tipsRes = R.drawable.id_wc2018_penalty_revive_get_coocaa_coin;
        voiceTipsList.add(loveworldcup);
        voiceTipsList.add(siRevive);
        voiceTipsList.add(sgetCoin);
        if(index >= voiceTipsList.size()){
            index = 0;
        }
        VoiceTips voiceTips = voiceTipsList.get(index);
        index++;
        return voiceTips;
    }


    public static class VoiceTips implements Serializable{
       public long startTime;
       public long endTime;
       public String tipsStr;
       public int tipsRes;
       public String voiceNum;
    }

    private static Long getSetTimestamp(int day, int hour, int min, int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(2018, 5, day, hour, min, seconds);
        return cal.getTimeInMillis();
    }

    private static String getCurrentTimeVoiceTips(){
        Long currentTime = System.currentTimeMillis();
        return null;
    }
}
