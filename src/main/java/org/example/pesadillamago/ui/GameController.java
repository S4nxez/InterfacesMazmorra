package org.example.pesadillamago.ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.pesadillamago.common.Constantes;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.demiurge.exceptions.EndGameException;
import org.example.pesadillamago.game.demiurge.exceptions.GoHomekException;

public class GameController implements DemiurgeConsumer {

    // Mantén estas anotaciones igual
    @FXML private AnchorPane screen;
    @FXML private ImageView creature;
    @FXML private Label infoLabel;
    @FXML private Label roomName;

    // Añade estos nuevos elementos del FXML
    @FXML private Button hechizosBtn;
    @FXML private Button inventarioBtn;
    @FXML private Button cofreBtn;
    @FXML private Button pelearBtn;

    @FXML
    public void initialize() {
        // Verifica que todos los componentes críticos estén presentes
        if (screen == null || creature == null || infoLabel == null || roomName == null) {
            throw new IllegalStateException("¡FXML no cargado correctamente! Verifica los fx:id");
        }

        configurarPantalla();
        configurarEventosTeclado();
        configurarBotones();
    }

    private void configurarBotones() {
        cofreBtn.setOnAction(e -> HelloApplication.cambiarPantalla(demiurge, "Constantes.CHEST"));
        pelearBtn.setOnAction(e -> HelloApplication.cambiarPantalla(demiurge, "Constantes.BATTLE"));
    }
    private Demiurge demiurge;




    private void configurarPantalla() {
        try {
            cargarImagenFondo();
            cargarImagenCriatura();
        } catch (NullPointerException e) {
            System.err.println("Error cargando recursos: " + e.getMessage());
        }
    }

    private void cargarImagenFondo() {
        String imagePath = getClass().getResource(Constantes.DUNGEON_IMAGE).toExternalForm();
        screen.setStyle("-fx-background-image: url('" + imagePath + "');"
                + "-fx-background-size: cover;"
                + "-fx-background-repeat: no-repeat;");
    }

    private void cargarImagenCriatura() {
        Image creatureImage = new Image(getClass().getResourceAsStream(Constantes.DUNGEON_IMAGE));
        creature.setImage(creatureImage);
        creature.setVisible(false);
    }

    private void configurarEventosTeclado() {
        screen.setFocusTraversable(true);
        screen.setOnKeyPressed(this::manejarTeclaPresionada);
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
        screen.requestFocus();
    }

    private void manejarTeclaPresionada(KeyEvent event) {
        int direccion = obtenerDireccion(event.getCode());
        if (direccion != -1) {
            procesarMovimiento(direccion);
        }
        event.consume();
    }

    private int obtenerDireccion(KeyCode codigo) {
        return switch (codigo) {
            case W -> 0;
            case D -> 1;
            case S -> 2;
            case A -> 3;
            default -> -1;
        };
    }

    private void procesarMovimiento(int direccion) {
        try {
            if (esMovimientoValido(direccion)) {
                ejecutarMovimiento(direccion);
            } else {
                mostrarNotificacion("¡Dirección no válida!");
            }
        } catch (WizardTiredException | GoHomekException e) {
            HelloApplication.cambiarPantalla(demiurge, Constantes.HOME);
        } catch (EndGameException e) {
            HelloApplication.cambiarPantalla(demiurge, Constantes.FINISH);
        }
    }

    private boolean esMovimientoValido(int direccion) {
        return direccion < demiurge.getDungeonManager().getNumberOfDoors();
    }

    private void ejecutarMovimiento(int direccion) throws EndGameException, GoHomekException, WizardTiredException {
        demiurge.getDungeonManager().openDoor(direccion);
        actualizarInformacionHabitacion();
        HelloApplication.cambiarPantalla(demiurge, Constantes.DUNGEON);
    }

    private void actualizarInformacionHabitacion() {
        roomName.setText(demiurge.getDungeonManager().getRoomInfo());
        creature.setVisible(demiurge.getDungeonManager().isAlive());
    }

    private void mostrarNotificacion(String mensaje) {
        infoLabel.setText(mensaje);
        infoLabel.setVisible(true);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), infoLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> infoLabel.setVisible(false));
        fade.play();
    }

}