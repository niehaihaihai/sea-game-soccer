package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

/**
 * Created by Sea on 2018/5/18.
 */

public class Time extends Actor {

    private CCGame game;
    private BitmapFont bitmapFont;

    public Time(CCGame game) {
        this.game = game;
        bitmapFont = PenaltyGame.Assets.bitmapFont_time;
        bitmapFont.setColor(1, 1, 0, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        bitmapFont.draw(batch, "TIME", getX(), getY());
    }
    
}
