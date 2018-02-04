/**
 * Copyright (c) 2015-2018 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.tomtom.core.maps.BuildConfig;

import timber.log.Timber;

public class SampleApp extends MultiDexApplication {

    //tag::doc_log[]
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        //end::doc_log[]
        //initStrictMode();
        CrashSupporter.create(this);
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
        } catch (Exception e) {
            String vmName = System.getProperty("java.vm.name");
            if (!vmName.startsWith("Java")) {
                throw e;
            }
        }
    }

}
