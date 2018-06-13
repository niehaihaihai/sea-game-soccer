package com.coocaa.ie.games.wc2018.pages.quit;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;

import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.pages.basedialog.CommonAnswerDialog;
import com.coocaa.ie.games.wc2018.pages.basedialog.DialogResConfig;
import com.skyworth.util.imageloader.ImageLoader;
import com.skyworth.util.imageloader.fresco.CoocaaFresco;
import com.tianci.media.api.Log;

public class QuitDialog extends CommonAnswerDialog {
    private Runnable mCancelCallback;
    private Context mContext;
    public QuitDialog(Context context, final Runnable cancelCallback) {
        super(context,
                context.getResources().getString(R.string.wc2018_quite_dialog_cancle), context.getResources().getString(R.string.wc2018_quite_dialog_sure),
                "", null, context.getResources().getString(R.string.wc2018_quite_dialog_name));
        mCancelCallback = cancelCallback;
        this.mContext = context;
        DialogResConfig.initViewConfig(WC2018GameController.getController().getGameName());
        initBtnBg();
    }

    public void initBtnBg(){

        View bgView = ImageLoader.getLoader().getView(mContext);
        bgView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        dialogContent.addView(bgView);
        ImageLoader.getLoader().with(getContext()).load(Uri.parse(CoocaaFresco.getFrescoResUri(mContext, DialogResConfig.resConfig().quiteViewBg))).into(bgView);
        setBtnBgResource( DialogResConfig.resConfig().buttonReviveUnFocuse, DialogResConfig.resConfig().buttonReviveWrong,  DialogResConfig.resConfig().buttonReviveFocuse);
        root.setBackgroundColor(mContext.getResources().getColor(DialogResConfig.resConfig().reviveViewBgColor));

    }

    @Override
    public void onFirstButtonClick() {
        super.onFirstButtonClick();
        dismiss();
        if (mCancelCallback != null)
            mCancelCallback.run();
        Log.d("YYMT", "QuitDialog first btn click");

    }

    @Override
    public void onSecondButtonClick() {
        super.onSecondButtonClick();
        CoocaaIEApplication.quit();
        Log.d("YYMT", "QuitDialog second btn click");
    }
}
