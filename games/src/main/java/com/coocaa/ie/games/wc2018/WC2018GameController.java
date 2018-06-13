package com.coocaa.ie.games.wc2018;

import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.assets.loaders.HttpDataLoader;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.demo.DemoGame;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

public class WC2018GameController {
    public static final String GAME_DEMO = "game.demo";
    public static final String GAME_ANSWER = "smarter";
    public static final String GAME_PENALTY = "shooter";
    public static final String GAME_DEMO_ID = "";
    public static final String GAME_ANSWER_ID_DEBUG = "90";
    public static final String GAME_PENALTY_ID_DEBUG = "89";
    public static final String GAME_ANSWER_ID_RELEASE = "71";
    public static final String GAME_PENALTY_ID_RELEASE = "70";

    private static WC2018GameController controller = null;

    public static synchronized final boolean start(String gameName, CCGame.CCGameSystem gameSystem, int width, int height, WC2018Game.WC2018GameCallback callback, WC2018Game.WC2018GameComponent component, String server, boolean bDebug) {
        quit();
        if (gameName == null)
            return false;
        String gameId;
        WC2018Game _game;
        if (gameName.equals(GAME_DEMO)) {
            _game = new DemoGame(gameSystem, width, height, callback, component);
            gameId = GAME_DEMO_ID;
        } else if (gameName.equals(GAME_ANSWER)) {
            _game = new AnswerGame(gameSystem, width, height, callback, component);
            if (bDebug)
                gameId = GAME_ANSWER_ID_DEBUG;
            else
                gameId = GAME_ANSWER_ID_RELEASE;
        } else if (gameName.equals(GAME_PENALTY)) {
            _game = new PenaltyGame(gameSystem, width, height, callback, component);
            if (bDebug)
                gameId = GAME_PENALTY_ID_DEBUG;
            else
                gameId = GAME_PENALTY_ID_RELEASE;
        } else
            return false;
        if (_game != null) {
            controller = new WC2018GameController(_game, gameName, gameId, server, bDebug);
        }
        return true;
    }

    public static synchronized final WC2018GameController getController() {
        return controller;
    }

    public static synchronized final void quit() {
        if (controller != null)
            controller.wc2018Game.dispose();
    }

    private WC2018Game wc2018Game;
    private String gameName;
    private String gameId;
    private String code;
    private HttpDataLoader.HeaderLoader headerLoader;
    private String server;
    private boolean bDebug = false;

    private WC2018GameController(WC2018Game wc2018Game, String gameName, String gameId, String server, boolean bDebug) {
        this.wc2018Game = wc2018Game;
        this.gameName = gameName;
        this.gameId = gameId;
        this.server = server;
        this.bDebug = bDebug;
    }

    public void updateCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public WC2018Game getGame() {
        return wc2018Game;
    }

    public void setHeaderLoader(HttpDataLoader.HeaderLoader headerLoader) {
        this.headerLoader = headerLoader;
    }

    public String getServer() {
        return server;
    }

    public HttpDataLoader.HeaderLoader getHeaderLoader() {
        return headerLoader;
    }

    public String getGameNameInChinese() {
        if (getGameName().equals(GAME_ANSWER))
            return "答题";
        else if (getGameName().equals(GAME_PENALTY))
            return "射门";
        else
            return "不知道";
    }

    public boolean isDebugMode() {
        return bDebug;
    }
}
