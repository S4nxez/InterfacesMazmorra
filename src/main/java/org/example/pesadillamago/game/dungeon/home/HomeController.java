package org.example.pesadillamago.game.dungeon.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public void handleReturnToDungeon(ActionEvent actionEvent) {
    }
    @FXML
    public void handleManageSinga(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/singaHomeInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleRest(ActionEvent actionEvent) {
    }
    @FXML
    public void handleGoToLibrary(ActionEvent actionEvent) {
    }

    @FXML
    public void handleExitGame(ActionEvent actionEvent) {
    }
}
