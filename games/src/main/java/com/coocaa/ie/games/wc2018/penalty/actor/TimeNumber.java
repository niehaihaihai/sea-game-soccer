package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

/**
 * Created by Sea on 2018/4/28.
 */

public class TimeNumber extends Actor {

    private CCGame game;
    private BitmapFont bitmapFont;
    private String time;

    public TimeNumber(CCGame game, int time) {
        if(time / 100 > 0) {
            this.time = String.valueOf(time);
        }else if(time / 10 > 0) {
            this.time = "0" + time;
        }else {
            this.time = "00" + time;
        }
        this.game = game;
        bitmapFont = PenaltyGame.Assets.bitmapFont_time_number;
        bitmapFont.setColor(1, 1, 0, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        bitmapFont.draw(batch, time, getX(), getY() + game.scaleX(35));
    }

    public void setTime(int time) {
        if(time / 100 > 0) {
            this.time = String.valueOf(time);
        }else if(time / 10 > 0) {
            this.time = "0" + time;
        }else {
            this.time = "00" + time;
        }
    }

}
