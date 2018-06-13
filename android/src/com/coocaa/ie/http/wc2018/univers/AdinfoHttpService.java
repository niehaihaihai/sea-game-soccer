package com.coocaa.ie.http.wc2018.univers;

import com.coocaa.ie.http.base.HttpListResult;

import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Sea on 2017/12/26.
 */

public interface AdinfoHttpService {

    @GET("getAdInfo")
    HttpListResult<Adinfo> getAdInfo(@Query("id") String id, @Query("partKey") String partKey);
}
