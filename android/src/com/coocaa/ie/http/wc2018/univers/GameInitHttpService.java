package com.coocaa.ie.http.wc2018.univers;

import com.coocaa.ie.http.base.HttpResult;

import retrofit2.http.GET;


/**
 * Created by Sea on 2017/12/26.
 */

public interface GameInitHttpService {

    @GET("init")
    HttpResult<GameInitData> getGameInitInfo();
}
