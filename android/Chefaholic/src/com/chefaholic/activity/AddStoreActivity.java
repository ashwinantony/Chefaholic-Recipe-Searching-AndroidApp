package com.chefaholic.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.chefaholic.utils.AddStore;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.GeoLocationDetailsJson;
import com.chefaholic.utils.URLDownloadUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddStoreActivity extends FragmentActivity {

	GoogleMap map = null;
	String latlong = null;
	String addres = null;

	String storeName = "";

	public class GetLOcation extends AsyncTask<String, String, String> {
		ProgressDialog dialog = new ProgressDialog(AddStoreActivity.this);

		@Override
		protected String doInBackground(String... params) {
			addres = new GeoLocationDetailsJson().getAddress(params[0]);

			return addres;

		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading....!");
			dialog.show();

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
					.show();
			if (result.contains(",")) {
				String[] split = result.split(",");
				Toast.makeText(getApplicationContext(), split[0],
						Toast.LENGTH_SHORT).show();
				storeName = split[0];
				Log.d("TAG", "STORE NAME " + storeName);
				showDialog(result);
			}

		}

		private void showDialog(final String result) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					AddStoreActivity.this);
			builder.setTitle("Add store ?");
			builder.setIcon(R.drawable.ic_store);
			builder.setMessage(result);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AddNewStoreAsyncTask().execute(latlong,
									storeName);
							Log.d("TAG", "LATLONG : " + latlong + " RESULT : "
									+ storeName);

						}
					});

			builder.setNegativeButton("NO", null);
			builder.create().show();

		}

	}

	private class AddNewStoreAsyncTask extends AsyncTask<String, Void, String> {

		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String res = new AddStore().add(params[0], params[1]);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result.equals("exist")) {
				Toast.makeText(getApplicationContext(), "Already added",
						Toast.LENGTH_LONG).show();
			} else if (result.equals("success")) {
				Toast.makeText(getApplicationContext(),
						"Store Successfully added", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Something went wrong\nTry again after sometimes",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapfragment);
		map = fragment.getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				map.clear();
				map.addMarker(new MarkerOptions().position(arg0));

				Toast.makeText(getApplicationContext(),
						"lat:" + arg0.latitude + " lang:" + arg0.longitude,
						Toast.LENGTH_LONG).show();
				// getLoacationName(arg0.latitude, arg0.longitude);
				latlong = arg0.latitude + "," + arg0.longitude;
				// String address = new
				// GeoLocationDetailsJson().getAddress(latlong);
				// Log.d("TAG", address);
				new GetLOcation().execute(latlong);

			}
		});
		// setMarker();

	}
}
