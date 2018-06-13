package com.coocaa.ie.games.wc2018.penalty.basedata;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Sea on 2018/5/9.
 */

public class DifficultyControler {

    public void changeLevel(int score) {

//        if(score >= 31) {
//            level = 9;
//        }else if(score > 25) {
//            level = 8;
//        }else if(score > 19) {
//            level = 7;
//        }else if(score > 12) {
//            level = 6;
//        }else if(score > 9) {
//            level = 5;
//        }else if(score > 7) {
//            level = 4;
//        }else if(score > 5) {
//            level = 3;
//        }else if(score > 2) {
//            level = 2;
//        }else {
//            level = 1;
//        }


        Random rand = new Random();
        level = rand.nextInt(10) + 1;

        switch (level) {
            case 1:
                moveArea = 1.0f;moveCyc = 8.0f;keeperCount = 1;keeperType = 1;
                break;
            case 2:
                moveArea = 1.0f;moveCyc = 6.0f;keeperCount = 1;keeperType = 1;
                break;
            case 3:
                moveArea = 1.0f;moveCyc = 4.0f;keeperCount = 1;keeperType = 1;
                break;
            case 4:
                moveArea = 1.0f;moveCyc = 6.0f;keeperCount = 1;keeperType = 2;
                break;
            case 5:
                moveArea = div(2, 3, 2);moveCyc = 8.0f;keeperCount = 2;keeperType = 1;
                break;
            case 6:
                moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 1;
                break;
            case 7:
                moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 2;
                break;
            case 8:
                moveArea = div(1, 2, 2);moveCyc = 5.0f;keeperCount = 2;keeperType = 1;
                break;
            case 9:
                moveArea = div(1, 2, 2);moveCyc = 3.0f;keeperCount = 2;keeperType = 2;
                break;
            case 10:
                moveArea = div(1, 2, 2);moveCyc = 2.0f;keeperCount = 2;keeperType = 1;
                break;

        }

//        switch (level) {
//            case 1:
//                moveArea = 1.0f;moveCyc = 10.0f;keeperCount = 1;keeperType = 1;
//                break;
//            case 2:
//                moveArea = 1.0f;moveCyc = 8.0f;keeperCount = 1;keeperType = 1;
//                break;
//            case 3:
//                moveArea = 1.0f;moveCyc = 6.0f;keeperCount = 1;keeperType = 1;
//                break;
//            case 4:
//                moveArea = 1.0f;moveCyc = 8.0f;keeperCount = 1;keeperType = 2;
//                break;
//            case 5:
//                moveArea = div(2, 3, 2);moveCyc = 8.0f;keeperCount = 2;keeperType = 1;
//                break;
//            case 6:
//                moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 1;
//                break;
//            case 7:
//                moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 2;
//                break;
//            case 8:
//                moveArea = div(1, 2, 2);moveCyc = 5.0f;keeperCount = 2;keeperType = 1;
//                break;
//            case 9:
//                moveArea = div(1, 2, 2);moveCyc = 3.0f;keeperCount = 2;keeperType = 2;
//                break;
//
//        }


//        if(score > 50) {
//            level = 9;moveArea = div(1, 2, 2);moveCyc = 4.0f;keeperCount = 2;keeperType = 2;
//        }else if(score > 40) {
//            level = 8;moveArea = div(1, 2, 2);moveCyc = 5.0f;keeperCount = 2;keeperType = 1;
//        }else if(score > 30) {
//            level = 7;moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 2;
//        }else if(score > 25) {
//            level = 6;moveArea = div(2, 3, 2);moveCyc = 6.0f;keeperCount = 2;keeperType = 1;
//        }else if(score > 20) {
//            level = 5;moveArea = div(2, 3, 2);moveCyc = 8.0f;keeperCount = 2;keeperType = 1;
//        }else if(score > 15) {
//            level = 4;moveArea = 1.0f;moveCyc = 10.0f;keeperCount = 1;keeperType = 2;
//        }else if(score > 10) {
//            level = 3;moveArea = 1.0f;moveCyc = 6.0f;keeperCount = 1;keeperType = 1;
//        }else if(score > 5) {
//            level = 2;moveArea = 1.0f;moveCyc = 8.0f;keeperCount = 1;keeperType = 1;
//        }else {
//            level = 1;moveArea = 1.0f;moveCyc = 8.0f;keeperCount = 1;keeperType = 1;
//        }
    }

    private static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 难度等级
     */
    private int level = 1;

    /**
     * 守门员移动范围
     */
    private float moveArea = 1.0f;

    /**
     * 守门员来回移动时间
     */
    private float moveCyc = 10.0f;

    /**
     * 守门员人数 最多只有两个
     */
    private int keeperCount = 1;

    /**
     * 守门员类型, 1：正常大小  2：增大型守门员
     */
    private int keeperType = 1;

    public int getLevel() {
        return level;
    }

    public float getMoveArea() {
        return moveArea;
    }

    public float getMoveCyc() {
        return moveCyc;
    }

    public int getKeeperCount() {
        return keeperCount;
    }

    public int getKeeperType() {
        return keeperType;
    }
}
