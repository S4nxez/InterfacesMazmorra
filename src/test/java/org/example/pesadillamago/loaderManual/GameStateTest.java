package org.example.pesadillamago.loaderManual;

import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.game.dungeon.Room;
import org.example.pesadillamago.game.objectContainer.RoomSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class GameStateTest {

    GameState gm = new GameState("player", 1, 0);
    private static List<Dungeon> dungeons;


    @BeforeAll
    static void setUp() {
        RoomSet roomSet = new RoomSet(3);

        // Crear habitaciones para la primera mazmorra
        Room room1 = new Room(1, "Entrada", roomSet);
        Room room2 = new Room(2, "Sala de tesoros", roomSet);
        Room room3 = new Room(3, "Habitación del jefe", roomSet);

        // Crear la primera mazmorra y agregar habitaciones
        Dungeon dungeon1 = new Dungeon();
        dungeon1.addRoom(room1);
        dungeon1.addRoom(room2);
        dungeon1.addRoom(room3);

        // Crear habitaciones para la segunda mazmorra
        Room room4 = new Room(4, "Entrada", roomSet);
        Room room5 = new Room(5, "Pasillo oscuro", roomSet);
        Room room6 = new Room(6, "Cámara secreta", roomSet);

        // Crear la segunda mazmorra y agregar habitaciones
        Dungeon dungeon2 = new Dungeon();
        dungeon2.addRoom(room4);
        dungeon2.addRoom(room5);
        dungeon2.addRoom(room6);

        dungeons =List.of(dungeon1, dungeon2);
    }

    @Test
    void saveToTextFile() {
        gm.setDungeons(dungeons);
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
        gm.setDungeons(dungeons);
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