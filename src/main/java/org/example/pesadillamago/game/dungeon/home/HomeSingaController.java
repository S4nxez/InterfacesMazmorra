package org.example.pesadillamago.game.dungeon.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.pesadillamago.dao.DaoPlayer;
import org.example.pesadillamago.game.character.exceptions.WizardNotEnoughEnergyException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.dungeon.HomeNotEnoughSingaException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerEmptyException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerErrorException;

import java.io.IOException;

public class HomeSingaController {
    private final DaoPlayer daoPlayer;

    public HomeSingaController(DaoPlayer daoPlayer) {
        this.daoPlayer = daoPlayer;
    }

    @FXML
    public void handleMergeSingaCrystal(ActionEvent actionEvent) {
        try {
            daoPlayer.handleMergeSingaCrystal(0);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (WizardTiredException e) {
            throw new RuntimeException(e);
        } catch (ContainerErrorException e) {
            throw new RuntimeException(e);
        } catch (ContainerEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleUpgradeMaxLife(ActionEvent actionEvent) {
        try {
            daoPlayer.upgradeLifeMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeMaxEnergy(ActionEvent actionEvent) {
        try {
            daoPlayer.upgradeEnergyMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeHomeComfort(ActionEvent actionEvent) {
        try {
            daoPlayer.upgradeComfort();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpgradeStoneCapacity(ActionEvent actionEvent) {
        try {
            daoPlayer.upgradeSingaMax();
        } catch (Exception | HomeNotEnoughSingaException | WizardNotEnoughEnergyException | WizardTiredException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExitMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/mainHomeInterface.fxml"));
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
    public void handleMergeCrystals(ActionEvent actionEvent) {


    }
    @FXML
    public void handleUpgradeCharacteristics(ActionEvent actionEvent) {
    }
    @FXML
    public void handleEndMerge(ActionEvent actionEvent) {

    }
}