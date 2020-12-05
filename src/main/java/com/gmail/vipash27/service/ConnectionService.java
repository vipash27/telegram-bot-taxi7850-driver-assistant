package com.gmail.vipash27.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private static ConnectionService instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/testDB?useSSL=false";
    private final String USERNAME = "root";
    private final String PASSWORD = "726030";

    private ConnectionService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionService getInstance() {
        if (instance == null) {
            instance = new ConnectionService();
        }
        return instance;
    }

    public Connection getConnection() {
        return  connection;
    }

}
