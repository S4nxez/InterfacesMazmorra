module org.example.pesadillamago {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens org.example.pesadillamago to javafx.fxml;
    exports org.example.pesadillamago;
    opens org.example.pesadillamago.game.dungeon.home to javafx.fxml;
    exports org.example.pesadillamago.ui.marcos;
    opens org.example.pesadillamago.ui.marcos to javafx.fxml;
}