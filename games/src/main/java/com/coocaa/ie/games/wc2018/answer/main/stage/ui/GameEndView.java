package com.coocaa.ie.games.wc2018.answer.main.stage.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.actor.TextureRegionActor;
import com.coocaa.ie.core.gdx.ui.CCView;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;

public class GameEndView extends CCView {
    public static final int GAME_OVER = 1;
    public static final int TIME_OUT = 2;

    private TextureRegionActor tips;

    public GameEndView(CCGame game) {
        super(game);
        int width = (int) game.getGlobalViewPort().getWorldWidth();
        int height = (int) game.getGlobalViewPort().getWorldHeight();
        setSize(width, height);
        setFillParent(true);
        setBackgroundColor(0, 0, 0, 0.8f);
        tips = new TextureRegionActor(getGame(), (TextureRegion) null);
//        tips.setSize(game.getGlobalViewPort().getWorldWidth(), game.getGlobalViewPort().getWorldHeight());
        add(tips).center();
    }

    public void setTips(int type) {
        if (type == GAME_OVER)
            tips.setRegion(AnswerGame.Assets.textureRegionGameOver);
        else
            tips.setRegion(AnswerGame.Assets.textureRegionTimeOut);
    }
}
