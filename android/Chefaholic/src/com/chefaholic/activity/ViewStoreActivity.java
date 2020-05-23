package com.chefaholic.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.chefaholic.model.StoreModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.GeoLocationDetailsJson;
import com.chefaholic.utils.URLDownloadUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewStoreActivity extends FragmentActivity {

	GoogleMap map = null;
	String latlong = null;
	String addres = null;
	ArrayList<StoreModel> list;

	public class GetLOcation extends AsyncTask<String, String, String> {
		ProgressDialog dialog = new ProgressDialog(ViewStoreActivity.this);

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

			}

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
		new LoadStoreAsyncTask().execute();
		
	}

	private class LoadStoreAsyncTask extends AsyncTask<String, Void, String> {

		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.VIEW_STORE_SERVLET);

				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						ViewStoreActivity.this);
				Log.d("LIST", "URL " + url);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return url;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if (!result.equals("")) {
					list = loadValues(result);
					addMarker(list);
				}

			}
			super.onPostExecute(result);
		}

		private void addMarker(ArrayList<StoreModel> list) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "size : " + list.size(),
					Toast.LENGTH_SHORT).show();
			for (StoreModel model : list) {
				String lats = model.getLatitude();
				String longitute = model.getLongitude();
				

				double lat = Double.parseDouble(lats);
				double lon = Double.parseDouble(longitute);
				map.addMarker(new MarkerOptions()
						.position(new LatLng(lat, lon)).title(model.getTitle()));
			}

		}

		private ArrayList<StoreModel> loadValues(String result) {
			// TODO Auto-generated method stub
			ArrayList<StoreModel> list = new ArrayList<StoreModel>();

			try {
				JSONArray array = new JSONArray(result);

				for (int i = 0; i < array.length(); i++) {
					JSONObject object = new JSONObject(array.getString(i));
					StoreModel bean = new StoreModel();
					bean.setLatitude(object.getString("latitude"));
					bean.setLongitude(object.getString("longitude"));
					bean.setTitle(object.getString("storeName"));

					list.add(bean);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;

		}

	}
}
