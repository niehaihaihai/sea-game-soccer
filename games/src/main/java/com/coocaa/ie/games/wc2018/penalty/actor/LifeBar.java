package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

import java.math.BigDecimal;

import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_lifebar_back;

/**
 * Created by Sea on 2018/5/18.
 */

public class LifeBar extends Actor {

    private Texture mainTexture;
    private TextureRegion region;
    private TextureRegion back;
    private int offset_x;
    private int duration;
    private int curTime;
    private int unSee = 291;

    public LifeBar(int duration) {
        this.duration = duration;
        this.curTime = duration;
        back = textureregion_lifebar_back;
        mainTexture = PenaltyGame.Assets.texture_lifebar_main_texture;
        setSize(back.getRegionWidth(), back.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float usedPercent = div(duration - curTime, duration, 2);
        offset_x = (int) (usedPercent * (mainTexture.getWidth() - unSee) / 2);
        region = new TextureRegion(mainTexture, offset_x, 0, mainTexture.getWidth() - 2 * offset_x, mainTexture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(back, getX(), getY());
        batch.draw(region, getX() + offset_x, getY());
    }

    public void setTime(int time) {
        this.curTime = time;
    }

    private static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
