package com.chefaholic.adapter;

import java.util.ArrayList;

import com.chefaholic.activity.R;
import com.chefaholic.model.CommentsModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends ArrayAdapter<CommentsModel> {

	ArrayList<CommentsModel> list;
	Context context;
	
	TextView username = null;
	TextView date = null;
	TextView comments = null;

	public CommentsAdapter(Context context, ArrayList<CommentsModel> list) {
		super(context, R.layout.comments_layout, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			Log.d("ERROR", "GETTING INSIDE" + list);

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.comments_layout, parent,
					false);
			username = (TextView) convertView
					.findViewById(R.id.commentUsernameTextView);
			date = (TextView) convertView
					.findViewById(R.id.commentDateTextView);
			comments = (TextView) convertView
					.findViewById(R.id.commentsTextViews);			
		}
		username.setText(list.get(position).getUsername());
		date.setText(list.get(position).getDate());
		comments.setText("'' "+ list.get(position).getComments() + " ''");
		return convertView;
	}

}
