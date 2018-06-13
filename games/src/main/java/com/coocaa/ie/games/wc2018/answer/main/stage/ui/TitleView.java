package com.coocaa.ie.games.wc2018.answer.main.stage.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCGroup;
import com.coocaa.ie.core.gdx.actor.FlashActor;
import com.coocaa.ie.core.gdx.actor.NumberActor;
import com.coocaa.ie.core.gdx.actor.TextureRegionActor;
import com.coocaa.ie.core.gdx.ui.CCTable;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;

public class TitleView extends CCTable {
    private static class LeftView extends CCGroup {
        private static class CoinsAction extends TemporalAction {
            private int start, target;
            private int current;

            public CoinsAction(int start, int target) {
                this.start = start;
                this.target = target;
                setDuration(0.5f);
            }

            @Override
            protected void begin() {
                NumberActor numberActor = (NumberActor) getTarget();
                numberActor.setNumber(start);
            }

            @Override
            protected void update(float percent) {
                NumberActor numberActor = (NumberActor) getTarget();
                current = start + (int) ((float) (target - start) * (int) (percent * 10) / 10.0f);
                numberActor.setNumber(current);
            }

            @Override
            protected void end() {
                NumberActor numberActor = (NumberActor) getTarget();
                numberActor.setNumber(target);
            }
        }

        private TextureRegionActor bgActor;
        private NumberActor numberActor;
        private CoinsAction coinsAction;

        public LeftView(CCGame game) {
            super(game);
            bgActor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionTitleLeft);
            addActor(bgActor);
            numberActor = new NumberActor(game, AnswerGame.Assets.texturesNumber, 0);
            numberActor.setPosition(game.scale(120), game.scale(25));
            addActor(numberActor);
            setSize(bgActor.getWidth(), bgActor.getHeight());
        }

