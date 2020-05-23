package com.chefaholic.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.chefaholic.adapter.ReceipeAdapter;
import com.chefaholic.model.ReceipeModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;


public class ViewUsersRecipieActivity extends ListActivity implements
		OnItemClickListener, OnClickListener {

	ListView recipielist = null;

	ArrayList<ReceipeModel> list;
	ReceipeAdapter receipeAdapter;

	Calendar fromCal;
	Calendar toCalendar;
	Calendar activeDate;

	EditText fromDateEditText = null;
	ImageView fromCalenderImageView = null;

	int fromday;
	int frommonth;
	int fromyear;
	int today;
	int tomonth;
	int toyear;
	int id;

	String fromdate = "";
	String todate = "";

	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		recipielist = getListView();
		String username = getIntent().getExtras().getString("user");
		new ViewUsersRecipie().execute(username);
		recipielist.setOnItemClickListener(this);
	}

	private class ViewUsersRecipie extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(
				ViewUsersRecipieActivity.this);
		String url = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.USERS_RECIPIE_DETAILS);

				builder.append("?username=" + params[0]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						ViewUsersRecipieActivity.this);
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
					list = getValues(result);
					receipeAdapter = new ReceipeAdapter(
							ViewUsersRecipieActivity.this, list);
					recipielist.setAdapter(receipeAdapter);
				}
			}
			super.onPostExecute(result);
		}

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
				// model.setUploadedBy(object.getString("admin"));
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		String id = list.get(position).getId();
		String image = list.get(position).getImage();
		Intent intent = new Intent(ViewUsersRecipieActivity.this,
				FoodDetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("image", image);
		Log.d("ERROR", id);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.filterbydate, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.filterByDate) {
			showDatePickerDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	private void showDatePickerDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ViewUsersRecipieActivity.this);
		LayoutInflater inflater = LayoutInflater
				.from(ViewUsersRecipieActivity.this);
		View view = inflater.inflate(R.layout.list_log_new, null);
		fromDateEditText = (EditText) view
				.findViewById(R.id.selectFromDateEditText);

		fromCalenderImageView = (ImageView) view
				.findViewById(R.id.selectFromDateCalender);

		//
		fromCal = Calendar.getInstance();
		toCalendar = Calendar.getInstance();
		fromday = fromCal.get(Calendar.DAY_OF_MONTH);
		frommonth = fromCal.get(Calendar.MONTH);
		fromyear = fromCal.get(Calendar.YEAR);
		//
		today = toCalendar.get(Calendar.DAY_OF_MONTH);
		tomonth = toCalendar.get(Calendar.MONTH);
		toyear = toCalendar.get(Calendar.YEAR);
		//
		builder.setView(view);
		fromCalenderImageView.setOnClickListener(this);

		builder.setPositiveButton("Get Details",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(
								getApplicationContext(),
								"FROM DATE = " + fromdate + " TO DATE = "
										+ todate, Toast.LENGTH_LONG).show();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"MM-dd-yyyy");
						try {
							//Date to = sdf.parse(todate);
							//Date date = sdf.parse(sdf.format(new Date()));
							if (fromDateEditText.getText().toString()
									.equals("")) {
								fromDateEditText.setError("Select date");
							} else {
								preferences = PreferenceManager
										.getDefaultSharedPreferences(ViewUsersRecipieActivity.this);
								String namesss = preferences.getString(
										AppConstants.USERNAME, "");
								new GetCallDetailsAsyncTask().execute(fromdate,
										namesss);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		builder.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == fromCalenderImageView.getId()) {
			showDialog(0);
		}
	}

	private class GetCallDetailsAsyncTask extends
			AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(
				ViewUsersRecipieActivity.this);
		String url = "";

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
				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.SEARCH_BY_DATE_SERVLET);

				builder.append("?date=" + params[0] + "&username=" + params[1]);
				Log.d("LIST", "hdfjh" + builder.toString());

				url = URLDownloadUtil.downloadUrl(builder.toString(),
						ViewUsersRecipieActivity.this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("LIST", "URL " + url);
			return url;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			dialog.dismiss();
			if (result != null) {
				if (!result.equals("")) {
					list = getValues(result);
					receipeAdapter = new ReceipeAdapter(
							ViewUsersRecipieActivity.this, list);
					recipielist.setAdapter(receipeAdapter);
				}
			}
		}

	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		
			Log.d("TAG", "id = 0");
		return new DatePickerDialog(this, datePickerListener, fromyear,
				frommonth, fromday);
		
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			fromDateEditText.setText(selectedDay + "-" + (selectedMonth + 1)
					+ "-" + selectedYear);
			fromdate = fromDateEditText.getText().toString();
			
			Log.d("DATE", "fromdate " + fromdate);

			

		}
	};

}
