package com.dataart.vyakunin.xwalk_test.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dataart.vyakunin.xwalk_test.MainActivity;

public class NativeBrowserFragment extends Fragment implements IWebFragment {
    public static final String TAG = NativeBrowserFragment.class.getSimpleName();
    private WebView webView;

    public static Fragment newInstance(boolean startNative) {
        Fragment result = new NativeBrowserFragment();
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webView = new WebView(getActivity());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        getActivity().setTitle("Native");
        onReloadPage();
        return webView;
    }

    @Override
    public void onReloadPage() {
        webView.loadUrl(MainActivity.URL_TO_LOAD);
    }
}
