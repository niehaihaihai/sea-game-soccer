package com.coocaa.ie.games.wc2018.answer.main.model.impl;

import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.answer.assets.AnswerData;
import com.coocaa.ie.games.wc2018.answer.main.model.AnswerMainModel;
import com.coocaa.ie.games.wc2018.utils.web.call.WC2018HttpBaseData;

public class AnswerMainModelImpl implements AnswerMainModel {
    private WC2018Game mGame;
    private AnswerData data = null;

    public AnswerMainModelImpl(WC2018Game game) {
        mGame = game;
    }

    @Override
    public void create() {
    }

    @Override
    public void reloadGame() {
        data = null;
    }

    private int index = 0;

    @Override
    public Question question() {
        if (data == null) {
            WC2018HttpBaseData _data = mGame.getAssetManager().get(AnswerGame.Assets.HH_DATA);
            data = _data.getObject();
            index = 0;
        }
        if (data != null) {
            AnswerData.Subject subject = data.subjectList.get(index++);
            Question question = new Question();
            question.id = String.valueOf(subject.subjectId);
            question.description = subject.subjectName;
            question.answer = subject.answerKey;
            question.addTime = subject.addTime;
            question.difficulty = subject.difficulty;
            question.selections.addAll(subject.optionArray);
            return question;
        }
        return null;
//        Question question = new Question();
//        int a = (int) (Math.random() * 1000);
//        int b = (int) (Math.random() * 1000);
//        int r = a + b;
//        question.description = index++ + "、" + a + " + " + b + " =（）";
//        question.answer = (int) (Math.random() * 3);
//        String[] sss = new String[]{"A、", "B、", "C、"};
//        for (int i = 0; i < 3; i++) {
//            int rr;
//            if (question.answer == i) {
//                rr = r;
//            } else {
//                while (true) {
//                    rr = r + (int) ((Math.random() - 0.5f) * 10.0f) * 10;
//                    if (rr != r)
//                        break;
//                }
//            }
//            question.selections.add(sss[i] + rr);
//        }
//        return question;
    }

    @Override
    public void dispose() {

    }
}
