package com.coocaa.ie.games.wc2018.net.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.coocaa.csr.connecter.CCConnecterManager;
import com.coocaa.csr.connecter.utils.CoocaaSystem;
import com.coocaa.ie.core.utils.thread.CCThread;
import com.coocaa.ie.games.wc2018.net.INet;

/**
 * Created by Eric on 2018/6/4.
 */

public class NetManagerImpl implements INet {

    @Override
    public Boolean isNetConnet(Context mContext) {
        return isNetConnected(mContext);
    }

    @Override
    public Boolean checkNetworkConnection(final Context mContext) {
        if (isNetConnected(mContext))
            return true;
        showNetworkDialog();
        return false;
    }

    private boolean showNetworkDialog() {
        CCThread.threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (CoocaaSystem.isCoocaaSystem()) {
                        CCConnecterManager.getManager().getCCConnecter().showConnectNetworkDialogWithConfirmUI();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return false;
    }

//    private NetCheckDialog netDialog;
//
//    private boolean showNetworkDialog(Context mContext) {
//        netDialog = new NetCheckDialog(mContext, new Runnable() {
//            @Override
//            public void run() {
//                netDialog = null;
//            }
//        });
//        netDialog.show();
//        return false;
//    }

    private static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        } else
            return false;
    }

}
