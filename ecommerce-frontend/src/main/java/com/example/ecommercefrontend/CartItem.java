package com.example.ecommercefrontend;

public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment() {
        quantity++;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
