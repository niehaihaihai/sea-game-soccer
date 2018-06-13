package com.coocaa.ie.games.wc2018.penalty.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.actor.FPSActor;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;
import com.coocaa.ie.games.wc2018.penalty.actor.GoalKeeper;
import com.coocaa.ie.games.wc2018.penalty.actor.MyActor;
import com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant;
import com.coocaa.ie.games.wc2018.penalty.actor.PenaltyMainScreen;
import com.coocaa.ie.games.wc2018.utils.web.ad.AdData;
import com.coocaa.ie.games.wc2018.penalty.basedata.DifficultyControler;
import com.coocaa.ie.games.wc2018.utils.web.ad.BaseAdData;
import com.coocaa.ie.games.wc2018.utils.web.ad.LoadImgFromNet;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Gdx.app;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_arrow;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_back;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_ball;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_ball_fail_back;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_ball_score_back;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_goal;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_help;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_no_goal;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_timeover;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_mainstage_tip_20score;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.PENALTY_PREFERENCES;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.PENALTY_PREFERENCES_FIRST_PLAY;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_WIDTH;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_KICK;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_OVER;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_PLAYING;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_SCORE;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_STARTED;

/**
 * Created by Sea on 2018/5/3.
 */

public class MainStage extends Stage {

    private String TAG = "Sea-game";
    private DifficultyControler difficultyControler = new DifficultyControler();
    private WC2018Game game;
    private List<AdData> adDatas;
    private int score;
    private boolean isTimeOver = false;
    private MyActor ball;
    private MyActor ballScoreBack;
    private Sound kickSound, scoreSound, failSound, sec10, scorex20, beginAndEndSound;
    private Music backgroundMusic;
    private MyActor arrow;
    private GoalKeeper goalkeeper1, goalkeeper2;
    private MyActor back;
    private MyActor goal;
    private MyActor doubleTip;
    private MyActor timeOverTip;
    private MyActor adleft, adright;
    private MyActor playHelp;
    private final Rectangle goalkeeper1Rect = new Rectangle();
    private final Rectangle goalkeeper2Rect = new Rectangle();
    private final Rectangle ballRect = new Rectangle();

    private final Rectangle gateRect = new Rectangle();

    private PenaltyMainScreen mainScreen;
    private OnResultChangeListener onResultChangeListener;

    private boolean isFirstPlay = true;

    public MainStage(WC2018Game game, PenaltyMainScreen mainScreen, Viewport viewport, OnResultChangeListener l1) {
        super(viewport);
        this.onResultChangeListener = l1;
        this.mainScreen = mainScreen;
        this.game = game;

        gateRect.set(game.scaleX(409), game.scaleX(330), game.scaleX(1098), game.scaleX(430));

        loadLocalPreference();
        initBack();
        initGoalKeeper();
        initGoleTip();
        initBall();
        initArrow();
        initScreenTip();
        initTimeOverTip();
        initSound();

        Gdx.input.setInputProcessor(this);
        addListener(new MyInputListener());

        addAd();
//        addActor(new FPSActor(CCGame.getInstance()));
    }

    private void loadLocalPreference() {
        Preferences preferences = app.getPreferences(PENALTY_PREFERENCES);
        isFirstPlay = preferences.getBoolean(PENALTY_PREFERENCES_FIRST_PLAY, true);
    }

    private void initSound() {
        failSound = PenaltyGame.Assets.sound_fail;
        kickSound = PenaltyGame.Assets.sound_kick;
        scoreSound = PenaltyGame.Assets.sound_score;
        sec10 = PenaltyGame.Assets.sound_sec10;
        scorex20 = PenaltyGame.Assets.sound_scorex20;
        beginAndEndSound = PenaltyGame.Assets.sound_begin_and_end;
        backgroundMusic = PenaltyGame.Assets.music_background;
        backgroundMusic.setLooping(true);
    }

