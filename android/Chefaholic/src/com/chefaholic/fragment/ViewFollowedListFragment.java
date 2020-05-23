package com.chefaholic.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chefaholic.activity.R;
import com.chefaholic.adapter.UnFollowAdapter;
import com.chefaholic.model.FollowersModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;

public class ViewFollowedListFragment extends ListFragment {

	ArrayList<FollowersModel> list;
	UnFollowAdapter unFollowAdapter;
	Context context;

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
		Toast.makeText(getActivity(), "followers list", Toast.LENGTH_LONG)
				.show();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		name = preferences.getString(AppConstants.USERNAME, "");
		new GetUsersList().execute();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		context = activity;
		Log.i("TAG", "onAttach called");
		super.onAttach(activity);
	}

	private class GetUsersList extends AsyncTask<String, Void, String> {

		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.GET_FOLLOWERS_LIST);
				builder.append("?username=" + name);
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
			super.onPostExecute(result);
			if (result != null) {
				if (!result.equals("")) {
					Log.d("TAG", "details : " + result);
					list = getValues(result);
					Log.d("TAG", "VAISHAKH " + list);

					unFollowAdapter = new UnFollowAdapter(context,
							R.layout.followers_list, list);
					setListAdapter(unFollowAdapter);
				}

			}
		}

		private ArrayList<FollowersModel> getValues(String result) {
			// TODO Auto-generated method stub
			ArrayList<FollowersModel> list = new ArrayList<FollowersModel>();
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					FollowersModel model = new FollowersModel();
					JSONObject object = new JSONObject(array.getString(i));
					model.setName(object.getString("name"));
					model.setEmail(object.getString("email"));
					model.setImage(object.getString("pic"));
					model.setUsername(object.getString("username"));
					model.setFollowcount(object.getString("followers"));
					if (object.getString("name").equals("admin")) {
						list.add(model);
					}
					if (!object.getString("name").equals(name)) {
						list.add(model);
					}

					/*
					 * followersadapter = new FollowersAdapter(getActivity(),
					 * list); setListAdapter(followersadapter);
					 */
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}

}
