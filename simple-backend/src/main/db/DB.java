package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/ecommerce";
            String user = "root";
            String pass = "root"; // change this

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
