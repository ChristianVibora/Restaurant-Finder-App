package com.thesis.restaurantfinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class BranchesActivity extends Activity {

	DataBaseHelper dbhelper;
	int[] idArray;
	String[] timesvisitedArray;
	String[] branchaddressArray;
	int i = 0;
	int size = 0;
	ListView listview;
	int restaurant_id;
	int selected_branch_id;
	String restaurant_name;
	String selectedtravelmode = "";
	Spinner travelmode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branches);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 
		dbhelper = new DataBaseHelper(this);
		Intent intent = getIntent();
		restaurant_id = intent.getIntExtra("RESTAURANT_ID", 0);
		restaurant_name = dbhelper.getRestaurantName(restaurant_id);
		setTitle(restaurant_name + " Branches");
		size = dbhelper.countBranches(restaurant_id);
		timesvisitedArray = new String[size];
		idArray = new int[size];
		branchaddressArray = new String[size];
		listview = (ListView) findViewById(R.id.branchListView);
		
		travelmode = (Spinner) findViewById(R.id.travelmodeSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.travelmodes_array, R.layout.travelmode_item_layout);
		adapter.setDropDownViewResource(R.layout.travelmode_item_layout);
		travelmode.setAdapter(adapter);

				travelmode.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						undisplayBranches();
						String spinnerselected = travelmode.getSelectedItem().toString();

						if (spinnerselected.equals("Walking") == true) {
							selectedtravelmode = "&travelmode=walking";
						}
						else if (spinnerselected.equals("Bicycling") == true) {
							selectedtravelmode = "&travelmode=bicycling";
						}
						else if (spinnerselected.equals("Driving") == true) {
							selectedtravelmode = "&travelmode=driving";
						}
						else if (spinnerselected.equals("Transit") == true) {
							selectedtravelmode = "&travelmode=transit";
						}
						displayBranches();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});

		displayBranches();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.branches, menu);
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
	 
	public void displayBranches() {
		Cursor cursor = dbhelper.getAllBranches(restaurant_id);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				idArray[i] = cursor.getInt(0);
				branchaddressArray[i] = cursor.getString(1);
				timesvisitedArray[i] = "Times Visited: " + dbhelper.countBranchVisit(idArray[i]);
				i++;
			}

			BranchAdapter adapter = new BranchAdapter(this, idArray, timesvisitedArray, branchaddressArray, selectedtravelmode);
			listview.setAdapter(adapter);
		}
		else {
			Toast.makeText(this, "No branches found...", Toast.LENGTH_SHORT).show();
		}
	}

	public void undisplayBranches() {
		i=0;
		listview.setAdapter(null);
	}
}
