package com.dataart.vyakunin.xwalk_test.webview;

import android.view.View;

public interface IWebView {
    View getView();
    void configure();
    void loadUrl(String url);
    void onResume();
    void onPause();
    void onDestroy();
    boolean isNative();
    String getUserAgent();
}
