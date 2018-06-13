package com.coocaa.ie.core.gdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.coocaa.ie.core.gdx.rpsg.lazyFont.LazyBitmapFont;

import java.util.List;

/**
 * Created by lu on 2018/4/26.
 */

public class CCAssetManager extends AssetManager {
    public static class CCAssetManagerException extends Exception {
        public int code;

        public CCAssetManagerException() {
            super();
        }

        public CCAssetManagerException(String s) {
            super(s);
        }

        public CCAssetManagerException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public CCAssetManagerException(Throwable throwable) {
            super(throwable);
        }
    }

    private static FreeTypeFontGenerator FONT_GENEROATOR = null;
    private CCGame mGame;
    private CCAssetManagerException exception;

    public CCAssetManager(CCGame game) {
        mGame = game;
    }

    public void create() {
        List<FileHandle> fonts = mGame.getCCGameSystem().getFonts();
        if (fonts != null) {
            for (FileHandle font : fonts) {
                if (font.exists()) {
                    try {
                        FONT_GENEROATOR = new FreeTypeFontGenerator(font);
                        break;
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (FONT_GENEROATOR == null) {
            throw new RuntimeException("can not find font file!!!!");
        }
    }

    public void setException(CCAssetManagerException exception) {
        this.exception = exception;
    }

    public CCAssetManagerException getException() {
        return exception;
    }

    public BitmapFont newBitmapFont(int size) {
        return newBitmapFont(size, Color.WHITE);
    }

    public BitmapFont newBitmapFont(int size, Color color) {
        LazyBitmapFont lazyBitmapFont = new LazyBitmapFont(FONT_GENEROATOR, (int) mGame.scale(size));
        lazyBitmapFont.setColor(color);
        lazyBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return lazyBitmapFont;
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
    }
}
