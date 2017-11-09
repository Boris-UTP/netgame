package com.netgame.netgame;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by arkanay on 8/11/17.
 */

public class NetGameApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
