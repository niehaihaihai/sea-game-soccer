package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

/**
 * Created by Sea on 2018/4/28.
 */

public class ScoreActor extends Actor {

    private CCGame game;
    private BitmapFont bitmapFont;
    private String score = "0000";
    private int lastScore = 0;

    public ScoreActor(CCGame game) {
        this.game = game;
        bitmapFont = PenaltyGame.Assets.bitmapFont_score_number;
        bitmapFont.setColor(1, 1, 1, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        bitmapFont.draw(batch, ""+score, getX(), getY());
    }

    public void setScore(int score) {
        lastScore = score;
        if(score / 1000 > 0) {
            this.score = String.valueOf(score);
        }else if(score / 100 > 0) {
            this.score = "0" + String.valueOf(score);
        }else if(score / 10 > 0) {
            this.score = "00" + String.valueOf(score);
        }else {
            this.score = "000" + String.valueOf(score);
        }
    }

    public void addScore(int score) {
        score += lastScore;
        if(score / 1000 > 0) {
            this.score = String.valueOf(score);
        }else if(score / 100 > 0) {
            this.score = "0" + String.valueOf(score);
        }else if(score / 10 > 0) {
            this.score = "00" + String.valueOf(score);
        }else {
            this.score = "000" + String.valueOf(score);
        }
    }

}
