package com.coocaa.ie.http.wc2018.univers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.coocaa.ie.BuildConfig;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.http.base.HttpCallBack;
import com.coocaa.ie.http.base.HttpMethod;
import com.coocaa.ie.http.base.HttpListResult;
import com.coocaa.ie.http.base.IEHeaders;

import java.util.List;
import java.util.Map;

/**
 * Created by Sea on 2018/5/22.
 */

public class AdinfoHttpMethod extends HttpMethod<AdinfoHttpService>{

    public static int _NET_ERROR = 123321;

    public AdinfoHttpMethod(Context context) {
        super(context);
    }

    /**
     * 获取广告信息接口
     * @param id              活动id 由运营提供
     * @param partKey         gameBegin 开始游戏前的广告位置    game 游戏中广告位      gameEnd 提交数据后的广告位置
     * @param callBack        接口回调
     */
    public void getAdInfo(String id, String partKey, @NonNull final HttpCallBack<List<Adinfo>> callBack) {
        HttpListResult<Adinfo> result = null;
        try {
            result = getService().getAdInfo(id, partKey);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Sea-game", "HttpError : " + e.toString());
        }

        if(result == null){
            Log.e("Sea-game", "getAdInfo failed null result-----");
            callBack.error(_NET_ERROR);
            return;
        }
        Log.i("Sea-game", "getAdInfo result code = " + result.code);
        if(result.code != 50100) {
            callBack.error(result.code);
            return;
        }

        List<Adinfo> infos = mapList(result);
        callBack.callback(infos);
    }

    private <E> List<E> mapList(HttpListResult<E> httpResult) {
        if (httpResult != null) {
            if (httpResult.data != null)
                return httpResult.data;
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        return WC2018GameController.getController().getServer()+"/v2/lottery/verify/";
    }

    @Override
    public int getTimeOut() {
        return 10;
    }

    @Override
    public Class getServiceClazz() {
        return AdinfoHttpService.class;
    }

    @Override
    public Map<String, String> getHeaders(Context context) {
        return IEHeaders.getHeader(context);
    }

}
