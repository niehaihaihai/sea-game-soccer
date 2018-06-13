package com.coocaa.ie.http.wc2018.univers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.coocaa.ie.BuildConfig;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.http.base.HttpCallBack;
import com.coocaa.ie.http.base.HttpMethod;
import com.coocaa.ie.http.base.HttpResult;
import com.coocaa.ie.http.base.IEHeaders;

import java.util.Map;

import static com.skyworth.framework.skysdk.ipc.SkyContext.context;

/**
 * Created by Sea on 2018/5/22.
 */

public class GameInitHttpMethod extends HttpMethod<GameInitHttpService> {

    public GameInitHttpMethod(Context context) {
        super(context);
    }

    public void getGameInitInfo(@NonNull HttpCallBack<GameInitData> callBack) {
        HttpResult<GameInitData> result = null;
        try {
            result = getService().getGameInitInfo();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Sea-game", "HttpError : " + e.toString());
        }

        if(result == null){
            Log.e("Sea-game", "getGameInitInfo failed null result-----");
            callBack.error(111101);
            return;
        }
        Log.i("Sea-game", "getGameInitInfo result code = " + result.code);
        if(result.code != 50100) {
            callBack.error(result.code);
            return;
        }

        GameInitData initData = result.data;
        callBack.callback(initData);
    }

    @Override
    public String getBaseUrl() {
        return WC2018GameController.getController().getServer()+"/v2/lottery/"+WC2018GameController.getController().getGameName()+
                "/"+ WC2018GameController.getController().getGameId()+"/";
    }

    @Override
    public int getTimeOut() {
        return 30;
    }

    @Override
    public Class<GameInitHttpService> getServiceClazz() {
        return GameInitHttpService.class;
    }

    @Override
    public Map<String, String> getHeaders(Context context) {
        return IEHeaders.getHeader(context);
    }
}
