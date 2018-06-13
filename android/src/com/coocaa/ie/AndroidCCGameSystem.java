package com.coocaa.ie;

import android.os.SystemProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.coocaa.ie.core.gdx.CCGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2018/id_wc2018_penalty_settlement_title_number_4/26.
 */

public class AndroidCCGameSystem implements CCGame.CCGameSystem {

    @Override
    public String getSystenProperty(String key, String defaultValue) {
        return SystemProperties.get(key, defaultValue);
    }


    private CcosDeviceInfo mDeviceInfo = null;

    @Override
    public synchronized CcosDeviceInfo getDeviceInfo() {
        if (mDeviceInfo == null) {
            String skymid = SystemProperties.get("ro.build.skymid", "xxx");
            String skymodel = SystemProperties.get("ro.build.skymodel", "www");
            String skytype = SystemProperties.get("ro.build.skytype", "eee");
            String brand = SystemProperties.get("ro.product.brand", "ttt");
            mDeviceInfo = new CcosDeviceInfo(skymid, skymodel, skytype, brand);
        }
        return mDeviceInfo;
    }

    @Override
    public List<FileHandle> getFonts() {
        List<FileHandle> fonts = new ArrayList();
        fonts.add(Gdx.files.absolute("/system/fonts/NotoSansHans-Light.ttf"));
        fonts.add(Gdx.files.absolute("/system/fonts/DroidSansFallback.ttf"));
        fonts.add(Gdx.files.absolute("/system/fonts/NotoSansCJK-Regular.ttc"));
        return fonts;
    }
}