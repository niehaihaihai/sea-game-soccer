package com.coocaa.ie.games.wc2018.utils.web.ad;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.coocaa.ie.core.gdx.CCAssetManager;

public class ADHttpDataLoader extends BaseADHttpDataLoader<AdData> {
    public ADHttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver, HeaderLoader headerLoader) {
        super(assetManager, resolver, headerLoader, AdData.class);
    }

    @Override
    protected BaseAdData parseOnSuccess(String source) {
        BaseAdData data = super.parseOnSuccess(source);
        return data;
    }
}
