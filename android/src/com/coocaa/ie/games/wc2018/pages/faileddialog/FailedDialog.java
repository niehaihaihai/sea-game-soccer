package com.coocaa.ie.games.wc2018.pages.faileddialog;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.pages.WC2018PageBaseDialog;
import com.coocaa.ie.games.wc2018.pages.basedialog.DialogButton;
import com.skyworth.ui.blurbg.BlurBgLayout;
import com.skyworth.ui.define.SkyTextSize;
import com.tianci.media.api.Log;

/**
 * Created by YYM on 2018/5/30.
 */

public class FailedDialog extends WC2018PageBaseDialog {
    private Context mContext;
    private final String TAG = "YYMT";
    public interface RefreshClickListener {

        public void onRefreshClick();
    }

    private RefreshClickListener rListener;

    public FailedDialog(Context context) {
        super(context, context.getResources().getString(R.string.wc2018_answer_load_failed_dialog_name), false);
        this.mContext = context;
        RelativeLayout contentLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams cParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(cParams);

        BlurBgLayout blurBgLayout = new BlurBgLayout(context);
        blurBgLayout.setPageType(BlurBgLayout.PAGETYPE.FIRST_PAGE);
        contentLayout.addView(blurBgLayout, new FrameLayout.LayoutParams(UI.div(1920), UI.div(1080)));
//        contentLayout.setBackgroundColor(Color.RED);

        LinearLayout main = new LinearLayout(context);
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        main.setLayoutParams(mParams);
        main.setBackgroundColor(Color.TRANSPARENT);
        main.setOrientation(LinearLayout.VERTICAL);

        ImageView icon = new ImageView(context);
        LinearLayout.LayoutParams icParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        icon.setLayoutParams(icParams);
        icParams.gravity = Gravity.CENTER;
        icon.setBackgroundResource(R.drawable.sky_status_icon_error);

        TextView remind = new TextView(context);
        LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rParams.topMargin = UI.div(20);
        rParams.gravity =  Gravity.CENTER;
        remind.setLayoutParams(rParams);
        remind.setBackgroundColor(Color.TRANSPARENT);
        remind.setTextSize(UI.dpi(SkyTextSize.t_5));
        remind.setTextColor(context.getResources().getColor(R.color.c_4));
        remind.setText(context.getResources().getString(R.string.wc2018_answer_load_content_failed));


        Button refreshBtn = new Button(context);
        LinearLayout.LayoutParams rbParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rbParams.topMargin = UI.div(40);
        rbParams.gravity = Gravity.CENTER;
        refreshBtn.setFocusable(true);
        refreshBtn.setTextSize(UI.dpi(SkyTextSize.t_5));
        refreshBtn.setTextColor(mContext.getResources().getColor(R.color.a_0));
        refreshBtn.setFocusableInTouchMode(true);
        refreshBtn.setLayoutParams(rbParams);
        refreshBtn.setOnClickListener(onClickListener);
        refreshBtn.setBackgroundResource(R.drawable.sky_focus_bg);
        refreshBtn.requestFocus();
        refreshBtn.setText(context.getResources().getString(R.string.wc2018_answer_load_failed_dialog_name));

        main.addView(icon);
        main.addView(remind);
        main.addView(refreshBtn);

        contentLayout.addView(main);
        this.setContentView(contentLayout);

    }

    public void setRefreshClickListener(RefreshClickListener listener){
        this.rListener = listener;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onRefresBtnClick();
        }
    };

    public void onRefresBtnClick(){
        Log.d(TAG, "onRefreshBtnClick---");
        if(rListener != null){
            rListener.onRefreshClick();
        }
    }
}
