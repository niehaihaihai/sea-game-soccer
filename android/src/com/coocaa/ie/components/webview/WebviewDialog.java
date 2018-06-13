package com.coocaa.ie.components.webview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.coocaa.ie.R;

public class WebviewDialog extends Dialog {
    public WebviewDialog(@NonNull Context context, String url) {
        super(context, R.style.wc2018_dialog_notitlebar_fullscreen);
        WebView webView = new WebView(context);
        setContentView(webView);
        webView.loadUrl(url);
    }
}