    @Override
    public void act() {
        super.act();
        if(mainScreen.curState == GAME_STATUS_PLAYING)
            checkCollision();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if(kickSound!=null)
            kickSound.dispose();
        if(scoreSound!=null)
            scoreSound.dispose();
        if(backgroundMusic!=null)
            backgroundMusic.dispose();
        if(failSound!=null)
            failSound.dispose();
        if(sec10!=null)
            sec10.dispose();
        if(scorex20!=null)
            scorex20.dispose();
        if(beginAndEndSound !=null)
            beginAndEndSound.dispose();
    }

    public void continueGame() {
        isTimeOver = false;
        changeGameState(GAME_STATUS_STARTED);
        timeOverTip.setVisible(false);
    }

    public interface OnResultChangeListener {
        void OnScoreChange(int score);
    }

    public void start() {
        changeGameState(GAME_STATUS_STARTED);
//        setBallState(GAME_STATUS_STARTED);
//        setArrowState(GAME_STATUS_STARTED);
//        setGoalKeeperState(GAME_STATUS_STARTED);
//        setGoalState(GAME_STATUS_STARTED);
        beginAndEndSound.play();
        backgroundMusic.play();
    }

    private void initBack() {
        back = new MyActor(textureregion_mainstage_back);
        back.setPosition(0,0);
        addActor(back);
    }

    private void addAd() {
        if(adDatas == null) {
            BaseAdData _data = game.getAssetManager().get(PenaltyGame.Assets.AD_DATA);
            adDatas = _data.getObject();
        }
        Gdx.app.debug("Sea-game", "addad-------addatas.size = "+(adDatas == null? "0" : ""+adDatas.size()));
        if(adDatas == null)
            return;
        if(adDatas.size() <= 0)
            return;
        Gdx.app.debug("Sea-game", "AD datas.get(0).url = "+adDatas.get(0).getAdvertImgUrl());
        if(adDatas.get(0)!=null && adDatas.get(0).getAdvertImgUrl()!=null && adDatas.get(0).getAdvertImgUrl().length() > 0) {
            adleft = new MyActor(null);
            adleft.setPosition(0, gateRect.getY());
            addActor(adleft);
            LoadImgFromNet.load(adDatas.get(0).getAdvertImgUrl(), adleft, game.scaleX(410), game.scaleX(200));
        }
        if(adDatas.get(1)!=null && adDatas.get(1).getAdvertImgUrl()!=null && adDatas.get(1).getAdvertImgUrl().length()>0) {
            adright = new MyActor(null);
            adright.setPosition(gateRect.getX()+gateRect.getWidth(), gateRect.getY());
            addActor(adright);
            LoadImgFromNet.load(adDatas.get(1).getAdvertImgUrl(), adright, game.scaleX(410), game.scaleX(200));
        }
    }

    private void initBall() {
        ball = new MyActor(textureregion_mainstage_ball);
        ballScoreBack = new MyActor(textureregion_mainstage_ball_score_back);
        ballScoreBack.setVisible(false);
        ball.setPosition(WORLD_WIDTH/2- ball.getWidth()/2, game.scaleX(20.0f));
        addActor(ball);
        addActor(ballScoreBack);

        if(isFirstPlay) {
            playHelp = new MyActor(textureregion_mainstage_help);
            playHelp.setPosition(WORLD_WIDTH/2 + ball.getWidth()/2+10, ball.getHeight() / 2 - game.scaleX(20));
            addActor(playHelp);
        }
    }

    private void initScreenTip() {
        doubleTip = new MyActor(textureregion_mainstage_tip_20score);
        doubleTip.setVisible(false);
        addActor(doubleTip);
    }

    private void initTimeOverTip() {
        timeOverTip = new MyActor(textureregion_mainstage_timeover);
        timeOverTip.setVisible(false);
        addActor(timeOverTip);
    }

    private void initArrow() {
        arrow = new MyActor(textureregion_mainstage_arrow);
        arrow.setPosition(WORLD_WIDTH/2- arrow.getWidth()/2, ball.getHeight() + game.scale(10.0f));
        addActor(arrow);
    }

