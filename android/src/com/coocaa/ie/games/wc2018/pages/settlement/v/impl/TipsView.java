package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.coocaa.ie.core.android.UI;

public class TipsView extends TextView {
    public TipsView(Context context) {
        super(context);
        setTextSize(UI.dpi(36));
        setTextColor(ViewConfig.viewConfig().tipsViewTextColor);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            if (ViewConfig.check(ViewConfig.viewConfig().tipsViewBG))
                setBackgroundResource(ViewConfig.viewConfig().tipsViewBG);
        }
        super.setText(text, type);
    }
}
