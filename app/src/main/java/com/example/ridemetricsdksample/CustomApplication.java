package com.example.ridemetricsdksample;

import android.app.Application;
import android.content.Intent;

import com.ridemetric.sdk.RideMetricSDKconfig;

public class CustomApplication extends Application {
    //rideMetricSdkKey should be retrieved from ridemetric licensing site

    //license enabling start stop of the trip and disabling auto detection
    static final String rideMetricSdkKey = "YOUR-RIDEMETRIC-SDK-KEY";
    static boolean USE_SERVICE = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (USE_SERVICE) {
            startService(new Intent(this, SampleService.class));
        } else {
            RideMetricSDKconfig.initApp(this, rideMetricSdkKey);
        }
    }
}