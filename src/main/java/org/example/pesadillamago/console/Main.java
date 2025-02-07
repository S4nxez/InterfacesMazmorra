package org.example.pesadillamago.console;

import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.demiurge.DungeonConfiguration;
import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.loaderManual.DungeonLoaderManual;
import org.example.pesadillamago.loaderManual.GameState;

public class Main {
    public static void main(String[] args) throws Exception {
        DungeonLoaderManual loader = new DungeonLoaderManual();
        DungeonConfiguration config = new DungeonConfiguration();
        Demiurge demiurge = new Demiurge();
        GameState gameState = new GameState();

        loader.load(demiurge, config);

        Wizard wizard = demiurge.getWizard();
        Dungeon dungeon = gameState.loadFromXML("filename.xml").getDungeons().getFirst();

        ConsoleLogicJump game = new ConsoleLogicJump(wizard, dungeon);
        game.start();
    }
}
