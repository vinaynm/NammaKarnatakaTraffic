package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 14/12/16.
 */

import android.app.Application;

public class ListenConnection extends Application {

    private static ListenConnection mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ListenConnection getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}