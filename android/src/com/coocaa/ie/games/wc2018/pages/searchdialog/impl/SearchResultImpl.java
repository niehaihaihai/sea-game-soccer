package com.coocaa.ie.games.wc2018.pages.searchdialog.impl;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.coocaa.ie.CoocaaIEApplication;
import com.coocaa.ie.R;
import com.coocaa.ie.games.wc2018.dataer.CommonDataer;
import com.coocaa.ie.games.wc2018.pages.searchdialog.ISearchResult;
import com.coocaa.ie.games.wc2018.pages.searchdialog.view.SearchDialogView;
import com.skyworth.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2018/5/30.
 */

public class SearchResultImpl implements ISearchResult {
    private Dialog mDialog = null;
    private SearchDialogView searchView = null;
    private boolean isShowing = false;
    private String cur_quesID = "";

    public SearchResultImpl() {
        Util.instence(CoocaaIEApplication.getContext());
    }


    @Override
    public void startSearch(final Context context, final String qId, final String qus, final String ans) {
        Log.d("active_push", "showDialog  isShowing:" + isShowing);
        if (isShowing) {
            return;
        }
        isShowing = true;
        cur_quesID = qId;
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("active_push", "startSearch  inthread");
                if (mDialog == null) {
                    mDialog = new Dialog(context, R.style.search_dialog_style);
                    mDialog.getWindow().setGravity(Gravity.CENTER | Gravity.BOTTOM);

                    WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
                    params.width = Util.Div(1920);
                    params.height = Util.Div(480);
                    mDialog.getWindow().setAttributes(params);
                    mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }

                searchView = new SearchDialogView(context, qus, ans);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.Div(480));
                params.gravity = Gravity.BOTTOM;
                mDialog.setContentView(searchView, params);
                mDialog.show();
                Log.d("active_push", "startSearch 00 qId:" + qId);
                CoocaaIEApplication.runOnUiThread(3000, new DialogRunnable(qId) {

                    @Override
                    public void run() {
                        Log.d("active_push", "startSearch 11 cur_quesID:" + cur_quesID + " quesID:"+quesID);
                        if (!quesID.equals(cur_quesID))
                            return;
                        searchView.finishAnimation();
                        CoocaaIEApplication.runOnUiThread(5000, new DialogRunnable(quesID) {
                            @Override
                            public void run() {
                                Log.d("active_push", "startSearch 22 cur_quesID:" + cur_quesID + " quesID:"+quesID);
                                if (!quesID.equals(cur_quesID))
                                    return;
                                if (mDialog != null && mDialog.isShowing())
                                    mDialog.dismiss();
                                isShowing = false;
                            }
                        });
                    }
                });

                try {
                    Map<String, String> pMap = new HashMap<>();
                    pMap.put("subject_id", qId);
                    CommonDataer.submit(context, "answer_assistant_show_event", pMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void hideSearchDialg() {
        Log.d("active_push", "hideSearchDialg  hide");
        CoocaaIEApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("active_push", "hideSearchDialg  hide dismiss");
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
                isShowing = false;
            }
        });
    }

    class DialogRunnable implements Runnable {

        public String quesID;

        public DialogRunnable(String id) {
            quesID = id;
        }

        @Override
        public void run() {

        }
    }


}
