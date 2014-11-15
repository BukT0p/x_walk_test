package com.dataart.vyakunin.xwalk_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.dataart.vyakunin.xwalk_test.ui.CrosswalkBrowserFragment;
import com.dataart.vyakunin.xwalk_test.ui.IWebFragment;
import com.dataart.vyakunin.xwalk_test.ui.NativeBrowserFragment;


public class MainActivity extends FragmentActivity {

    private static final String NATIVE_FRAGMENT_TAG = "NATIVE";
    private static final String XWALK_FRAGMENT_TAG = "XWALK";

    public static final String URL_TO_LOAD = "http://octane-benchmark.googlecode.com/svn/latest/index.html";// "http://html5test.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CrosswalkBrowserFragment.newInstance(true),XWALK_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reload) {
            ((IWebFragment)getSupportFragmentManager().getFragments().get(0)).onReloadPage();
            return false;
        } else if (item.getItemId() == R.id.action_switch) {
            String tag = getSupportFragmentManager().getFragments().get(0).getTag();
            Fragment newFragment;
            if (NATIVE_FRAGMENT_TAG.equals(tag)){
                newFragment = new CrosswalkBrowserFragment();
                tag = XWALK_FRAGMENT_TAG;
            } else {
                newFragment=new NativeBrowserFragment();
                tag = NATIVE_FRAGMENT_TAG;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,newFragment,tag);
            transaction.commit();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
