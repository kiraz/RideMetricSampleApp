package com.example.ridemetricsdksample;

import android.app.Application;

import com.ridemetric.sdk.RideMetricSDKconfig;

public class CustomApplication extends Application {
    //rideMetricSdkKey should be retrieved from ridemetric licensing site

    //license enabling start stop of the trip and disabling auto detection
    static final private String rideMetricSdkKey = "YOUR-RIDEMETRIC-SDK-KEY";

    @Override
    public void onCreate() {
        super.onCreate();
        RideMetricSDKconfig.initApp(this, rideMetricSdkKey);
    }
}