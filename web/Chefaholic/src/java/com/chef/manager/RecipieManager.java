/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chef.manager;

import com.chef.bean.RecipieBean;
import com.chef.bean.SortingBean;
import com.chef.db.DbConnection;
import com.chef.util.Appconstants;
import com.chef.util.SupportClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Staff
 */
public class RecipieManager {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public String addRecipie(RecipieBean bean, String path) throws ClassNotFoundException, SQLException {
        String status = Appconstants.FAILED;
        try {
            String systemdate = new ProjectManager().getSystemdate();
            int recipieId = new ProjectManager().getRecipieId();
            con = new DbConnection().getConnection();
            String query = "INSERT INTO recipie_info VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, recipieId);
            ps.setString(2, bean.getUsername());
            ps.setString(3, bean.getFoodName());
            ps.setString(4, bean.getIngredients());
            ps.setString(5, bean.getMaking());
            ps.setString(6, bean.getCategories());
            ps.setString(7, path);
            ps.setString(8, systemdate);
            int value = ps.executeUpdate();
            if (value > 0) {
                status = Appconstants.SUCCESS;
            }
        } finally {
            new SupportClass().close(ps, con);
        }
        return status;
    }

    public JSONObject getRecipieDetails(int recipieId) throws ClassNotFoundException, SQLException {
        JSONObject object = new JSONObject();
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM recipie_info WHERE recipie_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, recipieId);
            rs = ps.executeQuery();
            if (rs.next()) {
                object.put(Appconstants.RECIPIE_ID, recipieId);
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.INGREDIENTS, rs.getString("ingredients"));
                object.put(Appconstants.MAKING, rs.getString("making"));
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return object;
    }

    public JSONArray getAllRecipie(String username) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "select * from recipie_info join user_info on "
                    + "recipie_info.username = user_info.username where recipie_info.username in "
                    + "(select username from following_info where follower_username = ?) "
                    + "ORDER BY recipie_info.date DESC, follower_count DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.CATEGORY, rs.getString("categories"));
                object.put(Appconstants.NAME, rs.getString("name"));
                object.put(Appconstants.DATE, rs.getString("date"));
                array.add(object);
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public JSONArray getAdminAddedRecipie() throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT recipie_id, food_name, pic, categories, date FROM recipie_info WHERE username = ? ORDER BY date DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, Appconstants.ADMIN);
            rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.CATEGORY, rs.getString("categories"));
                object.put(Appconstants.DATE, rs.getString("date"));
                object.put(Appconstants.ADMIN, Appconstants.ADMIN);
                array.add(object);
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;

    }

    public JSONArray viewUsersRecipie(String username) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT recipie_id, food_name, pic, categories, date FROM recipie_info WHERE username = ? ORDER BY date DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.CATEGORY, rs.getString("categories"));
                object.put(Appconstants.DATE, rs.getString("date"));
                array.add(object);
            }
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public JSONArray searchUsingDate(String date, String username) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query1 = "SELECT * FROM recipie_info join following_info on following_info.username = recipie_info.username where recipie_info.date BETWEEN DATE_SUB(?, INTERVAL 5 DAY) and DATE_ADD(?, INTERVAL 5 DAY) and following_info.follower_username = ?";
            ps = con.prepareStatement(query1);
            ps.setString(1, date);
            ps.setString(2, date);
            ps.setString(3, username);
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
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public JSONArray searchUsingTags(String[] tagsArray, String username) throws ClassNotFoundException, SQLException {
       JSONArray array = null;
        ArrayList<SortingBean> list = new ArrayList<SortingBean>();
        int arraySize = tagsArray.length;
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT * FROM recipie_info join following_info on recipie_info.username = following_info.username where following_info.follower_username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                int count = 0;
                String ingredients = rs.getString("ingredients").toLowerCase();
                boolean flag = false;
                for (String tags : tagsArray) {
                    if (ingredients.contains(tags.toLowerCase())) {
                        flag = true;
                        count = count + 1;
                    }
                }
                if (flag) {
                    SortingBean bean = new SortingBean();
                    bean.setRecipieId(Integer.parseInt(rs.getString("recipie_id")));
                    bean.setCount(count);
                    list.add(bean);
                }
            }
             array = sortUserRecipies(list, arraySize);
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public JSONArray searchAdminRecipieUsingTags(String[] tagsArray) throws ClassNotFoundException, SQLException {
        ArrayList<SortingBean> list = new ArrayList<SortingBean>();
        JSONArray array = null;
        try {
            int arraySize = tagsArray.length;
            con = new DbConnection().getConnection();
            String query = "SELECT recipie_id, ingredients FROM recipie_info where username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Appconstants.ADMIN);
            rs = ps.executeQuery();
            while (rs.next()) {
                int count = 0;

                String ingredients = rs.getString("ingredients").toLowerCase();
                boolean flag = false;
                for (String tags : tagsArray) {
                    if (ingredients.contains(tags.toLowerCase())) {
                        flag = true;
                        count = count + 1;
                    }
                }
                if (flag) {
                    SortingBean bean = new SortingBean();
                    bean.setRecipieId(Integer.parseInt(rs.getString("recipie_id")));
                    bean.setCount(count);
                    list.add(bean);
                }

            }
            array = sortAdminRecipies(list, arraySize);
        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return array;
    }

    public JSONArray searchAdminRecipieUsingDate(String date) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        try {
            con = new DbConnection().getConnection();
            String query1 = "SELECT * FROM recipie_info where date BETWEEN DATE_SUB(?, INTERVAL 5 DAY) and DATE_ADD(?, INTERVAL 5 DAY) and username = ?";
            ps = con.prepareStatement(query1);
            ps.setString(1, date);
            ps.setString(2, date);
            ps.setString(3, Appconstants.ADMIN);
            rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                object.put(Appconstants.PIC, rs.getString("pic"));
                object.put(Appconstants.CATEGORY, rs.getString("categories"));
                object.put(Appconstants.ADMIN, Appconstants.ADMIN);
                object.put(Appconstants.DATE, rs.getString("date"));
                array.add(object);
            }
        } finally {
        }
        return array;
    }

    private JSONArray sortAdminRecipies(ArrayList<SortingBean> list, int arraySize) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        con = new DbConnection().getConnection();
        for (int i = arraySize; i > 0; i--) {
            for (SortingBean bean : list) {
                if (bean.getCount() == i) {
                    String query = "SELECT * FROM recipie_info WHERE recipie_id = ?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, bean.getRecipieId());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        JSONObject object = new JSONObject();
                        object.put(Appconstants.RECIPIE_ID, rs.getString("recipie_id"));
                        object.put(Appconstants.FOOD_NAME, rs.getString("food_name"));
                        object.put(Appconstants.PIC, rs.getString("pic"));
                        object.put(Appconstants.CATEGORY, rs.getString("categories"));
                        object.put(Appconstants.DATE, rs.getString("date"));
                        object.put(Appconstants.ADMIN, Appconstants.ADMIN);
                        array.add(object);
                    }
                }
            }
        }
        return array;
    }

    private JSONArray sortUserRecipies(ArrayList<SortingBean> list, int arraySize) throws ClassNotFoundException, SQLException {
        JSONArray array = new JSONArray();
        con = new DbConnection().getConnection();
        for (int i = arraySize; i > 0; i--) {
            for (SortingBean bean : list) {
                if (bean.getCount() == i) {
                    String query = "SELECT * FROM recipie_info WHERE recipie_id = ?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, bean.getRecipieId());
                    rs = ps.executeQuery();
                    if (rs.next()) {
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
                        object.put(Appconstants.DATE, rs.getString("date"));
                        object.put(Appconstants.NAME, name);
                        array.add(object);
                    }
                }
            }
        }
        return array;
    }
}
