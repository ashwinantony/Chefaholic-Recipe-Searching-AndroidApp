package com.chefaholic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chefaholic.activity.R;
import com.chefaholic.model.ReceipeModel;
import com.chefaholic.utils.AppConstants;
import com.squareup.picasso.Picasso;

public class ReceipeAdapter extends ArrayAdapter<ReceipeModel> {

	ArrayList<ReceipeModel> list;
	Context context;

	public ReceipeAdapter(Context context, ArrayList<ReceipeModel> list) {
		super(context, R.layout.custom_preparation, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		Log.d("RESULT", "AM HERE1");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			Log.d("RESULT", "AM HERE2");
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_preparation, parent,
					false);
			TextView receipenameTextView = (TextView) convertView
					.findViewById(R.id.receipeNameTextView);
			TextView uploadedByTextView = (TextView) convertView
					.findViewById(R.id.uploadedByTextView);
			TextView categoryTextView = (TextView) convertView
					.findViewById(R.id.categoryNameTextView);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.itemImageView);
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.dateTextView);
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			String username = preferences.getString(AppConstants.USERNAME, "");
			if (list.get(position).getUploadedBy().equals("admin")) {
				receipenameTextView.setText(list.get(position).getName());
				if (list.get(position).getUploadedBy().equals("")) {
					uploadedByTextView.setVisibility(View.GONE);
				}else {
					uploadedByTextView.setText("Uploaded by : "
						+ list.get(position).getUploadedBy());
				}
				
				categoryTextView.setText("Category : "
						+ list.get(position).getCategory());
				dateTextView.setText("Uploaded on : "
						+ list.get(position).getDate());
				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.USER_IMAGE_FOLDER);
				builder.append(list.get(position).getImage());
				Log.d("TAG", "image : " + builder.toString());
				Picasso.with(context)
						.load(builder.toString())
						.placeholder(
								context.getResources().getDrawable(
										R.drawable.no_image)).fit().into(imageView);
			}
			if(!list.get(position).getName().equals(username)) {
				receipenameTextView.setText(list.get(position).getName());
				uploadedByTextView.setText("Uploaded by : "
						+ list.get(position).getUploadedBy());
				categoryTextView.setText("Category : "
						+ list.get(position).getCategory());
				dateTextView.setText("Uploaded on : "
						+ list.get(position).getDate());
				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.USER_IMAGE_FOLDER);
				builder.append(list.get(position).getImage());
				Log.d("TAG", "image : " + builder.toString());
				Picasso.with(context)
						.load(builder.toString())
						.placeholder(
								context.getResources().getDrawable(
										R.drawable.no_image)).fit().into(imageView);			}

		
		}
		return convertView;
	}

}
