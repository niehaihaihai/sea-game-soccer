package com.coocaa.ie.games.wc2018.pages.startup;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;

/**
 * Created by Sea on 2018/5/21.
 */

public class StartLoadBar extends LinearLayout{

    private ImageView back;
    private ImageView bar;
    private int TOTAL = UI.div(800);
    private TextView progressText;

    public StartLoadBar(Context context) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);

        FrameLayout seekbarContent = new FrameLayout(context);
        addView(seekbarContent);

        back = new ImageView(context);
        bar = new ImageView(context);
        FrameLayout.LayoutParams back_p = new FrameLayout.LayoutParams(TOTAL, ViewGroup.LayoutParams.WRAP_CONTENT);
        back.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_progress_back);
        seekbarContent.addView(back, back_p);

        FrameLayout.LayoutParams bar_p = new FrameLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        bar.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_progress_bar);
        seekbarContent.addView(bar, bar_p);

        progressText = new TextView(context);
        progressText.setTextColor(Color.WHITE);
        progressText.setTextSize(UI.dpi(28));
        progressText.setText("  0%");
        FrameLayout.LayoutParams progressText_p = new FrameLayout.LayoutParams(UI.div(80), ViewGroup.LayoutParams.WRAP_CONTENT);
        progressText_p.leftMargin = UI.div(20);
        addView(progressText, progressText_p);
    }

    public void setProgress(int percent) {
        int curWidth = TOTAL * percent / 100;
        bar.setLayoutParams(new FrameLayout.LayoutParams(curWidth, FrameLayout.LayoutParams.WRAP_CONTENT));
        progressText.setText("  "+percent+"%");
        invalidate();
    }
}
