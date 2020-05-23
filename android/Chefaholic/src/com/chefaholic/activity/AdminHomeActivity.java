package com.chefaholic.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminHomeActivity extends Activity implements OnClickListener {

	Button addRecipieButton = null;
	Button addStoreButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);
		addRecipieButton = (Button) findViewById(R.id.addRecipie);
		addStoreButton = (Button) findViewById(R.id.addStoreButton);
		addStoreButton.setOnClickListener(this);
		addRecipieButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == addStoreButton.getId()) {
			Intent intent = new Intent(AdminHomeActivity.this,
				AddStoreActivity.class);
		startActivity(intent);
		}else {
			Intent i = new Intent(AdminHomeActivity.this,FoodDetailActivity.class);
			startActivity(i);
		}
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
		builder.setTitle("Confirm exit ?");
		builder.setIcon(R.drawable.ic_logout);
		builder.setMessage("This action will perform log out \nfrom this application");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			private SharedPreferences preferences;

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				preferences = PreferenceManager
						.getDefaultSharedPreferences(AdminHomeActivity.this);
				Editor editor = preferences.edit();
				editor.clear();
				editor.commit();
				
				Intent intent = new Intent(AdminHomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

		builder.setNegativeButton("NO", null);
		builder.create().show();
	}

}
