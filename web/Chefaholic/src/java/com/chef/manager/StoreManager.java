package com.chef.manager;

import com.chef.bean.StoreBean;
import com.chef.db.DbConnection;
import com.chef.util.Appconstants;
import com.chef.util.SupportClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StoreManager {
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;
    public boolean  checkIfAdded(StoreBean bean) throws ClassNotFoundException, SQLException {
        boolean exist = false;
        try{
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM store_info WHERE latitude = ? AND longitude = ? AND store_name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getLatitude());
            ps.setString(2, bean.getLongitude());
            ps.setString(3, bean.getStoreName());
            rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }finally{
           new SupportClass().close(rs, ps, con);
        }
        return exist;
    }

    public String addStore(StoreBean bean) throws ClassNotFoundException, SQLException {
        String added = Appconstants.FAILED;
        try{
            con = new DbConnection().getConnection();
            String query = "INSERT INTO store_info VALUES (?, ?, ?)";
            ps = con.prepareStatement(query);        
            ps.setString(1, bean.getStoreName());
            ps.setString(2, bean.getLatitude());
            ps.setString(3, bean.getLongitude());
            int value = ps.executeUpdate();
             if (value > 0) {
                added = Appconstants.SUCCESS;
            }
        }finally{
            new SupportClass().close(ps, con);
        }
        return added;
    }

    public JSONArray viewStore() throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try{
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM store_info";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {                
                JSONObject object = new JSONObject(); 
                object.put(Appconstants.LATITUDE, rs.getString("latitude"));
                object.put(Appconstants.LONGITUDE, rs.getString("longitude"));
                object.put(Appconstants.STORE_NAME, rs.getString("store_name"));
                array.add(object);
            }
            
        }finally{
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }
    
}
