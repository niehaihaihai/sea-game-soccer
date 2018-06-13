package com.coocaa.ie.games.wc2018.pages.basedialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;

/**
 * Created by YYM on 2018/5/15.
 */

public class DialogButton extends Button implements View.OnFocusChangeListener, View.OnClickListener {
    private Context mContext;
    private boolean isWrongState;

    public interface ButtonListener {
        public void onButtonFocusChanged(DialogButton button, boolean hasFocus);

        public void onButtonClick(DialogButton button);
    }

    public ButtonListener bListener;
//    private TextView title;
    private int bgResource, wrongResource, focusResource;


    public DialogButton(Context context) {
        super(context);
        enable();
        this.setOnClickListener(this);
        this.setOnFocusChangeListener(this);
        this.setBackgroundResource(bgResource);
        setTextColor(context.getResources().getColor(R.color.wc_color_white));
        setTextSize(UI.dpi(46));

    }

    public void setBtnBgResource(int bgResource, int wrongResource, int focusResource){
        this.bgResource = bgResource;
        this.wrongResource = wrongResource;
        this.focusResource = focusResource;
        this.setBackgroundResource(bgResource);
    }

    public void enable() {
        setEnabled(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void disable() {
        setEnabled(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    public void setButtonListener(ButtonListener listener) {
        this.bListener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            this.setBackgroundResource(focusResource);
        } else {
            this.setBackgroundResource(bgResource);
        }
        if (bListener != null)
            bListener.onButtonFocusChanged(this, hasFocus);
    }

    @Override
    public void onClick(View v) {
        if (bListener != null) {
            bListener.onButtonClick(this);
        }
    }

    public void setWrongStateBtnBg(){
        isWrongState = true;
        this.setBackgroundResource(wrongResource);
    }

    public void setNormalStateBtnBg(){
        isWrongState = false;
        this.setBackgroundResource(bgResource);
    }

    public void setTextTitle(String stitle){

        setText(stitle);
    }

    public boolean getIsWrongState(){
        return isWrongState;
    }

}
