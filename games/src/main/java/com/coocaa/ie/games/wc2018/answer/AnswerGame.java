package com.coocaa.ie.games.wc2018.answer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;
import com.coocaa.ie.core.gdx.drawable.CCTextureRegionDrawable;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.answer.assets.AnswerHttpDataLoader;
import com.coocaa.ie.games.wc2018.answer.main.AnswerMainScreen;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lu on 2018/4/25.
 */

public class AnswerGame extends WC2018Game {

    public static final class Assets {
        public static final AssetDescriptor<TextureAtlas> TEXTURE_PAK = new AssetDescriptor("games/wc2018/answer/texture_pak.txt", TextureAtlas.class);
        public static final AssetDescriptor<Texture> TEXTURE_BACKGROUND = new AssetDescriptor("games/wc2018/answer/background.png", Texture.class);
        public static final AssetDescriptor<WC2018HttpBaseData> HH_DATA = new AssetDescriptor(WC2018GameController.getController().getServer() + "/v2/lottery/smarter/" + WC2018GameController.getController().getGameId() + "/start", WC2018HttpBaseData.class);

        public static final AssetDescriptor<Sound> SOUND_CORRECT = new AssetDescriptor("games/wc2018/answer/audio/correct.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_INCORRECT = new AssetDescriptor("games/wc2018/answer/audio/incorrect.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_GAME_OVER = new AssetDescriptor("games/wc2018/answer/audio/game_over.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_TIME_OUT = new AssetDescriptor("games/wc2018/answer/audio/time_out.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_CRITICAL = new AssetDescriptor("games/wc2018/answer/audio/critical.mp3", Sound.class);
        public static final AssetDescriptor<Music> MUSIC_TIME_URGENT = new AssetDescriptor("games/wc2018/answer/audio/time_urgent.mp3", Music.class);
        public static final AssetDescriptor<Music> MUSIC_BG = new AssetDescriptor("games/wc2018/answer/audio/bg.mp3", Music.class);

        public static final String ATLAS_CRITICAL_TIPS0 = "critical_tips0";
        public static final String ATLAS_CRITICAL_TIPS1 = "critical_tips1";
        public static final String ATLAS_CRITICAL_TIPS_BG = "critical_tips_bg";
        public static final String ATLAS_CRITICAL_TIPS_NUMBER = "critical_tips_number";

        public static final String ATLAS_LAMB_LIGHT = "lamb_light";
        public static final String ATLAS_LAMB_NORMAL = "lamb_normal";

        public static final String ATLAS_NUMBER = "number";

        public static final String ATLAS_SELECTION_BUTTON_FOCUS = "selection_button_focus";
        public static final String ATLAS_SELECTION_BUTTON_NORMAL = "selection_button_normal";
        public static final String ATLAS_SELECTION_BUTTON_WRONG = "selection_button_wrong";
        public static final String ATLAS_SELECTION_ICON_RIGHT = "selection_icon_right";
        public static final String ATLAS_SELECTION_ICON_WRONG = "selection_icon_wrong";

        public static final String ATLAS_TIMER_NUMBER = "timer_number";
        public static final String ATLAS_TIMER_NORMAL = "timer_normal";
        public static final String ATLAS_TIMER_URGENT = "timer_urgent";
        public static final String ATLAS_TIMER_URGENT_LIGHT = "timer_urgent_light";

        public static final String ATLAS_TIPS0_ARRAW = "tips0_arraw";

        public static final String ATLAS_TITLE_LEFT = "title_left";
        public static final String ATLAS_TITLE_CENTER = "title_center";
        public static final String ATLAS_TITLE_RIGHT = "title_right";

        public static final String ATLAS_GAME_OVER = "game_over";
        public static final String ATLAS_TIME_OUT = "time_out";

        public static final String ATLAS_TIMER_PLUS_2 = "timer_plus_2s";
        public static final String ATLAS_TIMER_PLUS_3 = "timer_plus_3s";
        public static final String ATLAS_TIMER_PLUS_5 = "timer_plus_5s";

        public static final String ATLAS_GO_N = "go";
        public static final String ATLAS_GO_GO = "go_go";

        public static final List<AssetDescriptor> assets() {
            List<AssetDescriptor> assets = new ArrayList<AssetDescriptor>();
            assets.add(TEXTURE_PAK);
            assets.add(TEXTURE_BACKGROUND);
            assets.add(HH_DATA);
            assets.add(SOUND_CORRECT);
            assets.add(SOUND_GAME_OVER);
            assets.add(SOUND_TIME_OUT);
            assets.add(MUSIC_TIME_URGENT);
            assets.add(MUSIC_BG);
            assets.add(SOUND_CRITICAL);
            assets.add(SOUND_INCORRECT);
            return assets;
        }

        public static final List<AssetDescriptor> reloadAssets() {
            List<AssetDescriptor> assets = new ArrayList<AssetDescriptor>();
            assets.add(HH_DATA);
            return assets;
        }

        public static Texture textureBackground;
        public static CCTextureRegionDrawable focusedDrawable, unfocusedDrawable, wrongDrawable;
        public static TextureRegion[] texturesNumber, texturesTimerNumber, texturesCriticalNumber;
        public static TextureRegion textureRegionLambNormal, textureRegionLambLight;
        public static TextureRegion textureRegionCriticalBg, textureRegionCriticalTips0, textureRegionCriticalTips1;
        public static TextureRegion textureRegionTitleLeft, textureRegionTitleCenter, textureRegionTitleRight;

        public static TextureRegion textureTimerNormal, textureTimerUrgent, textureTimerUrgentLight;

        public static TextureRegion textureGo1, textureGo2, textureGo3, textureGoGo;

        public static TextureRegion textureTips0Arraw;

        public static TextureRegion textureSelectionRight, textureSelectionWrong;

        public static TextureRegion textureRegionGameOver, textureRegionTimeOut;

        public static TextureRegion textureRegionTimerPlus2, textureRegionTimerPlus3, textureRegionTimerPlus5;

        private static final TextureRegion[] loadNumber(TextureAtlas atlas, String name) {
            TextureRegion[] textureRegions = new TextureRegion[10];
            textureRegions[0] = atlas.findRegion(name, 0);
            textureRegions[1] = atlas.findRegion(name, 1);
            textureRegions[2] = atlas.findRegion(name, 2);
            textureRegions[3] = atlas.findRegion(name, 3);
            textureRegions[4] = atlas.findRegion(name, 4);
            textureRegions[5] = atlas.findRegion(name, 5);
            textureRegions[6] = atlas.findRegion(name, 6);
            textureRegions[7] = atlas.findRegion(name, 7);
            textureRegions[8] = atlas.findRegion(name, 8);
            textureRegions[9] = atlas.findRegion(name, 9);
            return textureRegions;
        }

        private static final void disposeNumber(TextureRegion[] textureRegions) {
            if (textureRegions != null) {
                for (TextureRegion tr : textureRegions)
                    CCGame.dispose(tr.getTexture());
            }
        }

        public static final void loadAssets(CCGame game, AssetManager assetManager) {
            TextureAtlas atlas = assetManager.get(TEXTURE_PAK);
            texturesNumber = loadNumber(atlas, ATLAS_NUMBER);
            texturesTimerNumber = loadNumber(atlas, ATLAS_TIMER_NUMBER);
            texturesCriticalNumber = loadNumber(atlas, ATLAS_CRITICAL_TIPS_NUMBER);

            textureBackground = assetManager.get(TEXTURE_BACKGROUND);

            textureRegionLambNormal = atlas.findRegion(ATLAS_LAMB_NORMAL);
            textureRegionLambLight = atlas.findRegion(ATLAS_LAMB_LIGHT);

            textureRegionCriticalBg = atlas.findRegion(ATLAS_CRITICAL_TIPS_BG);
            textureRegionCriticalTips0 = atlas.findRegion(ATLAS_CRITICAL_TIPS0);
            textureRegionCriticalTips1 = atlas.findRegion(ATLAS_CRITICAL_TIPS1);

            textureRegionTitleLeft = atlas.findRegion(ATLAS_TITLE_LEFT);
            textureRegionTitleCenter = atlas.findRegion(ATLAS_TITLE_CENTER);
            textureRegionTitleRight = atlas.findRegion(ATLAS_TITLE_RIGHT);

            textureTimerNormal = atlas.findRegion(ATLAS_TIMER_NORMAL);
            textureTimerUrgent = atlas.findRegion(ATLAS_TIMER_URGENT);
            textureTimerUrgentLight = atlas.findRegion(ATLAS_TIMER_URGENT_LIGHT);

            textureTips0Arraw = atlas.findRegion(ATLAS_TIPS0_ARRAW);

            textureSelectionRight = atlas.findRegion(ATLAS_SELECTION_ICON_RIGHT);
            textureSelectionWrong = atlas.findRegion(ATLAS_SELECTION_ICON_WRONG);

            textureRegionGameOver = atlas.findRegion(ATLAS_GAME_OVER);
            textureRegionTimeOut = atlas.findRegion(ATLAS_TIME_OUT);

            textureRegionTimerPlus2 = atlas.findRegion(ATLAS_TIMER_PLUS_2);
            textureRegionTimerPlus3 = atlas.findRegion(ATLAS_TIMER_PLUS_3);
            textureRegionTimerPlus5 = atlas.findRegion(ATLAS_TIMER_PLUS_5);

            textureGo1 = atlas.findRegion(ATLAS_GO_N, 1);
            textureGo2 = atlas.findRegion(ATLAS_GO_N, 2);
            textureGo3 = atlas.findRegion(ATLAS_GO_N, 3);
            textureGoGo = atlas.findRegion(ATLAS_GO_GO);

            focusedDrawable = new CCTextureRegionDrawable(game);
            focusedDrawable.setRegion(atlas.findRegion(ATLAS_SELECTION_BUTTON_FOCUS));
            unfocusedDrawable = new CCTextureRegionDrawable(game);
            unfocusedDrawable.setRegion(atlas.findRegion(ATLAS_SELECTION_BUTTON_NORMAL));
            wrongDrawable = new CCTextureRegionDrawable(game);
            wrongDrawable.setRegion(atlas.findRegion(ATLAS_SELECTION_BUTTON_WRONG));

//            focusedDrawable = new NinePatchDrawable(new NinePatch(assetManager.get(Assets.TEXTURE_FOCUS), 32, 32, 32, 32));//new NinePatchDrawable(new NinePatch(region));
//            Gdx.app.debug("MainLayout", "focusedDrawable size:" + focusedDrawable.getMinWidth() + "x" + focusedDrawable.getMinHeight());
//
//            unfocusedDrawable = new NinePatchDrawable(new NinePatch(assetManager.get(Assets.TEXTURE_UNFOCUS), 32, 32, 32, 32));// new NinePatchDrawable(new NinePatch(region));
//            Gdx.app.debug("MainLayout", "unfocusedDrawable size:" + unfocusedDrawable.getMinWidth() + "x" + unfocusedDrawable.getMinHeight());
//
//            correctDrawable = new TextureRegionDrawable(new TextureRegion(assetManager.get(Assets.TEXTURE_CORRECT)));
//
//            incorrectDrawable = new TextureRegionDrawable(new TextureRegion(assetManager.get(Assets.TEXTURE_INCORRECT)));
        }

        public static final void dispose() {
            disposeNumber(texturesNumber);
            disposeNumber(texturesTimerNumber);
            disposeNumber(texturesCriticalNumber);
            CCGame.dispose(textureBackground);
            CCGame.dispose(textureRegionLambNormal.getTexture());
            CCGame.dispose(textureRegionLambLight.getTexture());
            CCGame.dispose(textureRegionCriticalBg.getTexture());
            CCGame.dispose(textureRegionCriticalTips0.getTexture());
            CCGame.dispose(textureRegionCriticalTips1.getTexture());
            CCGame.dispose(textureRegionTitleLeft.getTexture());
            CCGame.dispose(textureRegionTitleCenter.getTexture());
            CCGame.dispose(textureRegionTitleRight.getTexture());
            CCGame.dispose(textureTimerNormal.getTexture());
            CCGame.dispose(textureTimerUrgent.getTexture());
            CCGame.dispose(textureTimerUrgentLight.getTexture());
            CCGame.dispose(textureTips0Arraw.getTexture());
            CCGame.dispose(textureSelectionRight.getTexture());
            CCGame.dispose(textureSelectionWrong.getTexture());
            CCGame.dispose(textureRegionGameOver.getTexture());
            CCGame.dispose(textureRegionTimeOut.getTexture());
            CCGame.dispose(textureRegionTimerPlus2.getTexture());
            CCGame.dispose(textureRegionTimerPlus3.getTexture());
            CCGame.dispose(textureRegionTimerPlus5.getTexture());
            CCGame.dispose(textureGo1.getTexture());
            CCGame.dispose(textureGo2.getTexture());
            CCGame.dispose(textureGo3.getTexture());
            CCGame.dispose(textureGoGo.getTexture());
        }
    }

    public static final class Rules {
        public static final int GAME_TIME_VALUE = 60;
        public static final int TIME_URGENT_VALUE = 5;
        public static final int COINS_VALUE = 5;
        public static final int DOUBLE_GATE_VALUE = 20;
    }

    public static final String NAME = "最强足球大师";

    public AnswerGame(CCGame.CCGameSystem gameSystem, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, callback, component);
    }

