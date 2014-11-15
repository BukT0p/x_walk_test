package com.dataart.vyakunin.xwalk_test.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkWebChromeClient;

public class WebViewCompat implements IWebView {
    private final static String TAG = WebViewCompat.class.getSimpleName();

    private final IWebView impl;

    public WebViewCompat(final Context context, boolean startNative) {
        if (startNative) {
            this.impl = new WebViewNative(context);
        } else {
            this.impl = new WebViewXWalk(context);
        }
    }

    @Override
    public View getView() {
        return impl.getView();
    }

    @Override
    public void configure() {
        impl.configure();
    }

    @Override
    public void loadUrl(String url) {
        impl.loadUrl(url);
    }

    @Override
    public void onResume() {
        impl.onResume();
    }

    @Override
    public void onPause() {
        impl.onPause();
    }

    @Override
    public void onDestroy() {
        impl.onDestroy();
    }

    @Override
    public boolean isNative() {
        return impl.isNative();
    }

    @Override
    public String getUserAgent() {
        return impl.getUserAgent();
    }

    private static class WebViewNative implements IWebView {
        private final WebView webView;

        public WebViewNative(final Context context) {
            this.webView = new WebView(context);
        }

        @Override
        public View getView() {
            return webView;
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void configure() {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Log.d(TAG, consoleMessage.sourceId() + "(" + consoleMessage.lineNumber() + "): " + consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);
                }
            });
            //WebView.setWebContentsDebuggingEnable(true);
            webView.getSettings().setUserAgentString("YOUR OWN CUSTOM ID");
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setDatabaseEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setSaveEnabled(true);


        }

        @Override
        public void loadUrl(String url) {
            webView.loadUrl(url);
        }

        @Override
        public void onResume() {
            webView.onResume();
        }

        @Override
        public void onPause() {
            webView.onPause();
        }

        @Override
        public void onDestroy() {
            webView.destroy();
        }

        @Override
        public boolean isNative() {
            return true;
        }

        @Override
        public String getUserAgent() {
            return webView.getSettings().getUserAgentString();
        }

    }

    private static class WebViewXWalk implements IWebView {
        private final XWalkView xwalkView;

        public WebViewXWalk(final Context context) {
            this.xwalkView = new XWalkView(context, (Activity) null);
        }

        @Override
        public View getView() {
            return xwalkView;
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void configure() {
            xwalkView.setXWalkWebChromeClient(new XWalkWebChromeClient(xwalkView) {
                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Log.d(TAG, consoleMessage.sourceId() + "(" + consoleMessage.lineNumber() + "): " + consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);
                }
            });
//            xwalkView.getSettings().setUserAgentString("App");
            xwalkView.getSettings().setAllowContentAccess(true);
            xwalkView.getSettings().setAllowFileAccess(true);
            xwalkView.getSettings().setDatabaseEnabled(true);
            xwalkView.getSettings().setAppCacheEnabled(true);
            xwalkView.getSettings().setDomStorageEnabled(true);
            xwalkView.getSettings().setJavaScriptEnabled(true);
            xwalkView.setSaveEnabled(true);
        }

        @Override
        public void loadUrl(String url) {
            xwalkView.load(url, null);
        }

        @Override
        public void onResume() {
            xwalkView.resumeTimers();
            xwalkView.onShow();
        }

        @Override
        public void onPause() {
            xwalkView.pauseTimers();
            xwalkView.onHide();
        }

        @Override
        public void onDestroy() {
            xwalkView.onDestroy();
        }

        @Override
        public boolean isNative() {
            return false;
        }

        @Override
        public String getUserAgent() {
            return xwalkView.getSettings().getUserAgentString();
        }

    }
}
