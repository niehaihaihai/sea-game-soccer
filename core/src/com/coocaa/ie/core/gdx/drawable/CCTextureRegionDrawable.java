package com.coocaa.ie.core.gdx.drawable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coocaa.ie.core.gdx.CCGame;

public class CCTextureRegionDrawable extends TextureRegionDrawable {
    private CCGame game;

    public CCTextureRegionDrawable(CCGame game) {
        this.game = game;
    }

    @Override
    public void setRegion(TextureRegion region) {
        super.setRegion(region);
        if (region != null) {
            setMinWidth(game.scale(region.getRegionWidth()));
            setMinHeight(game.scale(region.getRegionHeight()));
        }
    }
}
