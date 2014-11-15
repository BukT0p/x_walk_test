package com.dataart.vyakunin.xwalk_test.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dataart.vyakunin.xwalk_test.MainActivity;
import com.dataart.vyakunin.xwalk_test.R;
import com.dataart.vyakunin.xwalk_test.webview.IWebView;
import com.dataart.vyakunin.xwalk_test.webview.WebViewCompat;

public class CrosswalkBrowserFragment extends Fragment implements IWebFragment {
    public static final String TAG = CrosswalkBrowserFragment.class.getSimpleName();


    public static Fragment newInstance(boolean startNative) {
        Fragment result = new CrosswalkBrowserFragment();
        return result;
    }

    private IWebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webView = new WebViewCompat(getActivity(),false);
        getActivity().setTitle("Xwalk");
        onReloadPage();
        return webView.getView();
    }

    @Override
    public void onReloadPage() {
        webView.loadUrl(MainActivity.URL_TO_LOAD);
    }
}
