package com.chefaholic.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.chef.bean.StoreBean;

public class AddStore {

	private String res;

	public String add(String loc, String storeName) {
		// TODO Auto-generated method stub

		StoreBean bean = new StoreBean();
		bean.setStoreName(storeName);
		bean.setLatLng(loc);
		try {
			URL url = new URL("http://" + AppConstants.IP + ":"
					+ AppConstants.PORT + "/Chefaholic/AddStoreServlet");

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			ObjectOutputStream objOs = new ObjectOutputStream(
					conn.getOutputStream());
			objOs.writeObject(bean);
			objOs.flush();
			ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
			String readObject = (String) ois.readObject();
			res = readObject;
			System.out.println("" + readObject);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
