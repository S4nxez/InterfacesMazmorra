module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.pesadillamago to javafx.fxml;
    exports org.example.pesadillamago;
}