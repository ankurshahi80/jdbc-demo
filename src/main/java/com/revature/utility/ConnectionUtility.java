package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection; // JDBC interface from the java.sql package
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    private ConnectionUtility(){
    }

    public static Connection getConnection() throws SQLException {

        DriverManager.registerDriver(new Driver());

        String url = System.getenv("db_url");
        String username = System.getenv("db_username");
        String password = System.getenv("db_password");

//        String url = "jdbc:postgresql://localhost:5432/postgres";
//        String username="postgres";
//        String password="Nyr@2021";

        Connection connection = DriverManager.getConnection(url, username,password);

        return connection;
    }
}
