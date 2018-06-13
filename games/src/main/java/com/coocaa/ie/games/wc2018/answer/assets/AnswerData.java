package com.coocaa.ie.games.wc2018.answer.assets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnswerData implements Serializable {
    public static class Subject implements Serializable {
        //        Integer	题目id
        public int subjectId;
        //        String	题目名称
        public String subjectName;
        //	Integer	难度等级 值越大 难度越大 1为简单 2为一般 3为困难
        public int difficulty;
        //Integer	答对添加的时间值 单位s
        public int addTime;
        //   String[]	选项数组 为选项信息
        public List<String> optionArray;
        //        Integer	答案 0表示数组第一个位置 1表示第二个个为正确答案
        public int answerKey;
    }

    //返回一次游戏的唯一标示值
    public String lotteryCode;
    public List<Subject> subjectList = new ArrayList<Subject>();
}
