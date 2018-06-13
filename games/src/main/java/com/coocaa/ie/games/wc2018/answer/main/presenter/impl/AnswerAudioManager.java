package com.coocaa.ie.games.wc2018.answer.main.presenter.impl;

import com.badlogic.gdx.audio.Music;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;

public class AnswerAudioManager {
    private static AnswerAudioManager audioManager;

    public static final AnswerAudioManager audio() {
        return audioManager;
    }

    static final void create(CCGame game) {
        audioManager = new AnswerAudioManager(game);
    }

    private CCGame game;

    private Music bgMusic, timeUrgentMusic;

    private AnswerAudioManager(CCGame game) {
        this.game = game;
    }

    public synchronized final void playBG() {
        if (bgMusic == null) {
            bgMusic = game.getAssetManager().get(AnswerGame.Assets.MUSIC_BG);
            bgMusic.setLooping(true);
            bgMusic.setVolume(0.25f);
            bgMusic.play();
        }
    }

    public synchronized final void playCritical() {
        game.getAssetManager().get(AnswerGame.Assets.SOUND_CRITICAL).play();
    }

    public synchronized final void playCorrent() {
        game.getAssetManager().get(AnswerGame.Assets.SOUND_CORRECT).play();
    }

    public synchronized final void playIncorrent() {
        game.getAssetManager().get(AnswerGame.Assets.SOUND_INCORRECT).play();
    }

    public synchronized final void playGameOver() {
        game.getAssetManager().get(AnswerGame.Assets.SOUND_GAME_OVER).play();
    }

    public synchronized final void playTimeOut() {
        game.getAssetManager().get(AnswerGame.Assets.SOUND_TIME_OUT).play();
    }

    public synchronized final void playTimeUrgent() {
        if (timeUrgentMusic == null) {
            timeUrgentMusic = game.getAssetManager().get(AnswerGame.Assets.MUSIC_TIME_URGENT);
            timeUrgentMusic.setLooping(true);
            timeUrgentMusic.play();
        }
    }

    public synchronized final void stopTimeUrgent() {
        if (timeUrgentMusic != null) {
            timeUrgentMusic.stop();
            timeUrgentMusic.dispose();
            timeUrgentMusic = null;
        }
    }
}
