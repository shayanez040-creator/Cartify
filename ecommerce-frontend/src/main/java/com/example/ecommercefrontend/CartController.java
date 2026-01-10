package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class CartController {

    @FXML
    private ListView<CartItem> cartListView;

    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {
        // Load cart items into list view
        cartListView.getItems().setAll(Cart.getItems());

        // Custom cell to show product name, qty, price
        cartListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(
                            item.getProduct().getName() +
                                    "  x" + item.getQuantity() +
                                    "  = Rs. " + item.getTotalPrice()
                    );
                }
            }
        });

        updateTotal();
    }

    private void updateTotal() {
        totalLabel.setText("Total: Rs. " + Cart.getTotal());
    }

    @FXML
    public void checkout() {
        System.out.println("Checkout successful!");
        System.out.println("Total Bill: Rs. " + Cart.getTotal());

        // Clear cart after checkout
        Cart.clear();
        cartListView.getItems().clear();
        updateTotal();
    }
}
