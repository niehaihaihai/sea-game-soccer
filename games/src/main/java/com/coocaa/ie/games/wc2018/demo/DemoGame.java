package com.coocaa.ie.games.wc2018.demo;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.demo.main.MainScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2018/4/25.
 */

public class DemoGame extends WC2018Game {
    public static class Assets {
        public static final AssetDescriptor<TextureAtlas> TEXTURE_PAK = new AssetDescriptor("games/demo/texture_pak.txt", TextureAtlas.class);
        public static final AssetDescriptor<Sound> GOLD_SOUND = new AssetDescriptor<Sound>("games/demo/gold.wav", Sound.class);
        public static final AssetDescriptor<Music> BG_MUSIC = new AssetDescriptor<Music>("games/demo/bg.mp3", Music.class);

        public static final List<AssetDescriptor> assets() {
            List<AssetDescriptor> assets = new ArrayList<AssetDescriptor>();
            assets.add(TEXTURE_PAK);
            assets.add(GOLD_SOUND);
            assets.add(BG_MUSIC);
            return assets;
        }
    }

    public DemoGame(CCGameSystem gameSystem, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, callback, component);
    }

    public DemoGame(CCGameSystem gameSystem, int width, int height, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, width, height, callback, component);
    }

    @Override
    protected List<AssetDescriptor> getAssets() {
        return Assets.assets();
    }

//    @Override
//    public void startGame() {
//        post(new Runnable() {
//            @Override
//            public void run() {
//                setScreen(new MainScreen(DemoGame.this));
//            }
//        });
//    }

    @Override
    protected Go321Texture getGo321Texture() {
        return null;
    }

    @Override
    protected void initScreen() {

    }

    @Override
    protected void go() {

    }

    @Override
    public void continueGame() {

    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
