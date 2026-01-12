package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CartController {

    @FXML
    private ListView<CartItem> cartListView;

    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {
        cartListView.getItems().setAll(Cart.getItems());

        cartListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(
                            item.getProduct().getName()
                                    + " x" + item.getQuantity()
                                    + " = Rs. " + item.getTotalPrice()
                    );
                }
            }
        });

        updateTotal();
    }

    private void updateTotal() {
        totalLabel.setText("Total: Rs. " + Cart.getTotal());
    }

    // ============================
    // CHECKOUT → SAVE ORDER IN DB
    // ============================
    @FXML
    public void checkout() {

        try {
            Connection con = DBUtil.getConnection();
            con.setAutoCommit(false);

            // INSERT ORDER
            String orderSQL =
                    "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";

            PreparedStatement orderStmt =
                    con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);

            orderStmt.setInt(1, 1); // customer_id from DB
            orderStmt.setDouble(2, Cart.getTotal());
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            // INSERT ORDER ITEMS
            String itemSQL =
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

            PreparedStatement itemStmt = con.prepareStatement(itemSQL);

            for (CartItem item : Cart.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProduct().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getProduct().getPrice());
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            con.commit();

            Cart.clear();
            cartListView.getItems().clear();
            updateTotal();

            System.out.println("✅ ORDER SAVED SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
