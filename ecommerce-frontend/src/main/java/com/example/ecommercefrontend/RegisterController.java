package com.example.ecommercefrontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleRegister() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        // Validation
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            messageLabel.setText("All fields are required");
            return;
        }

        if (!password.equals(confirm)) {
            messageLabel.setText("Passwords do not match");
            return;
        }

        String sql =
                "INSERT INTO users (username, password, role) VALUES (?, ?, 'customer')";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            messageLabel.setStyle("-fx-text-fill: #4ade80;");
            messageLabel.setText("Registration successful!");

            usernameField.clear();
            passwordField.clear();
            confirmPasswordField.clear();

        } catch (SQLException e) {
            messageLabel.setStyle("-fx-text-fill: #ff4d4d;");
            messageLabel.setText("Username already exists");
        }
    }
}
