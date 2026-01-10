package com.example.ecommercefrontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductStore {

    // Shared product list (Admin + Products page dono use karenge)
    public static ObservableList<Product> products =
            FXCollections.observableArrayList();

}
