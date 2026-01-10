package com.example.ecommercefrontend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";   // change if needed
    private static final String PASS = "root";       // change if you set password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
