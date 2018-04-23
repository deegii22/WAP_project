package com.service;

import java.sql.*;

public class DBSingleton {

    private static DBSingleton instance = new DBSingleton();

    private DBSingleton()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public static DBSingleton getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cycling_club","root","root");
        return connection;
    }
}
