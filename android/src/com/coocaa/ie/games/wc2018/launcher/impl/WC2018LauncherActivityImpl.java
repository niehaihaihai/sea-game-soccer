package com.coocaa.ie.games.wc2018.launcher.impl;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.dataer.api.SkyDataer;
import com.coocaa.dataer.api.event.page.lifecycle.PageProperty;
import com.coocaa.ie.AndroidCCGameSystem;
import com.coocaa.ie.BuildConfig;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;
import com.coocaa.ie.games.wc2018.WC2018Config;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.skyworth.util.imageloader.ImageLoader;
import com.tianci.webservice.framework.CommonHeader;

import java.util.Map;

import static com.coocaa.ie.games.wc2018.launcher.WC2018LauncherActivity.EXTRA_GAME;

public class WC2018LauncherActivityImpl extends AndroidApplication implements WC2018Game.WC2018GameCallback, WC2018GameComponentController.WC2018GameCallbackMonitor {
    private static final HttpDataLoader.HeaderLoader headerLoader = new HttpDataLoader.HeaderLoader() {
        @Override
        public Map<String, String> load() {
            Map<String, String> headers = CommonHeader.getInstance().getCommonHeaderMap(GameApplicatoin.getContext(), "");
            try {
                headers.put("cOpenId", CCConnecterManager.getManager().getCCConnecter().getOpenID());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                headers.put("accessToken", CCConnecterManager.getManager().getCCConnecter().getTokenId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return headers;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.getLoader().init(getApplication());
        WC2018GameComponentController.controller = new WC2018GameComponentController(this, this);
        String game = getIntent().getStringExtra(EXTRA_GAME);
        if (!WC2018GameController.start(game,
                new AndroidCCGameSystem(),
                CoocaaIEApplication.getWindowWidth(), CoocaaIEApplication.getWindowHeight(),
                this, WC2018GameComponentController.controller, WC2018Config.getGameServer()
                , BuildConfig.DEBUG))
            finish();
        else {
            WC2018GameController.getController().setHeaderLoader(headerLoader);
//            WC2018GameComponentController.controller.handleStartGame(game);
//            WC2018Game.WC2018GameCallback startupDialog = new StartupDialog2(this);
//            setCallback(startupDialog);
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            try {
                config.numSamples = 2;
                initialize(WC2018GameController.getController().getGame(), config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private PageProperty pageProperty;

    @Override
    protected void onResume() {
        super.onResume();
        if (pageProperty == null) {
            WC2018GameComponentController.controller.handleStartGame(WC2018GameController.getController().getGameName());
            pageProperty = new PageProperty("WC2018." + WC2018GameController.getController().getGameName());
        }
        SkyDataer.onEvent().pageEvent().pageResumeEvent().onResume(pageProperty);
    }

    @Override
    protected void onPause() {
        SkyDataer.onEvent().pageEvent().pagePausedEvent().onPaused(pageProperty);
        super.onPause();
    }

    @Override
    public void onCreate(final WC2018Game game) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onCreate(game);
            }
        });
    }

    @Override
    public void onLoading(final WC2018Game game, final int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onLoading(game, progress);
            }
        });
    }

    @Override
    public void onError(final WC2018Game game, final CCAssetManager.CCAssetManagerException exception) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(game, exception);
            }
        });
    }

    @Override
    public void onLoadingComplete(final WC2018Game game) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onLoadingComplete(game);
            }
        });
    }

    @Override
    public void onDisposed(final WC2018Game game) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onDisposed(game);
                CoocaaIEApplication.quit();
            }
        });
    }

    private WC2018Game.WC2018GameCallback callback;

    @Override
    public void setCallback(WC2018Game.WC2018GameCallback callback) {
        this.callback = callback;
    }
}
