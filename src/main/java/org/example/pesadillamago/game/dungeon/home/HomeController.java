package org.example.pesadillamago.game.dungeon.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

import org.example.pesadillamago.common.Constantes;

import org.example.pesadillamago.dao.DaoPlayer;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.util.ValueOverMaxException;

import org.example.pesadillamago.ui.GameController;
import org.example.pesadillamago.ui.HelloApplication;
import org.example.pesadillamago.ui.DemiurgeConsumer;
import org.example.pesadillamago.ui.GameController;


import java.io.IOException;

public class HomeController implements DemiurgeConsumer {
    private final DaoPlayer daoPlayer;

    @Setter
    private Stage stage;

    public HomeController() {
        this.daoPlayer = new DaoPlayer();
    }

    @FXML
    public void handleManageSinga(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/pesadillamago/singaHomeInterface.fxml"));
            fxmlLoader.setControllerFactory(param -> new HomeSingaController(daoPlayer));
            Scene scene = new Scene(fxmlLoader.load());

            HomeSingaController homeSingaController = fxmlLoader.getController();
            homeSingaController.setStage(stage);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGoToLibrary(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/pesadillamago/libraryHomeInterface.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GameController gc = fxmlLoader.getController();

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
        GameController gc = fxmlLoader.getController();

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

    @Override
    public void loadScreenData(Demiurge demiurge) {

    }
}
