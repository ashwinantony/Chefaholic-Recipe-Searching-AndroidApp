package com.chefaholic.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chefaholic.adapter.ReceipeAdapter;
import com.chefaholic.model.ReceipeModel;
import com.chefaholic.utils.AppConstants;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class SearchResultByTagActivity extends ListActivity implements OnItemClickListener{
	
	
	ArrayList<ReceipeModel> list;
	ReceipeAdapter receipeAdapter;

	ListView searListView = null;

	SharedPreferences preferences;
	String name = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		searListView = getListView();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(SearchResultByTagActivity.this);
		name = preferences.getString(AppConstants.USERNAME, "");
		String result = getIntent().getExtras().getString("result");
		list = getValues(result);
		receipeAdapter = new ReceipeAdapter(SearchResultByTagActivity.this, list);
		searListView.setAdapter(receipeAdapter);
		searListView.setOnItemClickListener(this);
	}

	private ArrayList<ReceipeModel> getValues(String result) {
		// TODO Auto-generated method stub
		ArrayList<ReceipeModel> list = new ArrayList<ReceipeModel>();
		try {
			Log.d("RESULT", "AM HERE5");
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				ReceipeModel model = new ReceipeModel();
				JSONObject object = new JSONObject(array.getString(i));
				model.setName(object.getString("foodName"));
				model.setId(object.getString("recipieId"));
				model.setImage(object.getString("pic"));
				model.setCategory(object.getString("category"));
				model.setUploadedBy(object.getString("name"));
				model.setDate(object.getString("date"));
				// wishlistModel.setCost(object.getString("cost"));

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		String pid = list.get(position).getId();
		String image = list.get(position).getImage();
		Intent intent = new Intent(SearchResultByTagActivity.this, FoodDetailActivity.class);
		intent.putExtra("id", pid);
		intent.putExtra("image", image);
		Log.d("ERROR", pid);
		startActivity(intent);
	}

}
