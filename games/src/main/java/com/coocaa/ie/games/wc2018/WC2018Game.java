package com.coocaa.ie.games.wc2018;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.coocaa.ie.core.gdx.CCAssetManager;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.system.vc.VoiceMonitor;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;
import com.coocaa.ie.games.wc2018.penalty.actor.MyActor;

import java.util.List;
import java.util.Map;

import static com.coocaa.ie.games.wc2018.WC2018GameController.GAME_ANSWER;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_HEIGHT;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_WIDTH;

public abstract class WC2018Game extends CCGame {
    public interface WC2018GameComponent {
        void registerVCMonitor(VoiceMonitor monitor);

        void unRegisterVCMonitor(VoiceMonitor monitor);

        interface VCHelperCallback {
            void onQuit();
        }

        void showVCHelper();

        void showVCAnswer(String id, String question, String answer);

        void hideVCAnswer();

        void handleStartGame(String game);

        void loadWebUrl(String url);

        class Score {
            //游戏酷币，本地算的
            public int coins;
            //游戏得分，答对多少题、踢进多少球
            public int score;
            //游戏时长
            public long duration;
            //是否复活过？ true:复活过  false:未复活
            public boolean bRelived;
        }

        void setRevivableValue(int value);

        void handleGameover(Score score, Runnable runnable);

        void handleQuitGame();

        void startGame();

        void reloadGame();

        void continueGame();

        //6、备注信息：
//        若为射门游戏，则在本字段提交用户本局的射门次数
//        若为答题游戏，则在本字段提交本局游戏使用语音查答案的次数
        void updateLoggerExtra(int value);

        int getLoggerExtra();

        void logger(String eventID, Map<String, String> params);

        boolean isSupportVC();
    }


    public interface WC2018GameCallback {
        void onCreate(WC2018Game game);

        void onLoading(WC2018Game game, int progress);

        void onError(WC2018Game game, CCAssetManager.CCAssetManagerException exception);

        void onLoadingComplete(WC2018Game game);

        void onDisposed(WC2018Game game);
    }

    public static final int LOADING_STATE_IDLE = 0;
    public static final int LOADING_STATE_LOADING = 1;
    public static final int LOADING_STATE_COMPELETE = 2;
    public static final int LOADING_STATE_ERROR = 3;
    private WC2018GameCallback callback;
    private int loadingState = LOADING_STATE_IDLE;
    private WC2018GameComponent component;
    private Batch batch;

    public WC2018Game(CCGameSystem gameSystem, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem);
        this.callback = callback;
        this.component = component;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    public WC2018Game(CCGameSystem gameSystem, int width, int height, WC2018GameCallback callback, WC2018GameComponent component) {
        super(gameSystem, width, height);
        this.callback = callback;
        this.component = component;
    }

    public final WC2018GameComponent getWC2018GameComponent() {
        return component;
    }

    @Override
    public void create() {
        if (callback != null)
            callback.onCreate(this);
        super.create();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch = new SpriteBatch();
    }

    //加载游戏第一步
    //获取asset描述列表，供框架预加载
    protected abstract List<AssetDescriptor> getAssets();


    //加载游戏第二步
    //在asset加载完毕后，加载第一场景必要的纹理等等资源
    protected void loadAssets(AssetManager assetManager) {

    }

    //加载游戏第三步
    //在第一、二步完成后调用，一般用以创建即将显示的场景
    protected void loadComplete() {

    }

