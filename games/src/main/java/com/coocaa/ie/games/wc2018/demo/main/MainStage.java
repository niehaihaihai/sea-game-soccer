package com.coocaa.ie.games.wc2018.demo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCLabel;
import com.coocaa.ie.core.gdx.CCStage;
import com.coocaa.ie.core.gdx.actor.FPSActor;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

/**
 * Created by lu on 2018/4/25.
 */

public class MainStage extends CCStage {
    private boolean bCreated = false;
    private FPSActor fpsActor;
    CCLabel label, deviceInfo;
    private DragonActor[] dragons = new DragonActor[3];

    public MainStage(CCGame game) {
        super(game);

        fpsActor = new FPSActor(getGame());
        addActor(fpsActor);

        label = new CCLabel(getGame(), "");
        label.setText("zcxzczc在线充值贵公司欠人情翁群】【123123123", 32, Color.YELLOW);
        label.setPosition(game.scale(300), game.scale(1000));
        label.setWidth(game.scale(100));//设置每行的宽度
        label.setWrap(true);//开启换行
        addActor(label);

        CCGame.CCGameSystem.CcosDeviceInfo ccosDeviceInfo = getGame().getCCGameSystem().getDeviceInfo();
        deviceInfo = new CCLabel(getGame(), "uuuu", 32);
        deviceInfo.setColor(Color.BLUE);
        try {
            deviceInfo.setText(ccosDeviceInfo.skymodel + "-" + ccosDeviceInfo.skytype, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(deviceInfo);
        deviceInfo.getParent().setTransform(true);
        final Runnable rrr = new Runnable() {
            @Override
            public void run() {
                Action action1 = Actions.sequence(aaa(), Actions.run(this));
                deviceInfo.addAction(action1);
            }
        };

        RunnableAction runnable = Actions.run(rrr);
        Action action1 = Actions.sequence(aaa(), runnable);
        deviceInfo.addAction(action1);


        for (int i = 0; i < dragons.length; i++) {
            dragons[i] = new DragonActor();
            dragons[i].setPosition(game.scale(300), game.scale((i + 1) * 250));
            addActor(dragons[i]);
            dragons[i].startYourShow();
        }
    }

    private Action aaa() {
        float x = (float) (getGame().getGlobalViewPort().getWorldWidth() * Math.random());
        Action moveTo = Actions.moveTo(x, (int) (getGame().getGlobalViewPort().getWorldHeight() - deviceInfo.getY()), 3.0f);
        double s = Math.random() - 0.5f;
        Action rotateTo = Actions.rotateTo((int) (Math.abs(s) / s) * (int) (360.00f * Math.random()), 3.0f);
        float scale = (float) (Math.random() + 0.5f);
        Action scaleTo = Actions.scaleTo(scale, scale, 3.0f);
        Action action = Actions.parallel(moveTo, rotateTo, scaleTo);
        return action;
    }

    private int count = 0;
    private int countMax = (int) (Math.random() * 10);

    @Override
    public void act(float delta) {
        super.act(delta);
        for (DragonActor dragon : dragons)
            dragon.act(delta);
    }

    @Override
    public void draw() {
        super.draw();
        for (DragonActor dragon : dragons)
            dragon.draw();
    }

    @Override
    public void act() {
        super.act();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.log("KeyDown", "isKeyPressed: BACK键被按下");
        }
        if (fpsActor != null && fpsActor.getZIndex() >= 0) {
            fpsActor.setZIndex(getActors().size);
        }
        if (label.getZIndex() >= 0) {
            label.setZIndex(getActors().size);
        }
        if (deviceInfo.getZIndex() >= 0) {
            deviceInfo.setZIndex(getActors().size);
        }

        if (!bCreated) {
            bCreated = true;
            Image bg = new Image(new TextureRegion(getGame().getAssetManager().get(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.TEXTURE_PAK).findRegion("background")));
            bg.setScale(getGame().scale(1));
            addActor(bg);
            bCreated = true;
            Music music = getGame().getAssetManager().get(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.BG_MUSIC);
            music.setLooping(true);
            music.play();
        }
        count++;
        if (count >= countMax) {
            count = 0;
            countMax = (int) (Math.random() * 10);
            GoldActor.newGold(getGame(), this, getGame().getAssetManager().get(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.TEXTURE_PAK).findRegion("gold"), getGame().getAssetManager().get(com.coocaa.ie.games.wc2018.demo.DemoGame.Assets.GOLD_SOUND));
        }
//        if (!getGame().getAssetManager().update()) {
//            float progress = getGame().getAssetManager().getProgress();
//            System.out.println("AnswerMainStageImpl progress:" + progress);
//            if (progress >= 1) {
//                Image bg = new Image(new TextureRegion(getGame().getAssetManager().get(BG_TEXTURE)));
//                addActor(bg);
//                bCreated = true;
//                Music music = getGame().getAssetManager().get(BG_MUSIC);
//                music.setLooping(true);
//                music.play();
//            } else {
//                deviceInfo.setText("progress:" + progress);
//            }
//        } else {
//            Image bg = new Image(new TextureRegion(getGame().getAssetManager().get(BG_TEXTURE)));
//            addActor(bg);
//            bCreated = true;
//            Music music = getGame().getAssetManager().get(BG_MUSIC);
//            music.setLooping(true);
//            music.play();
//            count++;
//            if (count >= countMax) {
//                count = 0;
//                countMax = (int) (Math.random() * 10);
//                GoldActor.newGold(this, getGame().getAssetManager().get(GOLD_TEXTURE), getGame().getAssetManager().get(GOLD_SOUND));
//            }
//        }
    }


    @Override
    public void dispose() {
        super.dispose();
        label.dispose();
    }

    @Override
    public boolean keyDown(int keyCode) {
        System.out.println("keyDown:" + keyCode);
        return super.keyDown(keyCode);
    }
}
