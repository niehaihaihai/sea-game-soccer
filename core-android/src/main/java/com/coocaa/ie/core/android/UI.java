package com.coocaa.ie.core.android;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class UI {
    private static final Point window = new Point();
    private static float mDiv = 1;
    private static float mDpi = 1;

    static synchronized void init(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        display.getSize(window);
        int height = getWindowHeight();
        int width = getWindowWidth();
        float density = getDisplayDensity(context);
        mDiv = (float) width / 1920.0f;
        mDpi = mDiv / density;
        Log.d("UI", String.format("init %dx%d density:%f div:%f dpi:%f", width, height, density, mDiv, mDpi));
    }


    /**
     * 概述：得到当前density<br/>
     *
     * @param context
     * @return float
     * @date 2013-12-20
     */
    private static float getDisplayDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    public static float getDiv() {
        return mDiv;
    }

    public static float getDpi() {
        return mDpi;
    }

    public static void scale(View view) {
        view.setScaleX(mDiv);
        view.setScaleY(mDiv);
    }

    //layout
    public static int div(int x) {
        return (int) (x * mDiv);
    }

    //font size
    public static int dpi(int x) {
        return (int) (x * mDpi);
    }

    public static final int getWindowWidth() {
        return window.x;
    }

    public static final int getWindowHeight() {
        return window.y;
    }
}
