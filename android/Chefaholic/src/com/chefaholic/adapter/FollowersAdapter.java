package com.chefaholic.adapter;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chefaholic.activity.MainActivity;
import com.chefaholic.activity.R;
import com.chefaholic.model.FollowersModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;
import com.squareup.picasso.Picasso;

public class FollowersAdapter extends ArrayAdapter<FollowersModel> {
	Context context;
	ArrayList<FollowersModel> list;
	int followersList;

	TextView nameTextView = null;
	TextView emailTextView = null;
	TextView followcount = null;
	Button follow = null;
	Button view = null;
	ImageView personImageView = null;

	public FollowersAdapter(Context context, int followersList,
			ArrayList<FollowersModel> list) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.followers_list, list);
		this.list = list;
		this.context = context;
		
		
		Log.d("ERROR", "GETTING INSIDE");
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			
			Log.d("ERROR", "GETTING INSIDE" + list);
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.followers_list, parent,
					false);
			nameTextView = (TextView) convertView
					.findViewById(R.id.folloingNameTextView);
			emailTextView = (TextView) convertView
					.findViewById(R.id.followingEmailId);
			followcount = (TextView) convertView
					.findViewById(R.id.numberOfFollowers);
			follow = (Button) convertView
					.findViewById(R.id.followUnfollowButton);
			view = (Button) convertView.findViewById(R.id.viewButton);
			personImageView = (ImageView) convertView
					.findViewById(R.id.followerImageView);

		}
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String username = preferences.getString(AppConstants.USERNAME, "");
		if(!list.get(position).getName().equals(username)){
			view.setVisibility(View.GONE);
			nameTextView.setText(list.get(position).getName());
			emailTextView.setText(list.get(position).getEmail());
			followcount.setText(list.get(position).getFollowcount()
					+ " Followers");
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(AppConstants.USER_IMAGE_FOLDER);
		builder.append(list.get(position).getImage());
		Log.d("TAG", "image : " + builder.toString());
		Picasso.with(context)
				.load(builder.toString())
				.placeholder(
						context.getResources().getDrawable(R.drawable.no_image))
				.fit().into(personImageView);
		follow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// new FollowListAsyncTask().
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
				String username = preferences.getString(AppConstants.USERNAME, "");
				String uname = list.get(position).getUsername();
				new FollowUnfollowAsyncTask().execute(username,uname);
			}
		});

		return convertView;
	}
	
	private class FollowUnfollowAsyncTask extends AsyncTask<String, Void, String>{
		
		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.FOLLOW_UNFOLLOW_SERVLET);

				builder.append("?username="+params[1]+"&followerUsername="+params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						getContext());
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
			if (result.equals("follow")) {
				Log.d("TAG", "FOLLOW");
			}else if (result.equals("unfollow")) {
				Log.d("TAG", "UNFOLLOW");
				
			}else {
				Log.d("TAG", "ERROR");
			}
			super.onPostExecute(result);
		}
		
		
	}

}
