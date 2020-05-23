/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chef.db;


import com.chef.util.Appconstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Staff
 */
public class DbConnection {
    Connection con = null;
    public void createConnection() throws ClassNotFoundException, SQLException{
        Class.forName(Appconstants.DRIVER);
        con = DriverManager.getConnection(Appconstants.URL, Appconstants.MYSQL_USERNAME, Appconstants.MYSQL_PASSWORD);
    }
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        if (con == null) {
            createConnection();
        }
        return con;
    }
}
