package com.coocaa.ie.http.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.coocaa.csr.connecter.CCConnecterManager;
import com.tianci.webservice.framework.CommonHeader;

import java.util.Map;

/**
 * Created by Sea on 2018/5/22.
 */

public class IEHeaders {

    public static Map<String, String> getHeader(Context context)
    {

        Map<String, String> paramsMap = CommonHeader.getInstance().getCommonHeaderMap(context, context.getPackageName());
        try {
            String accessToken = CCConnecterManager.getManager().getCCConnecter().getTokenId();
            paramsMap.put("accessToken", accessToken);
            Log.e("Sea-game", "get accessToken success : "+accessToken);
            if(TextUtils.isEmpty(paramsMap.get("cOpenId"))) {
                String open_id = CCConnecterManager.getManager().getCCConnecter().getOpenID();
                paramsMap.put("cOpenId", open_id);
            }
        } catch (Exception e) {
            Log.e("Sea-game", e.toString());
            e.printStackTrace();
        }
        return paramsMap;
    }

}
