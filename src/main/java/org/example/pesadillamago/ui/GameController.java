package org.example.pesadillamago.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GameController {

    @FXML
    private TextArea mainArea;
    @FXML private Button northBtn;
    @FXML private Button southBtn;
    @FXML private Button eastBtn;
    @FXML private Button westBtn;
    @FXML private Button fightBtn;
    @FXML private Button fleeBtn;
    @FXML private ListView<String> itemsList;
    @FXML private ListView<String> spellsList;

    private int currentHP = 100;
    private int maxHP = 100;

    @FXML
    public void initialize() {
        updateHP(currentHP, maxHP);
    }

    @FXML
    private void handleDirection(ActionEvent event) {
        Button button = (Button) event.getSource();
        String direction = button.getText();
    }

    @FXML
    private void handleFight(ActionEvent event) {
    }

    @FXML
    private void handleFlee(ActionEvent event) {
    }

    private void updateHP(int current, int max) {
    }
}