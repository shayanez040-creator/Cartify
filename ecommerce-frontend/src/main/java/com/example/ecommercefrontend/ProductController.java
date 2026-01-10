package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class ProductController {

    @FXML
    private TilePane productGrid;

    // ================= INITIALIZE =================
    @FXML
    public void initialize() {
        loadProducts().forEach(this::createProductCard);
    }

    // ================= PRODUCT CARD =================
    private void createProductCard(Product product) {

        // ---- IMAGE ----
        ImageView imageView = new ImageView(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/com/example/ecommercefrontend/images/" + product.getImage()
                                )
                        )
                )
        );
        imageView.setFitWidth(160);
        imageView.setFitHeight(160);
        imageView.setPreserveRatio(true);

        // ---- NAME ----
        Label name = new Label(product.getName());
        name.getStyleClass().add("product-name");

        // ---- PRICE ----
        Label price = new Label("Rs. " + product.getPrice());
        price.getStyleClass().add("product-price");

        // ---- ADD TO CART BUTTON ----
        Button addToCart = new Button("Add to Cart");
        addToCart.getStyleClass().add("add-cart-btn");
        addToCart.setOnAction(e -> Cart.addProduct(product));

        // ---- DETAILS (NEECHAY) ----
        VBox details = new VBox(name, price, addToCart);
        details.setSpacing(6);
        details.setAlignment(Pos.CENTER);

        // ---- CARD (IMAGE + DETAILS) ----
        VBox card = new VBox(imageView, details);
        card.setSpacing(12);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("product-card");

        productGrid.getChildren().add(card);
    }

    // ================= PRODUCTS =================
    private List<Product> loadProducts() {
        return List.of(
                new Product(1, "iPhone 14", 350000, "iphone14.jpg"),
                new Product(2, "Mens T-Shirt", 1500, "tshirt.jpg"),
                new Product(3, "Lipstick Set", 1500, "lipstick.jpg"),
                new Product(4, "Football", 2500, "football.jpg"),
                new Product(5, "Home Vase Decor", 3000, "vase.jpg"),
                new Product(6, "Teddy Bear Large", 3000, "teddy.jpg"),
                new Product(7, "Wireless Headphones", 8500, "headphones.jpg"),
                new Product(8, "Running Shoes", 12000, "shoes.jpg"),
                new Product(9, "Smart Watch", 22000, "watch.jpg"),
                new Product(10, "Gaming Mouse", 4500, "mouse.jpg"),
                new Product(11, "Bluetooth Speaker", 9800, "speaker.jpg"),
                new Product(12, "Leather Wallet", 3200, "wallet.jpg")
        );
    }

    // ================= OPEN CART =================
    @FXML
    private void openCart() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("cart-view.fxml")
            );
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 450, 350));
            stage.setTitle("Your Cart");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

