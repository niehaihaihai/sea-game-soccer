package com.coocaa.ie.games.wc2018.demo.main;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.coocaa.ie.core.gdx.CCActor;
import com.coocaa.ie.core.gdx.CCGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lu on 2018/4/25.
 */

public class GoldActor extends CCActor {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private static Group GROUP = null;

    public synchronized static final void newGold(CCGame game, Stage stage, TextureRegion texture, Sound sound) {
        if (GROUP == null) {
            GROUP = new Group();
            stage.addActor(GROUP);
        }
        GROUP.addActor(new GoldActor(game, texture, sound));
    }

    private TextureRegion region;
    private Sound sound;
    private int x = 0;

    private GoldActor(CCGame game, TextureRegion texture, Sound sound) {
        super(game);
        this.sound = sound;
        region = texture;
        x = (int) ((getGame().getGlobalViewPort().getWorldWidth() - region.getRegionWidth()) * Math.random());
        setPosition(x, getGame().getGlobalViewPort().getWorldHeight());
        setOrigin(region.getRegionWidth() / 2, region.getRegionHeight() / 2);
        setScale(getGame().scale(1));
        setRotation(0);
    }


    private boolean bFirstAct = false;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!bFirstAct) {
            bFirstAct = true;
            final float d = (getGame().getGlobalViewPort().getWorldHeight() + 2 * region.getRegionHeight()) / 40.00f;

            final float t = (float) Math.sqrt(2 * d / 9.80f);
            Action moveTo = Actions.moveTo(x, -region.getRegionHeight(), t, new Interpolation() {
                @Override
                public float apply(float a) {
                    return 0.5f * 9.8f * (t * a) * (t * a) / d;
                }
            });
            double s = Math.random() - 0.5f;
            Action rotateTo = Actions.rotateTo((int) (Math.abs(s) / s) * (int) (90.00f * Math.random()), t);
            Action action = Actions.parallel(moveTo, rotateTo);

            // Runnable 动作
            RunnableAction runnable = Actions.run(new Runnable() {
                @Override
                public void run() {
                    // 打印一句 log 表示动作已执行
                    getParent().removeActor(GoldActor.this);
                    sound.play((float) Math.random() * 0.5f);
                }
            });
            // 将动作附加在演员身上, 执行动作
            SequenceAction sequence = Actions.sequence(action, runnable);
            addAction(sequence);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (region == null || !isVisible()) {
            return;
        }
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                region.getRegionWidth(), region.getRegionHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }
}
