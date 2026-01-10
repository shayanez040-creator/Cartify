package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.DB;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        ArrayList<String> products = new ArrayList<>();

        try {
            Connection con = DB.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT product_id, name, price, image FROM products"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String json =
                        "{ \"id\": " + rs.getInt("product_id") +
                                ", \"name\": \"" + rs.getString("name") + "\"" +
                                ", \"price\": " + rs.getDouble("price") +
                                ", \"image\": \"" + rs.getString("image") + "\" }";

                products.add(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = products.toString();

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
