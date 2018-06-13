package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.badlogic.gdx.Game;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.NumberImageView;
import com.skyworth.util.imageloader.ImageLoader;
import com.skyworth.util.imageloader.fresco.CoocaaFresco;

import java.util.Timer;
import java.util.TimerTask;

public class TitleView extends FrameLayout {
    private LinearLayout contentView;
    private View tips1View, tips2View;
    private NumberImageView valueView;

    public TitleView(@NonNull Context context) {
        super(context);
        {
            View view = ImageLoader.getLoader().getView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(view);
            ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(context, ViewConfig.viewConfig().titleViewBG))).into(view);
        }
        contentView = new LinearLayout(context);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setGravity(Gravity.CENTER);
        addView(contentView);
        {
            tips1View = ImageLoader.getLoader().getView(context);
            tips1View.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewConfig.viewConfig().titleViewText1Width, ViewConfig.viewConfig().titleViewText1Height);
            contentView.addView(tips1View, params);
            ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(context, ViewConfig.viewConfig().titleViewText1))).into(tips1View);
        }
        {
            valueView = new NumberImageView(context, ViewConfig.viewConfig().titleViewNumberFnt,
                    ViewConfig.viewConfig().titleViewNumberFntMargin,
                    ViewConfig.viewConfig().titleViewValueWidth, ViewConfig.viewConfig().titleViewValueHeight);
            contentView.addView(valueView);
        }
        {
            tips2View = ImageLoader.getLoader().getView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewConfig.viewConfig().titleViewText2Width, ViewConfig.viewConfig().titleViewText2Height);
            contentView.addView(tips2View, params);
            ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(context, ViewConfig.viewConfig().titleViewText2))).into(tips2View);
        }
    }

    private class RandomTimer extends Timer {
        private boolean bCancelled = false;

        public synchronized void start(final int start, final int end) {
            bCancelled = false;
            schedule(new TimerTask() {
                int value = -1;

                @Override
                public void run() {
                    if (value == -1 || value >= end)
                        value = start;
                    else
                        value += (float) (end - start) * Math.random() * 0.05f;
                    if (value > end)
                        value = end;
                    GameApplicatoin.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (RandomTimer.class) {
                                if (!bCancelled)
                                    setValue(value);
                            }
                        }
                    });
                }
            }, 0, 50);
        }

        @Override
        public synchronized void cancel() {
            bCancelled = true;
            super.cancel();
        }
    }

    private RandomTimer randomTimer;

    public synchronized void startRandom(int start, int end) {
        stopRandom();
        randomTimer = new RandomTimer();
        randomTimer.start(start, end);
    }

    public synchronized void stopRandom() {
        if (randomTimer != null) {
            randomTimer.cancel();
            randomTimer = null;
        }
    }

    public void setValue(final int score) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                valueView.setValue(score);
            }
        });
    }
}
