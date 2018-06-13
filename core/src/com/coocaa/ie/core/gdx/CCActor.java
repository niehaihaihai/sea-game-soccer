package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CCActor extends Actor {
    private CCGame mGame;

    public CCActor(CCGame game) {
        super();
        mGame = game;
    }

    public CCGame getGame() {
        return mGame;
    }
}
