package com.coocaa.ie.games.wc2018.answer.main.stage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.actor.TextureRegionActor;
import com.coocaa.ie.core.gdx.ui.CCTextView;
import com.coocaa.ie.core.gdx.ui.CCView;
import com.coocaa.ie.core.gdx.ui.CCViewGroup;
import com.coocaa.ie.core.gdx.ui.FocusManager;
import com.coocaa.ie.core.gdx.ui.Focusable;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.answer.main.stage.AnswerMainStage;

public class MainLayout extends CCViewGroup implements FocusManager {

    private static class XXButton extends CCTextView {
        private TextureRegionActor iconActor;

        public XXButton(CCGame game) {
            super(game, "", 46, Color.WHITE);
            setBackground(AnswerGame.Assets.unfocusedDrawable);
            setSize(game.scale(487), game.scale(177));
            setTextPadding((int) game.scale(0), (int) game.scale(20), (int) game.scale(0), (int) game.scale(20));
            iconActor = new TextureRegionActor(game, AnswerGame.Assets.textureSelectionRight);
            iconActor.setVisible(false);
            iconActor.setPosition(game.scale(395), (getHeight() - iconActor.getHeight()) / 2);
            addActor(iconActor);
        }

        public void reset() {
            clearFocus();
            setFocusable(true);
            setBackground(AnswerGame.Assets.unfocusedDrawable);
            iconActor.setVisible(false);
        }

        public void correctAnswer() {
            setFocusable(false);
            iconActor.setVisible(true);
            iconActor.setRegion(AnswerGame.Assets.textureSelectionRight);
        }

        public void incorrectAnswer() {
            setFocusable(false);
            setBackground(AnswerGame.Assets.wrongDrawable);
            iconActor.setVisible(true);
            iconActor.setRegion(AnswerGame.Assets.textureSelectionWrong);
        }
    }

    public static final int OFFSET = 50;

    public static final int BUTTON_UNKNOWN = -1;
    public static final int BUTTON1 = 0;
    public static final int BUTTON2 = 1;
    public static final int BUTTON3 = 2;

    private CCTextView description;

    private XXButton[] answerButtons = new XXButton[3];

    public MainLayout(CCGame game) {
        super(game);
        setFillParent(true);
        addListener(mInputListener);

        description = new CCTextView(game, "", 48, Color.WHITE);
//        description.setLabelWidth(game.scale(1200));
        add(description).colspan(3).center();
        row().pad(game.scale(14), 0, 0, 0);

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new XXButton(game);
            float pl = 0;
            if (i > 0)
                pl = game.scale(28);
            add(answerButtons[i]).padLeft(pl).center();
        }
        answerButtons[1].requestFocus();
    }

    public void setContentText(String descriptionText, String button1Text, String button2Text, String button3Text) {
        for (XXButton answerButton : answerButtons)
            answerButton.reset();
        description.setText(descriptionText);
        answerButtons[0].setText(button1Text);
        answerButtons[1].setText(button2Text);
        answerButtons[2].setText(button3Text);
        answerButtons[1].requestFocus();
    }

    public void correctAnswer(int button) {
        for (int i = 0; i < answerButtons.length; i++) {
            if (i == button) {
                answerButtons[i].correctAnswer();
                break;
            }
        }
    }

    public void incorrectAnswer(int button, int correct) {
        for (int i = 0; i < answerButtons.length; i++) {
            if (i == correct)
                answerButtons[i].correctAnswer();
            if (i == button) {
                answerButtons[i].incorrectAnswer();
            }
        }
    }

    private AnswerMainStage.AnswerMainStageEventListener eventListener = null;

    public void setEventListener(AnswerMainStage.AnswerMainStageEventListener eventListener) {
        this.eventListener = eventListener;
    }

    private final InputListener mInputListener = new InputListener() {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            Gdx.app.debug("MainLayout", "keyDown:" + keycode);
            if (event.getType() == InputEvent.Type.keyDown) {
                switch (keycode) {
                    case Input.Keys.LEFT: {
                        boolean flag = false;
                        for (int i = 0; i < answerButtons.length; i++) {
                            XXButton answerButton = answerButtons[i];
                            if (answerButton == getCurrentFocusable()) {
                                int j = i - 1;
                                if (j < 0)
                                    j = answerButtons.length - 1;
                                answerButtons[j].requestFocus();
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                            answerButtons[0].requestFocus();
                        if (eventListener != null)
                            eventListener.onDpadLeftClick();
                        return true;
                    }
                    case Input.Keys.RIGHT: {
                        boolean flag = false;
                        for (int i = 0; i < answerButtons.length; i++) {
                            XXButton answerButton = answerButtons[i];
                            if (answerButton == getCurrentFocusable()) {
                                int j = i + 1;
                                if (j >= answerButtons.length)
                                    j = 0;
                                answerButtons[j].requestFocus();
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                            answerButtons[0].requestFocus();
                        if (eventListener != null)
                            eventListener.onDpadRightClick();
                        return true;
                    }
                    case Input.Keys.ENTER:
                    case Input.Keys.CENTER: {
                        boolean flag = false;
                        if (eventListener != null) {
                            for (int i = 0; i < answerButtons.length; i++) {
                                XXButton answerButton = answerButtons[i];
                                if (getCurrentFocusable() == answerButton && eventListener != null) {
                                    eventListener.onButtonClick(i);
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag && eventListener != null)
                                eventListener.onButtonClick(BUTTON_UNKNOWN);
                        }
                        return true;
                    }
                    case Input.Keys.DPAD_DOWN: {
                        if (eventListener != null) {
                            eventListener.onDpadDownClick();
                        }
                        return true;
                    }
                    case Input.Keys.DPAD_UP: {
                        if (eventListener != null) {
                            eventListener.onDpadUpClick();
                        }
                        return true;
                    }
                    case Input.Keys.BACK: {
                        if (eventListener != null) {
                            eventListener.onBackKeyClick();
                        }
                        return true;
                    }
                    default:
                        return true;
                }
            } else
                return true;
        }
    };

    @Override
    public void onFocusChanged(Focusable focusable, boolean hasFocus) {
        if (focusable.isFocusable()) {
            Drawable drawable;
            if (hasFocus) {
                drawable = AnswerGame.Assets.focusedDrawable;
            } else {
                drawable = AnswerGame.Assets.unfocusedDrawable;
            }
            ((CCView) focusable).setBackground(drawable);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        CCGame.dispose(description);
        for (XXButton answerButton : answerButtons)
            CCGame.dispose(answerButton);
    }
}
