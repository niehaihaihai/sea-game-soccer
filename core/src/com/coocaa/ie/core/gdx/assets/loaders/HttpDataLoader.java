package com.coocaa.ie.core.gdx.assets.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.web.call.HttpCall;

import java.util.HashMap;
import java.util.Map;

public abstract class HttpDataLoader<T> extends AsynchronousAssetLoader<T, HttpDataLoader.HttpDataParameter<T>> {
    public interface HeaderLoader {
        Map<String, String> load();
    }

    private T data = null;
    private HeaderLoader headerLoader = null;
    private int retryTimes = 0;
    private CCAssetManager assetManager;

    public HttpDataLoader(CCAssetManager assetManager, FileHandleResolver resolver) {
        super(resolver);
        this.assetManager = assetManager;
    }

    public void setHeaderLoader(HeaderLoader headerLoader) {
        this.headerLoader = headerLoader;
    }

    public void setRetryTimes(int t) {
        retryTimes = t;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, HttpDataParameter parameter) {
        Gdx.app.debug("HttpDataLoader", "loadAsync:" + fileName + "  file:" + file);
        String result = "";

        Map<String, String> headers = new HashMap<String, String>();
        if (headerLoader != null)
            headers.putAll(headerLoader.load());
        int _retry = retryTimes;
        Exception ee;
        while (true) {
            try {
                HttpCall call = HttpCall.create(fileName).withHeaders(headers);
                result = call.call();
                Gdx.app.log("HttpDataLoader", "result:" + result);
                data = parseOnSuccess(result);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                ee = e;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            if (_retry-- <= 0)
                break;
        }
        assetManager.setException(new CCAssetManager.CCAssetManagerException(ee));
        data = parseOnFailed(result, ee);
    }

    @Override
    public T loadSync(AssetManager manager, String fileName, FileHandle file, HttpDataParameter parameter) {
        return data;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, HttpDataParameter parameter) {
        return null;
    }

    protected abstract T parseOnSuccess(String source);

    protected abstract T parseOnFailed(String source, Throwable throwable);

    static public class HttpDataParameter<T> extends AssetLoaderParameters<T> {
    }
}
