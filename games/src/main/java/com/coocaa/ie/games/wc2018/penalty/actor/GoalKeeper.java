package com.coocaa.ie.games.wc2018.penalty.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.coocaa.ie.games.wc2018.penalty.PenaltyGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sea on 2018/5/17.
 */

public class GoalKeeper extends Actor{

    private int dir;//1right    2:left
    private TextureRegion menRegion;
//    private TextureAtlas usuallyAtlas;
    private List<TextureRegion> curRegionList;

    private List<TextureRegion> regionMenBigNormalList;
    private List<TextureRegion> regionMenBigScoreList;
    private List<TextureRegion> regionMenBigFailList;

    private List<TextureRegion> regionMenSmallNormalList;
    private List<TextureRegion> regionMenSmallScoreList;
    private List<TextureRegion> regionMenSmallFailList;

//    private TextureAtlas atlasMenBigNormal;
//    private TextureAtlas atlasMenBigScore;
//    private TextureAtlas atlasMenBigFail;
//
//    private TextureAtlas atlasMenSmallNormal;
//    private TextureAtlas atlasMenSmallScore;
//    private TextureAtlas atlasMenSmallFail;

    private int curFrame = 10;


    private int curSize = 1;//1:small   2:big
    private int curState = 1;//1:normal   2:score   3:fail

    /**
     *
     * @param men  1: 任务A  2:人物B
     */
    public GoalKeeper(int men, int size) {
        this.curSize = size;
        if(men == 1) {
            regionMenBigNormalList = new ArrayList<>();
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_a_1);
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_a_2);
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_a_3);
            regionMenBigScoreList = new ArrayList<>();
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_a_1);
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_a_2);
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_a_3);
            regionMenBigFailList = new ArrayList<>();
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_a_1);
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_a_2);
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_a_3);

            regionMenSmallNormalList = new ArrayList<>();
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_a_1);
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_a_2);
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_a_3);
            regionMenSmallScoreList = new ArrayList<>();
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_a_1);
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_a_2);
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_a_3);
            regionMenSmallFailList = new ArrayList<>();
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_a_1);
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_a_2);
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_a_3);
        }else {
            regionMenBigNormalList = new ArrayList<>();
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_b_1);
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_b_2);
            regionMenBigNormalList.add(PenaltyGame.Assets.textureRegion_men_big_normal_b_3);
            regionMenBigScoreList = new ArrayList<>();
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_b_1);
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_b_2);
            regionMenBigScoreList.add(PenaltyGame.Assets.textureRegion_men_big_score_b_3);
            regionMenBigFailList = new ArrayList<>();
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_b_1);
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_b_2);
            regionMenBigFailList.add(PenaltyGame.Assets.textureRegion_men_big_fail_b_3);

            regionMenSmallNormalList = new ArrayList<>();
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_b_1);
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_b_2);
            regionMenSmallNormalList.add(PenaltyGame.Assets.textureRegion_men_small_normal_b_3);
            regionMenSmallScoreList = new ArrayList<>();
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_b_1);
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_b_2);
            regionMenSmallScoreList.add(PenaltyGame.Assets.textureRegion_men_small_score_b_3);
            regionMenSmallFailList = new ArrayList<>();
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_b_1);
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_b_2);
            regionMenSmallFailList.add(PenaltyGame.Assets.textureRegion_men_small_fail_b_3);
        }

        if(size == 1)
            curRegionList = regionMenSmallNormalList;
        else
            curRegionList = regionMenBigNormalList;
        menRegion = curRegionList.get(0);
    }

    /**
     *
     * @param size 1: small   2: big
     */
    public void setMenSize(int size) {
        if(curSize != size) {
            curSize = size;
            if(curSize == 1) {
                switch (curState) {
                    case 1:
                        curRegionList = regionMenSmallNormalList;
                        break;
                    case 2:
                        curRegionList = regionMenSmallScoreList;
                        break;
                    case 3:
                        curRegionList = regionMenSmallFailList;
                        break;
                }
            }else {
                switch (curState) {
                    case 1:
                        curRegionList = regionMenBigNormalList;
                        break;
                    case 2:
                        curRegionList = regionMenBigScoreList;
                        break;
                    case 3:
                        curRegionList = regionMenBigFailList;
                        break;
                }

            }
        }
    }

    public void setMenState(int state) {
        if(curState != state) {
            curState = state;
            if(curState == 1) {
                if(curSize == 1) {
                    curRegionList = regionMenSmallNormalList;
                }else {
                    curRegionList = regionMenBigNormalList;
                }
            }else if(curState == 2){
                if(curSize == 1) {
                    curRegionList = regionMenSmallScoreList;
                }else {
                    curRegionList = regionMenBigScoreList;
                }
            }else {
                if(curSize == 1) {
                    curRegionList = regionMenSmallFailList;
                }else {
                    curRegionList = regionMenBigFailList;
                }
            }
        }
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    @Override
    public float getWidth() {
        return menRegion.getRegionWidth();
    }

    @Override
    public float getHeight() {
        return menRegion.getRegionHeight();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        curFrame++;
        int curpic = curFrame / 5;
        if(curFrame == 19) curFrame = 10;
        menRegion = curRegionList.get(curpic-1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(menRegion, getX(), getY());
    }
}
