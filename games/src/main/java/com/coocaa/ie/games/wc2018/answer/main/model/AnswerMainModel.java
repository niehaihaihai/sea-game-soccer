package com.coocaa.ie.games.wc2018.answer.main.model;

import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

public interface AnswerMainModel extends Disposable {
    class Question {
        public String id;
        public String description;
        public List<String> selections = new ArrayList<String>();
        public int answer;
        //	Integer	难度等级 值越大 难度越大 1为简单 2为一般 3为困难
        public int difficulty;
        //Integer	答对添加的时间值 单位s
        public int addTime;
        //   String[]	选项数组 为选项信息
    }

    void create();

    void reloadGame();

    Question question();
}
