package org.example.pesadillamago.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.util.Duration;
import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Attack;
import org.example.pesadillamago.game.character.*;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.objectContainer.CrystalCarrier;
import org.example.pesadillamago.game.objectContainer.JewelryBag;
import org.example.pesadillamago.game.objectContainer.Wearables;
import org.example.pesadillamago.game.spell.*;

public class GameCont {

    @FXML
    private ImageView monsterImage;

    @FXML
    private ProgressBar monsterHealthBar;

    @FXML
    private Label monsterHealthLabel;

    @FXML
    private ProgressBar playerHealthBar;

    @FXML
    private Label playerHealthLabel;

    @FXML
    private ProgressBar playerEnergyBar;

    @FXML
    private Label playerEnergyLabel;

    @FXML
    private Button fireAttackBtn;

    @FXML
    private Button electricAttackBtn;

    @FXML
    private Button airAttackBtn;

    @FXML
    private Label resultLabel;

    private Wizard player;
    private Creature monster;
    private FireAttack fireAttack;
    private ElectricAttack electricAttack;
    private AirAttack airAttack;

    private final int ATTACK_ENERGY_COST = 10;

    @FXML
    public void initialize() {
        // Inicializar el jugador (Wizard)
        // Crear contenedores con capacidades adecuadas
        // Wearables(int maxWeapons, int maxNecklaces, int maxRings)
        Wearables wearables = new Wearables(1, 2, 3);
        // CrystalCarrier(int capacity)
        CrystalCarrier crystalCarrier = new CrystalCarrier(10);
        // JewelryBag(int capacity)
        JewelryBag jewelryBag = new JewelryBag(10);

        player = new Wizard("Mago", 100, 100, 100, 100, wearables, crystalCarrier, jewelryBag);

        // Inicializar el monstruo (Creature)
        monster = new Creature("Monstruo Oscuro", 100, 15, Domain.DARKNESS);

        // Inicializar los ataques
        fireAttack = new FireAttack();
        electricAttack = new ElectricAttack();
        airAttack = new AirAttack();

        try {
            player.addSpell(fireAttack);
            player.addSpell(electricAttack);
            player.addSpell(airAttack);
        } catch (SpellUnknowableException e) {
            System.err.println("Error al añadir hechizos: " + e.getMessage());
        }

        // Configurar las barras de vida y energía
        updateHealthBars();
    }

    private void updateHealthBars() {
        // Actualizar barra de vida del jugador
        double playerHealthRatio = (double) player.getLife() / player.getLifeMax();
        playerHealthBar.setProgress(playerHealthRatio);
        playerHealthLabel.setText(player.getLife() + "/" + player.getLifeMax());

        // Actualizar barra de energía del jugador
        double playerEnergyRatio = (double) player.getEnergy() / player.getEnergyMax();
        playerEnergyBar.setProgress(playerEnergyRatio);
        playerEnergyLabel.setText(player.getEnergy() + "/" + player.getEnergyMax());

        // Actualizar barra de vida del monstruo
        double monsterHealthRatio = (double) monster.getLife() / monster.getLifeMax();
        monsterHealthBar.setProgress(monsterHealthRatio);
        monsterHealthLabel.setText(monster.getLife() + "/" + monster.getLifeMax());

        // Verificar si los botones deben estar habilitados (basado en la energía)
        boolean hasEnoughEnergy = player.hasEnoughEnergy(ATTACK_ENERGY_COST);
        fireAttackBtn.setDisable(!hasEnoughEnergy);
        electricAttackBtn.setDisable(!hasEnoughEnergy);
        airAttackBtn.setDisable(!hasEnoughEnergy);
    }

    @FXML
    private void onFireAttack() {
        performPlayerAttack(fireAttack);
    }

    @FXML
    private void onElectricAttack() {
        performPlayerAttack(electricAttack);
    }

    @FXML
    private void onAirAttack() {
        performPlayerAttack(airAttack);
    }

    private void performPlayerAttack(Attack attack) {
        // Deshabilitar botones durante el ataque
        setAttackButtonsDisabled(true);

        try {
            // Consumir energía
            player.drainEnergy(ATTACK_ENERGY_COST);

            // Realizar ataque
            attack.attack(monster);

            // Mostrar efecto visual (opcional)
            monsterImage.setOpacity(0.5);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> monsterImage.setOpacity(1.0)));
            timeline.play();

            // Actualizar barras de vida
            updateHealthBars();

            // Verificar si el monstruo ha sido derrotado
            if (monster.getLife() <= 0) {
                showResult("¡Has derrotado al monstruo!");
                return;
            }

            // El monstruo contraataca después de un breve retraso
            Timeline monsterAttackTimeline = new Timeline(new KeyFrame(Duration.millis(800), event -> monsterAttack()));
            monsterAttackTimeline.play();

        } catch (CharacterKilledException e) {
            // El monstruo ha sido derrotado
            showResult("¡Has derrotado al monstruo!");
        } catch (WizardTiredException e) {
            // El mago está demasiado cansado
            showResult("¡Estás demasiado cansado para atacar!");
            setAttackButtonsDisabled(false);
        }
    }

    private void monsterAttack() {
        try {
            // El monstruo elige un ataque aleatorio
            Attack monsterAttack = monster.getRandomAttack();

            // Realizar ataque
            monsterAttack.attack(player);

            // Mostrar efecto visual (opcional)
            playerHealthBar.setStyle("-fx-accent: darkred;");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> playerHealthBar.setStyle("-fx-accent: green;")));
            timeline.play();

            // Actualizar barras de vida
            updateHealthBars();

            // Verificar si el jugador ha sido derrotado
            if (player.getLife() <= 0) {
                showResult("¡Has sido derrotado!");
                return;
            }

            // Habilitar botones después del ataque
            setAttackButtonsDisabled(false);

        } catch (CharacterKilledException e) {
            // El jugador ha sido derrotado
            showResult("¡Has sido derrotado!");
        }
    }

    private void setAttackButtonsDisabled(boolean disabled) {
        fireAttackBtn.setDisable(disabled);
        electricAttackBtn.setDisable(disabled);
        airAttackBtn.setDisable(disabled);
    }

    private void showResult(String message) {
        resultLabel.setText(message);
        resultLabel.setVisible(true);
        setAttackButtonsDisabled(true);
    }
}