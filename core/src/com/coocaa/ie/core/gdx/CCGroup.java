package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.scenes.scene2d.Group;

public class CCGroup extends Group {
    private CCGame mGame;

    public CCGroup(CCGame game) {
        mGame = game;
    }

    public CCGame getGame() {
        return mGame;
    }
}
