package com.coocaa.ie.games.wc2018.answer.main.stage.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCLabel;
import com.coocaa.ie.core.gdx.CCStage;
import com.coocaa.ie.core.gdx.actor.FPSActor;
import com.coocaa.ie.core.gdx.actor.FlashActor;
import com.coocaa.ie.core.gdx.actor.TextureRegionActor;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.answer.main.presenter.AnswerMainPresenter;
import com.coocaa.ie.games.wc2018.answer.main.stage.AnswerMainStage;
import com.coocaa.ie.games.wc2018.answer.main.stage.ui.CriticalTipsView;
import com.coocaa.ie.games.wc2018.answer.main.stage.ui.GameEndView;
import com.coocaa.ie.games.wc2018.answer.main.stage.ui.MainLayout;
import com.coocaa.ie.games.wc2018.answer.main.stage.ui.TitleView;

public class AnswerMainStageImpl extends CCStage implements AnswerMainStage {
    public static final int SELECTION1 = MainLayout.BUTTON1;
    public static final int SELECTION2 = MainLayout.BUTTON2;
    public static final int SELECTION3 = MainLayout.BUTTON3;

    private TitleView titleView;
    private MainLayout mainLayout;
    private GameEndView gameEndView;

    private CriticalTipsView criticalTipsView;

    private CCLabel tips0, tips1, tips2;
    private TextureRegionActor tips0ArrawLeft, tips0ArrawRight;

    private FlashActor lamb;

    public AnswerMainStageImpl(WC2018Game game) {
        super(game);
    }

    @Override
    public void create(AnswerMainPresenter presenter) {
        titleView = new TitleView(getGame());
        titleView.setPosition(0, (getGame().getGlobalViewPort().getWorldHeight() - titleView.getHeight()) / 2);

        mainLayout = new MainLayout(getGame());
        mainLayout.setY(getGame().scale(MainLayout.OFFSET));
        gameEndView = new GameEndView(getGame());

        criticalTipsView = new CriticalTipsView(getGame());
        criticalTipsView.setPosition((getGame().getGlobalViewPort().getWorldWidth() - criticalTipsView.getWidth()) / 2, getGame().scale(173 + MainLayout.OFFSET));
        criticalTipsView.setVisible(false);
        tips0 = new CCLabel(getGame(), "【左右键】移动焦点 / 【确定键】选择答案    ", 28, new Color(1.0f, 1.0f, 1.0f, 0.5f));
        tips0.setPosition((getGame().getGlobalViewPort().getWorldWidth() - tips0.getWidth()) / 2, getGame().scale(408 + MainLayout.OFFSET));
        tips0ArrawLeft = new TextureRegionActor(getGame(), new TextureRegion(AnswerGame.Assets.textureTips0Arraw));
        tips0ArrawLeft.setPosition(tips0.getX() - tips0ArrawLeft.getWidth(), getGame().scale(418 + MainLayout.OFFSET));
        tips0ArrawRight = new TextureRegionActor(getGame(), new TextureRegion(AnswerGame.Assets.textureTips0Arraw), true, false);
        tips0ArrawRight.setPosition(tips0.getX() + tips0.getWidth(), getGame().scale(418 + MainLayout.OFFSET));

        tips1 = new CCLabel(getGame(), "遇到难题了 用语音助手说出\"我要求助\"可查询答案哦~", 32, new Color(1.0f, 1.0f, 1.0f, 0.5f));
        tips1.setPosition((getGame().getGlobalViewPort().getWorldWidth() - tips1.getWidth()) / 2, getGame().scale(120 + MainLayout.OFFSET));
        tips2 = new CCLabel(getGame(), "按【下键】 查看语音使用教程", 28, new Color(1.0f, 1.0f, 1.0f, 0.2f));
        tips2.setPosition((getGame().getGlobalViewPort().getWorldWidth() - tips2.getWidth()) / 2, getGame().scale(62 + MainLayout.OFFSET));

        lamb = new FlashActor(getGame(), AnswerGame.Assets.textureRegionLambNormal, AnswerGame.Assets.textureRegionLambLight);
        lamb.setPosition(getGame().scale(505), getGame().scale(89 + MainLayout.OFFSET));
    }

