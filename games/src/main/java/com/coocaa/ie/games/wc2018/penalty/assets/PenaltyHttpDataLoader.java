package com.coocaa.ie.games.wc2018.penalty.assets;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpDataLoader;

public class PenaltyHttpDataLoader extends WC2018HttpDataLoader<PenaltyData> {
    public PenaltyHttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver, HeaderLoader headerLoader) {
        super(assetManager, resolver, headerLoader, PenaltyData.class);
    }

    @Override
    protected WC2018HttpBaseData parseOnSuccess(String source) {
        WC2018HttpBaseData data = super.parseOnSuccess(source);
        PenaltyData _data = data.getObject();
        WC2018GameController.getController().updateCode(_data.lotteryCode);
        return data;
    }
}
