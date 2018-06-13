package com.coocaa.ie.games.wc2018.pages.settlement;

import android.content.Context;

import com.coocaa.ie.R;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.pages.WC2018PageBaseDialog;
import com.coocaa.ie.games.wc2018.pages.settlement.m.SettlementModel;
import com.coocaa.ie.games.wc2018.pages.settlement.m.impl.SettlementModelImpl;
import com.coocaa.ie.games.wc2018.pages.settlement.p.SettlementPresenter;
import com.coocaa.ie.games.wc2018.pages.settlement.p.impl.SettlementPresenterImpl;
import com.coocaa.ie.games.wc2018.pages.settlement.v.SettlementView;
import com.coocaa.ie.games.wc2018.pages.settlement.v.impl.SettlementViewImpl;

public class SettlementDialog extends WC2018PageBaseDialog implements WC2018Game.WC2018GameCallback {
    private SettlementPresenter presenter;
    private SettlementModel model;
    private SettlementView view;

    public SettlementDialog(Context context, WC2018Game.WC2018GameComponent.Score score) {
        super(context, R.style.wc2018_settlement_dialog_translucent_notitlebar_fullscreen, "结束页");
        presenter = new SettlementPresenterImpl();
        model = new SettlementModelImpl();
        view = new SettlementViewImpl(context);
        setContentView(view.getView());
        model.create(context);
        presenter.create(this, view, model, score);
        view.create(presenter);
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        view.destroy();
        presenter.destroy();
        model.destroy();
    }

    @Override
    public void onCreate(WC2018Game game) {

    }

    @Override
    public void onLoading(WC2018Game game, int progress) {
        presenter.onLoading(game, progress);
    }

    @Override
    public void onError(WC2018Game game, CCAssetManager.CCAssetManagerException exception) {
        presenter.onError(game, exception);
    }

    @Override
    public void onLoadingComplete(WC2018Game game) {
        presenter.onLoadingComplete(game);
    }

    @Override
    public void onDisposed(WC2018Game game) {
        presenter.onDisposed(game);
        dismiss();
    }
}