    public AnswerGame(CCGame.CCGameSystem gameSystem, int width, int height, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, width, height, callback, component);
    }

    @Override
    protected void setAssetLoader(CCAssetManager assetManager, FileHandleResolver resolver) {
        super.setAssetLoader(assetManager, resolver);
        assetManager.setLoader(WC2018HttpBaseData.class, new AnswerHttpDataLoader(assetManager, resolver, new HttpDataLoader.HeaderLoader() {
            @Override
            public Map<String, String> load() {
                return WC2018GameController.getController().getHeaderLoader().load();
            }
        }));
    }

    AnswerMainScreen mainScreen;

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void dispose() {
        super.dispose();
        dispose(mainScreen);
        mainScreen = null;
    }

    @Override
    protected List<AssetDescriptor> getAssets() {
        return Assets.assets();
    }

    @Override
    protected void loadAssets(AssetManager assetManager) {
        Assets.loadAssets(this, assetManager);
    }

    @Override
    protected void loadComplete() {
        super.loadComplete();
        if (mainScreen == null)
            mainScreen = new AnswerMainScreen(AnswerGame.this);
    }

    @Override
    protected void initScreen() {
        mainScreen.playBgMusic();
        setScreen(mainScreen);
    }

    @Override
    protected void go() {
        mainScreen.startNewGame();
    }

    @Override
    protected Go321Texture getGo321Texture() {
        Go321Texture texture = new Go321Texture();
        texture._1 = Assets.textureGo1;
        texture._2 = Assets.textureGo2;
        texture._3 = Assets.textureGo3;
        texture._go = Assets.textureGoGo;
        return texture;
    }

    @Override
    protected void preReloadGame() {
        mainScreen.reloadGame();
    }

    @Override
    protected List<AssetDescriptor> getReloadAssets() {
        return Assets.reloadAssets();
    }

    @Override
    public void continueGame() {
        post(new Runnable() {
            @Override
            public void run() {
                mainScreen.continueGame();
            }
        });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
