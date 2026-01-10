package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.DB;

import java.io.*;
import java.sql.*;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String body = br.readLine(); // username=user&password=123

        String[] parts = body.split("&");
        String username = parts[0].split("=")[1];
        String password = parts[1].split("=")[1];

        boolean ok = false;

        try {
            Connection con = DB.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) ok = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = ok ? "{\"status\":\"ok\"}" : "{\"status\":\"fail\"}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
