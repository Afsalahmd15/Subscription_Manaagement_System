package com.odoo.brightloop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
  
    private static final String URL = "jdbc:postgresql://localhost:5432/brightloop";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection(){

        try {

            Connection conn =DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection Succesfull");
            return conn;
        }catch(SQLException e){

            e.printStackTrace();
            return null;
        }
    }
}
