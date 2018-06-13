package com.coocaa.ie.games.wc2018.net;

import android.content.Context;

import com.coocaa.ie.games.wc2018.net.impl.NetManagerImpl;

/**
 * Created by Eric on 2018/6/4.
 */

public interface INet {
    static NetManagerImpl nManager = new NetManagerImpl();

    /**
     * 判断网络状态，只需返回状态，不做其他操作
     *
     * @return
     */
    public Boolean isNetConnet(Context mContext);

    /**
     * 返回网络状态，网络未连接时，跳转网络设置或者弹出toast提示
     *
     * @return
     */
    public Boolean checkNetworkConnection(Context mContext);

}
