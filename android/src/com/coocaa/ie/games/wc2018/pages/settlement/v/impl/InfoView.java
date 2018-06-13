package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.android.NumberImageView;
import com.coocaa.ie.core.android.UI;
import com.skyworth.util.imageloader.ImageLoader;
import com.skyworth.util.imageloader.fresco.CoocaaFresco;

public class InfoView extends LinearLayout {
    private static class BadgesView extends LinearLayout {
        public static final int WIDTH = UI.div(343);
        public static final int HEIGHT = ADView.HEIGHT;
        private View badgeView;
        private Button shareButton;

        public BadgesView(Context context) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);
            setGravity(Gravity.CENTER);
            {
                badgeView = ImageLoader.getLoader().getView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(UI.div(285), UI.div(261));
                addView(badgeView, params);
            }
            {
                shareButton = new Button(context);
                shareButton.setPadding(0, 0, 0, 0);
                shareButton.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                            shareButton.setBackgroundResource(ViewConfig.viewConfig().infoViewButtonShareFocusedBG);
                        else
                            shareButton.setBackgroundResource(ViewConfig.viewConfig().infoViewButtonShareUnfocusedBG);
                    }
                });
                shareButton.setGravity(Gravity.CENTER);
                shareButton.setText("分享战绩");
                shareButton.setTextColor(Color.WHITE);
                shareButton.setTextSize(UI.dpi(24));
                shareButton.setBackgroundResource(ViewConfig.viewConfig().infoViewButtonShareUnfocusedBG);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewConfig.viewConfig().infoViewButtonShareWidth, ViewConfig.viewConfig().infoViewButtonShareHeight);
                addView(shareButton, params);
            }
        }

        public void updateBadge(final int level) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int _level = level - 1;
                    if (_level >= ViewConfig.viewConfig().infoViewBadges.length)
                        _level = ViewConfig.viewConfig().infoViewBadges.length - 1;
                    ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(getContext(), ViewConfig.viewConfig().infoViewBadges[_level]))).into(badgeView);
                }
            });
        }

        public void setShareOnclickListener(OnClickListener listener) {
            shareButton.setOnClickListener(listener);
        }
    }

    private static class ContentView extends FrameLayout {
        public static final int WIDTH = UI.div(700);
        public static final int HEIGHT = ADView.HEIGHT;
        private TextView title1View, title2View, tip1View, tip2View, tip3View;
        private NumberImageView value1View, value2View;
        private TextView stampView;

        public ContentView(Context context) {
            super(context);
            initTitleValue();
            initTip();
            initStamp();
        }

        private FrameLayout newTitleValue(int left, int top) {
            FrameLayout layout = new FrameLayout(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UI.div(396), UI.div(50));
            params.leftMargin = left;
            params.topMargin = top;
            addView(layout, params);
            return layout;
        }

        private void initTitleValue() {
            int[] font = ViewConfig.viewConfig().infoViewValueFnt;
            int fWidth = UI.div(20);
            int fHeight = UI.div(32);
            {
                FrameLayout layout = newTitleValue(UI.div(71), UI.div(92));
                {
                    title1View = new TextView(getContext());
                    title1View.setGravity(Gravity.CENTER);
                    title1View.setText(ViewConfig.viewConfig().infoViewTitle1);
                    title1View.setTextColor(Color.WHITE);
                    title1View.setTextSize(UI.dpi(40));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                    layout.addView(title1View, params);
                }
                {
                    value1View = new NumberImageView(getContext(), font, 0, fWidth, fHeight);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                    layout.addView(value1View, params);
                }
            }
            {
                FrameLayout layout = newTitleValue(UI.div(71), UI.div(169));
                {
                    title2View = new TextView(getContext());
                    title2View.setGravity(Gravity.CENTER);
                    title2View.setText(ViewConfig.viewConfig().infoViewTitle2);
                    title2View.setTextColor(Color.WHITE);
                    title2View.setTextSize(UI.dpi(40));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                    layout.addView(title2View, params);
                }
                {
                    value2View = new NumberImageView(getContext(), font, 0, fWidth, fHeight);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                    layout.addView(value2View, params);
                }
            }
        }

        public void setValue1(final int value1) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    value1View.setValue(value1);
                }
            });
        }

        public void setValue2(final int value2) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    value2View.setValue(value2);
                }
            });
        }

        public void setDefeat(final int value) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    stampView.setVisibility(View.VISIBLE);
                    stampView.setText(getResources().getString(R.string.wc2018_answer_settlement_info_defeat, value));
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.wc2018_settlement_stamb_scale);
                    animation.setFillEnabled(true);
                    animation.setFillAfter(true);
                    stampView.startAnimation(animation);
                }
            });
        }

        public void setThisCoins(final int coins) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = getResources().getString(R.string.wc2018_settlement_info_tips2, String.valueOf(coins));
                    tip2View.setText(str);
                }
            });
        }

        //还差xx题/球可再拿xx酷币
        public void setTips1(final String toast) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    String text = getResources().getString(R.string.wc2018_settlement_info_tips1, String.valueOf(score), u, String.valueOf(coins));
                    tip1View.setText(toast);
                }
            });
        }

        public void setRank(final int rank, final int rankdiff) {
            GameApplicatoin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = getResources().getString(R.string.wc2018_settlement_info_tips3, String.valueOf(rank));
                    if (rank > 10000)
                        str = getResources().getString(R.string.wc2018_settlement_info_tips3, "1W+");
                    tip3View.setText(str);
                    if (rank <= 10000) {
                        SpannableString _str = null;
                        if (rankdiff < 0) {
                            String ss = " " + getResources().getString(R.string.wc2018_setlement_info_arraw_up) + Math.abs(rankdiff);
                            _str = new SpannableString(ss);

                            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(UI.dpi(24));
                            _str.setSpan(colorSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            _str.setSpan(sizeSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (rankdiff > 0) {
                            String ss = " " + getResources().getString(R.string.wc2018_setlement_info_arraw_down) + Math.abs(rankdiff);
                            _str = new SpannableString(ss);
                            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.GREEN);
                            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(UI.dpi(24));
                            _str.setSpan(colorSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            _str.setSpan(sizeSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        if (!TextUtils.isEmpty(_str))
                            tip3View.append(_str);
                    }
                }
            });
        }

        private void initTip() {
            {
                tip1View = new TextView(getContext());
                tip1View.setTextSize(UI.dpi(22));
                tip1View.setTextColor(Color.argb(127, 255, 255, 255));
//                tip1View.setText(R.string.wc2018_settlement_info_tips1);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = UI.div(380);
                params.topMargin = UI.div(225);
                addView(tip1View, params);
            }
            {
                tip2View = new TextView(getContext());
                tip2View.setTextSize(UI.dpi(28));
                tip2View.setTextColor(Color.argb(127, 255, 255, 255));
//                tip2View.setText(getResources().getString(R.string.wc2018_settlement_info_tips2, "..."));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = UI.div(73);
                params.topMargin = UI.div(288);
                addView(tip2View, params);
            }
            {
                tip3View = new TextView(getContext());
                tip3View.setTextSize(UI.dpi(28));
                tip3View.setTextColor(Color.argb(127, 255, 255, 255));
//                tip3View.setText(R.string.wc2018_settlement_info_tips3);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = UI.div(423);
                params.topMargin = UI.div(288);
                addView(tip3View, params);
            }
        }

        private void initStamp() {
            stampView = new TextView(getContext());
            stampView.setGravity(Gravity.CENTER);
//            stampView.setRotation(-25);
            stampView.setTextColor(Color.WHITE);
            stampView.setTextSize(UI.dpi(24));
            stampView.setBackgroundResource(ViewConfig.viewConfig().infoViewStamb);
            stampView.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UI.div(151), UI.div(132));
            params.leftMargin = UI.div(517);
            params.topMargin = UI.div(26);
            addView(stampView, params);
        }
    }

    public static final int WIDTH = UI.div(1044);
    public static final int HEIGHT = ViewConfig.viewConfig().infoViewHeight;
    private BadgesView badgesView;
    private ImageView lineView;
    private ContentView contentView;

    public InfoView(Context context) {
        super(context);
        setGravity(Gravity.BOTTOM);
        if (ViewConfig.check(ViewConfig.viewConfig().infoViewBG))
            setBackgroundResource(ViewConfig.viewConfig().infoViewBG);
        else if (ViewConfig.check(ViewConfig.viewConfig().infoViewBGColor))
            setBackgroundColor(ViewConfig.viewConfig().infoViewBGColor);
        {
            badgesView = new BadgesView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(BadgesView.WIDTH, BadgesView.HEIGHT);
            addView(badgesView, params);
        }
        {
            lineView = new ImageView(context);
            lineView.setBackgroundColor(Color.argb(51, 255, 255, 255));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, UI.div(214));
            params.gravity = Gravity.CENTER_VERTICAL;
            addView(lineView, params);
        }
        {
            contentView = new ContentView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ContentView.WIDTH, ContentView.HEIGHT);
            addView(contentView, params);
        }
    }

    public void updateBadge(int level) {
        badgesView.updateBadge(level);
    }

    public void setValue1(final int value1) {
        contentView.setValue1(value1);
    }

    public void setValue2(final int value2) {
        contentView.setValue2(value2);
    }

    public void setDefeat(int value) {
        contentView.setDefeat(value);
    }

    public void setThisCoins(int coins) {
        contentView.setThisCoins(coins);
    }

    public void setRank(int rank, int rankdiff) {
        contentView.setRank(rank, rankdiff);
    }

    public void setTips1(String toast) {
        contentView.setTips1(toast);
    }

    public void setShareOnclickListener(OnClickListener listener) {
        badgesView.setShareOnclickListener(listener);
    }
}