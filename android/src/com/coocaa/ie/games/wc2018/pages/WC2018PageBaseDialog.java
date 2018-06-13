package com.coocaa.ie.games.wc2018.pages;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;

import com.coocaa.dataer.api.SkyDataer;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;

public abstract class WC2018PageBaseDialog extends Dialog {
    private String name;
    private boolean exitWithBackKey = true;
    private Context mContext;

    public WC2018PageBaseDialog(Context context) {
        this(context, "");
    }

    public WC2018PageBaseDialog(Context context, int themeResId, String name) {
        super(context, themeResId);
        this.name = name;
        this.mContext = context;
    }

    public WC2018PageBaseDialog(Context context, String name) {
        super(context, R.style.wc2018_dialog_translucent_notitlebar_fullscreen);
        this.name = name;
        this.mContext = context;
    }

    public WC2018PageBaseDialog(Context context, String name, boolean exitWithBackKey) {
        super(context, R.style.wc2018_dialog_translucent_notitlebar_fullscreen);
        this.name = name;
        this.exitWithBackKey = exitWithBackKey;
        this.mContext = context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        CommonDataer.pageShow(mContext, name);
    }

    @Override
    protected void onStop() {
        CommonDataer.pageHide(mContext, name);
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (exitWithBackKey && keyCode == KeyEvent.KEYCODE_BACK) {
            CoocaaIEApplication.quit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
