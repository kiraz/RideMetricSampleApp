package com.example.ridemetricsdksample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ridemetric.sdk.RideMetric;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(String.format(Locale.getDefault(), "%s-SDK v.%s", getString(R.string.sample_app_name), RideMetric.getVersion()));
    }
}
