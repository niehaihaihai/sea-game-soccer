package com.coocaa.ie.games.wc2018.dataer;

import android.content.Context;

import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.dataer.api.SkyDataer;
import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.skyworth.framework.skysdk.ccos.CcosProperty;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

public class CommonDataer {

    public static final Map<String, String> commonParams() {
        Map<String, String> params = new HashMap<>();
        params.put("game_name", WC2018GameController.getController().getGameNameInChinese());
        params.put("page_status", WC2018GameController.getController().getGameNameInChinese());
        String openId = "unknow open_id";
        try {
            openId = CCConnecterManager.getManager().getCCConnecter().getOpenID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("OPEN_ID", openId);
        params.put("memory_under_1G", CoocaaIEApplication.isSupportVC() ? "否" : "是");
        return params;
    }

    public static final void submit(Context context, String eventID, Map<String, String> params) {
        try {
            Map<String, String> _params = new HashMap<>();
            if (params != null)
                _params.putAll(params);
            _params.putAll(commonParams());
            SkyDataer.onEvent().baseEvent().withEventID(eventID).withParams(_params).submit();
            _params.put("chip_model", CcosProperty.getCcosDeviceInfo().skymodel + CcosProperty.getCcosDeviceInfo().skytype);
            MobclickAgent.onEvent(context, eventID, _params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Map<String, Long> pageShowLog = new HashMap<>();

    public static final void pageShow(Context context, String name) {
        synchronized (pageShowLog) {
            pageShowLog.put(name, System.currentTimeMillis());
        }
        Map<String, String> params = new HashMap<>();
        params.put("page_name", name);
        submit(context, "game_page_show_event", params);
    }

    public static final void pageHide(Context context, String name) {
        Long st;
        synchronized (pageShowLog) {
            st = pageShowLog.get(name);
            pageShowLog.remove(name);
        }
        long duration = -1;
        if (st != null) {
            duration = st - System.currentTimeMillis();
        }
        Map<String, String> params = new HashMap<>();
        params.put("page_name", name);
        params.put("duration_time", String.valueOf(duration / 1000));
        submit(context, "game_page_hide_event", params);
    }
}
