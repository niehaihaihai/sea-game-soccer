package com.coocaa.ie.games.wc2018.demo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCLabel;

/**
 * Created by lu on 2018/4/26.
 */

public class LoadingScreen implements Screen {
    private CCGame game;

    class MyStage extends Stage {
        private CCLabel label;

        public MyStage(CCGame game) {
            super();
            label = new CCLabel(game, "");
            label.setPosition(game.getGlobalViewPort().getWorldWidth() / 2, game.getGlobalViewPort().getWorldHeight() / 2);
            label.setOrigin(Align.center);
            addActor(label);
        }

        @Override
        public void draw() {
            super.draw();
            label.setText("start up loading:" + (int) (game.getAssetManager().getProgress() * 100));
        }

        @Override
        public void act() {
            super.act();
        }

        @Override
        public void dispose() {
            super.dispose();
            if (label != null)
                label.dispose();
        }
    }

    private Stage mStage;

    public LoadingScreen(CCGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        mStage = new MyStage(game);
        game.getAssetManager().load(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.TEXTURE_PAK);
        game.getAssetManager().load(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.GOLD_SOUND);
        game.getAssetManager().load(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.BG_MUSIC);
    }

    @Override
    public void render(float delta) {
//        if (game.getAssetManager().update()) {
//            game.setScreen(new AnswerMainScreen(game));
//        } else {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mStage.act();
        mStage.draw();
//        }
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