    private void initGoalKeeper() {
        goalkeeper1 = new GoalKeeper(1, 1);
        goalkeeper1.setPosition(gateRect.getX(), gateRect.getY());
        goalkeeper1.setDir(1);
        addActor(goalkeeper1);

        goalkeeper2 = new GoalKeeper(2, 1);
        goalkeeper2.setPosition(gateRect.getX()+gateRect.getWidth()-goalkeeper2.getWidth(), gateRect.getY());
        goalkeeper2.setDir(2);
        addActor(goalkeeper2);
        goalkeeper2.setVisible(false);
    }

    private void initGoleTip() {
        goal= new MyActor(textureregion_mainstage_no_goal);
        goal.setVisible(false);
        addActor(goal);
    }

    private void setBallState(PenaltyConstant.penaltyGameState state) {
        switch (state) {
            case GAME_STATUS_STARTED :
                ball.clearActions();
                ball.setPosition(WORLD_WIDTH/2- ball.getWidth()/2, game.scaleX(20.0f));
                ball.setScale(1.0f, 1.0f);
                break;
            case GAME_STATUS_PLAYING : {
                ball.clearActions();
                ball.setOrigin(ball.getWidth()/2, ball.getHeight()/2);
                float rotate = arrow.getRotation();
                app.debug(TAG, "rotate = "+rotate);
                float tan_a = (float) Math.tan(Math.abs(rotate) * Math.PI / 180);
                float x;
                float y = gateRect.getY()+gateRect.getHeight()/1.7f;
                if(arrow.getRotation()>0) {
                    x = WORLD_WIDTH/2 - y*tan_a - ball.getWidth()/2;
                }else {
                    x = WORLD_WIDTH/2 + y*tan_a - ball.getWidth()/2;
                }
                if(x<gateRect.getX())
                app.debug(TAG, "ball dest x = "+x+"  dest y = "+y);
                MoveToAction moveToAction = Actions.moveTo(x, y, 0.6f);

                RotateByAction rotateByAction = Actions.rotateBy(-360, 0.1f);
                RepeatAction repeatRotation = Actions.forever(rotateByAction);

                ScaleToAction scaleToAction = Actions.scaleTo(0.5f, 0.5f, 0.6f, new Interpolation.PowOut(4));
                ParallelAction parallelAction = Actions.parallel(moveToAction, repeatRotation, scaleToAction);
                ball.addAction(parallelAction);
                break;
            }
            case GAME_STATUS_SCORE : {
                addScore();

                ball.clearActions();
                ballScoreBack.setRegion(textureregion_mainstage_ball_score_back);
                ballScoreBack.setZIndex(4);
                ballScoreBack.setVisible(true);
                ball.setZIndex(5);
                ball.setRotation(0);
                ball.setOrigin(ball.getWidth()/2, ball.getHeight()/2);
                ballScoreBack.setPosition(ball.getX(), ball.getY());
                goalkeeper1.setZIndex(6);
                goalkeeper2.setZIndex(6);

                DelayAction delayAction = Actions.delay(1.0f);
                RunnableAction runnable = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ballScoreBack.setVisible(false);
                        changeGameState(GAME_STATUS_STARTED);
                    }
                });
                SequenceAction sequenceAction = Actions.sequence(delayAction, runnable);
                ball.addAction(sequenceAction);
                break;
            }
            case GAME_STATUS_KICK : {
                ball.clearActions();
                ballScoreBack.setRegion(textureregion_mainstage_ball_fail_back);
                ballScoreBack.setZIndex(5);
                ballScoreBack.setPosition(ball.getX(), ball.getY());
                ballScoreBack.setVisible(true);
                goalkeeper1.setZIndex(4);
                goalkeeper2.setZIndex(4);
                ball.setZIndex(6);

                DelayAction delayAction = Actions.delay(1.0f);

                RunnableAction runnable = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ballScoreBack.setVisible(false);
                        changeGameState(GAME_STATUS_STARTED);
                    }
                });
                SequenceAction sequenceAction = Actions.sequence(delayAction, runnable);
                ball.addAction(sequenceAction);
                break;
            }
        }

    }

    private int arrowDir = 1;//arrow direction    1 right    2 left
    private void setArrowState(PenaltyConstant.penaltyGameState state) {
        switch (state) {
            case GAME_STATUS_STARTED : {
                arrow.clearActions();
                arrow.setPosition(WORLD_WIDTH/2- arrow.getWidth()/2, ball.getHeight() + game.scale(10.0f));
                arrow.setOrigin(arrow.getWidth()/2, -(game.scaleX(10.0f) + ball.getHeight()/2));
//                arrow.setRotation(60);

                RunnableAction changeDirToLeftRunnable = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        arrowDir = 2;
                    }
                });
                RunnableAction changeDirToRightRunnable = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        arrowDir = 1;
                    }
                });
                RotateToAction rotateToRight = Actions.rotateTo(-60, 0.8f);
                RotateToAction rotateToLeft = Actions.rotateTo(60, 0.8f);

                float curRatio = arrow.getRotation();
                if(arrowDir == 1) {
                    float lastTime = div(curRatio + 60, 120, 2) * 0.8f;
                    RotateToAction tempRotateToRight = Actions.rotateTo(-60, lastTime);

                    SequenceAction sequenceAction1 = Actions.sequence(rotateToLeft, changeDirToRightRunnable, rotateToRight, changeDirToLeftRunnable);
                    RepeatAction repeatAction = Actions.forever(sequenceAction1);

                    SequenceAction sequenceAction2 = Actions.sequence(tempRotateToRight, changeDirToLeftRunnable, repeatAction);
                    arrow.addAction(sequenceAction2);

                }else {
                    float lastTime = div(60 - curRatio, 120, 2) * 0.8f;
                    RotateToAction tempRotateToLeft = Actions.rotateTo(60, lastTime);

                    SequenceAction sequenceAction1 = Actions.sequence(rotateToRight, changeDirToLeftRunnable, rotateToLeft, changeDirToRightRunnable);
                    RepeatAction repeatAction = Actions.forever(sequenceAction1);

                    SequenceAction sequenceAction2 = Actions.sequence(tempRotateToLeft, changeDirToRightRunnable, repeatAction);
                    arrow.addAction(sequenceAction2);
                }


