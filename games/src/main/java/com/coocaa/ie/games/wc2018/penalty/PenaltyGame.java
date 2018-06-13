package com.coocaa.ie.games.wc2018.penalty;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.utils.web.ad.ADHttpDataLoader;
import com.coocaa.ie.games.wc2018.utils.web.ad.BaseAdData;
import com.coocaa.ie.games.wc2018.penalty.assets.PenaltyHttpDataLoader;
import com.coocaa.ie.games.wc2018.penalty.actor.PenaltyMainScreen;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PenaltyGame extends WC2018Game {

    public static final class Assets {

        public static final AssetDescriptor<WC2018HttpBaseData> HH_DATA = new AssetDescriptor(WC2018GameController.getController().getServer() + "/v2/lottery/shooter/" + WC2018GameController.getController().getGameId() + "/start", WC2018HttpBaseData.class);
        public static final AssetDescriptor<BaseAdData> AD_DATA = new AssetDescriptor(WC2018GameController.getController().getServer() + "/v2/lottery/verify/getAdInfo?" + "id=" + WC2018GameController.getController().getGameId() + "&partKey=game", BaseAdData.class);

        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_NORMAL_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_normal_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_SCORE_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_score_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_FAIL_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_fail_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_NORMAL_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_normal_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_SCORE_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_score_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_FAIL_A = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_fail_a.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_NORMAL_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_normal_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_SCORE_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_score_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_BIG_FAIL_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_big_fail_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_NORMAL_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_normal_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_SCORE_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_score_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_MEN_SMALL_FAIL_B = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_men_small_fail_b.atlas", TextureAtlas.class);
        public static final AssetDescriptor<TextureAtlas> ATLAS_DEC_TIME = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_dec_time.atlas", TextureAtlas.class);

        public static final AssetDescriptor<Texture> TEXTURE_LIFEBAR_BACK = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_life_back.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_LIFEBAR_MAIN_TEXTURE = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_life.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_BACK = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_back.jpg", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_ADDEFAULT = new AssetDescriptor("games/wc2018/penalty/addefault.jpg", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_BALL = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_ball.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_BALL_SCORE_BACK = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_ballscoreback.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_HELP = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_help.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_TIP_20SCORE = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_tip_20score.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_TIMEOVER = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_timeover.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_ARROW = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_arrow.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_NO_GOAL = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_no_goal.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_GOAL = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_goal.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MAINSTAGE_BALL_FAIL_BACK = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_ballfailback.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MESSAGESTAGE_BACK = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_background.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MESSAGESTAGE_JINQIU = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_jinqiu.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MESSAGESTAGE_KUBI = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_kubi.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MESSAGESTAGE_ADDGOLD5 = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_addgold5.png", Texture.class);
        public static final AssetDescriptor<Texture> TEXTURE_MESSAGESTAGE_ADDGOLD10 = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_addgold10.png", Texture.class);

        public static final AssetDescriptor<Sound> SOUND_FAIL = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_fail.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_KICK = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_kickaction.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_SCORE = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_score.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_SEC10 = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_sec10.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_SCOREX20 = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_scorex20.mp3", Sound.class);
        public static final AssetDescriptor<Sound> SOUND_BEGIN_AND_END = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_timeover.mp3", Sound.class);

        public static final AssetDescriptor<Music> MUSIC_BACKGROUND = new AssetDescriptor("id_wc2018_penalty_gamepage_voice_bgm.mp3", Music.class);

        public static final AssetDescriptor<BitmapFont> BITMAP_FONT_SCORE_NUMBER = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_score_number.fnt", BitmapFont.class);
        public static final AssetDescriptor<BitmapFont> BITMAP_FONT_TIME = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_time.fnt", BitmapFont.class);
        public static final AssetDescriptor<BitmapFont> BITMAP_FONT_TIME_NUMBER = new AssetDescriptor("games/wc2018/penalty/id_wc2018_penalty_gamepage_msg_timenumber.fnt", BitmapFont.class);

        public static final List<AssetDescriptor> assets() {
            List<AssetDescriptor> assets = new ArrayList<>();

            assets.add(HH_DATA);
            assets.add(AD_DATA);

            assets.add(ATLAS_MEN_BIG_NORMAL_A);
            assets.add(ATLAS_MEN_BIG_SCORE_A);
            assets.add(ATLAS_MEN_BIG_FAIL_A);
            assets.add(ATLAS_MEN_SMALL_NORMAL_A);
            assets.add(ATLAS_MEN_SMALL_SCORE_A);
            assets.add(ATLAS_MEN_SMALL_FAIL_A);
            assets.add(ATLAS_MEN_BIG_NORMAL_B);
            assets.add(ATLAS_MEN_BIG_SCORE_B);
            assets.add(ATLAS_MEN_BIG_FAIL_B);
            assets.add(ATLAS_MEN_SMALL_NORMAL_B);
            assets.add(ATLAS_MEN_SMALL_SCORE_B);
            assets.add(ATLAS_MEN_SMALL_FAIL_B);
            assets.add(ATLAS_DEC_TIME);

            assets.add(TEXTURE_LIFEBAR_BACK);
            assets.add(TEXTURE_LIFEBAR_MAIN_TEXTURE);
            assets.add(TEXTURE_MAINSTAGE_BACK);
            assets.add(TEXTURE_MAINSTAGE_ADDEFAULT);
            assets.add(TEXTURE_MAINSTAGE_BALL);
            assets.add(TEXTURE_MAINSTAGE_BALL_SCORE_BACK);
            assets.add(TEXTURE_MAINSTAGE_HELP);
            assets.add(TEXTURE_MAINSTAGE_TIP_20SCORE);
            assets.add(TEXTURE_MAINSTAGE_TIMEOVER);
            assets.add(TEXTURE_MAINSTAGE_ARROW);
            assets.add(TEXTURE_MAINSTAGE_NO_GOAL);
            assets.add(TEXTURE_MAINSTAGE_GOAL);
            assets.add(TEXTURE_MAINSTAGE_BALL_FAIL_BACK);
            assets.add(TEXTURE_MESSAGESTAGE_BACK);
            assets.add(TEXTURE_MESSAGESTAGE_JINQIU);
            assets.add(TEXTURE_MESSAGESTAGE_KUBI);
            assets.add(TEXTURE_MESSAGESTAGE_ADDGOLD5);
            assets.add(TEXTURE_MESSAGESTAGE_ADDGOLD10);

            assets.add(SOUND_FAIL);
            assets.add(SOUND_KICK);
            assets.add(SOUND_SCORE);
            assets.add(SOUND_SEC10);
            assets.add(SOUND_SCOREX20);
            assets.add(SOUND_BEGIN_AND_END);

            assets.add(MUSIC_BACKGROUND);

            assets.add(BITMAP_FONT_SCORE_NUMBER);
            assets.add(BITMAP_FONT_TIME);
            assets.add(BITMAP_FONT_TIME_NUMBER);

            return assets;
        }

        public static TextureAtlas atlas_dec_time;

        public static TextureRegion textureRegion_men_big_normal_a_1, textureRegion_men_big_normal_a_2, textureRegion_men_big_normal_a_3;
        public static TextureRegion textureRegion_men_big_normal_b_1, textureRegion_men_big_normal_b_2, textureRegion_men_big_normal_b_3;
        public static TextureRegion textureRegion_men_big_score_a_1, textureRegion_men_big_score_a_2, textureRegion_men_big_score_a_3;
        public static TextureRegion textureRegion_men_big_score_b_1, textureRegion_men_big_score_b_2, textureRegion_men_big_score_b_3;
        public static TextureRegion textureRegion_men_big_fail_a_1, textureRegion_men_big_fail_a_2, textureRegion_men_big_fail_a_3;
        public static TextureRegion textureRegion_men_big_fail_b_1, textureRegion_men_big_fail_b_2, textureRegion_men_big_fail_b_3;
        public static TextureRegion textureRegion_men_small_normal_a_1,textureRegion_men_small_normal_a_2,textureRegion_men_small_normal_a_3;
        public static TextureRegion textureRegion_men_small_normal_b_1,textureRegion_men_small_normal_b_2,textureRegion_men_small_normal_b_3;
        public static TextureRegion textureRegion_men_small_score_a_1,textureRegion_men_small_score_a_2,textureRegion_men_small_score_a_3;
        public static TextureRegion textureRegion_men_small_score_b_1,textureRegion_men_small_score_b_2,textureRegion_men_small_score_b_3;
        public static TextureRegion textureRegion_men_small_fail_a_1,textureRegion_men_small_fail_a_2,textureRegion_men_small_fail_a_3;
        public static TextureRegion textureRegion_men_small_fail_b_1,textureRegion_men_small_fail_b_2,textureRegion_men_small_fail_b_3;

        public static Texture texture_lifebar_main_texture;

        public static Sound sound_fail, sound_kick, sound_score, sound_sec10, sound_scorex20, sound_begin_and_end;
        public static Music music_background;

        public static TextureRegion textureregion_lifebar_back;
        public static TextureRegion textureregion_mainstage_back, textureregion_mainstage_addefault, textureregion_mainstage_ball, textureregion_mainstage_ball_score_back;
        public static TextureRegion textureregion_mainstage_help, textureregion_mainstage_tip_20score, textureregion_mainstage_timeover;
        public static TextureRegion textureregion_mainstage_arrow, textureregion_mainstage_no_goal, textureregion_mainstage_goal, textureregion_mainstage_ball_fail_back;
        public static TextureRegion textureregion_messagestage_back, textureregion_messagestage_jinqiu, textureregion_messagestage_kubi, textureregion_messagestage_addgold5, textureregion_messagestage_addgold10;

        public static BitmapFont bitmapFont_score_number, bitmapFont_time, bitmapFont_time_number;

        public static void loadAssets(CCGame game, AssetManager assetManager) {

            textureRegion_men_big_normal_a_1 = assetManager.get(ATLAS_MEN_BIG_NORMAL_A).findRegion("men1");
            textureRegion_men_big_normal_a_2 = assetManager.get(ATLAS_MEN_BIG_NORMAL_A).findRegion("men2");
            textureRegion_men_big_normal_a_3 = assetManager.get(ATLAS_MEN_BIG_NORMAL_A).findRegion("men3");

            textureRegion_men_big_normal_b_1 = assetManager.get(ATLAS_MEN_BIG_NORMAL_B).findRegion("men1");
            textureRegion_men_big_normal_b_2 = assetManager.get(ATLAS_MEN_BIG_NORMAL_B).findRegion("men2");
            textureRegion_men_big_normal_b_3 = assetManager.get(ATLAS_MEN_BIG_NORMAL_B).findRegion("men3");

            textureRegion_men_big_score_a_1 = assetManager.get(ATLAS_MEN_BIG_SCORE_A).findRegion("men1");
            textureRegion_men_big_score_a_2 = assetManager.get(ATLAS_MEN_BIG_SCORE_A).findRegion("men2");
            textureRegion_men_big_score_a_3 = assetManager.get(ATLAS_MEN_BIG_SCORE_A).findRegion("men3");

            textureRegion_men_big_score_b_1 = assetManager.get(ATLAS_MEN_BIG_SCORE_B).findRegion("men1");
            textureRegion_men_big_score_b_2 = assetManager.get(ATLAS_MEN_BIG_SCORE_B).findRegion("men2");
            textureRegion_men_big_score_b_3 = assetManager.get(ATLAS_MEN_BIG_SCORE_B).findRegion("men3");

            textureRegion_men_big_fail_a_1 = assetManager.get(ATLAS_MEN_BIG_FAIL_A).findRegion("men1");
            textureRegion_men_big_fail_a_2 = assetManager.get(ATLAS_MEN_BIG_FAIL_A).findRegion("men2");
            textureRegion_men_big_fail_a_3 = assetManager.get(ATLAS_MEN_BIG_FAIL_A).findRegion("men3");

            textureRegion_men_big_fail_b_1 = assetManager.get(ATLAS_MEN_BIG_FAIL_B).findRegion("men1");
            textureRegion_men_big_fail_b_2 = assetManager.get(ATLAS_MEN_BIG_FAIL_B).findRegion("men2");
            textureRegion_men_big_fail_b_3 = assetManager.get(ATLAS_MEN_BIG_FAIL_B).findRegion("men3");

            textureRegion_men_small_normal_a_1 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_A).findRegion("men1");
            textureRegion_men_small_normal_a_2 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_A).findRegion("men2");
            textureRegion_men_small_normal_a_3 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_A).findRegion("men3");

            textureRegion_men_small_normal_b_1 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_B).findRegion("men1");
            textureRegion_men_small_normal_b_2 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_B).findRegion("men2");
            textureRegion_men_small_normal_b_3 = assetManager.get(ATLAS_MEN_SMALL_NORMAL_B).findRegion("men3");

            textureRegion_men_small_score_a_1 = assetManager.get(ATLAS_MEN_SMALL_SCORE_A).findRegion("men1");
            textureRegion_men_small_score_a_2 = assetManager.get(ATLAS_MEN_SMALL_SCORE_A).findRegion("men2");
            textureRegion_men_small_score_a_3 = assetManager.get(ATLAS_MEN_SMALL_SCORE_A).findRegion("men3");

            textureRegion_men_small_score_b_1 = assetManager.get(ATLAS_MEN_SMALL_SCORE_B).findRegion("men1");
            textureRegion_men_small_score_b_2 = assetManager.get(ATLAS_MEN_SMALL_SCORE_B).findRegion("men2");
            textureRegion_men_small_score_b_3 = assetManager.get(ATLAS_MEN_SMALL_SCORE_B).findRegion("men3");

            textureRegion_men_small_fail_a_1 = assetManager.get(ATLAS_MEN_SMALL_FAIL_A).findRegion("men1");
            textureRegion_men_small_fail_a_2 = assetManager.get(ATLAS_MEN_SMALL_FAIL_A).findRegion("men2");
            textureRegion_men_small_fail_a_3 = assetManager.get(ATLAS_MEN_SMALL_FAIL_A).findRegion("men3");

            textureRegion_men_small_fail_b_1 = assetManager.get(ATLAS_MEN_SMALL_FAIL_B).findRegion("men1");
            textureRegion_men_small_fail_b_2 = assetManager.get(ATLAS_MEN_SMALL_FAIL_B).findRegion("men2");
            textureRegion_men_small_fail_b_3 = assetManager.get(ATLAS_MEN_SMALL_FAIL_B).findRegion("men3");

            atlas_dec_time = assetManager.get(ATLAS_DEC_TIME);

            texture_lifebar_main_texture = assetManager.get(TEXTURE_LIFEBAR_MAIN_TEXTURE);

            textureregion_lifebar_back = new TextureRegion(assetManager.get(TEXTURE_LIFEBAR_BACK));
            textureregion_mainstage_back = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_BACK));
            textureregion_mainstage_addefault = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_ADDEFAULT));
            textureregion_mainstage_ball = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_BALL));
            textureregion_mainstage_ball_score_back = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_BALL_SCORE_BACK));
            textureregion_mainstage_help = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_HELP));
            textureregion_mainstage_tip_20score = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_TIP_20SCORE));
            textureregion_mainstage_timeover = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_TIMEOVER));
            textureregion_mainstage_arrow = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_ARROW));
            textureregion_mainstage_no_goal = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_NO_GOAL));
            textureregion_mainstage_goal = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_GOAL));
            textureregion_mainstage_ball_fail_back = new TextureRegion(assetManager.get(TEXTURE_MAINSTAGE_BALL_FAIL_BACK));
            textureregion_messagestage_back = new TextureRegion(assetManager.get(TEXTURE_MESSAGESTAGE_BACK));
            textureregion_messagestage_jinqiu = new TextureRegion(assetManager.get(TEXTURE_MESSAGESTAGE_JINQIU));
            textureregion_messagestage_kubi = new TextureRegion(assetManager.get(TEXTURE_MESSAGESTAGE_KUBI));
            textureregion_messagestage_addgold5 = new TextureRegion(assetManager.get(TEXTURE_MESSAGESTAGE_ADDGOLD5));
            textureregion_messagestage_addgold10 = new TextureRegion(assetManager.get(TEXTURE_MESSAGESTAGE_ADDGOLD10));

            sound_fail = assetManager.get(SOUND_FAIL);
            sound_kick = assetManager.get(SOUND_KICK);
            sound_score = assetManager.get(SOUND_SCORE);
            sound_sec10 = assetManager.get(SOUND_SEC10);
            sound_scorex20 = assetManager.get(SOUND_SCOREX20);
            sound_begin_and_end = assetManager.get(SOUND_BEGIN_AND_END);

            music_background = assetManager.get(MUSIC_BACKGROUND);

            bitmapFont_score_number = assetManager.get(BITMAP_FONT_SCORE_NUMBER);
            bitmapFont_time = assetManager.get(BITMAP_FONT_TIME);
            bitmapFont_time_number = assetManager.get(BITMAP_FONT_TIME_NUMBER);
        }

        public static final List<AssetDescriptor> reloadAssets() {
            List<AssetDescriptor> assets = new ArrayList<AssetDescriptor>();
            assets.add(HH_DATA);
            return assets;
        }

    }

    private String TAG = "Sea-game";

    private PenaltyMainScreen penaltyMainScreen;

    private static final int GAME_TIME = 60;

    public PenaltyGame(CCGameSystem gameSystem, int width, int height, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, width, height, callback, component);
    }

    @Override
    public void create() {
        super.create();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug(TAG, "this is PenaltyGame-------");

    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    protected List<AssetDescriptor> getAssets() {
        return Assets.assets();
    }

    @Override
    protected List<AssetDescriptor> getReloadAssets() {
        return Assets.reloadAssets();
    }

    @Override
    protected void loadAssets(AssetManager assetManager) {
        Assets.loadAssets(this, assetManager);
    }

    @Override
    protected void loadComplete() {
        super.loadComplete();
    }

    @Override
    protected void setAssetLoader(CCAssetManager assetManager, FileHandleResolver resolver) {
        super.setAssetLoader(assetManager, resolver);
        assetManager.setLoader(BaseAdData.class, new ADHttpDataLoader(assetManager, resolver, new HttpDataLoader.HeaderLoader() {
            @Override
            public Map<String, String> load() {
                return WC2018GameController.getController().getHeaderLoader().load();
            }
        }));
        assetManager.setLoader(WC2018HttpBaseData.class, new PenaltyHttpDataLoader(assetManager, resolver, new HttpDataLoader.HeaderLoader() {
            @Override
            public Map<String, String> load() {
                return WC2018GameController.getController().getHeaderLoader().load();
            }
        }));

    }

    @Override
    protected void initScreen() {
        post(new Runnable() {
            @Override
            public void run() {
                penaltyMainScreen = new PenaltyMainScreen(GAME_TIME, PenaltyGame.this);
                setScreen(penaltyMainScreen);
            }
        });
    }

    @Override
    protected Go321Texture getGo321Texture() {
        Go321Texture go321Texture = new Go321Texture();
        go321Texture._1 = Assets.atlas_dec_time.findRegion("time1");
        go321Texture._2 = Assets.atlas_dec_time.findRegion("time2");
        go321Texture._3 = Assets.atlas_dec_time.findRegion("time3");
        go321Texture._go = Assets.atlas_dec_time.findRegion("timego");
        return go321Texture;
    }

    @Override
    protected void go() {
        post(new Runnable() {
            @Override
            public void run() {
                penaltyMainScreen.go();
            }
        });
    }

    @Override
    public void continueGame() {
        post(new Runnable() {
            @Override
            public void run() {
                penaltyMainScreen.continueGame();
            }
        });
    }

}
