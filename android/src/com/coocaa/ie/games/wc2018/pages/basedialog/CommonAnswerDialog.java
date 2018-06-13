package com.coocaa.ie.games.wc2018.pages.basedialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.pages.WC2018PageBaseDialog;
import com.tianci.media.api.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YYM on 2018/5/15.
 */

public class CommonAnswerDialog extends WC2018PageBaseDialog {


    public Context mContext;
    private final int btn1Id = 0x01;
    private final int btn2Id = 0x02;
    private DialogButton btn1, btn2;
    private TextView updateTitle;
    public LinearLayout root;
    public FrameLayout dialogContent;
    private WC2018Game.WC2018GameComponent.Score score;
    private String dialogName;
    private ImageView voiceTips;
    private  RelativeLayout textLayout;

    public CommonAnswerDialog(Context context, String btn1Title, String btn2Title,
                              String updateRemind, WC2018Game.WC2018GameComponent.Score score,
                              String dialogName) {
        super(context, dialogName, false);
        this.score = score;
        mContext = context;
        this.dialogName = dialogName;
        initUI( btn1Title, btn2Title, updateRemind);
    }

    public void initUI( String btn1Title, String btn2Title, String updateRemind){
        root = new LinearLayout(mContext);
        root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_VERTICAL);

        dialogContent = new FrameLayout(mContext);
        LinearLayout.LayoutParams dcParams = new LinearLayout.LayoutParams(UI.div(1920), UI.div(371));
        dialogContent.setLayoutParams(dcParams);

        textLayout = new RelativeLayout(mContext);
        FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UI.div(85));
        textParams.setMargins(0, UI.div(180), 0, 0);
        textLayout.setLayoutParams(textParams);

        voiceTips = new ImageView(mContext);
        RelativeLayout.LayoutParams voiceParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        voiceTips.setLayoutParams(voiceParams);
        voiceParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textLayout.addView(voiceTips);
//        voiceTips.setFocusableInTouchMode(false);
//        voiceTips.setFocusable(false);
//        dialogContent.addView(textLayout);

        RelativeLayout btnMainLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams bmParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UI.div(154));
        bmParams.setMargins(0, UI.div(22), 0, 0);
        btnMainLayout.setLayoutParams(bmParams);

        LinearLayout btnLayout = new LinearLayout(mContext);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnLayout.setLayoutParams(btnParams);

        btn1 = new DialogButton(mContext);
        LinearLayout.LayoutParams btn1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btn1.setLayoutParams(btn1Params);
        btn1.setId(btn1Id);
        btn1.setText(btn1Title);

        btn2 = new DialogButton(mContext);
        LinearLayout.LayoutParams btn2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btn2Params.setMargins(UI.div(60), 0, 0, 0);
        btn2.setLayoutParams(btn2Params);
        btn2.setId(btn2Id);
        btn2.setText(btn2Title);

        btnLayout.addView(btn1);
        btnLayout.addView(btn2);
        btnMainLayout.addView(btnLayout);
        root.addView(dialogContent);
        root.addView(btnMainLayout);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);


        RelativeLayout wTitleLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams wTparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wTitleLayout.setLayoutParams(wTparams);

        updateTitle = new TextView(mContext);
        RelativeLayout.LayoutParams wsParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        wsParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        updateTitle.setLayoutParams(wsParams);
        updateTitle.setTextSize(UI.dpi(28));
        updateTitle.setTextColor(mContext.getResources().getColor(R.color.wc_color_wrong_red));
        updateTitle.setText(updateRemind);

        wTitleLayout.addView(updateTitle);
        root.addView(wTitleLayout);
        setContentView(root);
    }


    public void setBtnBgResource(int bgResource, int wrongResource, int focusResource){
        btn1.setBtnBgResource(bgResource, wrongResource, focusResource);
        btn2.setBtnBgResource(bgResource, wrongResource, focusResource);
    }

    public void setVoiceTipsRes(int res){
        voiceTips.setBackgroundResource(res);
        dialogContent.addView(textLayout);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case btn1Id:
                   onFirstButtonClick();
                    break;
                case btn2Id:
                    onSecondButtonClick();
                    break;
            }
        }
    };

   public  void onFirstButtonClick(){
       submitLogCollect(btn1.getText().toString());
   }

   public void onSecondButtonClick(){
       submitLogCollect(btn2.getText().toString());
   }


    public void updateButton1Title(String title){
        btn1.setTextTitle(title);
    }

    public void updateButton2Title(String title){
        btn2.setTextTitle(title);
    }

    public boolean getFirstBtnState(){
        return btn1.getIsWrongState();
    }

    public void updateBtn1WrongState(String btitle, String tips){
        updateTitle.setText(tips);
        btn1.setWrongStateBtnBg();
        btn1.setTextTitle(btitle);
    }

    public void updateBtn1NormalState(String btitle){
        updateTitle.setText("");
        btn1.setNormalStateBtnBg();
        btn1.setTextTitle(btitle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Log.d("YYMT", "Deal keycode back--");
            submitLogCollect(mContext.getResources().getString(R.string.wc2018_dialog_back));
            this.dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void submitLogCollect(String btnName){
        Map<String, String> logParams = new HashMap<>();
        if(score != null) {
            logParams.put("game_time", score.duration + "");
        }
        logParams.put("dialog_type", dialogName);
        logParams.put("button_name", btnName);

        CommonDataer.submit(mContext, "game_dialog_click_event", logParams);
    }
}
