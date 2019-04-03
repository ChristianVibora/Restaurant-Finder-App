package com.thesis.restaurantfinder;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
	String filename = "appData";
	Context context;
	
	public SharedPreferenceManager(Context context) {
		this.context = context;
	}
	
	public void saveLocation(String key_name, String value) {
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpref.edit();
		editor.putString(key_name, value);
		editor.commit();
	}
	
	public String getLocation(String key_name) {
		String value;
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		value = sharedpref.getString(key_name, "0.0");
		return value;
	}
	
	public void saveDefaultLocationProvider(String value) {
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpref.edit();
		editor.putString("Default_Location_Provider", value);
		editor.commit();
	}
	
	public String getDefaultLocationProvider() {
		String value;
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		value = sharedpref.getString("Default_Location_Provider", "Auto");
		return value;
	}
	
	public void saveDefaultLocation(String key_name, String value) {
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpref.edit();
		editor.putString(key_name, value);
		editor.commit();
	}
	
	public String getDefaultLocation(String key_name) {
		String value;
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		value = sharedpref.getString(key_name, "");
		return value;
	}
	
	public void saveInAppMapViewStatus(String value) {
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpref.edit();
		editor.putString("In_App_Map_View_Status", value);
		editor.commit();
	}
	
	public String getInAppMapViewStatus() {
		String value;
		SharedPreferences sharedpref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		value = sharedpref.getString("In_App_Map_View_Status", "ON");
		return value;
	}
}
