package com.example.ecommercefrontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminController {

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadProductsFromDB();
    }

    // ================= LOAD PRODUCTS =================
    private void loadProductsFromDB() {

        productList.clear();

        String sql = "SELECT product_id, name, price FROM products";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }

            table.setItems(productList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE PRODUCT =================
    @FXML
    private void deleteProduct() {

        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String sql = "DELETE FROM products WHERE product_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, selected.getId());
            ps.executeUpdate();

            productList.remove(selected);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
