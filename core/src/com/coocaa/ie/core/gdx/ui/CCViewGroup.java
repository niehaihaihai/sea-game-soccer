package com.coocaa.ie.core.gdx.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.coocaa.ie.core.gdx.CCGame;

import java.util.ArrayList;
import java.util.List;

public class CCViewGroup extends Table implements FocusManager, Disposable {
    private CCGame mGame;
    private List<Focusable> focusables = new ArrayList<Focusable>();
    private Focusable currentFocusable;

    public CCViewGroup(CCGame game) {
        mGame = game;
    }

    public final CCGame getGame() {
        return mGame;
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        addFocusable(actor);
    }

    @Override
    public void addActorAt(int index, Actor actor) {
        super.addActorAt(index, actor);
        addFocusable(actor);
    }

    @Override
    public void addActorBefore(Actor actorBefore, Actor actor) {
        super.addActorBefore(actorBefore, actor);
        addFocusable(actor);
    }

    @Override
    public void addActorAfter(Actor actorAfter, Actor actor) {
        super.addActorAfter(actorAfter, actor);
        addFocusable(actor);
    }

    private void addFocusable(Actor actor) {
        if (actor instanceof Focusable)
            addFocusable((Focusable) actor);
    }

    @Override
    public void addFocusable(Focusable focusable) {
        synchronized (focusables) {
            if (!focusables.contains(focusable)) {
                focusable.setFocusManager(this);
                focusables.add(focusable);
            }
        }
    }

    @Override
    public boolean requestFocus(Focusable focusable) {
        synchronized (focusables) {
            if (focusables.contains(focusable)) {
                if (currentFocusable != null) {
                    currentFocusable.clearFocus();
                    onFocusChanged(currentFocusable, false);
                }
                currentFocusable = focusable;
                onFocusChanged(currentFocusable, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public Focusable getCurrentFocusable() {
        return currentFocusable;
    }

    public void onFocusChanged(Focusable focusable, boolean hasFocus) {

    }

    @Override
    public void dispose() {

    }
}
