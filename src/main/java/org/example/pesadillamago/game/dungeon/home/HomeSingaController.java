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
import org.example.pesadillamago.game.character.exceptions.WizardNotEnoughEnergyException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.dungeon.HomeNotEnoughSingaException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerEmptyException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerErrorException;

import java.io.IOException;

public class HomeSingaController {
    private final DaoPlayer daoPlayer;

    @Setter
    private Stage stage;

    public HomeSingaController(DaoPlayer daoPlayer) {
        this.daoPlayer = daoPlayer;
    }

    @FXML
    public void handleMergeSingaCrystal() {
        try {
            daoPlayer.handleMergeSingaCrystal(0);
        } catch (WizardTiredException | ContainerErrorException | ContainerEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleUpgradeMaxLife() {
        try {
            daoPlayer.upgradeLifeMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeMaxEnergy() {
        try {
            daoPlayer.upgradeEnergyMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeHomeComfort() {
        try {
            daoPlayer.upgradeComfort();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeStoneCapacity() {
        try {
            daoPlayer.upgradeSingaMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExitMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/mainHomeInterface.fxml"));
            Scene scene = new Scene(loader.load());

            HomeController homeController = loader.getController();
            homeController.setStage(stage); 

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMergeCrystals(ActionEvent actionEvent) {
    }

    @FXML
    public void handleUpgradeCharacteristics(ActionEvent actionEvent) {
    }
    @FXML
    public void handleEndMerge(ActionEvent actionEvent) {

    }
}