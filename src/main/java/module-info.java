module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports org.example.pesadillamago;
    exports org.example.pesadillamago.ui;
    opens org.example.pesadillamago.ui to javafx.fxml;
}
