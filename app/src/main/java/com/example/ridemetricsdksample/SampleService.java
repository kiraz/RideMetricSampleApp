package com.example.ridemetricsdksample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ridemetric.sdk.DriverRegistrationInfo;
import com.ridemetric.sdk.RideMetric;
import com.ridemetric.sdk.RidemetricError;
import com.ridemetric.view.events.ScoringInfo;

import java.util.Timer;
import java.util.TimerTask;

public class SampleService extends Service implements RideMetric.ConnectionListener, RideMetric.TripListener {
    private static final String TAG = SampleService.class.getSimpleName();

    public SampleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            RideMetric.connect(this, this, this);
        } catch (RidemetricError e) {
            Log.e(TAG, "Connect error", e);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    private class LocalBinder extends Binder {
        public SampleService getService() {
            return SampleService.this;
        }
    }

    @Override
    public void onConnected(DriverRegistrationInfo driverRegistrationInfo) {
        try {
            if (RideMetric.isOngoingTrip()) return;

            RideMetric.startTrip();
            final Timer timer = new Timer("SampleServiceTimer");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        RideMetric.stopTrip();
                        timer.purge();
                    } catch (RidemetricError e) {
                        Log.e(TAG, "Error stop trip", e);
                    }
                }
            }, 15000L);
        } catch (RidemetricError e) {
            Log.e(TAG, "Error start trip", e);
        }
    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onError(RidemetricError error) {
        Log.d(TAG, "onError", error);
    }

    @Override
    public void onTripStarted() {
        Log.d(TAG, "onTripStarted");
    }

    @Override
    public void onTripEnded() {
        Log.d(TAG, "onTripEnd");
    }

    @Override
    public void onTripScore(ScoringInfo info) {
        Log.d(TAG, "onTripScore totalScore: " + info.totalScore);
    }
}
