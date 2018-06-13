package com.coocaa.ie.games.wc2018;

import com.coocaa.ie.BuildConfig;

public class WC2018Config {
    //游戏主服务器
    private static final String GAME_SERVER_DEBUG = "http://172.20.132.129:8081";// "http://beta.restful.lottery.coocaatv.com";
    private static final String GAME_SERVER_RELEASE = "https://restful.skysrt.com";// "http://beta.restful.lottery.coocaatv.com";

    //语音教程WebUrl
    private static final String VC_HELPER_URL_DEBUG = "http://beta.webapp.skysrt.com/games/voice/index.html";
    private static final String VC_HELPER_URL_RELEASE = "https://webapp.skysrt.com/appstore/voicehelp/index.html";

    //主任务列表WebUrl
    private static final String TASK_URL_DEBUG = "http://beta.webapp.skysrt.com/zy/618/index.html?id=95&shooterId=93&smarterId=90&battleOneId=91&battleTwoId=92&source=task";
    private static final String TASK_URL_RELEASE = "https://webapp.skysrt.com/zy/618/index.html?id=69&shooterId=70&smarterId=71&battleOneId=72&battleTwoId=73&source=task";

    public static final String getGameServer() {
        if (BuildConfig.DEBUG)
            return GAME_SERVER_DEBUG;
        return GAME_SERVER_RELEASE;
    }

    public static final String getVCHelperUrl() {
        if (BuildConfig.DEBUG)
            return VC_HELPER_URL_DEBUG;
        return VC_HELPER_URL_RELEASE;
    }

    public static final String getTaskUrl() {
        if (BuildConfig.DEBUG)
            return TASK_URL_DEBUG;
        return TASK_URL_RELEASE;
    }
}
