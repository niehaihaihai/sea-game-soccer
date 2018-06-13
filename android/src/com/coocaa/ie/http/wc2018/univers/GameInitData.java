package com.coocaa.ie.http.wc2018.univers;

import java.io.Serializable;

/**
 * Created by Sea on 2018/5/22.
 */

public class GameInitData implements Serializable {
    private static final long serialVersionUID = 8435798982001955424L;

    public ActiveEntity activeEntity;

    public NowPartTime nowPartTime;

    public NextPartTime nextPartTime;

    public int overNumber;//当时游戏段 剩余游戏次数

    public int reliveNumber;//复活次数 /默认情况下只有一次复活机会

    public String sysTime;//服务器时间毫秒值

    public CanaryInfo canaryInfo;//酷币信息和排名

    public class ActiveEntity
    {
        String activeBeginTime; // 活动开始时间 时间毫秒值
        String activeEndTime;// 活动结束时间 时间毫秒值
        String activeName; // 活动名称
    }

    public class NowPartTime
    {
        public int state;// -1表示不在游戏时间段 0表示在游戏时间段
        public String beginTime; //当前时间段开始时间 state为-1的时候为null 时间毫秒值
        public String endTime;//当前时间段结束时间 state为-1的时候为null 时间毫秒值
    }

    public class NextPartTime
    {
        public int state;//0表示下个时间段在当天 1表示在第二天
        public String beginTime;//下一个时间段开始时间 毫秒值
        public String endTime;//下一个时间段结束时间 毫秒值
    }

    public class CanaryInfo
    {
        public int ranking;//本轮排名
        public long coins;//本轮酷币
        public long totalCoins;//累计酷币
    }
}
