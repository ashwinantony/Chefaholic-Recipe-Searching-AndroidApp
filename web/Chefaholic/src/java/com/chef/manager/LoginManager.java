package com.chef.manager;

import com.chef.bean.LoginBean;
import com.chef.db.DbConnection;
import com.chef.util.Appconstants;
import com.chef.util.SupportClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public String login(LoginBean bean) throws ClassNotFoundException, SQLException {
        String type = Appconstants.INVALID;
        try {
            con = new DbConnection().getConnection();
            String query = "SELECT type FROM login_info WHERE BINARY username = ? AND BINARY password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getUsername());
            ps.setString(2, bean.getPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                type = rs.getString("type");
            }

        } finally {
            new SupportClass().close(rs, ps, con);
        }
        return type;
    }
}
