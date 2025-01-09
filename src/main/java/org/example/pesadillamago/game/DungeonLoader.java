package org.example.pesadillamago.game;

import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.demiurge.DungeonConfiguration;

public interface DungeonLoader {

    public void load(Demiurge demiurge, DungeonConfiguration dungeonConfiguration);

}
