package com.ashik619.meditrack;

import android.app.Application;

import com.ashik619.meditrack.helper.PrefHandler;

/**
 * Created by ashik619 on 08-06-2017.
 */
public class MeditrackApp extends Application {
    public static PrefHandler localStorageHandler;
    public static PrefHandler getLocalPrefInstance() {
        return localStorageHandler;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        if (localStorageHandler == null) {
            localStorageHandler = new PrefHandler(getApplicationContext());
        }

    }
}
