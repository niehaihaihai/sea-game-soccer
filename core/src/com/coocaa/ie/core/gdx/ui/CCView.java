package com.coocaa.ie.core.gdx.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.coocaa.ie.core.gdx.CCGame;

public class CCView extends Table implements Focusable, Disposable {
    private Texture bgTexture;
    private CCGame mGame;
    private boolean bFocusable = true;

    public CCView(CCGame game) {
        mGame = game;
        setFillParent(false);
    }

    public void setBackgroundColor(float r, float g, float b, float a) {
        CCGame.dispose(bgTexture);
        int w = (int) getWidth();
        int h = (int) getHeight();
        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fillRectangle(0, 0, w, h);
        bgTexture = new Texture(pixmap);
        Drawable bg = new TextureRegionDrawable(new TextureRegion(bgTexture));
        setBackground(bg);
    }

    @Override
    public void setFocusable(boolean flag) {
        bFocusable = flag;
    }

    @Override
    public boolean isFocusable() {
        return bFocusable;
    }

    public final CCGame getGame() {
        return mGame;
    }
//
//    public void setBackground(Drawable drawable) {
//        bg.setDrawable(drawable);
//        float w = getWidth();
//        float h = getHeight();
//        Gdx.app.debug("CCView", "setBackground bg.size:" + w + "x" + h);
//        bg.setSize(w, h);
//    }
//
//    @Override
//    public float getPrefWidth() {
//        float width = getWidth();
//        if (bg != null) width = Math.max(width, bg.getPrefWidth());
//        return width;
//    }
//
//    @Override
//    public float getPrefHeight() {
//        float height = getHeight();
//        if (bg != null) height = Math.max(height, bg.getPrefHeight());
//        return height;
//    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    private boolean bFocused = false;
    private FocusManager focusManager;
    private OnFocusChangeListener listener;

    @Override
    public void setFocusManager(FocusManager manager) {
        focusManager = manager;
    }

    @Override
    public boolean hasFocus() {
        return bFocused;
    }

    @Override
    public void requestFocus() {
        if (!bFocused) {
            bFocused = focusManager.requestFocus(this);
            if (listener != null && bFocused)
                listener.onFocusChanged(this, true);
        }
    }

    @Override
    public void clearFocus() {
        if (bFocused) {
            bFocused = false;
            if (listener != null)
                listener.onFocusChanged(this, false);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void dispose() {
        CCGame.dispose(bgTexture);
    }
}
