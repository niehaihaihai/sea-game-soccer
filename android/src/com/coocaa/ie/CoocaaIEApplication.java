package com.coocaa.ie;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.coocaa.csr.connecter.CCConnecter;
import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.dataer.api.SkyDataer;
import com.coocaa.dataer.api.ccc.CoocaaSystemConnecter;
import com.coocaa.ie.core.android.GameApplicatoin;
import com.coocaa.ie.core.utils.thread.CCThread;
import com.skyworth.framework.skysdk.ccos.CcosProperty;
import com.umeng.analytics.MobclickAgent;

public class CoocaaIEApplication extends GameApplicatoin {
    public static final String TAG = "CoocaaIE";

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e("APP_CRASH", "app crash on thread " + thread.getName(), ex);
            Process.killProcess(Process.myPid());
        }
    };

    private CCConnecterManager.CCCHandler cccHandler = new CCConnecterManager.CCCHandler() {
        @Override
        public byte[] onHandler(String fromtarget, String cmd, byte[] body) {
            return new byte[0];
        }

        @Override
        public void onResult(String fromtarget, String cmd, byte[] body) {

        }
    };

    private static final long VC_MEM_GATE = 768 * 1024 * 1024;
    private static final String[] VC_DISABLE_DEVICE = new String[]{
            "8H20",
            "8H26",
            "8H52",
    };

    private static final int VC_DISABLE_OS_VERSION = Build.VERSION_CODES.KITKAT;

    //是否支持语音
    public static final boolean isSupportVC() {
//        if (BuildConfig.DEBUG)
//            return true;
        String model = CcosProperty.getCcosDeviceInfo().skymodel;
        if (!TextUtils.isEmpty(model)) {
            for (String m : VC_DISABLE_DEVICE) {
                if (m.equals(model)) {
                    Gdx.app.log("VC", "model:" + model + " disable!!");
                    return false;
                }
            }
        }
        if (Build.VERSION.SDK_INT < VC_DISABLE_OS_VERSION) {
            Gdx.app.log("VC", "SDK_INT:" + Build.VERSION.SDK_INT + " < " + VC_DISABLE_OS_VERSION + " disable!!");
            return false;
        }
        if (getMemoryInfo().totalMem >= VC_MEM_GATE)
            return true;
        else {
            Gdx.app.log("VC", "totalMem:" + getMemoryInfo().totalMem + " < " + VC_MEM_GATE + " disable!!");
            return false;
        }
    }

    private static CCConnecter ccConnecter = null;

    public static final CCConnecter ccos() {
        return ccConnecter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        MultiDex.install(this);
        initCcos();
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }

    private void initCcos() {
        CCThread.threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                CCConnecterManager.CCConnecterManagerConfig config = new CCConnecterManager.CCConnecterManagerConfig();
                config.deSession = getPackageName();
                ccConnecter = CCConnecterManager.create(CoocaaIEApplication.this, config).getCCConnecter();
                CCConnecterManager.getManager().addHandler(cccHandler);
                initSkyDataer();
            }
        }).start();
    }

    private void initSkyDataer() {
        SkyDataer.onCore()
                .withContext(getContext())
                .withDebugMode(BuildConfig.DEBUG)
                .withProductID("WorldcupGame")
                .withCoocaaSystemConnecter(coocaaSystemConnecter)
                .create();
    }

    private static final CoocaaSystemConnecter coocaaSystemConnecter = new CoocaaSystemConnecter() {
        @Override
        public String getOpenID() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getOpenID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow OpenID";
        }

        @Override
        public String getSID() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getSID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow SID";
        }

        @Override
        public String getActiveID() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getActiveID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow ActiveID";
        }

        @Override
        public String getBarcode() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getBarcode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow Barcode";
        }

        @Override
        public String getMac() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getMac();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow mac";
        }

        @Override
        public String getVersionName() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getVersionName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow VersionName";
        }

        @Override
        public int getVersionCode() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getVersionCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        public String getDeviceBrand() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getDeviceBrand();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow DeviceBrand";
        }

        @Override
        public String getDeviceModel() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getDeviceModel();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow DeviceModel";
        }

        @Override
        public String getDeviceChip() {
            try {
                return CCConnecterManager.getManager().getCCConnecter().getDeviceChip();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unknow DeviceChip";
        }
    };
}
