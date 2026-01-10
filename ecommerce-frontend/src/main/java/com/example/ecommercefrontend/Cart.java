package com.example.ecommercefrontend;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static final List<CartItem> items = new ArrayList<>();

    public static void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.increment();
                return;
            }
        }
        items.add(new CartItem(product));
    }

    public static List<CartItem> getItems() {
        return items;
    }

    public static double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static void clear() {
        items.clear();
    }
}
