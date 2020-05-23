package com.chefaholic.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chefaholic.adapter.CustomDrawerAdapter;
import com.chefaholic.fragment.FavouratesFragment;
import com.chefaholic.fragment.PeopleListToFollowFragment;
import com.chefaholic.fragment.NewsFeedFragment;
//import com.chefaholic.fragment.UploadImageFragment;
import com.chefaholic.fragment.ViewFollowedListFragment;
import com.chefaholic.model.DrawerItem;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.URLDownloadUtil;

public class UserHomeActivity extends Activity implements OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_userhome);

		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("My Favorites")); // adding a header to the
														// list
		dataList.add(new DrawerItem("Whats new!", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Followers", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Favourates", R.drawable.ic_action_gamepad));
		// dataList.add(new DrawerItem("Peoples", R.drawable.ic_action_labels));

		dataList.add(new DrawerItem("Main Options"));// adding a header to the
														// list
		dataList.add(new DrawerItem("Upload", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Search By Tags",
				R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Peoples", R.drawable.ic_action_camera));
		dataList.add(new DrawerItem("Locate Store", R.drawable.ic_action_video));

		dataList.add(new DrawerItem("Privacy")); // adding a header to the
													// list
		dataList.add(new DrawerItem("Update Profile Picture",
				R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Change Password",
				R.drawable.ic_action_settings));
		dataList.add(new DrawerItem("Logout", R.drawable.ic_action_help));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);
		mDrawerList.setAdapter(adapter);
		Log.d("TAG", "ADAPTER " + dataList);
		SelectItem(0);

		/* mDrawerList.setOnItemClickListener(this); */
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			if (dataList.get(0).isSpinner()
					& dataList.get(1).getTitle() != null) {
				SelectItem(2);
			} else if (dataList.get(0).getTitle() != null) {
				SelectItem(1);
			} else {
				SelectItem(0);
			}
		}
	}

	private void SelectItem(int possition) {
		// TODO Auto-generated method stub

		Log.d("POSITION", "POSITION " + possition);
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 3:
			fragment = new ViewFollowedListFragment();
			args.putString(ViewFollowedListFragment.ITEM_NAME,
					dataList.get(possition).getItemName());
			args.putInt(ViewFollowedListFragment.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 4:
			fragment = new FavouratesFragment();
			args.putString(FavouratesFragment.ITEM_NAME, dataList
					.get(possition).getItemName());
			args.putInt(FavouratesFragment.IMAGE_RESOURCE_ID,
					dataList.get(possition).getImgResID());
			break;

		case 6:

			Intent intent = new Intent(UserHomeActivity.this,
					RecipieDetailsActivity.class);
			startActivity(intent);

			break;

		case 2:
			fragment = new NewsFeedFragment();
			args.putString(NewsFeedFragment.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(NewsFeedFragment.IMAGE_RESOURCE_ID,
					dataList.get(possition).getImgResID());
			break;

		case 8:
			fragment = new PeopleListToFollowFragment();
			args.putString(PeopleListToFollowFragment.ITEM_NAME,
					dataList.get(possition).getItemName());
			args.putInt(PeopleListToFollowFragment.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 7:
			Intent i = new Intent(UserHomeActivity.this,
					SearchByTagsActivity.class);
			startActivity(i);
			break;

		case 13:
			logoutDialog();
			break;

		case 11:
			Intent intentss = new Intent(UserHomeActivity.this,
					FileUploadActivity.class);
			startActivity(intentss);
			break;

		case 12:
			showChangePasswordDialog();
			break;
		case 9:
			Intent viewstoreintent = new Intent(UserHomeActivity.this,ViewStoreActivity.class);
			startActivity(viewstoreintent);
			break;/*
				 * 
				 * case 11: fragment = new FragmentThree();
				 * args.putString(FragmentThree.ITEM_NAME,
				 * dataList.get(possition) .getItemName());
				 * args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
				 * .get(possition).getImgResID()); break; case 12: fragment =
				 * new FragmentOne(); args.putString(FragmentOne.ITEM_NAME,
				 * dataList.get(possition) .getItemName());
				 * args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
				 * dataList.get(possition) .getImgResID()); break; case 14:
				 * fragment = new FragmentThree();
				 * args.putString(FragmentThree.ITEM_NAME,
				 * dataList.get(possition) .getItemName());
				 * args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
				 * .get(possition).getImgResID()); break; case 15: fragment =
				 * new FragmentOne(); args.putString(FragmentOne.ITEM_NAME,
				 * dataList.get(possition) .getItemName());
				 * args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
				 * dataList.get(possition) .getImgResID()); break; case 16:
				 * fragment = new FragmentTwo();
				 * args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
				 * .getItemName()); args.putInt(FragmentTwo.IMAGE_RESOURCE_ID,
				 * dataList.get(possition) .getImgResID()); break;
				 */
		default:
			break;
		}

		Log.e("TAG", "fragment " + fragment);
		Log.e("TAG", "args " + args);
		try {
			fragment.setArguments(args);
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private void logoutDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeActivity.this);
		builder.setTitle("Confirm exit ?");
		builder.setIcon(R.drawable.ic_logout);
		builder.setMessage("This action will perform log out \nfrom this application");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			private SharedPreferences preferences;

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				preferences = PreferenceManager
						.getDefaultSharedPreferences(UserHomeActivity.this);
				Editor editor = preferences.edit();
				editor.clear();
				editor.commit();
				
				Intent intent = new Intent(UserHomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

		builder.setNegativeButton("NO", null);
		builder.create().show();
	
	}

	private void showChangePasswordDialog() {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(
				UserHomeActivity.this);
		LayoutInflater inflater = LayoutInflater.from(UserHomeActivity.this);
		View view = inflater.inflate(R.layout.activity_changepassword, null);
		final EditText currentPasswordEditText = (EditText) view
				.findViewById(R.id.currentPasswordEditText);
		final EditText newpasswordEditText = (EditText) view
				.findViewById(R.id.newPassword);
		final EditText confirmpasswordEditText = (EditText) view
				.findViewById(R.id.confirmPasswordEditText);
		Button changePasswordButton = (Button) view
				.findViewById(R.id.changePasswordButton);
		builder.setView(view);
		changePasswordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		changePasswordButton.setVisibility(View.GONE);
		builder.setNeutralButton("Change Password",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String password = currentPasswordEditText.getText()
								.toString();
						String newPassword = newpasswordEditText.getText()
								.toString();
						String confirm = confirmpasswordEditText.getText()
								.toString();
						if (password.equals("")) {
							currentPasswordEditText
									.setError("Enter Current Password");
						} else if (newPassword.equals("")) {
							newpasswordEditText.setError("Enter New Password");
						} else if (confirm.equals("")) {
							confirmpasswordEditText
									.setError("Enter New Password");
						} else if (newPassword.length() < 5) {
							newpasswordEditText
									.setError("Password must contain atleast 5 characters");
						} else if (!confirm.equals(newPassword)) {
							confirmpasswordEditText
									.setError("Password doesn't match");
						} else {
							String username = PreferenceManager
									.getDefaultSharedPreferences(
											UserHomeActivity.this).getString(
											AppConstants.USERNAME, "");
							new ChangePasswordAsyncTask().execute(username,
									password, confirm);

						}
					}
				});
		builder.show();

	}

	private class ChangePasswordAsyncTask extends
			AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(UserHomeActivity.this);
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
				builder.append(AppConstants.CHANGE_PASSWORD);

				builder.append("?username=" + params[0] + "&currentPassword="
						+ params[1] + "&newPassword=" + params[2]);
				Log.d("LIST", "hdfjh" + builder.toString());
				url = URLDownloadUtil.downloadUrl(builder.toString(),
						UserHomeActivity.this);
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
			if (result.equals("success")) {
				Toast.makeText(getApplicationContext(),
						"PASSWORD SUCCESSFULLY UPDATED", Toast.LENGTH_LONG)
						.show();
			} else {

				Toast.makeText(getApplicationContext(), "INVALID CREDIANTIALS",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/*
	 * @Override protected void onPostCreate(Bundle savedInstanceState) { //
	 * TODO Auto-generated method stub mDrawerToggle.syncState(); }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		if (item.getItemId() == R.id.tagSearch) {

		}

		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (dataList.get(position).getTitle() == null) {
			SelectItem(position);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.searchs, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

}
