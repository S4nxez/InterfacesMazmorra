package org.example.pesadillamago.game.demiurge;

import java.util.HashMap;
import java.util.Map;

public class DungeonConfiguration {
    Map<String, Integer> configuration;

    public DungeonConfiguration(){ configuration = new HashMap<>(); }

    public void put(String param, int value){ configuration.put(param, value); }

    public int get(String param){ return configuration.get(param); }

}

