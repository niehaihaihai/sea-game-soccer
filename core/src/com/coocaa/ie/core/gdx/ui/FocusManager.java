package com.coocaa.ie.core.gdx.ui;

public interface FocusManager {
    void addFocusable(Focusable focusable);

    Focusable getCurrentFocusable();

    boolean requestFocus(Focusable focusable);
}
