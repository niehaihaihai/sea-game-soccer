package com.coocaa.ie.games.wc2018.utils.web.ad;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;

import java.io.Serializable;

public abstract class BaseADHttpDataLoader<T extends Serializable> extends HttpDataLoader<BaseAdData> {
    private Class<T> clazz;

    public BaseADHttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver, HeaderLoader headerLoader, Class<T> clazz) {
        super(assetManager, resolver);
        this.clazz = clazz;
        setHeaderLoader(headerLoader);
        setRetryTimes(3);
    }

    @Override
    protected BaseAdData parseOnSuccess(String source) {
        BaseAdData data = BaseAdData.parse(source);
        data.parseObject(clazz);
        return data;
    }

    @Override
    protected BaseAdData parseOnFailed(String source, Throwable throwable) {
        BaseAdData data = new BaseAdData();
        data.data = throwable.getMessage();
        data.source = source;
        return data;
    }
}
