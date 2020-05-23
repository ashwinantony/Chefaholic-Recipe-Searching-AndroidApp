package com.chefaholic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Test;

import com.chef.bean.RecipieBean;

import android.util.Base64;
import android.util.Log;

public class UploadImage {
	String res = "";
	public String upload(File file, String username) {
		BufferedReader in = null;
		try {

			if (file.exists()) {
				FileInputStream imageInFile = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				imageInFile.read(imageData);
				// String imgInString =
				// Base64.encodeBase64URLSafeString(imageData);
				// System.out.println("" + imgInString);
				RecipieBean bean = new RecipieBean();
				bean.setPic(imageData);
				bean.setUsername(username);
				// bean.setFoodName("fries");
				// bean.setIngredients("askjdghdf djfhsjdhf sjdfhsjdfsjdf");
				// bean.setMaking("asdasdfnhsodf loidsfhlsdnflsdf ldsahflsdfh");
				// bean.setCategories("non-veg");
				// String contents = "?username=anoop&tags=bore";
				URL url = new URL("http://" + AppConstants.IP + ":"
						+ AppConstants.PORT
						+ "/Chefaholic/UploadUserImageServlet");
				// in = new BufferedReader(new
				// InputStreamReader(url.openStream()));
				// String line = in.readLine();
				// System.out.println(line);
				// in.close();
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestProperty("Content-Type",
						"application/octet-stream");
				ObjectOutputStream objOs = new ObjectOutputStream(
						conn.getOutputStream());
				objOs.writeObject(bean);
				objOs.flush();
				ObjectInputStream ois = new ObjectInputStream(
						conn.getInputStream());
				String readObject = (String) ois.readObject();
				res = readObject;
				System.out.println("" + readObject);
			} else {
				Log.d("TAG", "file not found");
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;

	}
	
	
}
