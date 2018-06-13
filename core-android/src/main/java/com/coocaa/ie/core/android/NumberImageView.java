package com.coocaa.ie.core.android;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class NumberImageView extends LinearLayout {
    private int[] font;
    private int margin = 0;
    private int fontWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int fontHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public NumberImageView(Context context, int[] font) {
        super(context);
        init(font, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public NumberImageView(Context context, int[] font, int margin) {
        super(context);
        init(font, margin, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public NumberImageView(Context context, int[] font, int margin, int fontWidth, int fontHeight) {
        super(context);
        init(font, margin, fontWidth, fontHeight);
    }

    private void init(int[] font, int margin, int fontWidth, int fontHeight) {
        this.font = font;
        this.margin = margin;
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;
    }

    public void setValue(final int value) {
        GameApplicatoin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Integer> rr = new ArrayList<>();
                int _value = value;
                if (_value >= 10)
                    while (_value > 0) {
                        int nn = _value % 10;
                        rr.add(font[nn]);
                        _value /= 10;
                    }
                else {
                    rr.add(font[_value]);
                }
                removeAllViews();
                for (int i = rr.size() - 1; i >= 0; i--) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(rr.get(i));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(fontWidth, fontHeight);
                    if (i != rr.size() - 1)
                        params.leftMargin = margin;
                    else
                        params.leftMargin = 0;
                    addView(imageView, params);
                }
            }
        });
    }
}
