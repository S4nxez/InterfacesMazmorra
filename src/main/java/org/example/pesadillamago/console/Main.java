package org.example.pesadillamago.console;

import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.demiurge.DungeonConfiguration;
import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.loaderManual.DungeonLoaderManual;

public
class Main {
    public static void
    main(String[] args) {
        DungeonLoaderManual loader = new DungeonLoaderManual();
        DungeonConfiguration config = new DungeonConfiguration();
        Demiurge demiurge = new Demiurge();

        loader.load(demiurge, config);

        Wizard wizard = demiurge.getWizard();
        Dungeon dungeon = demiurge.getDungeon();

        ConsoleLogicJump game = new ConsoleLogicJump(wizard, dungeon);
        game.start();
    }
}
