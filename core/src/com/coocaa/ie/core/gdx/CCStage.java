package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CCStage extends Stage {
    private CCGame mGame;

    public CCStage(CCGame game) {
        super(game.getGlobalViewPort());
        Gdx.input.setInputProcessor(this);
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                Gdx.app.debug("CCStage", "keyDown:" + keycode);
                return super.keyDown(event, keycode);
            }
        });
        mGame = game;
    }

    public CCGame getGame() {
        return mGame;
    }
}
