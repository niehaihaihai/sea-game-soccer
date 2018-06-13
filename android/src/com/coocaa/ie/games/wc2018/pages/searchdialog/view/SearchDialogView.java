package com.coocaa.ie.games.wc2018.pages.searchdialog.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.skyworth.util.Util;

/**
 * Created by Eric on 2018/5/30.
 */

public class SearchDialogView extends FrameLayout {
    private Context mContext = null;
    private TextView contentTxt = null;
    private ImageView bg = null;
    private FrameLayout searchingLayout = null;
    private FrameLayout resultLayout = null;
    private AnimatorSet as = null;
    private ImageView left, right;
    private String qus = "";
    private String ans = "";

    public SearchDialogView(Context context, String q, String a) {
        super(context);
        mContext = context;
        qus = q;
        ans = a;
        initView();
    }

    private void initView() {
        bg = new ImageView(getContext());
        bg.setBackgroundResource(R.drawable.id_wc2018_search_dialog_bg);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.BOTTOM;
        addView(bg, layoutParams);
        addTextLine();
        addSearchingLayout(true);
    }

    private void addTextLine() {
        contentTxt = new TextView(mContext);
        contentTxt.setTextSize(Util.Div(24));
        contentTxt.setText("正在查询答案，请稍候...");
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Util.Div(100));
        lp.topMargin = Util.Div(300);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        addView(contentTxt, lp);
    }

    private void addSearchingLayout(boolean isSearching) {
        Log.d("demo", "addSearchingLayout isSearching: " + isSearching);
        if (searchingLayout == null) {
            searchingLayout = new FrameLayout(mContext);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.topMargin = Util.Div(300 + 60);
            addView(searchingLayout, lp);

            addTwoDot(searchingLayout);
        }

        if (resultLayout == null) {
            resultLayout = new FrameLayout(mContext);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.topMargin = Util.Div(300 + 60);
            addView(resultLayout, lp);

            TextView tv = new TextView(mContext);
            tv.setTextSize(Util.Div(24));
            tv.setText("参考答案：" + ans);
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            resultLayout.addView(tv, lp);
        }

        if (isSearching) {
            if (resultLayout != null)
                resultLayout.setVisibility(INVISIBLE);
            searchingLayout.setVisibility(VISIBLE);
            startRuning();
        } else {
            if (searchingLayout != null)
                searchingLayout.setVisibility(INVISIBLE);
            resultLayout.setVisibility(VISIBLE);
        }
    }

    private void addTwoDot(FrameLayout fl) {
        left = new ImageView(mContext);
        left.setBackgroundResource(R.drawable.id_wc2018_search_dialog_right_dot);
        left.setVisibility(INVISIBLE);
        LayoutParams lp = new LayoutParams(Util.Div(200), Util.Div(100));
        lp.leftMargin = Util.Div(380);
        fl.addView(left, lp);


        right = new ImageView(mContext);
        right.setBackgroundResource(R.drawable.id_wc2018_search_dialog_left_dot);
        right.setVisibility(INVISIBLE);
        lp = new LayoutParams(Util.Div(200), Util.Div(100));
        lp.leftMargin = Util.Div(380 + 1000);
        fl.addView(right, lp);
    }

    private void startRuning() {
        left.setVisibility(VISIBLE);
        ObjectAnimator left_translate = ObjectAnimator.ofFloat(left, "translationX", Util.Div(0), Util.Div(1000));
        left_translate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                left.setVisibility(INVISIBLE);
                right.setVisibility(VISIBLE);
            }
        });
        ObjectAnimator right_translate = ObjectAnimator.ofFloat(right, "translationX", Util.Div(0), Util.Div(-1000));
        right_translate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                right.setVisibility(INVISIBLE);
                left.setVisibility(VISIBLE);
            }
        });
        as = new AnimatorSet();
        as.playSequentially(left_translate, right_translate);
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                as.start();
            }

        });
        as.setDuration(800);
        as.setInterpolator(null);
        as.start();
    }

    public void finishAnimation() {
        as.cancel();
        contentTxt.setText("问题：" + qus);
        addSearchingLayout(false);
    }

}
