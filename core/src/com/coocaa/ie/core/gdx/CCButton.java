package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CCButton extends Button {
    private CCGame mGame;

    public CCButton(CCGame game) {
        super();
        mGame = game;
    }

    public CCButton(Drawable up, Drawable down) {
        super(up, down);
    }

    public CCGame getGame() {
        return mGame;
    }
}
