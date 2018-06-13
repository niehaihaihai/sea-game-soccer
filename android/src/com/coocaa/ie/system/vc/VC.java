package com.coocaa.ie.system.vc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.badlogic.gdx.Gdx;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.system.vc.VoiceMonitor;
import com.coocaa.ie.core.system.vc.VoiceObject;
import com.coocaa.ie.core.utils.thread.CCThread;
import com.tianci.media.api.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VC {
    private static VC vc = null;

    public static synchronized VC getVC() {
        if (vc == null)
            vc = new VC();
        return vc;
    }

    private final String VOICE_RECEIVER_ACTION = "com.skyworth.srtnj.action.voice.outcmd";
    private Context mContext;
    private VoiceReceiver mReceiver;
    private final String TAG = "YYMT";
    private List<VoiceMonitor> monitors = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor(CCThread.threadFactory);

    private VC() {
        this.mContext = GameApplicatoin.getContext();
        registerVCBroadcast();
    }

    private void registerVCBroadcast() {
        Log.d(TAG, "registerVCBroadcast--");
        if (mReceiver == null)
            mReceiver = new VoiceReceiver();
        IntentFilter voiceFilter = new IntentFilter();
        voiceFilter.addAction(VOICE_RECEIVER_ACTION);
        mContext.registerReceiver(mReceiver, voiceFilter);
    }

    public void unRegisterVCBroadcast() {
        Log.d(TAG, "unregisterVCBroadcast--");
        try {
            if (mReceiver != null) {
                mContext.unregisterReceiver(mReceiver);
                mReceiver = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerVCMonitor(VoiceMonitor monitor) {
        synchronized (monitors) {
            monitors.add(monitor);
        }
    }

    public void unRegisterVCMonitor(VoiceMonitor monitor) {
        synchronized (monitors) {
            monitors.remove(monitor);
        }
    }

    class VoiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (VOICE_RECEIVER_ACTION.equals(action)) {
                final String value = intent.getStringExtra("voicecmd");
                Gdx.app.log("VC", "value:" + value);
                if (!TextUtils.isEmpty(value)) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            VoiceObject obj = VoiceObject.parse(value);
                            Log.d(TAG, "obj --" + obj.toString() + "  monitors.size:" + monitors.size());
                            synchronized (monitors) {
                                for (int i = 0; i < monitors.size(); i++) {
                                    try {
                                        monitors.get(i).onReceive(obj);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