        public synchronized void updateCoins(int coins) {
            if (coins < 10) {
                numberActor.setNumber(coins);
            } else {
                numberActor.clearActions();
                int start = numberActor.getNumber();
                if (coinsAction != null) {
                    numberActor.setNumber(coinsAction.current);
                    start = coinsAction.current;
                }
                coinsAction = new CoinsAction(start, coins);
                numberActor.addAction(coinsAction);
            }
        }
    }

    private static class CenterView extends CCGroup {
        private static class TimerView {
            private static class TimerNumberActor extends CCGroup {
                public static final float WIDTH = 79;
                public static final float SPACE = 38;
                private FlashActor bgActor;
                private NumberActor numberActor;

                public TimerNumberActor(CCGame game) {
                    super(game);
                    bgActor = new FlashActor(game);
                    bgActor.setBg(AnswerGame.Assets.textureTimerNormal);
                    addActor(bgActor);
                    numberActor = new NumberActor(game, AnswerGame.Assets.texturesTimerNumber, 0);
                    numberActor.setPosition((bgActor.getWidth() - numberActor.getWidth()) / 2, (bgActor.getHeight() - numberActor.getHeight()) / 2);
                    addActor(numberActor);
                    setSize(bgActor.getWidth(), bgActor.getHeight());
                }

                public void startFlash() {
                    bgActor.setBg(AnswerGame.Assets.textureTimerUrgent);
                    bgActor.setFlash(AnswerGame.Assets.textureTimerUrgentLight);
                    bgActor.startFlash(1.0f);
                }

                public void stopFlash() {
                    bgActor.stopFlash();
                    bgActor.setBg(AnswerGame.Assets.textureTimerNormal);
                }

                public void updateNumber(int number) {
                    numberActor.setNumber(number);
                }
            }

            private boolean bFlashing = false;
            private TimerNumberActor[] numberActors = new TimerNumberActor[3];
            private TextureRegionActor plusTimerActor;
            private float plusTimerActorY;
            private CCGame game;

            public TimerView(CCGame game, CenterView centerView) {
                this.game = game;
                for (int i = 0; i < numberActors.length; i++) {
                    numberActors[i] = new TimerNumberActor(game);
                    centerView.addActor(numberActors[i]);
                }
                float y = game.scale(65 - TimerNumberActor.SPACE);
                numberActors[0].setPosition(game.scale(190), y);
                numberActors[1].setPosition(game.scale(190 + TimerNumberActor.WIDTH + 23), y);
                numberActors[2].setPosition(game.scale(190 + TimerNumberActor.WIDTH + 23 + TimerNumberActor.WIDTH + 23), y);
//                numberActors[3].setPosition(game.scale(170 + TimerNumberActor.WIDTH + 12 + TimerNumberActor.WIDTH + 37 + TimerNumberActor.WIDTH + 12 - TimerNumberActor.SPACE), y);

                plusTimerActor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionTimerPlus3);
                plusTimerActorY = centerView.getHeight() - plusTimerActor.getHeight();
                plusTimerActor.setOrigin(Align.center);
                plusTimerActor.setVisible(false);
                plusTimerActor.setPosition(game.scale(190 + TimerNumberActor.WIDTH + 23 + TimerNumberActor.WIDTH + 23 + TimerNumberActor.WIDTH + 23), plusTimerActorY);
                centerView.addActor(plusTimerActor);
            }

            public void plusTimer(int v) {
                plusTimerActor.clearActions();
                plusTimerActor.setY(plusTimerActorY);
                switch (v) {
                    case 2: {
                        plusTimerActor.setRegion(AnswerGame.Assets.textureRegionTimerPlus2);
                        break;
                    }
                    case 3: {
                        plusTimerActor.setRegion(AnswerGame.Assets.textureRegionTimerPlus3);
                        break;
                    }
                    case 5: {
                        plusTimerActor.setRegion(AnswerGame.Assets.textureRegionTimerPlus5);
                        break;
                    }
                    default: {
                        return;
                    }
                }
                plusTimerActor.setScale(0.0f, 0.0f);
                Action show = Actions.show();
                Action scaleIn = Actions.scaleTo(1.0f, 1.0f, 0.2f);
                Action actionIn = Actions.sequence(show, scaleIn);
                Action delay = Actions.delay(0.44f);
                Action moveOut = Actions.moveBy(0, game.scale(39), 0.24f);
                Action scaleOut = Actions.scaleTo(0.0f, 0.0f, 0.24f);
                Action hide = Actions.hide();
                Action actionOut = Actions.sequence(Actions.parallel(moveOut, scaleOut), hide);
                Action action = Actions.sequence(actionIn, delay, actionOut, Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        plusTimerActor.setY(plusTimerActorY);
                    }
                }));
                plusTimerActor.addAction(action);
            }

            public synchronized void updateTimer(int seconds) {
                if (seconds > 999)
                    seconds = 999;
                if (seconds <= AnswerGame.Rules.TIME_URGENT_VALUE && !bFlashing) {
                    bFlashing = true;
                    for (TimerNumberActor numberActor : numberActors)
                        numberActor.startFlash();
                } else if (seconds > AnswerGame.Rules.TIME_URGENT_VALUE) {
                    bFlashing = false;
                    for (TimerNumberActor numberActor : numberActors)
                        numberActor.stopFlash();
                }

                int _value = seconds;
                int i = numberActors.length - 1;
                while (i >= 0) {
                    int nn = _value % 10;
                    numberActors[i].updateNumber(nn);
                    _value /= 10;
                    i--;
                }
            }
        }

        private TextureRegionActor bgActor;
        private TimerView timerView;

        public CenterView(CCGame game) {
            super(game);
            bgActor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionTitleCenter);
            addActor(bgActor);
            setSize(bgActor.getWidth(), bgActor.getHeight());
            timerView = new TimerView(game, this);
        }

        public void plusTimer(int v) {
            timerView.plusTimer(v);
        }

        public void updateTimer(int seconds) {
            timerView.updateTimer(seconds);
        }
    }

    private static class RightView extends CCGroup {
        private TextureRegionActor bgActor;
        private NumberActor numberActor;

        public RightView(CCGame game) {
            super(game);
            bgActor = new TextureRegionActor(game, AnswerGame.Assets.textureRegionTitleRight);
            addActor(bgActor);
            numberActor = new NumberActor(game, AnswerGame.Assets.texturesNumber, 0);
            numberActor.setPosition(game.scale(103), game.scale(25));
            addActor(numberActor);
            setSize(bgActor.getWidth(), bgActor.getHeight());
            numberActor.setNumber(0);
        }

        public void updateQuestions(int count) {
            if (count >= 10)
                numberActor.setPosition(getGame().scale(80), getGame().scale(25));
            numberActor.setNumber(count);
        }
    }

    private LeftView leftView;
    private CenterView centerView;
    private RightView rightView;

    public TitleView(CCGame game) {
        super(game);
        leftView = new LeftView(game);
        centerView = new CenterView(game);
        rightView = new RightView(game);
        add(leftView).padTop(game.scale(33)).padRight(game.scale(-45)).right().top();
        add(centerView).center().top();
        add(rightView).padTop(game.scale(33)).padLeft(game.scale(-45)).left().top();
        setHeight(centerView.getHeight());
        setFillParent(true);
    }

    public void plusTimer(int v) {
        centerView.plusTimer(v);
    }

    public void updateTimer(int seconds) {
        centerView.updateTimer(seconds);
    }

    public void updateConins(int coins) {
        leftView.updateCoins(coins);
    }

    public void updateQuestions(int count) {
        rightView.updateQuestions(count);
    }
}
