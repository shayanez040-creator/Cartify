package com.example.ecommercefrontend;

public class Product {

    private int id;
    private String name;
    private double price;
    private String image;

    // 🔹 EXISTING (4 params) – product screen ke liye
    public Product(int id, String name, double price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    // 🔹 NEW (3 params) – admin panel ke liye ✅
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // ================= GETTERS =================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
