
package org.example.pesadillamago.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.pesadillamago.common.Constantes;
import org.example.pesadillamago.game.demiurge.Demiurge;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;


    @Override
    public void start(Stage rstage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(Constantes.HOME));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        rstage.setResizable(false);
        rstage.setTitle("Main Home Interface");
        rstage.setScene(scene);
        rstage.show();

        stage = rstage;
        rstage.setTitle("Dungeon");
        cambiarPantalla(null, Constantes.HOME);
        rstage.setMinHeight(500);
        rstage.setMinWidth(500);
        rstage.setMaxHeight(900);
        rstage.setMaxWidth(900);
        rstage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void cambiarPantalla(Demiurge demiurge, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Object controller = fxmlLoader.getController();

            ((DemiurgeConsumer) controller).loadScreenData(demiurge);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}