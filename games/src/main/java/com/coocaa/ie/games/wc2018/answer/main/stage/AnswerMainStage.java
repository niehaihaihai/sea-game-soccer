package com.coocaa.ie.games.wc2018.answer.main.stage;

import com.badlogic.gdx.utils.Disposable;
import com.coocaa.ie.core.gdx.CCStage;
import com.coocaa.ie.games.wc2018.answer.main.presenter.AnswerMainPresenter;

public interface AnswerMainStage extends Disposable {
    interface AnswerMainStageEventListener {
        void onButtonClick(int button);

        void onDpadLeftClick();

        void onDpadRightClick();

        void onDpadDownClick();

        void onDpadUpClick();

        void onBackKeyClick();
    }

    void create(AnswerMainPresenter presenter);

    void reset();

    void reset(int score, int coins, int time);

    void setAnswerMainStageEventListener(AnswerMainStageEventListener eventListener);

    void initContent(String descriptionText, String button1Text, String button2Text, String button3Text);

    void correctAnswer(int button);

    void incorrectAnswer(int button, int correct);

    void updateScore(int coins, int score, boolean isDouble);

    //隐藏操作提示
    void hideDpadTips();

    //隐藏语音求助提示
    void hideVCTips();

    void plusTimer(int ptime);

    void updateTimer(int time);

    int END_TYPE_GAME_OVER = 1;
    int END_TYPE_TIME_OUT = 2;

    void showEnd(int endType);

    void hideEnd();

    void flashTips(boolean v);

    CCStage getStage();
}