    private void init(int score, int coins, int time) {
        TextureRegionActor backgroundActor = new TextureRegionActor(getGame(), AnswerGame.Assets.textureBackground);
        addActor(backgroundActor);

        addActor(titleView);
        addActor(mainLayout);
        addActor(criticalTipsView);
        addActor(tips0);
        addActor(tips0ArrawLeft);
        addActor(tips0ArrawRight);
        if (WC2018GameController.getController().getGame().getWC2018GameComponent().isSupportVC()) {
            addActor(tips1);
            addActor(tips2);
            addActor(lamb);

            tips1.setVisible(true);
            tips2.setVisible(true);
            lamb.setVisible(true);
        }

        tips0ArrawLeft.setVisible(true);
        tips0.setVisible(true);
        tips0ArrawRight.setVisible(true);

        titleView.updateConins(coins);
        titleView.updateQuestions(score);
        titleView.updateTimer(time);

        if (WC2018GameController.getController().isDebugMode())
            addActor(new FPSActor(getGame()));

        setKeyboardFocus(mainLayout);
//        if (score > 0)
//            updateScore(score);
//        else
//            updateScore(0);
//        if (time > 0)
//            updateTimer(time);
    }

    @Override
    public void reset() {
        clear();
        init(0, 0, 0);
    }

    @Override
    public void reset(int score, int coins, int time) {
        clear();
        init(score, coins, time);
    }

    @Override
    public void setAnswerMainStageEventListener(AnswerMainStageEventListener eventListener) {
        mainLayout.setEventListener(eventListener);
    }

    @Override
    public void initContent(String descriptionText, String button1Text, String button2Text, String button3Text) {
        mainLayout.setContentText(descriptionText, button1Text, button2Text, button3Text);
    }

    @Override
    public void hideDpadTips() {
        tips0ArrawLeft.setVisible(false);
        tips0.setVisible(false);
        tips0ArrawRight.setVisible(false);
    }

    @Override
    public void hideVCTips() {
        tips1.setVisible(false);
        tips2.setVisible(false);
        lamb.setVisible(false);
    }

    @Override
    public void updateScore(final int coins, final int score, final boolean isDouble) {
        getGame().post(new Runnable() {
            @Override
            public void run() {
                titleView.updateQuestions(score);
                titleView.updateConins(coins);
                if (isDouble) {
                    criticalTipsView.show(score);
                }
            }
        });
    }

    @Override
    public void plusTimer(final int ptime) {
        titleView.plusTimer(ptime);
    }

    @Override
    public void updateTimer(int time) {
        titleView.updateTimer(time);
    }

    @Override
    public void correctAnswer(int button) {
        mainLayout.correctAnswer(button);
    }

    @Override
    public void incorrectAnswer(int button, int correct) {
        mainLayout.incorrectAnswer(button, correct);
    }

    @Override
    public void showEnd(int endType) {
        setKeyboardFocus(null);
        if (endType == END_TYPE_GAME_OVER) {
            gameEndView.setVisible(true);
            gameEndView.setTips(GameEndView.GAME_OVER);
            addActor(gameEndView);
        } else if (endType == END_TYPE_TIME_OUT) {
            gameEndView.setVisible(true);
            gameEndView.setTips(GameEndView.TIME_OUT);
            addActor(gameEndView);
        }
    }

    @Override
    public void hideEnd() {
        gameEndView.setVisible(false);
    }

    @Override
    public void flashTips(boolean v) {
        if (v)
            lamb.startFlash(1.0f);
        else
            lamb.stopFlash();
    }

    @Override
    public CCStage getStage() {
        return this;
    }

    @Override
    public void dispose() {
        super.dispose();
        clear();
        CCGame.dispose(gameEndView);
        CCGame.dispose(mainLayout);
    }
}
