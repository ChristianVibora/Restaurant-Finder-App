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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BranchAdapter extends ArrayAdapter<Object> {
	int[] idArray;
	String[] timesvisitedArray;
	String[] branchaddressArray;
	private final Context context;
	String travelmode;

	public BranchAdapter(Context context, int[] ids, String[] timesvisited, String[] branchaddresses, String travelmode) {
		super(context, R.layout.branches_list_layout, branchaddresses);
		this.idArray = ids;
		this.timesvisitedArray = timesvisited;
		this.branchaddressArray = branchaddresses;
		this.context = context;
		this.travelmode = travelmode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.branches_list_layout, parent, false);

		Button go_branch = (Button) row.findViewById(R.id.gobranchButton);
		TextView timesvisited = (TextView) row.findViewById(R.id.timesvisitedTextView);
		TextView branchaddress = (TextView) row.findViewById(R.id.branchaddressTextView);

		timesvisited.setText(timesvisitedArray[position]);
		branchaddress.setTag(idArray[position]);
		branchaddress.setText(branchaddressArray[position]);

		go_branch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				RelativeLayout rl = (RelativeLayout) view.getParent();
				TextView textview = (TextView) rl.findViewById(R.id.branchaddressTextView);
				final int branch_id = Integer.parseInt(textview.getTag().toString());
				final String searchaddress = textview.getText().toString();
				final DataBaseHelper dbhelper = new DataBaseHelper(view.getContext());
				final SharedPreferenceManager settings = new SharedPreferenceManager(context);
				double latitude = 0;
				double longitude = 0;
				final String restaurantname;
				final String in_app_mapviewstatus;

				restaurantname = dbhelper.getRestaurantNamebyBranchID(branch_id);
				latitude = Double.parseDouble(settings.getLocation("Latitude"));
				longitude = Double.parseDouble(settings.getLocation("Longitude"));
				in_app_mapviewstatus = settings.getInAppMapViewStatus();

				if (latitude != 0.0 && longitude != 0.0) {
					String origin = "&origin=" + latitude + "," + longitude;
					String destination = "";

					try {
						destination = "&destination=" + URLEncoder.encode(restaurantname + " - " + searchaddress, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					String urladdress = "https://www.google.com/maps/dir/?api=1" + origin + destination + travelmode;

					dbhelper.insertLog(branch_id);

					if (in_app_mapviewstatus.equalsIgnoreCase("OFF") == true) {
						view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urladdress)));
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
								destination = "&destination=" + URLEncoder.encode(restaurantname + " - " + searchaddress, "UTF-8");
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
