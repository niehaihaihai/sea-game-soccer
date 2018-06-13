package com.coocaa.ie.games.wc2018.answer.main.stage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCGroup;
import com.coocaa.ie.core.gdx.actor.NumberActor;
import com.coocaa.ie.core.gdx.actor.TextureRegionActor;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;

public class CriticalTipsView extends CCGroup {
    private TextureRegionActor bgActor, tips0Actor, tips1Actor;
    private NumberActor numberActor;

    public CriticalTipsView(CCGame game) {
        super(game);
        bgActor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionCriticalBg);
        tips0Actor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionCriticalTips0);
        numberActor = new NumberActor(game, AnswerGame.Assets.texturesCriticalNumber, game.scale(-20));
        tips1Actor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionCriticalTips1);
        updateX();
        addActor(bgActor);
        addActor(tips0Actor);
        addActor(numberActor);
        addActor(tips1Actor);
        setSize(bgActor.getWidth(), bgActor.getHeight());
        setOrigin(Align.center);
    }

    private void updateX(Actor actor, float x) {
        float y = (bgActor.getHeight() - actor.getHeight()) / 2;
        Gdx.app.log("update", "x:" + x + "   y:" + y);
        actor.setPosition(x, y);
    }

    public void show(int value) {
        clearActions();
        numberActor.setNumber(value);
        updateX();
        Color color = getColor();
        color.a = 0;
        setColor(color);
        setScale(0.0f, 0.0f);
        Action show = Actions.show();
        Action fadeIn = Actions.fadeIn(0.24f);
        Action scaleIn = Actions.scaleTo(1.0f, 1.0f, 0.24f);
        Action actionIn = Actions.sequence(show, Actions.parallel(fadeIn, scaleIn));
        Action delay = Actions.delay(0.68f);
        Action fadeOut = Actions.fadeOut(0.24f);
        Action scaleOut = Actions.scaleTo(1.6f, 1.6f, 0.24f);
        Action hide = Actions.hide();
        Action actionOut = Actions.sequence(Actions.parallel(fadeOut, scaleOut), hide);
        Action action = Actions.sequence(actionIn, delay, actionOut);
        addAction(action);
    }

    private void updateX() {
        float numberOffset = getGame().scale(-15);
        float tips1Offset = getGame().scale(-15);

        float width = tips0Actor.getWidth() + numberActor.getWidth() + numberOffset + tips1Actor.getWidth() + tips1Offset;
        float x = (bgActor.getWidth() - width) / 2;
        updateX(tips0Actor, x);
        updateX(numberActor, tips0Actor.getX() + tips0Actor.getWidth() + numberOffset);
        updateX(tips1Actor, numberActor.getX() + numberActor.getWidth() + tips1Offset);
    }
}
