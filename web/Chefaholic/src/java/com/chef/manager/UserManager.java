package com.chef.manager;

import com.chef.bean.LoginBean;
import com.chef.bean.RecipieBean;
import com.chef.bean.RegisterBean;
import com.chef.db.DbConnection;
import com.chef.util.Appconstants;
import com.chef.util.SupportClass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserManager {
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public boolean checkUsername(String username) throws ClassNotFoundException, SQLException {
        boolean status = false;
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT type FROM login_info WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return status;
    }
    
    public String registerUser(RegisterBean bean) throws ClassNotFoundException, SQLException {
        String status = Appconstants.FALSE;
        try {
            con = new DbConnection().getConnection();
            String query1 = "INSERT INTO login_info VALUES (?, ?, ?)";
            ps = con.prepareStatement(query1);
            ps.setString(1, bean.getUsername());
            ps.setString(2, bean.getPassword());
            ps.setString(3, Appconstants.USER_TYPE);
            int value1 = ps.executeUpdate();
            if (value1 > 0) {
                String query2 = "INSERT INTO user_info (username, name, mobile, email, address, follower_count) VALUES (?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(query2);
                ps.setString(1, bean.getUsername());
                ps.setString(2, bean.getName());
                ps.setString(3, bean.getMobile());
                ps.setString(4, bean.getEmail());
                ps.setString(5, bean.getAddress());
                ps.setInt(6, Appconstants.NO_FOLLOWER);
                int value2 = ps.executeUpdate();
                if (value2 > 0) {
                    status = Appconstants.TRUE;
                }
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }
    
    public JSONObject getUserDetails(String username) throws ClassNotFoundException, SQLException {
        JSONObject object = new JSONObject();
        try {
            con = new DbConnection().getConnection();
            String query = "select * from user_info join login_info on user_info.username = login_info.username where user_info.username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                object.put(Appconstants.NAME, rs.getString("name"));
                object.put(Appconstants.PASSWORD, rs.getString("password"));
                object.put(Appconstants.EMAIL, rs.getString("email"));
                object.put(Appconstants.MOBILE, rs.getString("mobile"));
                object.put(Appconstants.ADDRESS, rs.getString("address"));
                object.put(Appconstants.PIC, "<img src=" + rs.getString("pic") + " >");
                object.put(Appconstants.FOLLOWERS, rs.getString("follower_count"));
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return object;
    }
    
    public JSONArray getAllUsersDetails(String username) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM user_info WHERE username NOT IN (select username from following_info where follower_username = ?) ORDER BY follower_count DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put(Appconstants.USERNAME, rs.getString("username"));
                object.put(Appconstants.NAME, rs.getString("name"));
                object.put(Appconstants.MOBILE, rs.getString("mobile"));
                object.put(Appconstants.EMAIL, rs.getString("email"));
                object.put(Appconstants.ADDRESS, rs.getString("address"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.FOLLOWERS, rs.getString("follower_count"));
                array.add(object);
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }
    
    public String uploadProfilePic(String username, String path) throws ClassNotFoundException, SQLException {
        String status = Appconstants.NOT_UPLOADED;
        try {
            con = new DbConnection().getConnection();
            String query = "UPDATE user_info SET pic = ? WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, path);
            ps.setString(2, username);
            int value = ps.executeUpdate();
            if (value > 0) {
                status = Appconstants.UPLOADED;
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }
    
    public boolean checkIfFollowing(String username, String followerUsername) throws ClassNotFoundException, SQLException {
        boolean present = false;
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM following_info WHERE username = ? AND follower_username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, followerUsername);
            rs = ps.executeQuery();
            if (rs.next()) {
                present = true;
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return present;
    }
    
    public boolean unfollow(String username, String followerUsername) throws ClassNotFoundException, SQLException {
        boolean status = false;
        try {
            con = new DbConnection().getConnection();
            String query1 = "DELETE FROM following_info WHERE username = ? AND follower_username = ?";
            ps = con.prepareStatement(query1);
            ps.setString(1, username);
            ps.setString(2, followerUsername);
            int value1 = ps.executeUpdate();
            if (value1 > 0) {
                String query2 = "UPDATE user_info SET follower_count = follower_count - 1 WHERE username = ?";
                ps = con.prepareStatement(query2);
                ps.setString(1, username);
                int value2 = ps.executeUpdate();
                if (value2 > 0) {
                    status = true;
                }
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return status;
    }
    
    public boolean follow(String username, String followerUsername) throws ClassNotFoundException, SQLException {
        boolean status = false;
        try {
            con = new DbConnection().getConnection();
            String query1 = "INSERT INTO following_info VALUES (?, ?)";
            ps = con.prepareStatement(query1);
            ps.setString(1, username);
            ps.setString(2, followerUsername);
            int value1 = ps.executeUpdate();
            if (value1 > 0) {
                String query2 = "UPDATE user_info SET follower_count = follower_count + 1 WHERE username = ?";
                ps = con.prepareStatement(query2);
                ps.setString(1, username);
                int value2 = ps.executeUpdate();
                if (value2 > 0) {
                    status = true;
                }
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }
    
    public JSONArray getComments(int recipieId) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM comments_info WHERE recipie_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, recipieId);
            rs = ps.executeQuery();
            while (rs.next()) {                
                JSONObject object = new JSONObject();
                object.put(Appconstants.USERNAME, rs.getString("username"));
                object.put(Appconstants.COMMENTS, rs.getString("comments"));
                object.put(Appconstants.DATE, rs.getString("date"));
                array.add(object);
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }
    
    public String addComments(RecipieBean bean) throws ClassNotFoundException, SQLException {
        String dateInString = new ProjectManager().getSystemdate();
        Date date = java.sql.Date.valueOf(dateInString);
        String status = Appconstants.FAILED;
        try {
            con = new DbConnection().getConnection();
            String query = "INSERT INTO comments_info VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getUsername());
            ps.setInt(2, bean.getRecipieId());
            ps.setString(3, bean.getComments());
            ps.setDate(4, date);
            int value = ps.executeUpdate();
            if (value > 0) {
                status = Appconstants.SUCCESS;
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }
    
    public String addFavourites(String username, int recipieId) throws ClassNotFoundException, SQLException {
        String status = Appconstants.FAILED;
        try {
            con = new DbConnection().getConnection();
            String query = "INSERT INTO favourite_info VALUES (?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, recipieId);
            int value = ps.executeUpdate();
            if (value > 0) {
                status = Appconstants.SUCCESS;
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }
    
    public JSONArray viewFollowers(String username) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "select * from user_info where username IN (select username from following_info where follower_username = ?) ORDER BY follower_count DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {                
                JSONObject object = new JSONObject();
                object.put(Appconstants.USERNAME, rs.getString("username"));
                object.put(Appconstants.NAME, rs.getString("name"));
                object.put(Appconstants.MOBILE, rs.getString("mobile"));
                object.put(Appconstants.EMAIL, rs.getString("email"));
                object.put(Appconstants.ADDRESS, rs.getString("address"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.FOLLOWERS, rs.getString("follower_count"));
                array.add(object);
            }
        } finally {
           new SupportClass().close(rs, ps, con); 
        }
        return array;
    }

    public boolean  alreadyAddedToFavourites(String username, int recipieId) throws ClassNotFoundException, SQLException {
        boolean alreadyAdded = false; 
        try{
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM favourite_info WHERE username = ? AND recipie_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, recipieId);
            rs = ps.executeQuery();
            if (rs.next()) {
                alreadyAdded = true;
            }
            
        } finally{
            new SupportClass().close(rs, ps, con);
        }
        return alreadyAdded;
    }

    public JSONArray viewFavourites(String username) throws ClassNotFoundException, SQLException {
       JSONArray array = new JSONArray();
        try{
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM recipie_info WHERE recipie_id IN (SELECT recipie_id FROM favourite_info WHERE username = ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {      
                String query2 = "SELECT name FROM user_info WHERE username = ?";
                ps = con.prepareStatement(query2);
                ps.setString(1, rs.getString("username"));
                ResultSet rs2 = ps.executeQuery();
                String name = "";
                if (rs2.next()) {
                    name = rs2.getString("name");
                }
                JSONObject object = new JSONObject();
                object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.CATEGORY, rs.getString("categories"));
                object.put(Appconstants.NAME, name);
                object.put(Appconstants.DATE, rs.getString("date"));
                array.add(object);
            }
        }finally{
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public String changePassword(LoginBean bean, String newPassword) throws ClassNotFoundException, SQLException {
        String status = Appconstants.FAILED;
        try{
            con = new DbConnection().getConnection();
            String query = "UPDATE login_info SET password = ? WHERE username = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, newPassword);
            ps.setString(2, bean.getUsername());
            ps.setString(3, bean.getPassword());
            int value = ps.executeUpdate();
            if (value > 0) {
                status = Appconstants.SUCCESS;
            }
        }finally{
            new SupportClass().close(ps, con);
        }
        return status;
    }
}
