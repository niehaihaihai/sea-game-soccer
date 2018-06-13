package com.coocaa.ie.games.wc2018.answer.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCScreen;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.answer.main.model.AnswerMainModel;
import com.coocaa.ie.games.wc2018.answer.main.model.impl.AnswerMainModelImpl;
import com.coocaa.ie.games.wc2018.answer.main.presenter.AnswerMainPresenter;
import com.coocaa.ie.games.wc2018.answer.main.presenter.impl.AnswerMainPresenterImpl;
import com.coocaa.ie.games.wc2018.answer.main.stage.AnswerMainStage;
import com.coocaa.ie.games.wc2018.answer.main.stage.impl.AnswerMainStageImpl;

public class AnswerMainScreen extends CCScreen {
    private AnswerMainStage stage;
    private AnswerMainPresenter presenter;
    private AnswerMainModel model;


    public AnswerMainScreen(WC2018Game game) {
        super(game);
        stage = new AnswerMainStageImpl(game);
        presenter = new AnswerMainPresenterImpl(game);
        model = new AnswerMainModelImpl(game);
        model.create();
        stage.create(presenter);
        presenter.create(stage, model);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getStage().act();
        stage.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        presenter.onPause();
    }

    @Override
    public void resume() {
        presenter.onResume();
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        CCGame.dispose(stage);
        CCGame.dispose(presenter);
        CCGame.dispose(model);
    }

    public void playBgMusic() {
        presenter.playBgMusic();
    }

    public void startNewGame() {
        presenter.startNewGame();
    }

    public void reloadGame() {
        presenter.reloadGame();
    }

    public void continueGame() {
        presenter.continueGame();
    }
}
