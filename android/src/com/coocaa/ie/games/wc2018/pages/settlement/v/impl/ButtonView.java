package com.coocaa.ie.games.wc2018.pages.settlement.v.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;

public class ButtonView extends LinearLayout {
    public static final int HEIGHT = UI.div(84);

    public interface ButtonViewOnClickListener {
        void onButton1Click();

        void onButton2Click();
    }

    private static class InnerButton extends Button {
        public static final int WIDTH = UI.div(322);
        private static OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    v.setBackgroundResource(ViewConfig.viewConfig().buttonViewFocusedBG);
                else
                    v.setBackgroundResource(ViewConfig.viewConfig().buttonViewUnfocusedBG);
            }
        };

        public InnerButton(Context context) {
            super(context);
            setOnFocusChangeListener(onFocusChangeListener);
            setBackgroundResource(ViewConfig.viewConfig().buttonViewUnfocusedBG);
            setPadding(UI.div(40), 0, UI.div(40), 0);
            setTextColor(Color.WHITE);
            setTextSize(UI.dpi(48));
            setGravity(Gravity.CENTER);
        }
    }

    private static final int[] TICKETS = new int[]{
            R.drawable.id_wc2018_answer_startpage_start_chance1,
            R.drawable.id_wc2018_answer_startpage_start_chance2,
            R.drawable.id_wc2018_answer_startpage_start_chance3,
            R.drawable.id_wc2018_answer_startpage_start_chance4,
            R.drawable.id_wc2018_answer_startpage_start_chance5,
            R.drawable.id_wc2018_answer_startpage_start_chance6,
            R.drawable.id_wc2018_answer_startpage_start_chance7,
            R.drawable.id_wc2018_answer_startpage_start_chance8,
            R.drawable.id_wc2018_answer_startpage_start_chance9,
            R.drawable.id_wc2018_answer_startpage_start_chance10,
            R.drawable.id_wc2018_answer_startpage_start_chance11,
            R.drawable.id_wc2018_answer_startpage_start_chance12,
            R.drawable.id_wc2018_answer_startpage_start_chance13,
            R.drawable.id_wc2018_answer_startpage_start_chance14,
            R.drawable.id_wc2018_answer_startpage_start_chance15,
            R.drawable.id_wc2018_answer_startpage_start_chance16,
            R.drawable.id_wc2018_answer_startpage_start_chance17,
            R.drawable.id_wc2018_answer_startpage_start_chance18,
            R.drawable.id_wc2018_answer_startpage_start_chance19,
            R.drawable.id_wc2018_answer_startpage_start_chance20,
    };

    private InnerButton button1, button2;

    public ButtonView(Context context, final ButtonViewOnClickListener listener) {
        super(context);
        setGravity(Gravity.CENTER);
        int height = HEIGHT;
        if (ViewConfig.viewConfig().buttonViewButtonHeight > 0)
            height = ViewConfig.viewConfig().buttonViewButtonHeight;
        {
            button1 = new InnerButton(context);
            button1.setText(R.string.wc2018_answer_settlement_button1_check_other);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onButton1Click();
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewConfig.viewConfig().buttonViewButtonWidth, height);
            addView(button1, params);
        }
        {
            button2 = new InnerButton(context);
            button2.setText(R.string.wc2018_answer_settlement_button2_exit);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onButton2Click();
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewConfig.viewConfig().buttonViewButtonWidth, height);
            params.leftMargin = UI.div(173);
            addView(button2, params);
        }
    }

    public void setButton1PlayAgain(final int ticket) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button1.setText(R.string.wc2018_answer_settlement_button1_play_again);

                try {
                    Drawable drawable = getContext().getResources().getDrawable(TICKETS[ticket - 1]);
                    drawable.setBounds(0, 0, UI.div(drawable.getIntrinsicWidth()), UI.div(drawable.getIntrinsicHeight()));
                    button1.setCompoundDrawablePadding(UI.div(-70));
                    button1.setCompoundDrawablesWithIntrinsicBounds(0, 0, TICKETS[ticket - 1], 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                button1.setCompoundDrawables(null, null, drawable, null);
            }
        });
    }

    public void setButton1CheckOther() {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button1.setText(R.string.wc2018_answer_settlement_button1_check_other);
            }
        });
    }

    public void setFocus() {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button1.requestFocus();
            }
        });
    }

    public void updateGameLoading(final int progress) {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String value = getContext().getResources().getString(R.string.wc2018_answer_settlement_button1_reloading, String.valueOf(progress));
                button1.setText(value);
                button1.setCompoundDrawables(null, null, null, null);
            }
        });
    }

    public void disableButton1() {
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button1.setEnabled(false);
            }
        });
    }
}
