package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.pages.settlement.p.SettlementPresenter;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;
import com.skyworth.util.imageloader.ImageLoader;
import com.skyworth.util.imageloader.fresco.CoocaaFresco;

import java.util.List;

public class SettlementViewImpl extends RelativeLayout implements SettlementView {
    private static final boolean DEBUG = false;
    private SettlementPresenter presenter;

    private TitleView titleView;
    private CenterView centerView;
    private TipsView tipsView;
    private ButtonView buttonView;

    private SettlementViewData data;

    public SettlementViewImpl(Context context) {
        super(context);
        if (isInEditMode() || DEBUG) {
            ViewConfig.initViewConfig(WC2018GameController.GAME_ANSWER);
        } else {
            ViewConfig.initViewConfig(WC2018GameController.getController().getGameName());
        }

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (ViewConfig.check(ViewConfig.viewConfig().bg)) {
            View view = ImageLoader.getLoader().getView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(view);
            ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(context, ViewConfig.viewConfig().bg))).into(view);
        }

        {
            titleView = new TitleView(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewConfig.viewConfig().titleViewWidth, ViewConfig.viewConfig().titleViewHeight);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.topMargin = UI.div(211) + ViewConfig.viewConfig().titleViewMarginTopOffset;
            addView(titleView, params);
        }
        {
            centerView = new CenterView(context);
            centerView.setShareOnclickListener(shareOnClickListener);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CenterView.WIDTH, CenterView.HEIGHT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.bottomMargin = UI.div(363) + ViewConfig.viewConfig().infoViewMarginBottomOffset;
            addView(centerView, params);
        }
        {
            tipsView = new TipsView(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UI.div(65));
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.bottomMargin = UI.div(260) + ViewConfig.viewConfig().infoViewMarginBottomOffset;
            addView(tipsView, params);
        }
        {
            buttonView = new ButtonView(context, buttonViewOnClickListener);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.bottomMargin = UI.div(140) + ViewConfig.viewConfig().infoViewMarginBottomOffset;
            if (ViewConfig.viewConfig().buttonViewButtonHeight > 0) {
                params.bottomMargin += (ButtonView.HEIGHT - ViewConfig.viewConfig().buttonViewButtonHeight) / 2;
            }
            addView(buttonView, params);
        }

        buttonView.setFocus();

//        if (DEBUG) {
//            SettlementViewData data = new SettlementViewData();
//            data.rank = 120;
//            data.rankdiff = 12;
//            data.thisScore = 122;
//            data.thisCoins = 12;
//            update(data);
////            centerView.setValue(33, 1280);
////            centerView.setDefeat(99);
//
//            {
////                List<String> images = new ArrayList();
////                images.add(CoocaaFresco.getFrescoResUri(context, R.drawable.test_wc2018_settlement_ad_small));
////                images.add(CoocaaFresco.getFrescoResUri(context, R.drawable.test_wc2018_settlement_ad_small));
////                centerView.drawAD(images);
//            }
//
//
//            tipsView.setText("本时段游戏已结束");
//        }
    }

    @Override
    public void create(SettlementPresenter presenter) {
        this.presenter = presenter;
        titleView.startRandom(1000, 9999);
    }

    @Override
    public void destroy() {

    }

    @Override
    public View getView() {
        return this;
    }

//    @Override
//    public void update(final int coins, final int score, final int defeat) {
//        GameApplicatoin.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                titleView.setValue(coins);
//                centerView.updateBadge(score);
//                centerView.setDefeat(defeat);
//                centerView.setValue1(score);
//            }
//        });
//    }


    @Override
    public void update(final int coins, final int score, final int level, final int defeat, final SettlementViewData data) {
        GameApplicatoin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleView.stopRandom();
                titleView.setValue(coins);
                centerView.updateBadge(level);
                centerView.setDefeat(defeat);
                centerView.setValue1(score);

                SettlementViewImpl.this.data = data;
                if (!TextUtils.isEmpty(SettlementViewImpl.this.data.tips)) {
                    tipsView.setText(SettlementViewImpl.this.data.tips);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonView.getLayoutParams();
                    params.bottomMargin = UI.div(140) + ViewConfig.viewConfig().infoViewMarginBottomOffset;
                    if (ViewConfig.viewConfig().buttonViewButtonHeight > 0) {
                        params.bottomMargin += (ButtonView.HEIGHT - ViewConfig.viewConfig().buttonViewButtonHeight) / 2;
                    }
                    buttonView.setLayoutParams(params);
                }
                else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonView.getLayoutParams();
                    params.bottomMargin = UI.div(220) + ViewConfig.viewConfig().infoViewMarginBottomOffset;
                    if (ViewConfig.viewConfig().buttonViewButtonHeight > 0) {
                        params.bottomMargin += (ButtonView.HEIGHT - ViewConfig.viewConfig().buttonViewButtonHeight) / 2;
                    }
                    buttonView.setLayoutParams(params);
                }
                if (SettlementViewImpl.this.data.button1Type == SettlementViewData.BUTTON1_TYPE_PLAY_AGAIN)
                    buttonView.setButton1PlayAgain(SettlementViewImpl.this.data.ticket);
                else
                    buttonView.setButton1CheckOther();
                centerView.setValue2(SettlementViewImpl.this.data.thisScore);
                centerView.setThisCoins(SettlementViewImpl.this.data.thisCoins);
                centerView.setRank(SettlementViewImpl.this.data.rank, SettlementViewImpl.this.data.rankdiff);
                centerView.setTips1(SettlementViewImpl.this.data.toast);
                buttonView.setFocus();
            }
        });
    }

    @Override
    public void updateAD(List<ADViewData> data) {
        centerView.updateAD(data);
    }

    @Override
    public void updateGameLoading(int progress) {
        buttonView.updateGameLoading(progress);
    }

    @Override
    public void disableButton1() {
        buttonView.disableButton1();
    }

    private final OnClickListener shareOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.share();
        }
    };

    private final ButtonView.ButtonViewOnClickListener buttonViewOnClickListener = new ButtonView.ButtonViewOnClickListener() {
        @Override
        public void onButton1Click() {
            if (data != null && data.button1Action != null)
                data.button1Action.run();
        }

        @Override
        public void onButton2Click() {
            presenter.exit();
        }
    };
}
