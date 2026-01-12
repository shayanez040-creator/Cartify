package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    // ==============================
    // ADVANCED CHECKOUT WITH SAFETY
    // ==============================
    @FXML
    public void checkout() {

        try (Connection con = DBUtil.getConnection()) {

            // 1️⃣ Start transaction
            con.setAutoCommit(false);

            // 2️⃣ Insert order
            int orderId = insertOrder(con);

            // 3️⃣ Insert order items + update stock
            insertOrderItemsAndUpdateStock(con, orderId);

            // 4️⃣ Commit if everything succeeded
            con.commit();

            // 5️⃣ Clear cart
            Cart.clear();
            cartListView.getItems().clear();
            updateTotal();

            showInfo("Success", "Order placed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Order Failed", "Something went wrong while placing order.");
        }
    }

    // ------------------------------
    // Helper Methods
    // ------------------------------

    private int insertOrder(Connection con) throws Exception {
        String sql =
                "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";

        PreparedStatement ps =
                con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, 1); // customer_id
        ps.setDouble(2, Cart.getTotal());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    private void insertOrderItemsAndUpdateStock(Connection con, int orderId)
            throws Exception {

        String itemSql =
                "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        String stockSql =
                "UPDATE products SET stock = stock - ? WHERE product_id = ?";

        PreparedStatement itemStmt = con.prepareStatement(itemSql);
        PreparedStatement stockStmt = con.prepareStatement(stockSql);

        for (CartItem item : Cart.getItems()) {

            // Insert order item
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, item.getProduct().getId());
            itemStmt.setInt(3, item.getQuantity());
            itemStmt.setDouble(4, item.getProduct().getPrice());
            itemStmt.executeUpdate();

            // Update product stock
            stockStmt.setInt(1, item.getQuantity());
            stockStmt.setInt(2, item.getProduct().getId());
            stockStmt.executeUpdate();
        }
    }

    // ------------------------------
    // UI Alerts
    // ------------------------------

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
