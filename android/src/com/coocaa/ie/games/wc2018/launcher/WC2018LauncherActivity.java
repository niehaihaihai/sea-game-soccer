package com.coocaa.ie.games.wc2018.launcher;

import android.view.KeyEvent;

import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.launcher.impl.WC2018LauncherActivityImpl;
import com.umeng.analytics.MobclickAgent;

public class WC2018LauncherActivity extends WC2018LauncherActivityImpl {
    public static final String EXTRA_GAME = "extra.game";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            WC2018GameController.getController().getGame().getWC2018GameComponent().handleQuitGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
