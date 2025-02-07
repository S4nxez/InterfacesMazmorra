package org.example.pesadillamago.game.dungeon;

import java.util.List;

public interface Location {
    String getDescription();
    List<Door> getDoors();
}
