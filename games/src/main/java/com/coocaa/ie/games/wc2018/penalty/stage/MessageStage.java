package com.coocaa.ie.games.wc2018.penalty.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.games.wc2018.penalty.actor.LifeBar;
import com.coocaa.ie.games.wc2018.penalty.actor.MyActor;
import com.coocaa.ie.games.wc2018.penalty.actor.ScoreActor;
import com.coocaa.ie.games.wc2018.penalty.actor.Time;
import com.coocaa.ie.games.wc2018.penalty.actor.TimeNumber;

import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_messagestage_addgold10;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_messagestage_addgold5;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_messagestage_back;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_messagestage_jinqiu;
import static com.coocaa.ie.games.wc2018.penalty.PenaltyGame.Assets.textureregion_messagestage_kubi;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_HEIGHT;
import static com.coocaa.ie.games.wc2018.penalty.actor.PenaltyConstant.WORLD_WIDTH;

/**
 * Created by Sea on 2018/4/28.
 */

public class MessageStage extends Stage {

    private CCGame game;
    private int duration;
    private MyActor back;
    private LifeBar life;
    private MyActor jinqiu, kubi;
    private ScoreActor jinqiuScore, kubiScore;
    private Time time;
    private TimeNumber timeNumber;
    private MyActor addGold;
    private int curKubi;
    private int curScore;

    public MessageStage(Viewport viewport, CCGame game, int duration) {
        super(viewport);
        this.game = game;
        this.duration = duration;
        addBack();
    }

    private void addBack() {
        life = new LifeBar(duration);
        life.setPosition(WORLD_WIDTH/2-life.getWidth()/2, WORLD_HEIGHT - life.getHeight() - game.scaleX(8));
        addActor(life);

        back = new MyActor(textureregion_messagestage_back);
        back.setPosition(WORLD_WIDTH/2-back.getWidth()/2, WORLD_HEIGHT-back.getHeight());
        addActor(back);

        jinqiu = new MyActor(textureregion_messagestage_jinqiu);
        jinqiu.setPosition(game.scaleX(1224), game.scaleX(982));
        addActor(jinqiu);

        kubi = new MyActor(textureregion_messagestage_kubi);
        kubi.setPosition(game.scaleX(625), game.scaleX(982));
        addActor(kubi);

        jinqiuScore = new ScoreActor(game);
        jinqiuScore.setPosition(game.scaleX(1224) - game.scaleX(100), game.scaleX(1009));
        addActor(jinqiuScore);

        kubiScore = new ScoreActor(game);
        kubiScore.setPosition(game.scaleX(625) + game.scaleX(100), game.scaleX(1009));
        addActor(kubiScore);

        time = new Time(game);
        time.setPosition(game.scaleX(922), game.scaleX(1072));
        addActor(time);

        timeNumber = new TimeNumber(game, duration);
        timeNumber.setPosition(game.scaleX(885), game.scaleX(998));
        addActor(timeNumber);

        addGold = new MyActor(textureregion_messagestage_addgold5);
        addGold.setPosition(game.scaleX(773), game.scaleX(954));
        addGold.setVisible(false);
        addActor(addGold);
    }

    public void setScore(int score) {
        this.curScore = score;
        jinqiuScore.setScore(score);
    }

    public int getScore() {
        return curScore;
    }

    public int getKubi() {
        return curKubi;
    }

    public void setKubi(int kubi) {

        kubiScore.setScore(kubi);
        if(kubi - curKubi == 5) {
            addGold.setRegion(textureregion_messagestage_addgold5);
        }else if(kubi - curKubi == 10) {
            addGold.setRegion(textureregion_messagestage_addgold10);
        }
        this.curKubi = kubi;

        DelayAction delayAction = Actions.delay(0.5f);
        RunnableAction runnableAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                addGold.setVisible(false);
            }
        });
        addGold.setVisible(true);
        SequenceAction sequenceAction = Actions.sequence(delayAction, runnableAction);
        addGold.addAction(sequenceAction);
    }


    public void setTimes(int times) {
        timeNumber.setTime(times);
        life.setTime(times);
    }
}
