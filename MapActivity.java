package com.thesis.restaurantfinder;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@TargetApi(19) public class MapActivity extends Activity {
	String urladdress;
	ProgressDialog progressdialog;
	WebView mapview;
	boolean finishloading = false;
	@SuppressLint("SetJavaScriptEnabled") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		progressdialog = ProgressDialog.show(this, "Loading Map", "Please wait...", true);
		progressdialog.setCancelable(true);
		
		Intent intent = getIntent();
		urladdress = intent.getStringExtra("URL");
		
		mapview = (WebView) findViewById(R.id.mapWebView);
		mapview.getSettings().setJavaScriptEnabled(true);
		mapview.getSettings().setLoadWithOverviewMode(true);
		mapview.getSettings().setBuiltInZoomControls(true);
		mapview.getSettings().setUseWideViewPort(true);
		mapview.setWebViewClient(new WebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView wv, String url) {
				progressdialog.show();
				wv.loadUrl(url);
				
				return true;
			}
			
			@Override
			public void onPageFinished(WebView wv, final String url) {
				if (progressdialog.isShowing()) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							progressdialog.dismiss();
						}
					}, 2000);
				}
			}
		});

		mapview.loadUrl(urladdress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.action_settings:
	    		Intent intent = new Intent(this, SettingsActivity.class);
	    		startActivity(intent);
	    		return true;
	    	case R.id.open_in_googlemaps:
	    	
			//	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urladdress)));
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
}
