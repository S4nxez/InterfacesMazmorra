module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens org.example.pesadillamago to javafx.fxml;
    exports org.example.pesadillamago;
}