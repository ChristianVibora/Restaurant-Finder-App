package com.thesis.restaurantfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<Object> {
	int[] iconArray;
	int[] idArray;
	String[] restaurantnameArray;
	
	public RestaurantAdapter(Context context, int[] icons, int[] ids, String[] restaurantnames) {
		super(context, R.layout.restaurant_list_layout, restaurantnames);
		this.iconArray = icons;
		this.idArray = ids;
		this.restaurantnameArray = restaurantnames;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.restaurant_list_layout, parent, false);
		
		ImageView icon = (ImageView) row.findViewById(R.id.iconImageView);
		TextView restaurantname = (TextView) row.findViewById(R.id.restaurantnameTextView);
		
		icon.setImageResource(iconArray[position]);
		restaurantname.setTag(idArray[position]);
		restaurantname.setText(restaurantnameArray[position]);
		
		return row;
	}
	
}
