package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;
import com.skyworth.util.imageloader.ImageLoader;

import java.util.List;

public class ADView extends LinearLayout {
    public static final int WIDTH = UI.div(347);
    public static final int HEIGHT = UI.div(372);

    public ADView(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }

    public void updateAD(final List<SettlementView.ADViewData> data) {
        if (data == null)
            return;
        GameApplicatoin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
                if (data.size() == 1) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WIDTH, HEIGHT);
                    final SettlementView.ADViewData _data = data.get(0);
                    View imageView = newAD(_data.onclick);
                    addView(imageView, params);
                    ImageLoader.getLoader().with(getContext()).load(Uri.parse(_data.source)).into(imageView);

                } else if (data.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WIDTH, UI.div(176));
                        if (i == 1)
                            params.topMargin = UI.div(20);
                        final SettlementView.ADViewData _data = data.get(i);
                        View imageView = newAD(_data.onclick);
                        addView(imageView, params);
                        ImageLoader.getLoader().with(getContext()).load(Uri.parse(_data.source)).into(imageView);
                    }
                }
            }
        });
    }

    private View newAD(final Runnable onclick) {
        View view = ImageLoader.getLoader().getView(getContext());
        view.setBackgroundColor(Color.argb(26, 255, 255, 255));
        view.setFocusable(true);
        view.setPadding(UI.div(3), UI.div(3), UI.div(3), UI.div(3));
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.id_wc2018_penalty_startpage_adfocus);
                } else {
                    v.setBackgroundColor(Color.argb(26, 255, 255, 255));
                }
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclick != null)
                    onclick.run();
            }
        });
        return view;
    }
}
