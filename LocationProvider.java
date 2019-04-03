package com.thesis.restaurantfinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import android.provider.Settings;

public class LocationProvider implements LocationListener {
	
	private LocationManager locationManager = null;
	private LocationManager locationManager2 = null;
	double longitude = 0;
	double latitude = 0;
	String cityname;
	private final Context context;
	
	public LocationProvider(Context context) {
		this.context = context;
	}
	
	public void createLocation() {
		
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager2 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		boolean networkstatus = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean gpsstatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		SharedPreferenceManager defaultlocationsettings = new SharedPreferenceManager(context);
		String default_locationprovider = defaultlocationsettings.getDefaultLocationProvider();
		String default_barangay = defaultlocationsettings.getDefaultLocation("Default_Barangay");
		String default_city = defaultlocationsettings.getDefaultLocation("Default_City");
		String default_province = defaultlocationsettings.getDefaultLocation("Default_Province");
		
		if (networkstatus == true && gpsstatus == true) {
			
			Log.d("Location Provider", "Network & GPS");

			if (default_locationprovider.equals("Auto") == true) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 1, 10, this);
				locationManager2.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, this);
			}
			else if (default_locationprovider.equals("Network") == true) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 1, 10, this);
			}
			else if (default_locationprovider.equals("GPS") == true) {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, this);
			}
			else {
				Log.d("Location Provider", "GPS");
			}
			if (default_barangay == "" || default_city == "" || default_province == "") {
				Intent intent = new Intent(context, SettingsActivity.class);
				context.startActivity(intent);
				Toast.makeText(context, "Set default location before before proceeding.", Toast.LENGTH_LONG).show();
			}
			else {
				Intent intent = new Intent(context, HomeActivity.class);
				context.startActivity(intent);
			}
		}
		else if (networkstatus == true) {
			
			if (default_locationprovider.equals("GPS") == true) {
				alertbox("Location Provider Status", "Your GPS Provider is off. Enable GPS Provider to get your location?");
			}
			else {
				Log.d("Location Provider", "Network");
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 1, 10, this);
				if (default_barangay == "" || default_city == "" || default_province == "") {
					Intent intent = new Intent(context, SettingsActivity.class);
					context.startActivity(intent);
					Toast.makeText(context, "Set default location before before proceeding.", Toast.LENGTH_LONG).show();
				}
				else {
					Intent intent = new Intent(context, HomeActivity.class);
					context.startActivity(intent);
				}
			}
		}
		else if (gpsstatus == true) {

			if (default_locationprovider.equals("Network") == true) {
				alertbox("Location Provider Status", "Your Network Provider is off. Enable Network Provider to get your location?");
			}
			else {
				Log.d("Location Provider", "GPS");
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, this);
				if (default_barangay == "" || default_city == "" || default_province == "") {
					Intent intent = new Intent(context, SettingsActivity.class);
					context.startActivity(intent);
					Toast.makeText(context, "Set default location before before proceeding.", Toast.LENGTH_LONG).show();
				}
				else {
					Intent intent = new Intent(context, HomeActivity.class);
					context.startActivity(intent);
				}
			}
		}
		else {
			alertbox("Location Provider Status", "Your Location Provider is off. Enable Location Provider to get your location?");
		}
	}

	protected void alertbox(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(myIntent);
				dialog.cancel();
			}
		})
		.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		cityname = null;

		SharedPreferenceManager locationstorage = new SharedPreferenceManager(context);
		locationstorage.saveLocation("Latitude", latitude + "");
		locationstorage.saveLocation("Longitude", longitude + "");
		
		Geocoder gcd = new Geocoder(context, Locale.getDefault());             
		List<Address>  addresses;  

		try {  
			addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);  
			if (addresses.size() > 0)  {
				cityname = addresses.get(0).getLocality();  
			}
			else {
				cityname = "Not Available";
			}
		} 
		catch (IOException e) {            
			cityname = "Not Available";
		} 

		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
		
		Toast.makeText(context, "Location Updated:\n Latitude: " + latitude + "\n Longitude: " + longitude + "\n City/Municipality: " + cityname, Toast.LENGTH_LONG).show();
		
		NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle("Location Updated")
		.setContentText("Find a restaurant now!");
		
		NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationmanager.notify(0, builder.build());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
