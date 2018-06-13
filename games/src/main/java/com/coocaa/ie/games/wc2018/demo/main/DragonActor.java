package com.coocaa.ie.games.wc2018.demo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.coocaa.ie.core.gdx.CCGame;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.utils.SkeletonActor;

public class DragonActor extends SkeletonActor {
    private static AnimationStateData animData;
    private static SkeletonRenderer renderer;
    private static Skeleton skeleton;

    private static PolygonSpriteBatch polygonBatch;

    static {
        TextureAtlas tAtlas = new TextureAtlas(Gdx.files.internal("games/demo/spine/Dragon.atlas"));
        SkeletonJson sJson = new SkeletonJson(tAtlas);
        sJson.setScale(CCGame.getInstance().scale(0.3f));// 缩放，以后不可更改
        SkeletonData sData = sJson.readSkeletonData(Gdx.files.internal("games/demo/spine/Dragon.json"));
        animData = new AnimationStateData(sData);
        renderer = new SkeletonRenderer();
        skeleton = new Skeleton(sData);
        polygonBatch = new PolygonSpriteBatch();
    }

    private final float SPEED = (float) (20.0f * Math.random()) + 10.0f;
    private final float DSPEED = SPEED / 30.0f;

    private AnimationState stateStand, stateWalk, stateJump, stateFall;
    private int des = -1;

    public DragonActor() {
        super(renderer, new Skeleton(skeleton), null);
        stateStand = new AnimationState(animData);
        stateStand.setTimeScale(DSPEED);
        stateStand.setAnimation(0, "stand", true);
        stateWalk = new AnimationState(animData);
        stateWalk.setTimeScale(DSPEED);
        stateWalk.setAnimation(0, "walk", true);
        stateJump = new AnimationState(animData);
        stateJump.setAnimation(0, "jump", false);
        stateFall = new AnimationState(animData);
        stateFall.setAnimation(0, "fall", false);
        stand();
    }

    public void draw() {
        polygonBatch.begin();
        super.draw(polygonBatch, 1.0f);
        polygonBatch.end();
    }

    public void startYourShow() {
        Runnable rr = new Runnable() {
            @Override
            public void run() {
                if (getX() <= 0 || getX() >= CCGame.getInstance().getGlobalViewPort().getWorldWidth()) {
                    turnAround();
                    walk(this);
                } else {
                    double dd = Math.random();
                    if (dd <= 0.2f)
                        walk(this);
                    else if (dd > 0.2f && dd <= 0.8f)
                        jump(this);
                    else {
                        turnAround();
                        walk(this);
                    }
                }
            }
        };
        walk(rr);
    }

    public synchronized void turnAround() {
        des *= -1;
        getSkeleton().setFlipX(des != -1);
    }

    public synchronized void stand() {
        clearActions();
        setAnimationState(stateStand);
    }

    public synchronized void walk(final Runnable callback) {
        float target;
        if (des == -1) {
            target = (float) (getX() * Math.random()) + getWidth() / 2;
        } else {
            target = (float) ((CCGame.getInstance().getGlobalViewPort().getWorldWidth() - getX()) * Math.random() + getX()) - getWidth() / 2;
        }
        float time = Math.abs(target - getX()) / SPEED;

        Action move = Actions.moveTo(target, getY(), time);
        setAnimationState(stateWalk);

        Action action = Actions.sequence(move, Actions.run(callback));
        addAction(action);
    }

    public synchronized void jump(Runnable runnable) {
        int h = 50;
        float t = 1.0f;
        float ddes = des * SPEED * t * 2;
        if (getX() + ddes < 0)
            ddes = getX();
        if (getX() + ddes > CCGame.getInstance().getGlobalViewPort().getWorldWidth())
            ddes = CCGame.getInstance().getGlobalViewPort().getWorldWidth() - getX();
        Action jump = Actions.moveBy(ddes / 2, h, t);
        Action fall = Actions.moveBy(ddes / 2, -h, t);
        Action action = Actions.sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setAnimationState(stateJump);
                    }
                }), jump,
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setAnimationState(stateFall);
                    }
                }), fall,
                Actions.run(runnable));
        addAction(action);
    }
}
