package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;
import com.coocaa.ie.games.wc2018.penalty.stage.MainStage;
import com.coocaa.ie.games.wc2018.penalty.stage.MessageStage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_HEIGHT;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_WIDTH;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.penaltyGameState.GAME_STATUS_OVER;

/**
 * Created by Sea on 2018/5/3.
 */

public class PenaltyMainScreen implements Screen {

    private String TAG = "Sea";
    private int useTime;
    private int GAME_TIME;
    private WC2018Game game;
    private MainStage stageMain;
    private MessageStage stageMessage;
    private ScheduledThreadPoolExecutor timer;

    public penaltyGameState curState = penaltyGameState.GAME_STATUS_OVER;

    public PenaltyMainScreen(int gameTime, WC2018Game game) {
        GAME_TIME = gameTime;
        this.game = game;
    }

    MainStage.OnResultChangeListener onScoreChangeListener = new MainStage.OnResultChangeListener() {
        @Override
        public void OnScoreChange(int score) {
            stageMessage.setScore(score);
            if(score >= 20)
                stageMessage.setKubi(score * 10);
            else
                stageMessage.setKubi(score * 5);
        }
    };

    public void changeGameState(penaltyGameState state) {
        Gdx.app.debug(TAG, "changeGameState : "+state);
        curState = state;

        if(state == GAME_STATUS_OVER) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    WC2018Game.WC2018GameComponent.Score scor = new WC2018Game.WC2018GameComponent.Score();
                    scor.score = stageMessage.getScore();
                    scor.coins = stageMessage.getKubi();
                    scor.duration = GAME_TIME;
                    scor.bRelived = false;
                    game.getWC2018GameComponent().handleGameover(scor, new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }).start();

        }
    }

    @Override
    public void show() {
        Viewport viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stageMain = new MainStage(game, this, viewport, onScoreChangeListener);
        stageMessage = new MessageStage(viewport, game, GAME_TIME);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(stageMain!=null) {
            stageMain.act();
            stageMain.draw();
        }
        if(stageMessage!=null) {
            stageMessage.act();
            stageMessage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stopTimer();
    }

    private void startTimer()
    {
        stopTimer();
        try
        {
            timer = new ScheduledThreadPoolExecutor(1);
            timer.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    if (curState != GAME_STATUS_OVER)
                    {
                        useTime++;
                        if(useTime >= GAME_TIME) {
                            stopTimer();
                            stageMain.changeGameState(GAME_STATUS_OVER);
                        }
                        stageMessage.setTimes(GAME_TIME - useTime);
                    } else
                    {
                        stopTimer();
                    }
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void stopTimer()
    {
        if (timer != null)
        {
            timer.shutdownNow();
            timer = null;
        }
    }

    public void continueGame() {
        stageMain.continueGame();
        useTime = 0;
        startTimer();
    }

    public void go() {
        stageMain.start();
        startTimer();
    }
}
