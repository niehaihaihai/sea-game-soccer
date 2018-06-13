package com.coocaa.ie.core.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCLabel;

/**
 * Created by lu on 2018/4/25.
 */

public class FPSActor extends CCLabel {
    private static final float DELTA_MAX = 1.0f;

    private float deltaSum = 0;
    private float count = 0;

    private float min = -1, max = -1, sum = 0, time = 0;

    private String res;

    public FPSActor(CCGame game) {
        super(game, "", 24, Color.BLUE);
        setPosition(0, game.scale(50));
        res = getGame().getGlobalViewPort().getWorldWidth() + "x" + getGame().getGlobalViewPort().getWorldHeight();
        setWrap(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        deltaSum += delta;
        if (deltaSum >= DELTA_MAX) {
            float fps = count / deltaSum;
            setText("FPS:" + fps + "  max:" + getMaxFps() + "  min:" + getMinFps() + "  ave:" + getAveFps() + "  resolution:" + res
                    + "\nscale:" + getGame().scale(1));
            deltaSum = 0;
            count = 0;

            sum += fps;
            time++;
            if (min < 0)
                min = fps;
            if (max < 0)
                max = fps;
            if (fps < min)
                min = fps;
            if (fps > max)
                max = fps;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        count++;
    }

    public float getMaxFps() {
        return max;
    }

    public float getMinFps() {
        return min;
    }

    public float getAveFps() {
        return sum / time;
    }
}
