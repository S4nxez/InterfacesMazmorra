module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires static lombok;
    requires com.google.gson;

    opens org.example.pesadillamago.game.dungeon.home to javafx.fxml;
    exports org.example.pesadillamago.ui;
    opens org.example.pesadillamago.ui to javafx.fxml;
}