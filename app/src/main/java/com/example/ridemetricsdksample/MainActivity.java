package com.example.ridemetricsdksample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ridemetric.sdk.DriverRegistrationInfo;
import com.ridemetric.sdk.RideMetric;
import com.ridemetric.sdk.RideMetricSignupError;
import com.ridemetric.sdk.RidemetricError;
import com.ridemetric.view.events.ScoringInfo;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RideMetricSampleApp";

    private RideMetric.ConnectionListener connectionListener;
    private RideMetric.TripListener tripListener;
    private StringFile stringFile;
    private TextView driverIdView;
    private TextView pinIdView;
    private EditText maxSpeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_app);

        stringFile = new StringFile("test_sample_app.txt");
        try {
            stringFile.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*****************************************************************
         * Start RideMetric specific code
         *****************************************************************/

        tripListener = new RideMetric.TripListener() {
            @Override
            public void onTripStarted() {
                stringFile.write("TRIP STARTED \n");
                Log.d(TAG, "TRIP STARTED");
                updateUI();
            }

            @Override
            public void onTripEnded() {
                stringFile.write("TRIP ENDED \n");
                Log.d(TAG, "TRIP ENDED");
                updateUI();
            }

            @Override
            public void onTripScore(ScoringInfo scoringInfo) {
                updateScores(scoringInfo);
            }
        };


        connectionListener = new RideMetric.ConnectionListener() {
            @Override
            public void onConnected(DriverRegistrationInfo driverRegistrationInfo) {
                setRegistered(true);
                updateIdTexts(driverRegistrationInfo);
                updateUI();

                stringFile.write("onConnected driverId = " + driverRegistrationInfo.getDriverId()
                        + " pinId = " + driverRegistrationInfo.getPinId());
            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onError(RidemetricError error) {
                if (error instanceof RideMetricSignupError) {
                    driverIdView.setText("Response from web server was not successful");
                    findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                } else {
                    driverIdView.setText(String.format("Error: %s", error.getLocalizedMessage()));
                }
            }
        };


        /************** end RideMetric specific code ***********/

        driverIdView = (TextView) findViewById(R.id.text_driver_id);
        pinIdView = (TextView) findViewById(R.id.text_pin_id);
        maxSpeedView = (EditText) findViewById(R.id.edit_max_speed);

        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.button_unregister).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_stop).setOnClickListener(this);

        maxSpeedView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        Integer value = Integer.valueOf(maxSpeedView.getText().toString());
                        RideMetric.setMaxPermittedSpeed(MainActivity.this, value);
                    } catch (NumberFormatException t) {
                        Log.e(TAG, "Number format error", t);
                    }
                }
                return false;
            }
        });

        updateUI();

        if (isRegistered()) {
            try {
                RideMetric.connect(this, tripListener, connectionListener);
            } catch (RidemetricError ridemetricError) {
                Toast.makeText(this, ridemetricError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stringFile.close();
    }

    public void onClick(View view) {
        int id = view.getId();
        try {
            if (id == R.id.button_register) {
                if (!isRegistered()) {
                    RideMetric.connect(
                            this,
                            tripListener,
                            connectionListener
                    );
                    findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                }
            } else if (id == R.id.button_start) {
                RideMetric.startTrip();
                clearScores();
            } else if (id == R.id.button_stop) {
                RideMetric.stopTrip();
            } else if (id == R.id.button_unregister) {
                if (RideMetric.isOngoingTrip()) {
                    Toast.makeText(MainActivity.this, "Stop trip before stopService, please", Toast.LENGTH_SHORT).show();
                    return;
                }
                RideMetric.stopService(this);
                setRegistered(false);
                driverIdView.setText("Driver ID");
                pinIdView.setText("Pin ID");
            }
        } catch (RidemetricError ridemetricError) {
            Toast.makeText(this, ridemetricError.getMessage(), Toast.LENGTH_SHORT).show();
        }
        updateUI();
    }

    private void updateUI() {
        if (isRegistered()) {
            findViewById(R.id.button_register).setEnabled(false);
            findViewById(R.id.button_unregister).setEnabled(true);
            findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
            findViewById(R.id.view_buttons2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.button_register).setEnabled(true);
            findViewById(R.id.button_unregister).setEnabled(false);
            findViewById(R.id.view_buttons2).setVisibility(View.INVISIBLE);
        }
        findViewById(R.id.button_start).setEnabled(!RideMetric.isOngoingTrip());
        findViewById(R.id.button_stop).setEnabled(RideMetric.isOngoingTrip());
    }

    private void updateIdTexts(DriverRegistrationInfo driverRegistrationInfo) {
        driverIdView.setText(String.format("Driver ID: %s", driverRegistrationInfo.getDriverId()));
        pinIdView.setText(String.format("Pin ID: %s", driverRegistrationInfo.getPinId()));
    }

    private void updateScores(ScoringInfo scoringInfo) {
        setTextViewContent(R.id.text_score_driving,
                String.format(Locale.getDefault(), "SMOOTH DRIVING: %d", scoringInfo.accelScore));
        setTextViewContent(R.id.text_score_fuel,
                String.format(Locale.getDefault(), "FUEL: %d", scoringInfo.fuelScore));
        setTextViewContent(R.id.text_score_turning,
                String.format(Locale.getDefault(), "TURNING: %d", scoringInfo.corneringScore));
        setTextViewContent(R.id.text_score_speeding,
                String.format(Locale.getDefault(), "SPEED: %d", scoringInfo.speedingScore));
        setTextViewContent(R.id.text_score_total,
                String.format(Locale.getDefault(), "TOTAL: %d", scoringInfo.totalScore));
    }

    private void clearScores() {
        setTextViewContent(R.id.text_score_driving, "SMOOTH DRIVING");
        setTextViewContent(R.id.text_score_fuel, "FUEL");
        setTextViewContent(R.id.text_score_turning, "TURNING");
        setTextViewContent(R.id.text_score_speeding, "SPEED");
        setTextViewContent(R.id.text_score_total, "TOTAL");
    }

    private void setTextViewContent(int id, String text) {
        TextView tv = (TextView) findViewById(id);
        if (tv != null) tv.setText(text);
    }

    private boolean isRegistered() {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        return pref.getBoolean("registered", false);
    }

    private void setRegistered(boolean registered) {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        pref.edit().putBoolean("registered", registered).apply();
    }
}
