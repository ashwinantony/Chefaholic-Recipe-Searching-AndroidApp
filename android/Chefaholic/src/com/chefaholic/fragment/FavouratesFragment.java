package com.chefaholic.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chefaholic.activity.FoodDetailActivity;
import com.chefaholic.adapter.ReceipeAdapter;
import com.chefaholic.model.ReceipeModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;

public class FavouratesFragment extends ListFragment {

	ArrayList<ReceipeModel> list;
	ReceipeAdapter receipeAdapter;

	ImageView ivIcon;
	TextView tvItemName;

	SharedPreferences preferences;
	String name = "";

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		name = preferences.getString(AppConstants.USERNAME, "");
		new GetReceipesByAllUsers().execute(name);
	}

	private class GetReceipesByAllUsers extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(getActivity());
		String url = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading..");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.VIEW_FAVOURATES);
				builder.append("?username=" + params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						getActivity());
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
			dialog.dismiss();
			if (result != null) {

				if (!result.equals("")) {
					Log.d("RESULT", "AM HERE3");
					list = getValues(result);
					receipeAdapter = new ReceipeAdapter(getActivity(), list);
					setListAdapter(receipeAdapter);
				}
			}
			super.onPostExecute(result);
		}
	}

	private ArrayList<ReceipeModel> getValues(String result) {
		// TODO Auto-generated method stub
		ArrayList<ReceipeModel> list = new ArrayList<ReceipeModel>();
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				ReceipeModel model = new ReceipeModel();
				JSONObject object = new JSONObject(array.getString(i));
				model.setName(object.getString("foodName"));
				model.setCategory(object.getString("category"));
				model.setImage(object.getString("pic"));
				model.setUploadedBy(object.getString("name"));
				model.setDate(object.getString("date"));
				model.setId(object.getString("recipieId"));
				if (!object.getString("name").equals(name)) {
					list.add(model);
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String pid = list.get(position).getId();
		String image = list.get(position).getImage();
		Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
		intent.putExtra("id", pid);
		intent.putExtra("image", image);
		Log.d("ERROR", pid);
		startActivity(intent);
	}

}
