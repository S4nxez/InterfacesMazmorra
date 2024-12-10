package game.demiurge;

import game.DungeonLoader;
import game.character.Wizard;
import game.conditions.Condition;
import game.dungeon.Dungeon;
import game.dungeon.Home;

public class Demiurge {
    private int day = 0;
    private Dungeon dungeon;
    private Home home;
    private Wizard wizard;

    DungeonConfiguration dungeonConfiguration;
    DemiurgeHomeManager homeManager;
    DemiurgeContainerManager containerManager;
    DemiurgeDungeonManager dungeonManager;
    DemiurgeEndChecker endChecker;

    public Demiurge() {
        dungeonConfiguration = new DungeonConfiguration();
        endChecker = new DemiurgeEndChecker();

        dungeonConfiguration.put("basicEnergyConsumption", 1);

        dungeonConfiguration.put("comfortModifierForEnergy", 10);

        dungeonConfiguration.put("basicIncrease", 1);
        dungeonConfiguration.put("stoneIncrease", 50);

        dungeonConfiguration.put("basicUpgradeCost", 10);
        dungeonConfiguration.put("comfortUpgradeCost", 100);

        dungeonConfiguration.put("singaPerLifePointCost", 3);

        //Crystal regeneration
        dungeonConfiguration.put("crystalsPerDay", 3);
        dungeonConfiguration.put("singaPerCrystal", 10);

        //Creature encounters
        dungeonConfiguration.put("minimumForRunAway", 30);
        dungeonConfiguration.put("fightSuccessHigh", 20);
        dungeonConfiguration.put("fightSuccessMedium", 50);
        dungeonConfiguration.put("fightSuccessLow", 80);
    }

    public void setDungeon(Dungeon dungeon) { this.dungeon = dungeon; }
    public void setHome(Home home) {
        this.home = home;
    }
    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }
    public void addCondition(Condition condition){ endChecker.addCondition(condition); }

    public DemiurgeHomeManager getHomeManager() { return homeManager; }
    public DemiurgeContainerManager getContainerManager() { return containerManager; }
    public DemiurgeDungeonManager getDungeonManager() { return dungeonManager; }

    public void loadEnvironment(DungeonLoader dungeonLoader) {
        dungeonLoader.load(this, dungeonConfiguration);
        containerManager = new DemiurgeContainerManager(wizard.getWearables(), wizard.getJewelryBag(), home.getContainer());
        homeManager =  new DemiurgeHomeManager(dungeonConfiguration, wizard, home, containerManager);
        dungeonManager = new DemiurgeDungeonManager(dungeonConfiguration, wizard, home, containerManager, endChecker);
        nextDay();
    }

    public int getDay() {
        return day;
    }

    public void nextDay() {
        wizard.sleep(home.getComfort() * dungeonConfiguration.get("comfortModifierForEnergy"));
        dungeon.generateCrystals(dungeonConfiguration.get("crystalsPerDay"), dungeonConfiguration.get("singaPerCrystal"));
        day++;
    }
}
