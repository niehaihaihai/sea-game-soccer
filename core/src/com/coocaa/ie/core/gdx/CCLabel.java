package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by lu on 2018/4/25.
 */

public class CCLabel extends Label implements Disposable {
    private static class CCLabelStyle extends LabelStyle {
        public int size;
    }

    private static final CCLabelStyle newStyle(BitmapFont font, int size) {
        CCLabelStyle style = new CCLabelStyle();
        style.font = font;
        style.size = size;
        return style;
    }

    private static final CCLabelStyle newDefaultStyle(CCGame game) {
        int size = 24;
        return newStyle(game.getAssetManager().newBitmapFont(size, Color.WHITE), size);
    }

    private BitmapFont bitmapFont;
    private CCGame mGame;

    public CCLabel(CCGame game, CharSequence text) {
        super(text, newDefaultStyle(game));
        mGame = game;
        bitmapFont = getStyle().font;
    }

    public CCLabel(CCGame game, CharSequence text, int size) {
        this(game, text, size, Color.WHITE);
    }

    public CCLabel(CCGame game, CharSequence text, int size, Color color) {
        super(text, newStyle(game.getAssetManager().newBitmapFont(size, color), size));
        mGame = game;
        bitmapFont = getStyle().font;
    }

    public CCGame getGame() {
        return mGame;
    }

    @Override
    public void setText(CharSequence newText) {
        super.setText(newText);
    }

    public void setText(CharSequence newText, int size) {
        setText(newText, size, bitmapFont.getColor());
    }

    public void setText(CharSequence newText, int size, Color color) {
        if (bitmapFont != null)
            bitmapFont.dispose();
        bitmapFont = mGame.getAssetManager().newBitmapFont(size, color);
        LabelStyle style = newStyle(bitmapFont, size);
        setStyle(style);
        super.setText(newText);
    }

    @Override
    public void dispose() {
        if (bitmapFont != null)
            bitmapFont.dispose();
    }
}
