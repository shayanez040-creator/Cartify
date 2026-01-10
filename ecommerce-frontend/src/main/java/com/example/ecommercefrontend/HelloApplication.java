package com.example.ecommercefrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("login-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 420, 650);

        // ✅ APPLY DARK THEME
        scene.getStylesheets().add(
                HelloApplication.class
                        .getResource("cartify-dark.css")
                        .toExternalForm()
        );

        // ✅ ADD CARTIFY LOGO TO WINDOW (HERE 👇)
        stage.getIcons().add(
                new Image(
                        HelloApplication.class.getResourceAsStream(
                                "/com/example/ecommercefrontend/images/cartify-logo.jpg"
                        )
                )
        );

        stage.setTitle("Cartify - Login");
        stage.setScene(scene);

        // ❗ MUST BE LAST
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
