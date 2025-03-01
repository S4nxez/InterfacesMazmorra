package org.example.pesadillamago.game.dungeon.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.pesadillamago.dao.DaoPlayer;
import org.example.pesadillamago.game.util.ValueOverMaxException;
import org.example.pesadillamago.ui.HelloApplication;

import java.io.IOException;

public class HomeController {
    private final DaoPlayer daoPlayer;

    @Setter
    private Stage stage;

    public HomeController() {
        this.daoPlayer = new DaoPlayer();
    }

    @FXML
    public void handleManageSinga(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/singaHomeInterface.fxml"));
            loader.setControllerFactory(param -> new HomeSingaController(daoPlayer));
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
    public void handleGoToLibrary(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/libraryHomeInterface.fxml"));
            loader.setControllerFactory(param -> new HomeLibraryController(daoPlayer));
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
    public void handleExitGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleGoToDungeon(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/pesadillamago/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleSleep(ActionEvent actionEvent) {
        daoPlayer.handleSleep();
    }
    @FXML
    public void handleRecoverLife(ActionEvent actionEvent) {
        try {
            daoPlayer.handleRecoverLife();
        } catch (ValueOverMaxException e) {

        }
    }
}
