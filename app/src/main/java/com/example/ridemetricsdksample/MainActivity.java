package com.example.ridemetricsdksample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ridemetric.sdk.CustomIf;

public class MainActivity extends ActionBarActivity {

    String applicationSerialNumber;

    CustomIf.RegisterListener registerListener;
    private CustomIf.TripListener tripListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_app);


        /*****************************************************************
         * Start RideMetric specific code
         *****************************************************************/

        tripListener = new CustomIf.TripListener() {
            @Override
            public void onTripStarted() {
                // TODO set text "driving"
            }

            @Override
            public void onTripEnded() {
                // TODO set text "not driving"
            }
        };


        registerListener = new CustomIf.RegisterListener() {

            // TODO hide registering progress bar

            @Override
            public void onRegisterComplete(String applicationNumber) {
                // TODO Auto-generated method stub
                applicationSerialNumber = applicationNumber;
            }
        };

        // TODO show registering progress bar

        if (!CustomIf.registerUser(
                this,
                tripListener, // can be null
                registerListener // can not be null
        )
                ) {
            return;
        }

        /************** end RideMetric specific code ***********/

        /************* do app specific operations ****************/

        //.........
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample_app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
