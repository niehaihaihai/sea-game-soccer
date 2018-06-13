package com.coocaa.ie.core.gdx.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCLabel;

public class CCTextView extends CCView {
    private static final Actor PADDING_ACTOR = new Actor();
    protected CCLabel label;

    public CCTextView(CCGame game, CharSequence text) {
        this(game, text, 24, Color.WHITE);
    }

    public CCTextView(CCGame game, CharSequence text, int size) {
        this(game, text, size, Color.WHITE);
    }

    public CCTextView(CCGame game, CharSequence text, int size, Color color) {
        super(game);
        label = new CCLabel(game, text, size, color);
        label.setAlignment(Align.center);
        label.setWrap(true);
        add(label);
    }

    public void setTextPadding(int top, int left, int bottom, int right) {
        getCell(label).pad(top, left, bottom, right);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
//        label.setSize(width, height);
    }

    public void setText(CharSequence newText) {
        label.setText(newText);
    }

    public void setText(CharSequence newText, int size) {
        label.setText(newText, size);
    }

    public void setText(CharSequence newText, int size, Color color) {
        label.setText(newText, size, color);
    }

    @Override
    public float getPrefWidth() {
        float width = super.getPrefWidth();
        if (label != null) width = Math.max(width, label.getWidth());
        return width;
    }

    @Override
    public float getPrefHeight() {
        float height = super.getPrefHeight();
        if (label != null) height = Math.max(height, label.getHeight());
        return height;
    }

    @Override
    public void dispose() {
        super.dispose();
        label.dispose();
    }
//
//    @Override
//    public void layout() {
//        super.layout();
//        float pw = getPrefWidth(), ph = getPrefHeight();
//        float w = label.getPrefWidth(), h = label.getPrefHeight();
//        float x = (pw - w) / 2, y = (ph - h) / 2;
//        label.setPosition(x, y);
//        Gdx.app.log("CCTextView", "setText pref.size:" + pw + "x" + ph + "  lable.size:" + w + "x" + h + "  lable.pos:" + x + "," + y);
//    }
}
