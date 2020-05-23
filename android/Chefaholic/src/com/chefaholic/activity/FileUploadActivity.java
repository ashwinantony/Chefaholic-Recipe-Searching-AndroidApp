package com.chefaholic.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chef.bean.RecipieBean;
import com.chefaholic.utils.AppConstants;
import com.chefaholic.utils.FileUtils;
import com.chefaholic.utils.UploadImage;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class FileUploadActivity extends Activity implements OnClickListener {

	public class SendTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			File file = new File(params[0]);
			String res = new UploadImage().upload(file, PreferenceManager
					.getDefaultSharedPreferences(FileUploadActivity.this)
					.getString(AppConstants.USERNAME, ""));
			return res;
		}

		@Override 
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(),
					"Image Uploaded Successfully!", Toast.LENGTH_SHORT).show();
		}

	}

	Button browseButton = null;
	Button captureButton = null;
	Button uploadButton = null;
	EditText imageLocationEditText = null;
	ImageView capturedImageView = null;
	String path = "";

	String image = "";
	String username = "";

	private static final int FILE_SELECT_CODE = 0;
	SharedPreferences preferences;
	String image_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_upload_pic);
		browseButton = (Button) findViewById(R.id.browseButton);
		captureButton = (Button) findViewById(R.id.captureButton);
		uploadButton = (Button) findViewById(R.id.uploadButton);
		imageLocationEditText = (EditText) findViewById(R.id.imagepathEditText);
		capturedImageView = (ImageView) findViewById(R.id.captureImageView);
		uploadButton.setOnClickListener(this);
		browseButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		path = imageLocationEditText.getText().toString();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(FileUploadActivity.this);
		username = preferences.getString(AppConstants.USERNAME, "");
		if (v.getId() == browseButton.getId()) {
			showFileChooser();
		} else {
			if (!path.equals("")) {
				new UploadProfilePictureAsyncTask().execute(username, image);
			}
		}
	}

	private class UploadProfilePictureAsyncTask extends
			AsyncTask<String, Void, String> {

		String url = "";

		@SuppressWarnings("resource")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				File imagefile = new File(params[1]);
				FileInputStream fis = null;
				Log.d("TAG", "inside upload");

				fis = new FileInputStream(imagefile);
				byte[] fileBytes = new byte[fis.available()];
				fis.read(fileBytes);
				RecipieBean bean = new RecipieBean();
				bean.setPic(fileBytes);
				bean.setUsername(username);

				URL url = new URL("http://" + AppConstants.IP + ":"
						+ AppConstants.PORT
						+ "/Chefaholic/UploadUserImageServlet");
				URLConnection con = url.openConnection();
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setRequestProperty("Content-Type",
						"application/octet-stream");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						con.getOutputStream());
				objectOutputStream.writeObject(bean);
				objectOutputStream.flush();

				ObjectInputStream inputStream = new ObjectInputStream(
						con.getInputStream());
				try {
					String result = (String) inputStream.readObject();
					Log.d("LIST", "RESULTS IMAGES : " + result);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return url;
		}

	}

	private void showFileChooser() {
		// TODO Auto-generated method stub
		Log.d("POSITION", "ENTERS HERE");
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent, "Select a File to Upload"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(getApplicationContext(),
					"Please install a File Manager.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
			// Get the Uri of the selected file
			Uri uri = data.getData();
			Log.d("TAG", "File Uri: " + uri.toString());
			// Get the path
			String path = "";
			image_name = "";
			try {
				path = FileUtils.getPath(this, uri);
				String[] split = path.split("/");
				int length = split.length;
				image_name = split[length - 1];
				imageLocationEditText.setText(path);
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("TAG", "File Path: " + path);

			// Get the file instance
			// File file = new File(path);
			// Initiate the upload
			sendvalues(path);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void sendvalues(String path2) {
		// TODO Auto-generated method stub
		
		new SendTask().execute(path2);
	}

	/******************************************************************************************************/

	/******************************************************************************************************/

}
