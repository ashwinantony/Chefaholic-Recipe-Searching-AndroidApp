package com.chefaholic.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chefaholic.adapter.CommentsAdapter;
import com.chefaholic.model.CommentsModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;
import com.squareup.picasso.Picasso;

public class FoodDetailActivity extends Activity {

	TextView recipeTextView = null;
	TextView ingradientTextView = null;
	TextView preparationTextView = null;
	ImageView itemFullImageView = null;

	EditText addcommentsEditText = null;
	Button addcommentButton = null;
	ListView commentsListView = null;

	String IDS = "";
	String image = "";

	ImageView ivIcon;
	TextView tvItemName;

	ArrayList<CommentsModel> list;
	CommentsAdapter adapter;

	SharedPreferences preferences;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_food_detail);
		recipeTextView = (TextView) findViewById(R.id.receipeTextView);
		ingradientTextView = (TextView) findViewById(R.id.ingredientsTextView);
		preparationTextView = (TextView) findViewById(R.id.preparationTextView);
		itemFullImageView = (ImageView) findViewById(R.id.itemFullImageView);

		IDS = getIntent().getExtras().getString("id");
		image = getIntent().getExtras().getString("image");

		new LoadFoodDetail().execute(IDS);
		super.onCreate(savedInstanceState);
	}

	private class LoadFoodDetail extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(FoodDetailActivity.this);

		String url = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Please wait");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.FOOD_DETAIL);

				builder.append("?recipieId=" + params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						FoodDetailActivity.this);
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
			String name = "";
			String ingradients = "";
			String preparation = "";
			// String date = "";
			if (result != null) {
				if (!result.equals("")) {
					JSONObject object;
					try {
						object = new JSONObject(result);

						name = object.getString("foodName");
						ingradients = object.getString("ingredients");
						preparation = object.getString("making");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					recipeTextView.setText(name);
					ingradientTextView.setText(ingradients);
					preparationTextView.setText(preparation);
					StringBuilder builder = new StringBuilder();
					builder.append(AppConstants.USER_IMAGE_FOLDER);
					builder.append(image);
					Log.d("TAG", "image : " + builder.toString());
					Picasso.with(FoodDetailActivity.this)
							.load(builder.toString())
							.placeholder(R.drawable.no_image).fit()
							.into(itemFullImageView);

				}
			}

			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.dish_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.addToFavourate) {
			new AddToFavourateAsyncTask().execute(IDS);
		} else {
			new ShowCommentsList().execute(IDS);

		}
		return super.onOptionsItemSelected(item);
	}

	private class ShowCommentsList extends AsyncTask<String, Void, String> {

		String url = "";
		ProgressDialog dialog = new ProgressDialog(FoodDetailActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading comments");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.COMMENTS_SERVLET);

				builder.append("?recipieId=" + params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						FoodDetailActivity.this);
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
			showCommentsDialog(result);
			super.onPostExecute(result);
		}

	}

	
	
	
	private void showCommentsDialog(String result) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(
				FoodDetailActivity.this);
		LayoutInflater inflater = LayoutInflater.from(FoodDetailActivity.this);
		View view = inflater.inflate(R.layout.commentslists, null);
		addcommentsEditText = (EditText) view
				.findViewById(R.id.writeCommentEditText);
		addcommentButton = (Button) view.findViewById(R.id.addCommentButton);
		commentsListView = (ListView) view.findViewById(R.id.commentsListView);
		// commentsListView = view
		list = getComments(result);
		Log.d("TAG", "COMMENTS : " + list);
		adapter = new CommentsAdapter(FoodDetailActivity.this, list);
		commentsListView.setAdapter(adapter);
		addcommentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comments = addcommentsEditText.getText().toString();

				new addComments().execute(IDS, comments);
			}
		});
		builder.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		builder.setView(view);
		builder.show();

	}

	private class addComments extends AsyncTask<String, Void, String> {

		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {

				preferences = PreferenceManager
						.getDefaultSharedPreferences(FoodDetailActivity.this);
				String nam = preferences.getString(AppConstants.USERNAME, "");

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.ADD_COMMENTS);

				builder.append("?username=" + nam + "&recipieId=" + params[0]
						+ "&comments=" + params[1]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						FoodDetailActivity.this);
				Log.d("LIST", "URL " + url);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return url;
		}

	}

	private ArrayList<CommentsModel> getComments(String result) {
		// TODO Auto-generated method stub
		ArrayList<CommentsModel> list = new ArrayList<CommentsModel>();
		try {
			Log.d("RESULT", "AM HERE5");
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				CommentsModel model = new CommentsModel();
				JSONObject object = new JSONObject(array.getString(i));
				model.setUsername(object.getString("username"));
				model.setComments(object.getString("comments"));
				model.setDate(object.getString("date"));

				list.add(model);
				Log.d("TRY", "" + list);

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("ERROR", e.toString());
			e.printStackTrace();
		}
		return list;
	}

	private class AddToFavourateAsyncTask extends
			AsyncTask<String, Void, String> {

		String url = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				preferences = PreferenceManager
						.getDefaultSharedPreferences(FoodDetailActivity.this);
				String nam = preferences.getString(AppConstants.USERNAME, "");

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.ADD_TO_FAVOURATE);

				builder.append("?username=" + nam + "&recipieId=" + params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						FoodDetailActivity.this);
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
			if (result.contains("exists")) {
				Toast.makeText(getApplicationContext(),
						"This item is already in your favourate list",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Item added to favourate list successfully",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}

}
