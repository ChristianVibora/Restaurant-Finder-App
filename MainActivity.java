package com.thesis.restaurantfinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	DataBaseHelper dbhelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        Button go_button = (Button) findViewById(R.id.gobranchButton);
        dbhelper = new DataBaseHelper(this);
        
        if (dbhelper.checkRestaurantsExist() == false && dbhelper.checkBranchesExist() == false) {
        	go_button.setVisibility(View.GONE);
        	dbhelper.addRestaurants(this);
        	dbhelper.addBranches(this);
        	go_button.setVisibility(View.VISIBLE);
        }
        
        SharedPreferenceManager locationstorage = new SharedPreferenceManager(this);
        locationstorage.saveLocation("Latitude", "0.0");
        locationstorage.saveLocation("Longitude", "0.0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_settings:
    		Intent intent = new Intent(this, SettingsActivity.class);
    		startActivity(intent);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    public void callHomePage(View view) {
     	LocationProvider locationprovider = new LocationProvider(this);
     	locationprovider.createLocation();
    }
}
