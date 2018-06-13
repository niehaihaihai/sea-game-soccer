package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.graphics.Color;
import android.view.ViewGroup;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.WC2018GameController;

public class ViewConfig {
    public int bg = -1;
    //titleView
    public int titleViewWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewMarginTopOffset = 0;
    public int titleViewBG = -1;
    public int titleViewValueWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewValueHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewText1Width = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewText1Height = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewText1 = -1;
    public int titleViewText1PaddingLeft = -1;
    public int titleViewText2Width = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewText2Height = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int titleViewText2 = -1;
    public int titleViewText2PaddingLeft = -1;
    public int titleViewNumberFntMargin = 0;
    public int[] titleViewNumberFnt = null;

    //infoView
    public int infoViewMarginBottomOffset = 0;
    public int infoViewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int infoViewBG = -1;
    public int infoViewBGColor = -1;
    public int[] infoViewBadges = null;
    public int[] infoViewValueFnt = null;
    public int infoViewButtonShareWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int infoViewButtonShareHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int infoViewButtonShareFocusedBG = -1;
    public int infoViewButtonShareUnfocusedBG = -1;
    public int infoViewTitle1 = -1;
    public int infoViewTitle2 = -1;
    public int infoViewStamb = -1;

    //tipsView
    public int tipsViewBG = -1;
    public int tipsViewTextColor = -1;

    //buttonView
    public int buttonViewButtonHeight = -1;
    public int buttonViewButtonWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    public int buttonViewFocusedBG = -1;
    public int buttonViewUnfocusedBG = -1;

    private static ViewConfig viewConfig;

    private static ViewConfig initAnswerConfig() {
        ViewConfig config = new ViewConfig();
//        config.bg = R.drawable.id_wc2018_answer_settlement_bg;
        config.titleViewWidth = UI.div(1032);
        config.titleViewHeight = UI.div(476);
        config.titleViewMarginTopOffset = UI.div(-300);
        config.titleViewBG = R.drawable.id_wc2018_answer_settlement_title_bg;
        config.titleViewText1 = R.drawable.id_wc2018_answer_settlement_title_text1;
        config.titleViewText1PaddingLeft = 139;
        config.titleViewText1Width = UI.div(294);
        config.titleViewText1Height = UI.div(78);
        config.titleViewText2 = R.drawable.id_wc2018_answer_settlement_title_text2;
        config.titleViewText2PaddingLeft = 776;
        config.titleViewText2Width = UI.div(152);
        config.titleViewText2Height = UI.div(78);
        config.titleViewNumberFnt = new int[]{
                R.drawable.id_wc2018_answer_settlement_title_number_0,
                R.drawable.id_wc2018_answer_settlement_title_number_1,
                R.drawable.id_wc2018_answer_settlement_title_number_2,
                R.drawable.id_wc2018_answer_settlement_title_number_3,
                R.drawable.id_wc2018_answer_settlement_title_number_4,
                R.drawable.id_wc2018_answer_settlement_title_number_5,
                R.drawable.id_wc2018_answer_settlement_title_number_6,
                R.drawable.id_wc2018_answer_settlement_title_number_7,
                R.drawable.id_wc2018_answer_settlement_title_number_8,
                R.drawable.id_wc2018_answer_settlement_title_number_9};
        config.titleViewValueWidth = UI.div(100);
        config.titleViewValueHeight = UI.div(120);
        config.titleViewNumberFntMargin = UI.div(-25);

        config.infoViewMarginBottomOffset = UI.div(50);
        config.infoViewBG = R.drawable.id_wc2018_answer_settlement_info_bg;
        config.infoViewHeight = UI.div(393);
        config.infoViewButtonShareWidth = UI.div(137);
        config.infoViewButtonShareHeight = UI.div(45);
        config.infoViewButtonShareFocusedBG = R.drawable.id_wc2018_answer_settlement_info_share_focus;
        config.infoViewButtonShareUnfocusedBG = R.drawable.id_wc2018_answer_settlement_info_share_unfocus;
        config.infoViewTitle1 = R.string.wc2018_answer_settlement_info_title1;
        config.infoViewTitle2 = R.string.wc2018_answer_settlement_info_title2;
        config.infoViewBadges = new int[]{
                R.drawable.id_wc2018_answer_settlement_badge_1,
                R.drawable.id_wc2018_answer_settlement_badge_2,
                R.drawable.id_wc2018_answer_settlement_badge_3,
                R.drawable.id_wc2018_answer_settlement_badge_4,
                R.drawable.id_wc2018_answer_settlement_badge_5,
                R.drawable.id_wc2018_answer_settlement_badge_6
        };
        config.infoViewStamb = R.drawable.id_wc2018_answer_settlement_info_stamb;
        config.infoViewValueFnt = new int[]{
                R.drawable.id_wc2018_answer_number_a_0,
                R.drawable.id_wc2018_answer_number_a_1,
                R.drawable.id_wc2018_answer_number_a_2,
                R.drawable.id_wc2018_answer_number_a_3,
                R.drawable.id_wc2018_answer_number_a_4,
                R.drawable.id_wc2018_answer_number_a_5,
                R.drawable.id_wc2018_answer_number_a_6,
                R.drawable.id_wc2018_answer_number_a_7,
                R.drawable.id_wc2018_answer_number_a_8,
                R.drawable.id_wc2018_answer_number_a_9,
        };

//        config.tipsViewBG = R.drawable.id_wc2018_answer_settlement_tips_bg;
        config.tipsViewTextColor = Color.rgb(255, 73, 73);

        config.buttonViewButtonHeight = UI.div(177);
        config.buttonViewButtonWidth = UI.div(487);
        config.buttonViewFocusedBG = R.drawable.id_wc2018_answer_button_focus;
        config.buttonViewUnfocusedBG = R.drawable.id_wc2018_answer_button_unfocus;
        return config;
    }

