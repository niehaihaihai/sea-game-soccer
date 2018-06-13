package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2018/4/25.
 */

public class CCGame extends Game {
    public interface CCGameSystem {
        class CcosDeviceInfo {
            public String skymid = "";
            public String skymodel = "";
            public String skytype = "";
            public String brand = "";

            public CcosDeviceInfo(String skymid, String skymodel, String skytype, String brand) {
                this.skymid = skymid;
                this.skymodel = skymodel;
                this.skytype = skytype;
                this.brand = brand;
            }
        }

        String getSystenProperty(String key, String defaultValue);

        CcosDeviceInfo getDeviceInfo();

        List<FileHandle> getFonts();
    }

    private static CCGame instance = null;

    public static final CCGame getInstance() {
        return instance;
    }

    private Viewport globalViewPort;
    private CCAssetManager mAssetManager;
    private CCGameSystem mCCGameSystem;
    private float mScale;

    public CCGame(CCGameSystem gameSystem) {
        this.mCCGameSystem = gameSystem;
        init(1920, 1080);
    }

    public CCGame(CCGameSystem gameSystem, int width, int height) {
        this.mCCGameSystem = gameSystem;
        init(width, height);
    }

    private void init(int width, int height) {
        globalViewPort = new FitViewport(width, height);
        mScale = globalViewPort.getWorldWidth() / 1920.0f;
        mAssetManager = new CCAssetManager(this);
        setAssetLoader(mAssetManager, mAssetManager.getFileHandleResolver());
    }

    protected void setAssetLoader(CCAssetManager assetManager, FileHandleResolver resolver) {

    }

    public Viewport getGlobalViewPort() {
        return globalViewPort;
    }

    public float scale(float x) {
        return (float) Math.ceil(mScale * x);
    }

    public float scaleX(float x) {
        return x;
    }

    public CCGameSystem getCCGameSystem() {
        return mCCGameSystem;
    }

    public CCAssetManager getAssetManager() {
        return mAssetManager;
    }

    @Override
    public void create() {
        instance = this;
        mAssetManager.create();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
        synchronized (runnables) {
            for (int i = 0; i < runnables.size(); i++) {
                try {
                    runnables.get(i).run();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            runnables.clear();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        mAssetManager.dispose();
        instance = null;
    }

    private List<Runnable> runnables = new ArrayList<Runnable>();

    public void post(Runnable runnable) {
        synchronized (runnables) {
            runnables.add(runnable);
        }
    }

    public static final void dispose(Disposable disposable) {
        if (disposable != null)
            disposable.dispose();
    }
}
