package com.coocaa.ie.games.wc2018.utils.web.call;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;

import java.io.Serializable;

public abstract class WC2018HttpDataLoader<T extends Serializable> extends HttpDataLoader<WC2018HttpBaseData> {
    private Class<T> clazz;

    public WC2018HttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver, HeaderLoader headerLoader, Class<T> clazz) {
        super(assetManager, resolver);
        this.clazz = clazz;
        setHeaderLoader(headerLoader);
        setRetryTimes(5);
    }

    @Override
    protected WC2018HttpBaseData parseOnSuccess(String source) {
        WC2018HttpBaseData data = WC2018HttpBaseData.parse(source);
        data.parseObject(clazz);
        return data;
    }

    @Override
    protected WC2018HttpBaseData parseOnFailed(String source, Throwable throwable) {
        WC2018HttpBaseData data = new WC2018HttpBaseData();
        data.data = throwable.getMessage();
        data.source = source;
        return data;
    }
}
