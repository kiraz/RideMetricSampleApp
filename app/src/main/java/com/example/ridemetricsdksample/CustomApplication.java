package com.example.ridemetricsdksample;

import android.app.Application;
import android.content.Intent;

public class CustomApplication extends Application {
    //rideMetricSdkKey should be retrieved from ridemetric licensing site

    //license enabling start stop of the trip and disabling auto detection
    static final String rideMetricSdkKey = "YOUR-RIDEMETRIC-SDK-KEY";

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, SampleService.class));
    }
}