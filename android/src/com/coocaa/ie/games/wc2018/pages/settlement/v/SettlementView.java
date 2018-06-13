package com.coocaa.ie.games.wc2018.pages.settlement.v;

import android.view.View;

import com.coocaa.ie.games.wc2018.pages.settlement.p.SettlementPresenter;

import java.util.List;

public interface SettlementView {
    class SettlementViewData {
        public static final int BUTTON1_TYPE_CHECK_OTHERS = 1;
        public static final int BUTTON1_TYPE_PLAY_AGAIN = 2;

        //本时段得分/进球
        public int thisScore;
        //本轮酷币
        public int thisCoins;
        //本轮排名
        public int rank;
        //排名的变化
        public int rankdiff;

        //还差xx球/题可得xx酷币
        public String toast;

        //中间的提示条内容
        public String tips;
        //剩余游戏次数
        public int ticket;
        public int button1Type;
        public Runnable button1Action;
    }

    class ADViewData {
        public String source;
        public Runnable onclick;
    }

    void create(SettlementPresenter presenter);

    void destroy();

    //defeat 击败xx%的人
    //        //标题上显示的酷币数
    //        public int coins;
    //        //本局得分/进球
    //        public int score;
    //level 徽章等级  7级
//    void update(int conis, int score, int defeat);

    void update(int coins, int score, int level, int defeat, SettlementViewData data);

    //点击再玩一局时，保证loading过程不再被触发加载，需要disable此button
    void disableButton1();

    void updateAD(List<ADViewData> data);

    void updateGameLoading(int progress);

    View getView();
}
