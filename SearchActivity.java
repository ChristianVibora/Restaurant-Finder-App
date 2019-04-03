package com.thesis.restaurantfinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchActivity extends Activity {
	DataBaseHelper dbhelper;
	int[] iconArray;
	String[] restaurantnameArray;
	String[] timesvisitedArray;
	int[] branch_idArray;
	String[] branch_addressArray;
	int i;
	int size;
	String icon_name;
	ListView listview;
	String search_key;
	EditText search;
	String selectedtravelmode = "";
	Spinner travelmode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	

		dbhelper = new DataBaseHelper(this);
		search = (EditText) findViewById(R.id.search_keyEditText);
		
		travelmode = (Spinner) findViewById(R.id.travelmode_Spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.travelmodes_array, R.layout.travelmode_item_layout);
		adapter.setDropDownViewResource(R.layout.travelmode_item_layout);
		travelmode.setAdapter(adapter);

		displaySearchResults(search_key);
		
		travelmode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				undisplaySearchResults();
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
				displaySearchResults(search_key);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				i = 0;
				size = 0;
				search_key = "";
				search_key = search.getText().toString();

				if (search_key.length() > 0) {

					int end = search_key.length() - 1;

					if (search.getText().charAt(end) == '\'') {
						Toast.makeText(getApplicationContext(), "Invalid character: '", Toast.LENGTH_SHORT).show();
						search.setText(search_key.substring(0, end));
						search.setSelection(end);
					}
					else {
						size = dbhelper.countSearchResults(search_key);
						iconArray = new int[size];
						restaurantnameArray = new String[size];
						timesvisitedArray = new String[size];
						branch_idArray = new int[size];
						branch_addressArray = new String[size];
						displaySearchResults(search_key);
					}

				}
				else {
					size = dbhelper.countSearchResults(search_key);
					iconArray = new int[size];
					restaurantnameArray = new String[size];
					timesvisitedArray = new String[size];
					branch_idArray = new int[size];
					branch_addressArray = new String[size];
					displaySearchResults(search_key);
				}
			}
		});
	}

	public void displaySearchResults(String search_key) {
		Cursor cursor = dbhelper.getSearchResults(search_key);
		listview = (ListView) findViewById(R.id.search_resultListView);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				icon_name = cursor.getString(0);
				restaurantnameArray[i] = cursor.getString(1);
				branch_idArray[i] = cursor.getInt(2);
				branch_addressArray[i] = cursor.getString(3);
				iconArray[i] = getResources().getIdentifier(icon_name, "drawable", getPackageName());
				timesvisitedArray[i] = "Times Visited: " + dbhelper.countBranchVisit(branch_idArray[i]);
				i++;
			}

			SearchAdapter adapter = new SearchAdapter(this, iconArray, restaurantnameArray, timesvisitedArray, branch_idArray, branch_addressArray, selectedtravelmode);
			listview.setAdapter(adapter);
		}
		else {
			listview.setAdapter(null);
		}
	}

	public void undisplaySearchResults() {
		i=0;
		listview.setAdapter(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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

}
