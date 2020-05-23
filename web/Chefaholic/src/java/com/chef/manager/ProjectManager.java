package com.chef.manager;

import com.chef.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ProjectManager {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public String getSystemdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    public int getRecipieId() throws ClassNotFoundException, SQLException {
        int id = 0;
        Random random = new Random();
        id = random.nextInt(5000);
        con = new DbConnection().getConnection();
        String query = "SELECT recipie_id FROM recipie_info WHERE recipie_id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            getRecipieId();
        }
        return id;
    }
    public String formatDate(String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date parse = sdf.parse(date);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(parse);
        return format;
    }
}
