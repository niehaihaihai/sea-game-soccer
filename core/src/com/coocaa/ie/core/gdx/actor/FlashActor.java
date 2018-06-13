package com.coocaa.ie.core.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCGroup;

public class FlashActor extends CCGroup {
    private TextureRegionActor bgActor;
    private TextureRegionActor flashActor;

    private boolean bFlashing = false;

    public FlashActor(CCGame game) {
        super(game);
        init(null, null);
    }

    public FlashActor(CCGame game, TextureRegion bg, TextureRegion flash) {
        super(game);
        init(bg, flash);
    }

    private void init(TextureRegion bg, TextureRegion flash) {
        bgActor = new TextureRegionActor(getGame(), bg);
        flashActor = new TextureRegionActor(getGame(), flash);
        addActor(bgActor);
        addActor(flashActor);
        updateSize(bgActor);
        updateSize(flashActor);
        invisiableFlash();
    }

    public void setBg(TextureRegion bg) {
        bgActor.setRegion(bg);
        updateSize(bgActor);
    }

    public void setFlash(TextureRegion flash) {
        flashActor.setRegion(flash);
        updateSize(flashActor);
        invisiableFlash();
    }

    private void updateSize(TextureRegionActor region) {
        float width = region.getWidth();
        float height = region.getHeight();
        if (getWidth() < width)
            setWidth(width);
        if (getHeight() < height)
            setHeight(height);
    }

    public synchronized void startFlash(float duration) {
        if (bFlashing)
            return;
        clearActions();
        invisiableFlash();
        float d = duration / 2;
        Action show = Actions.run(new Runnable() {
            @Override
            public void run() {
                flashActor.getColor().a = 0.5f;
            }
        });
        Action fadeIn = Actions.alpha(1.0f, d);
        Action fadeOut = Actions.alpha(0.5f, d);
        Action action = Actions.forever(Actions.sequence(show, fadeIn, fadeOut));
        flashActor.addAction(action);
        bFlashing = true;
    }

    public synchronized boolean isFlashing() {
        return bFlashing;
    }

    public synchronized void stopFlash() {
        if (!bFlashing)
            return;
        flashActor.clearActions();
        invisiableFlash();
        bFlashing = false;
    }

    public void invisiableFlash() {
        Color color = flashActor.getColor();
        color.a = 0;
        flashActor.setColor(color);
    }
}
