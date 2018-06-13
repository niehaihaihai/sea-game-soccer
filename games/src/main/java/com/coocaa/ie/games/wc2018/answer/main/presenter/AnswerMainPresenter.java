package com.coocaa.ie.games.wc2018.answer.main.presenter;

import com.badlogic.gdx.utils.Disposable;
import com.coocaa.ie.games.wc2018.answer.main.model.AnswerMainModel;
import com.coocaa.ie.games.wc2018.answer.main.stage.AnswerMainStage;

public interface AnswerMainPresenter extends Disposable {
    void create(AnswerMainStage stage, AnswerMainModel model);

    void onPause();

    void onResume();

    void playBgMusic();

    void startNewGame();

    void reloadGame();

    void continueGame();
}
