package com.chefaholic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class RecipieDetailsActivity extends Activity implements OnClickListener {

	EditText recipieNameEditText = null;
	EditText ingradientsEditText = null;
	EditText makingEditText = null;
	Button nextbButton = null;
	RadioButton vegRadioButton = null;
	RadioButton nonvegRadioButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipiedetails);
		recipieNameEditText = (EditText) findViewById(R.id.recipieEditText);
		ingradientsEditText = (EditText) findViewById(R.id.ingrediantsEditText);
		makingEditText = (EditText) findViewById(R.id.makingEditText);
		nextbButton = (Button) findViewById(R.id.nextButton);
		vegRadioButton = (RadioButton) findViewById(R.id.vegRadioButton);
		nonvegRadioButton = (RadioButton) findViewById(R.id.nonvegRadioButton);
		nextbButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = recipieNameEditText.getText().toString();
		String ingrediants = ingradientsEditText.getText().toString();
		String making = makingEditText.getText().toString();
		String category = "";
		if (name.equals("")) {
			recipieNameEditText.setError("ENTER RECIPIE NAME");
		} else if (ingrediants.equals("")) {
			ingradientsEditText.setError("ENTER INGRADIENTS");
		} else if (making.equals("")) {
			makingEditText.setError("ENTER MAKING STEPS");
		} /*else if (vegRadioButton.isSelected() == false
				|| nonvegRadioButton.isSelected() == false) {
			Toast.makeText(getApplicationContext(), "Select Category",
					Toast.LENGTH_LONG).show();
		}*/ else {
			if (vegRadioButton.isSelected()) {
				category = "veg";
			}else {
				category = "non-veg";
			}
			Intent intent = new Intent(RecipieDetailsActivity.this,
					RecipieUploadActivity.class);
			intent.putExtra("name", name);
			intent.putExtra("ingredients", ingrediants);
			intent.putExtra("making", making);
			intent.putExtra("category", category);
			startActivity(intent);
		}
	}

}
