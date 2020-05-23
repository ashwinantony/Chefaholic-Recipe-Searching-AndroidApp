package com.chefaholic.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chefaholic.adapter.ReceipeAdapter;
import com.chefaholic.model.ReceipeModel;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;

public class MainActivity extends ListActivity implements OnItemClickListener {

	String username = "";
	String password = "";

	EditText usernameEditText = null;
	EditText passwordEditText = null;

	ListView adminRecipieList = null;
	ArrayList<ReceipeModel> list;
	ReceipeAdapter receipeAdapter;

	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adminRecipieList = getListView();
		new GetReceipesByAdmin().execute();
		adminRecipieList.setOnItemClickListener(this);

	}

	private class GetReceipesByAdmin extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		String url = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading..");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.ADMIN_RECEIPE_SERVLET);

				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						MainActivity.this);
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
					list = getValues(result);
					receipeAdapter = new ReceipeAdapter(MainActivity.this, list);
					adminRecipieList.setAdapter(receipeAdapter);
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
				model.setUploadedBy(object.getString("admin"));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		/*
		 * case R.id.action_serch: return true;
		 */

		case R.id.loginMenu:
			Toast.makeText(getApplicationContext(), "login is selected",
					Toast.LENGTH_LONG).show();
			showLoginDialog("Login");
			return true;

		case R.id.register:
			Toast.makeText(getApplicationContext(), "activity is refreshed",
					Toast.LENGTH_LONG).show();
			showRegisterDialog();
			return true;

		case R.id.searchbyTag:
			showSearchDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void showSearchDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		final View view = inflater.inflate(R.layout.tag_search, null);
		builder.setTitle("Search");
		final EditText searchEditText = (EditText) view
				.findViewById(R.id.searchTagEditText);
		final CheckBox chickenCheckBox = (CheckBox) view
				.findViewById(R.id.chickencheckBox);
		final CheckBox muttonCheckBox = (CheckBox) view
				.findViewById(R.id.muttoncheckBox);
		final CheckBox beefCheckBox = (CheckBox) view
				.findViewById(R.id.beefcheckBox);
		final CheckBox porkCheckBox = (CheckBox) view
				.findViewById(R.id.porkcheckBox);
		final CheckBox eggCheckBox = (CheckBox) view
				.findViewById(R.id.eggcheckBox);
		final CheckBox potateoCheckBox = (CheckBox) view
				.findViewById(R.id.potatoscheckBox);
		builder.setView(view);
		builder.setPositiveButton("Search",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						StringBuilder sb = new StringBuilder();
						// sb.append(AppConstants.ADMIN_RECEIPE_SERVLET);
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

	private class SearchTagAsyncTask extends AsyncTask<String, Void, String> {

		String url = "";
		ProgressDialog dialog = new ProgressDialog(MainActivity.this);

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
				builder.append(AppConstants.SEARCH_BY_TAG_SERVLET);
				builder.append("?username=admin" + "&tags=" + params[0]);

				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						MainActivity.this);
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
					list = getValues(result);
					receipeAdapter = new ReceipeAdapter(MainActivity.this, list);
					adminRecipieList.setAdapter(receipeAdapter);
				}
			}
			super.onPostExecute(result);
		}

	}

	private void showRegisterDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		final View view = inflater.inflate(R.layout.dialog_register, null);
		builder.setTitle("Register");
		final EditText registername = (EditText) view
				.findViewById(R.id.registerNameEditText);
		final EditText mobileEditText = (EditText) view
				.findViewById(R.id.registerNumberEditText);
		final EditText emailEditText = (EditText) view
				.findViewById(R.id.registerEmailEditText);
		final EditText addressEditText = (EditText) view
				.findViewById(R.id.registerAddressEditText);
		final EditText usernames = (EditText) view
				.findViewById(R.id.registerusernameEditText);
		final EditText passwords = (EditText) view
				.findViewById(R.id.registerPasswordEditText);
		final EditText confirmEditText = (EditText) view
				.findViewById(R.id.registerConfirmPasswordEditText);
		builder.setView(view);
		builder.setPositiveButton("Register",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String name = registername.getText().toString();
						String number = mobileEditText.getText().toString();
						String address = addressEditText.getText().toString();
						String email = emailEditText.getText().toString();
						String uname = usernames.getText().toString();
						String pswd = passwords.getText().toString();
						String confirm = confirmEditText.getText().toString();

						if (name.equals("")) {
							registername.setError("Enter name");
						} else if (number.equals("")) {
							mobileEditText.setError("Enter MobileNumber");
						} else if (address.equals("")) {
							addressEditText.setError("Enter Address");
						} else if (email.equals("")) {
							emailEditText.setError("Enter Email");
						} else if (uname.equals("")) {
							usernames.getText().toString();
						} else if (pswd.equals("")) {
							passwords.setError("Enter Password");
						} else if (confirm.equals("")) {
							confirmEditText
									.setError("Please Confirm Your Password");
						} else if (!confirm.equals(pswd)) {
							confirmEditText.setError("Passwords donot match");
						} else {
							new RegisterNewUserAsyncTask().execute(name,
									number, address, email, uname, pswd);
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
		builder.setCancelable(false);
		builder.show();

	}

	private class RegisterNewUserAsyncTask extends
			AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		String url = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.d("TAG", "REGISTER");
			dialog.setMessage("Registering...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				StringBuilder builder = new StringBuilder();
				builder.append(AppConstants.REGISTER_SERVLET);

				builder.append("?name=" + params[0] + "&mobile=" + params[1]
						+ "&address=" + params[2] + "&email=" + params[3]
						+ "&username=" + params[4] + "&password=" + params[5]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						MainActivity.this);
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
			if (result.equals("error")) {
				Toast.makeText(getApplicationContext(), "Network Error",
						Toast.LENGTH_LONG).show();
			} else if (result.equals("exists")) {
				Toast.makeText(getApplicationContext(),
						"Usename Already Exists", Toast.LENGTH_LONG).show();
			} else if (result.equals("true")) {
				Toast.makeText(getApplicationContext(),
						"Successfully Registered", Toast.LENGTH_LONG).show();
				showLoginDialog("Login");
			} else {
				Toast.makeText(getApplicationContext(),
						"Please Try again after sometimes", Toast.LENGTH_LONG)
						.show();

			}
			super.onPostExecute(result);
		}

	}

	private void showLoginDialog(String title) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		final View view = inflater.inflate(R.layout.dialog_login, null);
		builder.setTitle(title);
		usernameEditText = (EditText) view.findViewById(R.id.usernameEditText);
		passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
		builder.setView(view);
		builder.setPositiveButton("Login",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						username = usernameEditText.getText().toString();
						password = passwordEditText.getText().toString();
						if (username.equals("")) {

						} else if (password.equals("")) {

						} else {
							new LoginAsyncTask().execute(username, password);
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
		builder.setCancelable(false);
		builder.show();

	}

	private class LoginAsyncTask extends AsyncTask<String, Void, String> {

		String url = "";

		ProgressDialog dialog = new ProgressDialog(MainActivity.this);

		String url2 = "";

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
				builder.append(AppConstants.LOGIN_SERVLET);

				builder.append("?username=" + params[0] + "&password="
						+ params[1]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						MainActivity.this);
				if (url.equals("user")) {
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(MainActivity.this);
					Editor editor = preferences.edit();
					editor.putString(AppConstants.USERNAME, params[0]);
					editor.putString(AppConstants.PASSWORD, params[1]);
					editor.commit();
					StringBuilder bui = new StringBuilder();
					bui.append(AppConstants.RETRIEVE_USER_DETAILS_SERVLET);

					bui.append("?username=" + params[0]);
					Log.d("LIST", "RETRIVE " + bui.toString());
					url2 = URLDownloadUtil.downloadUrl(bui.toString(),
							MainActivity.this);
					Log.d("LIST", "URL2 " + url2);
				
				Log.d("LIST", "URL " + url);
				}
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
					if (result.equals("admin")) {
						Toast.makeText(getApplicationContext(), "hai admin",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(MainActivity.this,
								AdminHomeActivity.class);
						startActivity(intent);

					} else if (result.equals("user")) {

						try {
							JSONObject object = new JSONObject(url2);
							String name = object.getString("name");
							String pass = object.getString("password");
							String email = object.getString("email");
							String mobile = object.getString("mobile");
							// String address = object.getString("address");
							preferences = PreferenceManager
									.getDefaultSharedPreferences(MainActivity.this);
							Editor editor = preferences.edit();
							editor.putString(AppConstants.NAME, name);
							editor.putString(AppConstants.PASSWORD, pass);
							editor.putString(AppConstants.EMAIL_ID, email);
							editor.putString(AppConstants.MOBILE, mobile);
							editor.commit();

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent intent = new Intent(MainActivity.this,
								UserHomeActivity.class);
						startActivity(intent);
						Toast.makeText(getApplicationContext(), "hai user",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Invalid username or password",
								Toast.LENGTH_LONG).show();
						showLoginDialog("Retry");
					}

					// itemsList.setAdapter(itemsAdapter);
				}
			} else {
				Toast.makeText(getApplicationContext(), "error",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		String id = list.get(position).getId();
		String image = list.get(position).getImage();
		Intent intent = new Intent(MainActivity.this, FoodDetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("image", image);
		Log.d("ERROR", id);
		startActivity(intent);

	}

}
