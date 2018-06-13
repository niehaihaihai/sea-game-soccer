package com.coocaa.ie.games.wc2018.answer.assets;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpDataLoader;

public class AnswerHttpDataLoader extends WC2018HttpDataLoader<AnswerData> {
    public AnswerHttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver, HeaderLoader headerLoader) {
        super(assetManager, resolver, headerLoader, AnswerData.class);
    }

    @Override
    protected WC2018HttpBaseData parseOnSuccess(String source) {
        WC2018HttpBaseData data = super.parseOnSuccess(source);
        AnswerData _data = data.getObject();
        WC2018GameController.getController().updateCode(_data.lotteryCode);
        return data;
    }
}
