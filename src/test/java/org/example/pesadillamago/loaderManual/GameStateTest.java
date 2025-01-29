package org.example.pesadillamago.loaderManual;

import org.junit.jupiter.api.Test;

import java.io.IOException;
class GameStateTest {

    GameState gm = new GameState("playerName", 1, 1);

    @Test
    void saveToTextFile() {
        try {
            gm.saveToTextFile("filename");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromTextFile() {
        try {
            gm.loadFromTextFile("filename");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void saveToXML() {
        try {
            gm.saveToXML("filename.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromXML() {
        try {
            gm.loadFromXML("filename.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}