package com.example.ridemetricsdksample;

import android.app.Application;
import android.content.Intent;

import com.ridemetric.sdk.RideMetricSDKconfig;

public class CustomApplication extends Application {
    //rideMetricSdkKey should be retrieved from ridemetric licensing site

    //license enabling start stop of the trip and disabling auto detection
    static final private String rideMetricSdkKey = "YOUR-RIDEMETRIC-SDK-KEY";
    static boolean USE_SERVICE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        RideMetricSDKconfig.initApp(this, rideMetricSdkKey);
        if (USE_SERVICE) {
            startService(new Intent(this, SampleService.class));
        }
    }
}