package com.coocaa.ie.games.wc2018.penalty.actor;

/**
 * Created by Sea on 2018/5/7.
 */

public class PenaltyConstant {

    public static final float WORLD_WIDTH = 1920.0f;

    public static final float WORLD_HEIGHT = 1080.0f;

    /**
     * 点球大战配置文件名
     */
    public static String PENALTY_PREFERENCES = "_penalty_preferences";

    /**
     * 点球大战初次游戏标志，（决定游戏提示）
     */
    public static String PENALTY_PREFERENCES_FIRST_PLAY = "_penalty_preferences_first_play";

    public enum penaltyGameState
    {
        GAME_STATUS_STARTED,

        GAME_STATUS_PLAYING,

        GAME_STATUS_SCORE,

        GAME_STATUS_KICK,

        GAME_STATUS_OVER
    }

}
