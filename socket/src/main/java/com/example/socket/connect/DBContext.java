package com.example.socket.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    public static Connection getConnect(){
        String hostName = "127.0.0.1:3306";
        String dbName = "onlineshop";
        String username = "root";
        String password = "";
        String connectionURL = "jdbc:mysql://"+hostName+"/"+dbName;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionURL, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("thất bại");
        }
        return conn;
    }
}
