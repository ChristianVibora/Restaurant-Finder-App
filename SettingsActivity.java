package com.thesis.restaurantfinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setValues();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	public void setValues() {
		RadioButton auto_radiobutton = (RadioButton) findViewById(R.id.autoRadio);
		RadioButton network_radiobutton = (RadioButton) findViewById(R.id.networkRadio);
		RadioButton gps_radiobutton = (RadioButton) findViewById(R.id.gpsRadio);
		ToggleButton in_app_mapview = (ToggleButton) findViewById(R.id.in_app_mapviewToggleButton);
		EditText barangay = (EditText) findViewById(R.id.barangayEditText);
		EditText city = (EditText) findViewById(R.id.cityEditText);
		EditText province = (EditText) findViewById(R.id.provinceEditText);
		
		SharedPreferenceManager defaultsettings = new SharedPreferenceManager(this);
		
		String default_locationprovider = defaultsettings.getDefaultLocationProvider();
		String in_app_mapview_status = defaultsettings.getInAppMapViewStatus();
		String default_barangay = defaultsettings.getDefaultLocation("Default_Barangay");
		String default_city = defaultsettings.getDefaultLocation("Default_City");
		String default_province = defaultsettings.getDefaultLocation("Default_Province");
		
		if (default_locationprovider.equals("Auto") == true) {
			auto_radiobutton.setChecked(true);
		}
		else if (default_locationprovider.equals("Network") == true) {
			network_radiobutton.setChecked(true);
		}
		else if (default_locationprovider.equals("GPS") == true) {
			gps_radiobutton.setChecked(true);
		}
		
		if (in_app_mapview_status.equalsIgnoreCase("ON") == true) {
			in_app_mapview.setChecked(true);
		}
		else {
			in_app_mapview.setChecked(false);	
		}
		
		barangay.setText(default_barangay);
		city.setText(default_city);
		province.setText(default_province);
		
		barangay.setSelection(barangay.getText().length());
	}

	public void saveValues(View view) {
		RadioButton auto_radiobutton = (RadioButton) findViewById(R.id.autoRadio);
		RadioButton network_radiobutton = (RadioButton) findViewById(R.id.networkRadio);
		RadioButton gps_radiobutton = (RadioButton) findViewById(R.id.gpsRadio);
		ToggleButton in_app_mapview = (ToggleButton) findViewById(R.id.in_app_mapviewToggleButton);
		EditText barangay = (EditText) findViewById(R.id.barangayEditText);
		EditText city = (EditText) findViewById(R.id.cityEditText);
		EditText province = (EditText) findViewById(R.id.provinceEditText);
		
		String locationprovider = "";
		String in_app_mapview_status;
		
		if (auto_radiobutton.isChecked() == true) {
			locationprovider = "Auto";
		}
		else if (network_radiobutton.isChecked() == true) {
			locationprovider = "Network";
		}
		else if (gps_radiobutton.isChecked() == true) {
			locationprovider = "GPS";
		}
		
		in_app_mapview_status = in_app_mapview.getText().toString();
		
		String defaultbarangay = barangay.getText().toString();
		String defaultcity = city.getText().toString(); 
		String defaultprovince = province.getText().toString();
		
		SharedPreferenceManager defaultsettings = new SharedPreferenceManager(this);
		
		defaultsettings.saveDefaultLocationProvider(locationprovider);
		defaultsettings.saveInAppMapViewStatus(in_app_mapview_status);
		
		if (defaultbarangay.length() == 0 || defaultcity.length() == 0 || defaultprovince.length() == 0) {
			Toast.makeText(this, "Complete all fields.", Toast.LENGTH_SHORT).show();
		}
		else {
			defaultsettings.saveDefaultLocation("Default_Barangay", defaultbarangay);
			defaultsettings.saveDefaultLocation("Default_City", defaultcity);
			defaultsettings.saveDefaultLocation("Default_Province", defaultprovince);
			
			Toast.makeText(this, "Settings saved successfully.", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

}
