
package org.example.pesadillamago.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.pesadillamago.game.dungeon.home.HomeController;
import org.example.pesadillamago.common.Constantes;
import org.example.pesadillamago.game.demiurge.Demiurge;


import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/pesadillamago/mainHomeInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        HomeController homeController = fxmlLoader.getController();
        stage.setResizable(false);
        stage.setTitle("Main Home Interface");
        stage.setScene(scene);
        stage.show();
        homeController.setStage(stage);

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