//                SequenceAction sequenceAction = Actions.sequence(rotateActionRight, rotateActionLeft);
//                RepeatAction repeatAction = Actions.forever(sequenceAction);
//                arrow.addAction(repeatAction);
                break;
            }
            case GAME_STATUS_PLAYING :
            case GAME_STATUS_KICK:
            case GAME_STATUS_SCORE:
            {
                arrow.clearActions();
                break;
            }
        }

    }

    private void setGoalKeeperState(PenaltyConstant.penaltyGameState state) {
        switch (state) {
            case GAME_STATUS_STARTED :
            {
                goalkeeper1.clearActions();
                goalkeeper2.clearActions();
                goalkeeper1.setMenSize(difficultyControler.getKeeperType());
                goalkeeper2.setMenSize(difficultyControler.getKeeperType());
                goalkeeper1.setMenState(1);
                goalkeeper2.setMenState(1);

                if(difficultyControler.getKeeperCount() == 1) {
                    goalkeeper2.setVisible(false);
                    float k1Left = gateRect.getX();
                    float k1Right = k1Left + gateRect.getWidth() * difficultyControler.getMoveArea() - goalkeeper1.getWidth();
                    setGoalkeeperAction(goalkeeper1, k1Left, k1Right);
                }else {
                    goalkeeper2.setVisible(true);
                    float k1Left = gateRect.getX();
                    float k1Right = k1Left + gateRect.getWidth() * difficultyControler.getMoveArea() - goalkeeper1.getWidth();
                    float k2Left = gateRect.getX() + gateRect.getWidth() * (1 - difficultyControler.getMoveArea());
                    float k2Right = gateRect.getX() + gateRect.getWidth() - goalkeeper2.getWidth();
                    setGoalkeeperAction(goalkeeper1, k1Left, k1Right);
                    setGoalkeeperAction(goalkeeper2, k2Left, k2Right);
                }
                break;
            }
            case GAME_STATUS_PLAYING:
            {
                break;
            }
            case GAME_STATUS_SCORE:
            {
                goalkeeper1.clearActions();
                goalkeeper2.clearActions();
                goalkeeper1.setMenState(2);
                goalkeeper2.setMenState(2);
                break;
            }
            case GAME_STATUS_KICK:
            {
                goalkeeper1.clearActions();
                goalkeeper2.clearActions();
                goalkeeper1.setMenState(3);
                goalkeeper2.setMenState(3);
                break;
            }
        }

    }

    private void setGoalkeeperAction(final GoalKeeper goalkeeper, float left, float right) {
        Gdx.app.debug("Sea", "set keeperAction left = "+left+"   right = "+right + "   dir = "+goalkeeper.getDir());
        float singleCyc = div(difficultyControler.getMoveCyc(), 2, 2);
        if(goalkeeper.getX() <= left) {
            goalkeeper.setPosition(left, goalkeeper.getY());
            goalkeeper.setDir(1);

            MoveToAction moveToRight = Actions.moveTo(right, gateRect.getY(), singleCyc);
            RunnableAction runnable1 = Actions.run(new Runnable() {
                @Override
                public void run() {
                    goalkeeper.setDir(2);
                }
            });
            MoveToAction moveToLeft = Actions.moveTo(left, gateRect.getY(), singleCyc);
            RunnableAction runnable2 = Actions.run(new Runnable() {
                @Override
                public void run() {
                    goalkeeper.setDir(1);
                }
            });
            SequenceAction sequenceAction = Actions.sequence(moveToRight, runnable1, moveToLeft, runnable2);
            RepeatAction repeatAction = Actions.forever(sequenceAction);
            goalkeeper.addAction(repeatAction);

        }else if(goalkeeper.getX() >= right) {
            goalkeeper.setPosition(right, goalkeeper.getY());
            goalkeeper.setDir(2);

            MoveToAction moveToLeft = Actions.moveTo(left, gateRect.getY(), singleCyc);
            RunnableAction runnable1 = Actions.run(new Runnable() {
                @Override
                public void run() {
                    goalkeeper.setDir(1);
                }
            });
            MoveToAction moveToRight = Actions.moveTo(right, gateRect.getY(), singleCyc);
            RunnableAction runnable2 = Actions.run(new Runnable() {
                @Override
                public void run() {
                    goalkeeper.setDir(2);
                }
            });
            SequenceAction sequenceAction = Actions.sequence(moveToLeft, runnable1, moveToRight, runnable2);
            RepeatAction repeatAction = Actions.forever(sequenceAction);
            goalkeeper.addAction(repeatAction);

        }else {

            if(goalkeeper.getDir() == 1) {//right
                MoveToAction moveToRight1 = Actions.moveTo(right, gateRect.getY(), div(right - goalkeeper.getX(), right - left, 2) * singleCyc);

                RunnableAction runnable1 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(2);
                    }
                });

                MoveToAction moveToLeft = Actions.moveTo(left, gateRect.getY(), singleCyc);
                RunnableAction runnable2 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(1);
                    }
                });
                MoveToAction moveToRight2 = Actions.moveTo(right, gateRect.getY(), singleCyc);
                RunnableAction runnable3 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(2);
                    }
                });
                SequenceAction sequenceAction1 = Actions.sequence(moveToLeft, runnable2, moveToRight2, runnable3);
                RepeatAction repeatAction = Actions.forever(sequenceAction1);
                SequenceAction sequenceAction2 = Actions.sequence(moveToRight1, runnable1, repeatAction);
                goalkeeper.addAction(sequenceAction2);
            }else {//left
                MoveToAction moveToLeft1 = Actions.moveTo(left, gateRect.getY(), div(goalkeeper.getX() - left, right - left, 2) * singleCyc);

                RunnableAction runnable1 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(1);
                    }
                });

                MoveToAction moveToRight = Actions.moveTo(right, gateRect.getY(), singleCyc);
                RunnableAction runnable2 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(2);
                    }
                });
                MoveToAction moveToLeft2 = Actions.moveTo(left, gateRect.getY(), singleCyc);
                RunnableAction runnable3 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goalkeeper.setDir(1);
                    }
                });
                SequenceAction sequenceAction1 = Actions.sequence(moveToRight, runnable2, moveToLeft2, runnable3);
                RepeatAction repeatAction = Actions.forever(sequenceAction1);

                SequenceAction sequenceAction2 = Actions.sequence(moveToLeft1, runnable1, repeatAction);
                goalkeeper.addAction(sequenceAction2);
            }
        }

    }

    public void setGoalState(PenaltyConstant.penaltyGameState state) {
        switch (state) {
            case GAME_STATUS_OVER:
            case GAME_STATUS_PLAYING:
            case GAME_STATUS_STARTED:
            {
                goal.clear();
                goal.setVisible(false);
                break;
            }
            case GAME_STATUS_KICK:
            {
                failSound.play();
                goal.setRegion(textureregion_mainstage_no_goal);
                goal.clearActions();
                goal.setVisible(true);
                goal.setZIndex(10);
                goal.setPosition(WORLD_WIDTH/2-goal.getWidth()/2, gateRect.getY()+gateRect.getHeight()/2-goal.getHeight()/2);
                DelayAction delay1 = Actions.delay(0.2f);
                RunnableAction runnable1 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goal.setVisible(false);
                    }
                });
                DelayAction delay2 = Actions.delay(0.2f);
                RunnableAction runnable2 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goal.setVisible(true);
                    }
                });
                DelayAction delay3 = Actions.delay(0.2f);
                RunnableAction runnable3 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goal.setVisible(false);
                    }
                });
                DelayAction delay4 = Actions.delay(0.2f);
                RunnableAction runnable4 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goal.setVisible(true);
                    }
                });
                DelayAction delay5 = Actions.delay(0.2f);
                RunnableAction runnable5 = Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        goal.clear();
                        goal.setVisible(false);
                    }
                });
                SequenceAction sequenceAction = Actions.sequence(delay1, runnable1, delay2, runnable2, delay3, runnable3, delay4, runnable4, delay5, runnable5);
                goal.addAction(sequenceAction);
                break;
            }
            case GAME_STATUS_SCORE:
            {
                if(score == 20) {
                    scorex20.play();
                    doubleTip.setPosition(WORLD_WIDTH, gateRect.getY()+gateRect.getHeight()/2- doubleTip.getHeight()/2);
                    doubleTip.setVisible(true);
                    doubleTip.setZIndex(11);
                    MoveToAction moveToAction1 = Actions.moveTo(WORLD_WIDTH/2- doubleTip.getWidth()/2, gateRect.getY()+gateRect.getHeight()/2- doubleTip.getHeight()/2, 0.1f);
                    DelayAction delayAction = Actions.delay(1.0f);
                    MoveToAction moveToAction2 = Actions.moveTo(- doubleTip.getWidth(), gateRect.getY()+gateRect.getHeight()/2- doubleTip.getHeight()/2, 0.1f);
                    RunnableAction runnableAction = Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            doubleTip.setVisible(false);
                        }
                    });
                    SequenceAction sequenceAction = Actions.sequence(moveToAction1,delayAction,moveToAction2,runnableAction);
                    doubleTip.addAction(sequenceAction);
                }else {

                    goal.setRegion(textureregion_mainstage_goal);
                    goal.clearActions();
                    goal.setVisible(true);
                    goal.setZIndex(10);
                    goal.setPosition(WORLD_WIDTH, gateRect.getY()+gateRect.getHeight()/2-goal.getHeight()/2);
                    MoveToAction moveToAction = Actions.moveTo(WORLD_WIDTH/2-goal.getWidth()/2, gateRect.getY()+gateRect.getHeight()/2-goal.getHeight()/2, 0.1f);
                    DelayAction delay = Actions.delay(0.5f);
                    RunnableAction runnable = Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            goal.clear();
                            goal.setVisible(false);
                        }
                    });
                    SequenceAction sequenceAction = Actions.sequence(moveToAction, delay, runnable);
                    goal.addAction(sequenceAction);
                }
                break;
            }
        }
    }

    private void checkCollision() {

        goalkeeper1Rect.set(
                goalkeeper1.getX(),
                goalkeeper1.getY()+game.scaleX(50),
                goalkeeper1.getWidth(),
                goalkeeper1.getHeight()-game.scaleX(50)
        );
        goalkeeper2Rect.set(
                goalkeeper2.getX(),
                goalkeeper2.getY()+game.scaleX(50),
                goalkeeper2.getWidth(),
                goalkeeper2.getHeight()-game.scaleX(50)
        );
        ballRect.set(//scale动作不会真正改变球的size，所以直接结算缩放后的尺寸
                ball.getX()+ball.getWidth()*0.3f,
                ball.getY()+ball.getHeight()*0.3f,
                ball.getWidth()*0.4f,
                ball.getHeight()*0.4f
        );

        if (goalkeeper1Rect.overlaps(ballRect) ||(goalkeeper2.isVisible() && goalkeeper2Rect.overlaps(ballRect))) {
            changeGameState(GAME_STATUS_KICK);
        } else if(ball.getX() <= gateRect.getX() || ball.getX()+ball.getWidth() >= gateRect.getX()+gateRect.width ||
                ball.getY()+ball.getHeight() >= gateRect.getY()+gateRect.getHeight()/1.7f){
            changeGameState(GAME_STATUS_SCORE);
        } else if(goalkeeper2.isVisible() && ball.getY() > goalkeeper2.getY()+goalkeeper2.getHeight() ||
                !goalkeeper2.isVisible() && ball.getY() > goalkeeper1.getY()+goalkeeper1.getHeight()) {
            changeGameState(GAME_STATUS_SCORE);
        }
    }

    public void changeGameState(PenaltyConstant.penaltyGameState state) {

        if(isTimeOver && state == GAME_STATUS_STARTED)
            return;
        if(state == GAME_STATUS_OVER) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put("game_name", WC2018GameController.getController().getGameName());
                params.put("page_name", "游戏主界面");
                params.put("status_name", "时间到");
                WC2018GameController.getController().getGame().getWC2018GameComponent().logger("game_main_page_status_event",params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isTimeOver = true;
            beginAndEndSound.play();
            showTimeOverTip();
        }
        mainScreen.changeGameState(state);
        setArrowState(state);
        setBallState(state);
        setGoalKeeperState(state);
        setGoalState(state);
    }

    private void showTimeOverTip() {
        timeOverTip.setPosition(WORLD_WIDTH, gateRect.getY()+gateRect.getHeight()/2- timeOverTip.getHeight()/2);
        timeOverTip.setVisible(true);
        timeOverTip.setZIndex(11);
        MoveToAction moveToAction = Actions.moveTo(WORLD_WIDTH/2- timeOverTip.getWidth()/2, gateRect.getY()+gateRect.getHeight()/2- timeOverTip.getHeight()/2, 0.1f);
        DelayAction delay = Actions.delay(1.0f);
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run() {
                timeOverTip.setVisible(false);
            }
        });
        SequenceAction seq = Actions.sequence(moveToAction, delay, run);
        timeOverTip.addAction(seq);
    }

    private void addScore() {
        scoreSound.play();
        score++;
        difficultyControler.changeLevel(score);

        if(score == 20) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put("game_name", WC2018GameController.getController().getGameName());
                params.put("page_name", "游戏主界面");
                params.put("status_name", "酷币翻倍");
                WC2018GameController.getController().getGame().getWC2018GameComponent().logger("game_main_page_status_event",params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(onResultChangeListener !=null)
            onResultChangeListener.OnScoreChange(score);
    }

    private int kickTimes = 0;

    private class MyInputListener extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.ENTER:
                case Input.Keys.DPAD_CENTER:
                {
                    if(mainScreen.curState == GAME_STATUS_STARTED) {
                        kickTimes++;
                        game.getWC2018GameComponent().updateLoggerExtra(kickTimes);
                        kickSound.play();
                        changeGameState(GAME_STATUS_PLAYING);

                        if(playHelp !=null)
                            playHelp.setVisible(false);
                        if(isFirstPlay) {
                            isFirstPlay = false;
                            Preferences preferences = Gdx.app.getPreferences(PENALTY_PREFERENCES);
                            preferences.putBoolean(PENALTY_PREFERENCES_FIRST_PLAY, false);
                            preferences.flush();
                        }
                    }
                    break;
                }
            }
            return super.keyDown(event, keycode);
        }
    }

    private static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


}
