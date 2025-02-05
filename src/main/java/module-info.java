module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;

    opens org.example.pesadillamago.game.dungeon.home to javafx.fxml;
    exports org.example.pesadillamago.ui.marcos;
    opens org.example.pesadillamago.ui.marcos to javafx.fxml;
}
