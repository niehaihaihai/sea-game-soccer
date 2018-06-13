package com.coocaa.ie.games.wc2018.pages.startup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badlogic.gdx.utils.StringBuilder;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.skyworth.util.imageloader.ImageLoader;

import static com.coocaa.ie.games.wc2018.WC2018GameController.GAME_PENALTY;
import static com.skyworth.webSDK.webservice.mediaFactory.model.ImageUrl.img_style.v;

/**
 * Created by Sea on 2018/5/25.
 */

public class DetailRule extends FrameLayout{

    private LinearLayout contentLayout;
    private Context context;
    private String game;
    private boolean needVoice;
    private View back;
    public DetailRule(Context context, String game) {
        super(context);
        this.context = context;
        this.game = game;
        this.needVoice = CoocaaIEApplication.isSupportVC();
        this.setBackgroundColor(Color.parseColor("#cc000000"));

        back = ImageLoader.getLoader().getView(context);
        FrameLayout.LayoutParams back_p;
        if(GAME_PENALTY.equals(WC2018GameController.getController().getGameName())) {
            back_p = new LayoutParams(UI.div(1700), UI.div(920));
            back_p.gravity = Gravity.CENTER;
            addView(back, back_p);
            ImageLoader.getLoader().with(context).load(R.drawable.id_wc2018_penalty_startpage_detailrule_back).into(back);
        }else {
            back_p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(back, back_p);
            back_p.gravity = Gravity.CENTER;
            ImageLoader.getLoader().with(context).load(R.drawable.id_wc2018_answer_startpage_detailrule_back).into(back);
        }

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams contentLayout_p;
        if(GAME_PENALTY.equals(WC2018GameController.getController().getGameName())) {
            contentLayout_p = new LayoutParams(UI.div(1700), UI.div(820));
        }else {
            contentLayout_p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        contentLayout_p.gravity = Gravity.CENTER;
        addView(contentLayout, contentLayout_p);

        addTitle();
        addDetail();
        if(needVoice) {
            addVoiceTip();
        }
        addLawTip();
    }

    private void addLawTip() {
        TextView lawTip = new TextView(context);
        lawTip.setText("如对上述内容和活动有异议，可按照相关法律法规予以解释");
        LinearLayout.LayoutParams lawTip_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(WC2018GameController.GAME_ANSWER.equals(WC2018GameController.getController().getGameName())) {
            lawTip.setTextColor(Color.WHITE);
            lawTip.setTextSize(UI.dpi(32));
            lawTip.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            lawTip.setPadding(UI.div(167), UI.div(35),0,0);
        }else {
            lawTip.setTextColor(Color.parseColor("#606060"));
            lawTip.setTextSize(UI.dpi(18));
            lawTip.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            lawTip_p.topMargin = UI.div(10);
            lawTip_p.rightMargin = UI.div(50);
        }
        contentLayout.addView(lawTip, lawTip_p);
    }

    private void addVoiceTip() {
        TextView voiceTip = new TextView(context);
        String txt = "按  “菜单”  键可查看语音教程";
        SpannableString spannable = new SpannableString(txt);
        int color = Color.WHITE;
        spannable.setSpan(new ForegroundColorSpan(color), 0, txt.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        LinearLayout.LayoutParams voiceTip_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(WC2018GameController.GAME_ANSWER.equals(WC2018GameController.getController().getGameName())) {
            color = Color.parseColor("#ffdc3b");
            int start = txt.indexOf("“");
            spannable.setSpan(new ForegroundColorSpan(color), start, start + 4,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            voiceTip.setTextSize(UI.dpi(32));
            voiceTip.setGravity(Gravity.LEFT);
            voiceTip.setPadding(UI.div(167), UI.div(99),0,0);
        }else {
            voiceTip.setTextSize(UI.dpi(36));
            voiceTip.setGravity(Gravity.CENTER);
            voiceTip_p.topMargin = UI.div(50);
        }
        voiceTip.setText(spannable);
        contentLayout.addView(voiceTip, voiceTip_p);
    }

    private void addDetail() {
        TextView detailrule = new TextView(context);
        detailrule.setTextColor(Color.WHITE);
        detailrule.setSingleLine(false);
        LinearLayout.LayoutParams detailrule_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(WC2018GameController.GAME_ANSWER.equals(WC2018GameController.getController().getGameName())) {
            detailrule.setTextSize(UI.dpi(32));
            detailrule.setLineSpacing(UI.div(26), 1.0f);
            detailrule.setPadding(UI.div(167),UI.div(24),UI.div(167),0);
            if(needVoice) {
                detailrule.setText("1.\t游戏仅在6月22日00:00:00-6月28日24:59:59期间每日10点-13点、19点-22点两个时间段开启；\n" +
                        "2.\t本游戏为答题游戏，用户每答对一题可增加答题时间并获得相应的酷币（获得增加的时间根据用户答题正确数的不同会有不同），当用户答题错误或者答题时间结束即结束当局游戏；\n" +
                        "3.\t每时段用户各有20局游戏机会，单局时间60秒, 每答对1道题获得5酷币，用户当单答对20题及以上，则当局所获得的酷币可翻倍；\n" +
                        "4.\t每时段累计答题超150道，额外奖励100酷币；\n" +
                        "5.\t在答题期间用户可随时使用语音助手查询答案——念出指定语音口令即可查询；\n" +
                        "6.\t每用户每时段有1次语音复活机会，复活成功则在当局得分基础上继续进行一局游戏（达到翻倍条件的可继续翻倍）；\n" +
                        "7.\t每局赢得的酷币将在每局结束后发放，入账若有系统延迟请耐心等候。");
            }else {
                detailrule.setText("1.\t游戏仅在6月22日00:00:00-6月28日24:59:59期间每日10点-13点、19点-22点两个时间段开启；\n" +
                        "2.\t本游戏为答题游戏，每时段用户各有20局游戏机会，单局时间60秒，用户答题错误或者答题时间结束即结束当局游戏；\n" +
                        "3.\t用户每答对一题可增加答题时间并获得相应的酷币，每答对1道题获得5酷币，用户当单答对20题及以上，则当局所获得的酷币可翻倍，获得增加的时间根据用户答题正确数的不同会有不同；\n" +
                        "4.\t每时段累计答题超150道，额外奖励100酷币；\n" +
                        "5.\t每局赢得的酷币将在每局结束后发放，入账若有系统延迟请耐心等候。");
            }
        }else {
            detailrule_p.topMargin = UI.div(50);
            detailrule.setTextSize(UI.dpi(36));
            detailrule.setLineSpacing(UI.div(6), 1.0f);
            detailrule.setPadding(UI.div(125),0,UI.div(125),0);
            if(needVoice) {
                detailrule.setText("1.\t游戏仅在6月14日00:00:00-6月21日24:59:59期间每日10点-13点、19点-22点两个时间段开启；\n" +
                        "2.\t本游戏为足球射门游戏，每游戏时段每用户各有20局游戏机会，单局时间60秒，当进球时间结束时当局游戏即结束；\n" +
                        "3.\t根据每局射入球门的数量，用户获得相应的酷币，每射入1球获得5酷币，用户当局射入球20球及以上，则当局所获得的酷币可翻倍；\n" +
                        "4.\t每时段累计进球超260个，额外奖励100酷币；\n" +
                        "5.\t每用户每时段有1次语音复活机会，复活成功则在当局得分基础上继续进行一局游戏（达到翻倍条件的可继续翻倍）；\n" +
                        "6.\t每局赢得的酷币将在每局结束后发放，入账若有系统延迟请耐心等候。");
            }else {
                detailrule.setText("1.\t游戏仅在6月14日00:00:00-6月21日24:59:59期间每日10点-13点、19点-22点两个时间段开启；\n" +
                        "2.\t本游戏为足球射门游戏，每游戏时段每用户各有20局游戏机会，单局时间60秒，当进球时间结束时当局游戏即结束；\n" +
                        "3.\t根据每局射入球门的数量，用户获得相应的酷币，每射入1球获得5酷币，用户当局射入球20球及以上，则当局所获得的酷币可翻倍；\n" +
                        "4.\t每时段累计进球超260个，额外奖励100酷币；\n" +
                        "5.\t每局赢得的酷币将在每局结束后发放，入账若有系统延迟请耐心等候。");
            }
        }
        contentLayout.addView(detailrule, detailrule_p);

    }

    private void addTitle() {
        TextView title = new TextView(context);
        title.setText("游戏规则");
        title.setTextColor(Color.WHITE);
        title.setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams title_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(WC2018GameController.GAME_ANSWER.equals(WC2018GameController.getController().getGameName())) {
            title.setTextSize(UI.dpi(40));
            title_p.topMargin = UI.div(76);
            title.setGravity(Gravity.LEFT);
            title.setPadding(UI.div(167),0,0,0);
        }else {
            title.setGravity(Gravity.CENTER);
            title.setTextSize(UI.dpi(50));
            title_p.topMargin = UI.div(65);
        }
        contentLayout.addView(title, title_p);
    }


}
