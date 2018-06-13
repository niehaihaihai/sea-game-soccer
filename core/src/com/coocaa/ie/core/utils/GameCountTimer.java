package com.coocaa.ie.core.utils;

import java.util.Timer;
import java.util.TimerTask;

public class GameCountTimer {
    public interface GameCountTimerListener {
        void onTimeUpdate(int time);

        void onTimeOut();
    }

    private Timer timer;
    private int time = 10;
    private boolean bPaused = false;

    public synchronized void start(int time, final GameCountTimerListener listener) {
        release();
        if (timer == null) {
            bPaused = false;
            this.time = time;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    synchronized (GameCountTimer.this) {
                        if (bPaused)
                            return;
                        GameCountTimer.this.time--;
                        if (listener != null)
                            listener.onTimeUpdate(GameCountTimer.this.time);
                        if (GameCountTimer.this.time <= 0) {
                            release();
                            if (listener != null)
                                listener.onTimeOut();
                        }
                    }
                }
            }, 0, 1000);
        }
    }

    public synchronized void pause() {
        bPaused = true;
    }

    public synchronized void resume() {
        bPaused = false;
    }

    public synchronized void plusTime(int ptime) {
        if (GameCountTimer.this.time > 0)
            GameCountTimer.this.time += ptime;
    }

    public synchronized void release() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
