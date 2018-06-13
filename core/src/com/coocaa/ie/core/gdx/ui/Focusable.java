package com.coocaa.ie.core.gdx.ui;

public interface Focusable {
    interface OnFocusChangeListener {
        void onFocusChanged(com.coocaa.ie.core.gdx.ui.CCView view, boolean hasFocus);
    }

    void setFocusable(boolean flag);

    boolean isFocusable();

    void clearFocus();

    boolean hasFocus();

    void requestFocus();

    void setFocusManager(com.coocaa.ie.core.gdx.ui.FocusManager manager);

    void setOnFocusChangeListener(OnFocusChangeListener listener);
}
