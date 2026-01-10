module com.example.ecommercefrontend {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.dlsc.formsfx;

    opens com.example.ecommercefrontend to javafx.fxml;
    exports com.example.ecommercefrontend;
}