    @Override
    public void render() {
        super.render();
        if (loadingState == LOADING_STATE_LOADING) {
            if (getAssetManager().update()) {
                CCAssetManager.CCAssetManagerException exception = getAssetManager().getException();
                if (exception != null) {
                    loadingState = LOADING_STATE_ERROR;
                    if (callback != null)
                        callback.onError(this, exception);
                } else {
                    loadAssets(getAssetManager());
                    loadComplete();
                    loadingState = LOADING_STATE_COMPELETE;
                    if (callback != null) {
                        callback.onLoading(this, 100);
                        callback.onLoadingComplete(this);
                    }
                }
            } else {
                if (callback != null)
                    callback.onLoading(this, (int) (getAssetManager().getProgress() * 80));
            }
        } else if (showDec && decTime != null && batch != null) {
            decTime.act(Gdx.graphics.getDeltaTime());
            batch.begin();
            decTime.draw(batch, 1.0f);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (callback != null)
            callback.onDisposed(this);
    }

    public final boolean isLoadComplete() {
        return loadingState == LOADING_STATE_COMPELETE;
    }

    //第一次加载所有游戏资源调用
    public void loadGame() {
        post(new Runnable() {
            @Override
            public void run() {
                if (loadingState != LOADING_STATE_LOADING) {
                    loadingState = LOADING_STATE_LOADING;
                    getAssetManager().clear();
                    getAssetManager().setException(null);
                    final List<AssetDescriptor> assets = getAssets();
                    if (assets == null || assets.size() == 0)
                        return;
                    for (AssetDescriptor asset : assets) {
                        getAssetManager().load(asset);
                    }
                }
            }
        });
    }

    private long st;

    //开始游戏
    public final void startGame() {
        post(new Runnable() {
            @Override
            public void run() {
                st = System.currentTimeMillis();
                initScreen();
                go321();
            }
        });
    }

    public static class Go321Texture {
        public TextureRegion _3, _2, _1, _go;
    }

    private boolean showDec = false;

    private void go321() {
        showDec = true;
        decToStart(getGo321Texture());
    }

    protected abstract Go321Texture getGo321Texture();

    //开始游戏第一步：初始化、显示主场景
    protected abstract void initScreen();

    //开始游戏第二步：游戏逻辑开始
    protected abstract void go();

    public final long getPlayDuration() {
        return System.currentTimeMillis() - st;
    }

    //重新加载游戏资源
    //需要重新加载的资源由getReloadAssets决定
    //这个方法一般再重新开始游戏的时候调用
    public void reloadGame() {
        post(new Runnable() {
            @Override
            public void run() {
                if (loadingState != LOADING_STATE_LOADING) {
                    loadingState = LOADING_STATE_LOADING;
                    preReloadGame();
                    List<AssetDescriptor> assets = getReloadAssets();
                    if (assets != null && assets.size() > 0) {
                        for (AssetDescriptor asset : assets)
                            getAssetManager().unload(asset.fileName);
                        getAssetManager().setException(null);
                        for (AssetDescriptor asset : assets)
                            getAssetManager().load(asset);
                    }
                }
            }
        });
    }

    protected void preReloadGame() {
    }

    protected List<AssetDescriptor> getReloadAssets() {
        return null;
    }

    //复活游戏
    public abstract void continueGame();

    public String getName() {
        return getClass().getSimpleName();
    }

    private MyActor decTime;

    private void decToStart(Go321Texture go321Texture) {
        final TextureRegion region1 = go321Texture._1;
        final TextureRegion region2 = go321Texture._2;
        final TextureRegion region3 = go321Texture._3;
        final TextureRegion regiongo = go321Texture._go;
        decTime = new MyActor(null);

        decTime.setRegion(region3, scale(region3.getRegionWidth()), scale(region3.getRegionHeight()));
        decTime.setPosition(scale(WORLD_WIDTH) / 2 - scale(decTime.getWidth()) / 2, scale(WORLD_HEIGHT) / 2 - scale(decTime.getHeight()) / 2);
        decTime.setOrigin(decTime.getWidth() / 2, decTime.getHeight() / 2);
        final ScaleToAction scaleToAction1 = Actions.scaleTo(2.0f, 2.0f, 0.9f);
        final ScaleToAction scaleToAction2 = Actions.scaleTo(2.0f, 2.0f, 0.9f);
        final ScaleToAction scaleToAction3 = Actions.scaleTo(2.0f, 2.0f, 0.9f);
        final ScaleToAction scaleToAction4 = Actions.scaleTo(2.0f, 2.0f, 0.9f);

        final RunnableAction runnableAction4 = Actions.run(new Runnable() {
            @Override
            public void run() {
                decTime.setVisible(false);
                decTime.clear();
                showDec = false;
                go();
            }
        });

        final RunnableAction runnableAction3 = Actions.run(new Runnable() {
            @Override
            public void run() {
                decTime.setVisible(false);
                decTime.clear();
                decTime.setScale(1.0f);
                decTime.setRegion(regiongo, scale(regiongo.getRegionWidth()), scale(regiongo.getRegionHeight()));
                decTime.setPosition(scale(WORLD_WIDTH) / 2 - scale(decTime.getWidth()) / 2, scale(WORLD_HEIGHT) / 2 - scale(decTime.getHeight()) / 2);
                decTime.setOrigin(decTime.getWidth() / 2, decTime.getHeight() / 2);
                decTime.setVisible(true);
                SequenceAction sequenceAction = Actions.sequence(scaleToAction4, runnableAction4);
                decTime.addAction(sequenceAction);
            }
        });

        final RunnableAction runnableAction2 = Actions.run(new Runnable() {
            @Override
            public void run() {
                decTime.setVisible(false);
                decTime.clear();
                decTime.setScale(1.0f);
                decTime.setRegion(region1, scale(region1.getRegionWidth()), scale(region1.getRegionHeight()));
                decTime.setPosition(scale(WORLD_WIDTH) / 2 - scale(decTime.getWidth()) / 2, scale(WORLD_HEIGHT) / 2 - scale(decTime.getHeight()) / 2);
                decTime.setOrigin(decTime.getWidth() / 2, decTime.getHeight() / 2);
                decTime.setVisible(true);
                SequenceAction sequenceAction = Actions.sequence(scaleToAction3, runnableAction3);
                decTime.addAction(sequenceAction);
            }
        });

        RunnableAction runnableAction1 = Actions.run(new Runnable() {
            @Override
            public void run() {
                decTime.setVisible(false);
                decTime.clear();
                decTime.setScale(1.0f);
                decTime.setRegion(region2, scale(region2.getRegionWidth()), scale(region2.getRegionHeight()));
                decTime.setPosition(scale(WORLD_WIDTH) / 2 - scale(decTime.getWidth()) / 2, scale(WORLD_HEIGHT) / 2 - scale(decTime.getHeight()) / 2);
                decTime.setOrigin(decTime.getWidth() / 2, decTime.getHeight() / 2);
                decTime.setVisible(true);
                SequenceAction sequenceAction = Actions.sequence(scaleToAction2, runnableAction2);
                decTime.addAction(sequenceAction);
            }
        });
        SequenceAction sequenceAction = Actions.sequence(scaleToAction1, runnableAction1);
        decTime.addAction(sequenceAction);
    }
}
