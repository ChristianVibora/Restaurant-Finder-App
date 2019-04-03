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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity {

	DataBaseHelper dbhelper;
	int[] iconArray;
	int[] idArray;
	String[] restaurantnameArray;
	int i = 0;
	int size = 0;
	String icon_name;
	ListView listview;
	int selected_restaurant_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 
		dbhelper = new DataBaseHelper(this);
		size = dbhelper.countRestaurants();
		iconArray = new int[size];
		idArray = new int[size];
		restaurantnameArray = new String[size];
		displayRestaurants();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
	 
	private void displayRestaurants() {
		Cursor cursor = dbhelper.getAllRestaurants();
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				idArray[i] = cursor.getInt(0);
				icon_name = cursor.getString(1);
				iconArray[i] = getResources().getIdentifier(icon_name, "drawable", getPackageName());
				restaurantnameArray[i] = cursor.getString(2);
				i++;
			}
			listview = (ListView) findViewById(R.id.restaurantListView);
			RestaurantAdapter adapter = new RestaurantAdapter(this, iconArray, idArray, restaurantnameArray);
			listview.setAdapter(adapter);
			
			OnItemClickListener itemClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					RelativeLayout layoutParent = (RelativeLayout) view;
					RelativeLayout layoutChild = (RelativeLayout) layoutParent.getChildAt(1);
					TextView textview = (TextView) layoutChild.getChildAt(0);
					selected_restaurant_id = Integer.parseInt(textview.getTag().toString());
					callBranchesPage();
				}
			};
			listview.setOnItemClickListener(itemClickListener);
		}
	}
	
	public void callBranchesPage() {
		Intent intent = new Intent(this, BranchesActivity.class);
		intent.putExtra("RESTAURANT_ID", selected_restaurant_id);
		startActivity(intent);
	}
	
	public void callSearchPage(View view) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
}
