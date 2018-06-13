package com.coocaa.ie.core.gdx.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.coocaa.ie.core.gdx.CCGame;

public class CCTable extends Table {
    private CCGame game;

    public CCTable(CCGame game) {
        this.game = game;
    }

    public final CCGame getGame() {
        return game;
    }
}
