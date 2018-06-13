package com.coocaa.ie.core.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class GameApplicatoin extends Application {
    private static Handler UI_HANDLER;

    public static final void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper())
            runnable.run();
        else
            UI_HANDLER.post(runnable);
    }

    public static final void runOnUiThread(long delay, Runnable runnable) {
        UI_HANDLER.postDelayed(runnable, delay);
    }

    public static final void quit() {
        Process.killProcess(Process.myPid());
    }


    public static final int getWindowWidth() {
        return UI.getWindowWidth();
    }

    public static final int getWindowHeight() {
        return UI.getWindowHeight();
    }

    private static Context context;

    public static final Context getContext() {
        return context;
    }

    public static final ActivityManager.MemoryInfo getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        return outInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UI.init(this);
        UI_HANDLER = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = this;
    }
}