    private static ViewConfig initPenaltyConfig() {
        ViewConfig config = new ViewConfig();
        config.bg = R.drawable.id_wc2018_penalty_settlement_bg;
        config.titleViewBG = R.drawable.id_wc2018_penalty_settlement_title_bg;
        config.titleViewWidth = UI.div(1055);
        config.titleViewHeight = UI.div(231);
        config.titleViewMarginTopOffset = UI.div(-107);
        config.titleViewText1 = R.drawable.id_wc2018_penalty_settlement_title_text1;
        config.titleViewText1PaddingLeft = 139;
        config.titleViewText1Width = UI.div(439);
        config.titleViewText1Height = UI.div(94);
        config.titleViewText2 = R.drawable.id_wc2018_penalty_settlement_title_text2;
        config.titleViewText2PaddingLeft = 776;
        config.titleViewText2Width = UI.div(135);
        config.titleViewText2Height = UI.div(60);
        config.titleViewNumberFnt = new int[]{
                R.drawable.id_wc2018_penalty_settlement_title_number_0,
                R.drawable.id_wc2018_penalty_settlement_title_number_1,
                R.drawable.id_wc2018_penalty_settlement_title_number_2,
                R.drawable.id_wc2018_penalty_settlement_title_number_3,
                R.drawable.id_wc2018_penalty_settlement_title_number_4,
                R.drawable.id_wc2018_penalty_settlement_title_number_5,
                R.drawable.id_wc2018_penalty_settlement_title_number_6,
                R.drawable.id_wc2018_penalty_settlement_title_number_7,
                R.drawable.id_wc2018_penalty_settlement_title_number_8,
                R.drawable.id_wc2018_penalty_settlement_title_number_9};
        config.titleViewValueWidth = UI.div(40);
        config.titleViewValueHeight = UI.div(57);
        config.titleViewNumberFntMargin = UI.div(0);

        config.infoViewBGColor = Color.argb(76, 0, 0, 0);
        config.infoViewButtonShareWidth = UI.div(179);
        config.infoViewButtonShareHeight = UI.div(46);
        config.infoViewButtonShareFocusedBG = R.drawable.id_wc2018_penalty_settlement_info_share_focus;
        config.infoViewButtonShareUnfocusedBG = R.drawable.id_wc2018_penalty_settlement_info_share_unfocus;
        config.infoViewTitle1 = R.string.wc2018_penalty_settlement_info_title1;
        config.infoViewTitle2 = R.string.wc2018_penalty_settlement_info_title2;
        config.infoViewBadges = new int[]{
                R.drawable.id_wc2018_penalty_settlement_badge_1,
                R.drawable.id_wc2018_penalty_settlement_badge_2,
                R.drawable.id_wc2018_penalty_settlement_badge_3,
                R.drawable.id_wc2018_penalty_settlement_badge_4,
                R.drawable.id_wc2018_penalty_settlement_badge_5,
                R.drawable.id_wc2018_penalty_settlement_badge_6
        };
        config.infoViewStamb = R.drawable.id_wc2018_penalty_settlement_info_stamb;
        config.infoViewValueFnt = new int[]{
                R.drawable.id_wc2018_answer_number_a_0,
                R.drawable.id_wc2018_answer_number_a_1,
                R.drawable.id_wc2018_answer_number_a_2,
                R.drawable.id_wc2018_answer_number_a_3,
                R.drawable.id_wc2018_answer_number_a_4,
                R.drawable.id_wc2018_answer_number_a_5,
                R.drawable.id_wc2018_answer_number_a_6,
                R.drawable.id_wc2018_answer_number_a_7,
                R.drawable.id_wc2018_answer_number_a_8,
                R.drawable.id_wc2018_answer_number_a_9,
        };

        config.tipsViewBG = R.drawable.id_wc2018_penalty_settlement_tips_bg;


        config.buttonViewButtonHeight = UI.div(84);
        config.buttonViewButtonWidth = UI.div(420);
        config.buttonViewFocusedBG = R.drawable.id_wc2018_penalty_settlement_button_focus;
        config.buttonViewUnfocusedBG = R.drawable.id_wc2018_penalty_settlement_button_unfocus;
        return config;
    }

    public static final void initViewConfig(String game) {
        if (game.equals(WC2018GameController.GAME_ANSWER)) {
            viewConfig = initAnswerConfig();
        } else {
            viewConfig = initPenaltyConfig();
        }
    }

    public static final ViewConfig viewConfig() {
        return viewConfig;
    }

    public static final boolean check(int res) {
        return res != -1;
    }
}
