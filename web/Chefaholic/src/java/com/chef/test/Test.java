/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chef.test;

import com.chef.bean.StoreBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Staff
 */
public class Test {

    public static void main(String[] args) {

        BufferedReader in = null;
        try {
            StoreBean bean = new StoreBean();
            bean.setLatLng("123,345");
            bean.setStoreName("asdasdfasd123 ,23434 defsdf");
            //     String contents = "?latlng=123,456&storeName="+toString;
            URL url = new URL("http://localhost:8084/Chefaholic/AddStoreServlet");
//            in = new BufferedReader(new InputStreamReader(url.openStream()));
//            String line = in.readLine();
//             System.out.println(line);
//            in.close();
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            ObjectOutputStream objOs = new ObjectOutputStream(conn.getOutputStream());
            objOs.writeObject(bean);
            objOs.flush();
            ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
            String readObject = (String) ois.readObject();
            System.out.println("" + readObject);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
//select * from recipie_info where date BETWEEN DATE_SUB('2015-03-19', INTERVAL 5 DAY) and DATE_ADD('2015-03-19', INTERVAL 5 DAY)