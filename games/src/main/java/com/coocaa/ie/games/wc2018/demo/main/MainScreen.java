package com.coocaa.ie.games.wc2018.demo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.coocaa.ie.core.gdx.CCGame;

/**
 * Created by lu on 2018/4/25.
 */

public class MainScreen implements Screen {
    private CCGame game;
    private Stage mStage;

    public MainScreen(CCGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        mStage = new MainStage(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mStage.act();
        mStage.draw();
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
        mStage.dispose();
    }
}
