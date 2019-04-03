package com.thesis.restaurantfinder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<Object>{
	int[] iconArray;
	String[] restaurantnameArray;
	String[] timesvisitedArray;
	int[] branch_idArray;
	String[] branchaddressArray;
	Context context;
	String travelmode;

	public SearchAdapter(Context context, int[] icons, String[] restaurantnames, String[] timesvisited, int[] branch_ids, String[] branch_addresses, String travelmode) {
		super(context, R.layout.restaurant_list_layout, branch_addresses);
		this.iconArray = icons;
		this.restaurantnameArray = restaurantnames;
		this.timesvisitedArray = timesvisited;
		this.branch_idArray = branch_ids;
		this.branchaddressArray = branch_addresses;
		this.context = context;
		this.travelmode = travelmode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.search_layout, parent, false);

		ImageView icon = (ImageView) row.findViewById(R.id.restaurant_iconImageView);
		TextView times_visited = (TextView) row.findViewById(R.id.times_visitedTextView);
		TextView branch_address = (TextView) row.findViewById(R.id.branch_addressTextView);
		Button go_branch = (Button) row.findViewById(R.id.go_branchButton);

		icon.setImageResource(iconArray[position]);
		times_visited.setText(timesvisitedArray[position]);
		branch_address.setTag(branch_idArray[position]);
		branch_address.setText(restaurantnameArray[position] + " - " + branchaddressArray[position]);

		go_branch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				RelativeLayout rl = (RelativeLayout) view.getParent();
				TextView textview = (TextView) rl.findViewById(R.id.branch_addressTextView);
				final int branch_id = Integer.parseInt(textview.getTag().toString());
				final String searchaddress = textview.getText().toString();
				final DataBaseHelper dbhelper = new DataBaseHelper(view.getContext());
				final SharedPreferenceManager settings = new SharedPreferenceManager(context);
				double latitude = 0;
				double longitude = 0;
				final String in_app_mapviewstatus;

				latitude = Double.parseDouble(settings.getLocation("Latitude"));
				longitude = Double.parseDouble(settings.getLocation("Longitude"));
				in_app_mapviewstatus = settings.getInAppMapViewStatus();
				
				if (latitude != 0.0 && longitude != 0.0) {
					String origin = "&origin=" + latitude + "," + longitude;
					String destination = "";

					try {
						destination = "&destination=" + URLEncoder.encode(searchaddress, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					String urladdress = "https://www.google.com/maps/dir/?api=1" + origin + destination + travelmode;

					dbhelper.insertLog(branch_id);

					if (in_app_mapviewstatus.equalsIgnoreCase("OFF") == true) {
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urladdress)));
					}
					else {
						Intent intent = new Intent(context, MapActivity.class);
						intent.putExtra("URL", urladdress);
						context.startActivity(intent);
					}
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Your location is not yet set. Use your default location instead?")
					.setCancelable(false)
					.setTitle("Location Status")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							String origin = "";
							String destination = "";

							String default_barangay = settings.getDefaultLocation("Default_Barangay");
							String default_city = settings.getDefaultLocation("Default_City");
							String default_province = settings.getDefaultLocation("Default_Province");

							origin = default_barangay + ", " + default_city + ", " + default_province;

							try {
								origin = "&origin=" + URLEncoder.encode(origin, "UTF-8");
								destination = "&destination=" + URLEncoder.encode(searchaddress, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}

							String urladdress = "https://www.google.com/maps/dir/?api=1" + origin + destination + travelmode;

							dbhelper.insertLog(branch_id);

							if (in_app_mapviewstatus.equalsIgnoreCase("OFF") == true) {
								context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urladdress)));
							}
							else {
								Intent intent = new Intent(context, MapActivity.class);
								intent.putExtra("URL", urladdress);
								context.startActivity(intent);
							}
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
			}

		});

		return row;
	}
}
