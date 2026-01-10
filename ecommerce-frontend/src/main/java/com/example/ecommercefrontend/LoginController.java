package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // ================= LOGIN =================
    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter username and password");
            return;
        }

        String sql = "SELECT role FROM users WHERE username=? AND password=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");

                if (role.equalsIgnoreCase("admin")) {
                    openAdminPanel();
                } else {
                    openProductScreen();
                }

            } else {
                messageLabel.setText("Invalid username or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Database error");
        }
    }

    // ================= REGISTER SCREEN =================
    @FXML
    private void openRegister() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("register-view.fxml")
            );

            Scene scene = new Scene(loader.load(), 400, 500);
            scene.getStylesheets().add(
                    HelloApplication.class
                            .getResource("cartify-dark.css")
                            .toExternalForm()
            );

            stage.setTitle("Cartify - Register");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= PRODUCT SCREEN =================
    private void openProductScreen() throws Exception {

        Stage stage = (Stage) usernameField.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("product-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(
                HelloApplication.class
                        .getResource("cartify-dark.css")
                        .toExternalForm()
        );

        stage.setTitle("Cartify - Products");
        stage.setScene(scene);
        stage.show();
    }

    // ================= ADMIN PANEL =================
    private void openAdminPanel() throws Exception {

        Stage stage = (Stage) usernameField.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("admin-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 500);
        scene.getStylesheets().add(
                HelloApplication.class
                        .getResource("cartify-dark.css")
                        .toExternalForm()
        );

        stage.setTitle("Cartify - Admin Panel");
        stage.setScene(scene);
        stage.show();
    }
}
