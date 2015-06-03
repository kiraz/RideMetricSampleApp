package com.example.ridemetricsdksample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ridemetric.sdk.RideMetricSdk;
import com.ridemetric.sdk.RideMetricSdk.RegisterCallback;

public class MainActivity extends ActionBarActivity {

	String applicationSerialNumber;
	
	//rideMetricSdkKey retrieved from ridemetric licensing site
	static final String rideMetricSdkKey = "ShouldBeRetrievedFromLicensingSite";
	
	RideMetricSdk.DriveListener rideMetricListener;
	RideMetricSdk.RegisterCallback rideMetricRegisterCallback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		/*****************************************************************
		 * Start RideMetric specific code
		 *****************************************************************/
		rideMetricListener = new RideMetricSdk.DriveListener() {
		    @Override
		    public void onDriveStart() {
		    	// TODO set text "driving"
		    }
		    @Override
		    public void onDriveEnd() { 
		    	// TODO set text "not driving"
		    }
		};
		
		
		rideMetricRegisterCallback = new RegisterCallback() {

			// TODO hide registering progress bar
			
			@Override
			public void onRegisterComplete(String applicationNumber) {
				// TODO Auto-generated method stub
				applicationSerialNumber = applicationNumber;
			}
		};
		
		// TODO show registering progress bar
		
		if (!RideMetricSdk.registerUser(
				this, 
				rideMetricSdkKey, 
				rideMetricListener, // can be null
				rideMetricRegisterCallback // can not be null
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
		getMenuInflater().inflate(R.menu.main, menu);
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
