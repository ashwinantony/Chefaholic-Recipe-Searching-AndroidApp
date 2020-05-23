package com.chefaholic.activity;



import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;

public class SearchByTagsActivity extends Activity implements OnClickListener{
	
	EditText searchEditText = null;
	CheckBox chickenCheckBox = null;
	CheckBox muttonCheckBox = null;
	CheckBox beefCheckBox = null;
	CheckBox porkCheckBox = null;
	CheckBox eggCheckBox = null;
	CheckBox potateoCheckBox = null;
	Button searchButton = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_search);
		searchEditText = (EditText) findViewById(R.id.searchTagEditText);
		chickenCheckBox= (CheckBox) findViewById(R.id.chickencheckBox);
		muttonCheckBox = (CheckBox) findViewById(R.id.muttoncheckBox);
		beefCheckBox = (CheckBox) findViewById(R.id.beefcheckBox);
		porkCheckBox = (CheckBox) findViewById(R.id.porkcheckBox);
		eggCheckBox = (CheckBox) findViewById(R.id.eggcheckBox);
		potateoCheckBox = (CheckBox) findViewById(R.id.potatoscheckBox);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);			
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		String tag = searchEditText.getText().toString();
		if (!tag.equals("")) {
			sb.append("" + tag + ",");
		}
		if (chickenCheckBox.isChecked()) {
			sb.append("chicken,");
		}
		if (muttonCheckBox.isChecked()) {
			sb.append("mutton,");
		}
		if (beefCheckBox.isChecked()) {
			sb.append("beef,");
		}
		if (porkCheckBox.isChecked()) {
			sb.append("pork,");
		}
		if (eggCheckBox.isChecked()) {
			sb.append("egg,");
		}
		if (potateoCheckBox.isChecked()) {
			sb.append("patateo,");
		}
		new SearchTagAsyncTask().execute(sb.toString());
		Log.d("TAG", "STRING BUILDER " + sb);
	}
	
	private class SearchTagAsyncTask extends AsyncTask<String, Void, String> {

		String url = "";
		ProgressDialog dialog = new ProgressDialog(SearchByTagsActivity.this);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading");
			dialog.show();
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(SearchByTagsActivity.this);
				String uname = preferences.getString(AppConstants.USERNAME, "");
				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.SEARCH_BY_TAG_SERVLET);
				builder.append("?username=" + uname + "&tags=" + params[0]);

				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						SearchByTagsActivity.this);
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
					Intent intent = new Intent(SearchByTagsActivity.this,SearchResultByTagActivity.class);
					intent.putExtra("result", result);
					startActivity(intent);
					finish();
					/*list = getValues(result);
					receipeAdapter = new ReceipeAdapter(MainActivity.this, list);
					adminRecipieList.setAdapter(receipeAdapter);*/
					
				}
			}
			super.onPostExecute(result);
		}
		

	}

}
