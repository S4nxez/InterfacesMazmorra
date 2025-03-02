package org.example.pesadillamago.game.dungeon.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.pesadillamago.dao.DaoPlayer;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.example.pesadillamago.game.spell.SpellUnknowableException;
import org.example.pesadillamago.game.util.ValueOverMaxException;

import java.io.IOException;

public class HomeLibraryController {

    @Setter
    private Stage stage;
    private final DaoPlayer daoPlayer;

    public HomeLibraryController(DaoPlayer daoPlayer) {
        this.daoPlayer = daoPlayer;
    }

    public HomeLibraryController() {
        this.daoPlayer = new DaoPlayer();
    }

    @FXML
    public void handleManageSpells(ActionEvent actionEvent) {

    }
    @FXML
    public void handleManageStorage(ActionEvent actionEvent) {

    }

    @FXML
    public void handleImproveSpell(ActionEvent actionEvent) {
        try {
            daoPlayer.handleImproveSpell();
        } catch (ValueOverMaxException e) {

        }
    }
    @FXML
    public void handleLearnNewSpell(ActionEvent actionEvent) {
        try {
            daoPlayer.handleLearnNewSpell();
        } catch (SpellUnknowableException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/org/example/pesadillamago/mainHomeInterface.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HomeController homeController = fxmlLoader.getController();
            homeController.setStage(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDeleteFromStorage(ActionEvent actionEvent) {
        daoPlayer.handleDeleteFromStorage();
    }
    @FXML
    public void handleDeleteFromJewelryBag(ActionEvent actionEvent) {
        daoPlayer.handleDeleteFromJewelryBag();
    }
    @FXML
    public void handleTakeFromStorage(ActionEvent actionEvent) {
        try {
            daoPlayer.handleTakeFromStorage();
        } catch (ContainerUnacceptedItemException e) {

        } catch (ContainerFullException e) {

        }
    }
    @FXML
    public void handleTakeFromJewelryBag(ActionEvent actionEvent) {
        try {
            daoPlayer.handleTakeFromJewelryBag();
        } catch (ContainerUnacceptedItemException e) {

        } catch (ContainerFullException e) {

        }
    }
    @FXML
    public void handleLeaveInChest(ActionEvent actionEvent) {
        try {
            daoPlayer.handleLeaveInChest();
        } catch (ContainerUnacceptedItemException e) {

        } catch (ContainerFullException e) {

        }
    }
    @FXML
    public void handleLeaveInJewelryBag(ActionEvent actionEvent) {
        try {
            daoPlayer.handleLeaveInJewelryBag();
        } catch (ContainerUnacceptedItemException e) {

        } catch (ContainerFullException e) {

        }
    }
    @FXML
    public void handleExchangeWizardChest(ActionEvent actionEvent) {
        try {
            daoPlayer.handleExchangeWizardChest();
        } catch (ContainerUnacceptedItemException e) {
        } catch (ContainerFullException e) {
        }
    }
    @FXML
    public void handleExchangeWizardJewelryBag(ActionEvent actionEvent) {
        try {
            daoPlayer.handleExchangeWizardJewelryBag();
        } catch (ContainerUnacceptedItemException e) {
        } catch (ContainerFullException e) {
        }
    }
}
