package org.example.pesadillamago.loaderManual;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.DungeonLoader;
import org.example.pesadillamago.game.character.Creature;
import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.conditions.KillAllCreaturesCondition;
import org.example.pesadillamago.game.conditions.VisitAllRoomsCondition;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.demiurge.DungeonConfiguration;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.game.dungeon.Home;
import org.example.pesadillamago.game.dungeon.Room;
import org.example.pesadillamago.game.object.ItemCreationErrorException;
import org.example.pesadillamago.game.object.Necklace;
import org.example.pesadillamago.game.object.SingaCrystal;
import org.example.pesadillamago.game.object.Weapon;
import org.example.pesadillamago.game.objectContainer.*;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.example.pesadillamago.game.spell.AirAttack;
import org.example.pesadillamago.game.spell.ElectricAttack;
import org.example.pesadillamago.game.spell.FireAttack;
import org.example.pesadillamago.game.spell.SpellUnknowableException;
import org.example.pesadillamago.game.spellContainer.Knowledge;
import org.example.pesadillamago.game.spellContainer.Library;

public class DungeonLoaderManual implements DungeonLoader {

    //Home
    final int INITIAL_COMFORT = 1;
    final int INITIAL_SINGA = 10;
    final int INITIAL_SINGA_CAPACITY = 50;
    final int INITIAL_CHEST_CAPACITY = 4;

    //Wizard
    final int INITIAL_LIFE = 10;
    final int INITIAL_LIFE_MAX = 10;
    final int INITIAL_ENERGY = 10;
    final int INITIAL_ENERGY_MAX = 10;
    final int INITIAL_CRYSTAL_CARRIER_CAPACITY = 3;
    final int INITIAL_CRYSTAL_BAG_CAPACITY = 2;
    final int INITIAL_MAX_WEAPONS = 1;
    final int INITIAL_MAX_NECKLACES = 1;
    final int INITIAL_MAX_RINGS = 2;

    @Override
    public void load(Demiurge demiurge, DungeonConfiguration dungeonConfiguration) {

        try {
            /*-----Configuration-----*/


            /*-----Home-----*/
            System.out.println("\tCreating HOME");
            Knowledge library = new Library();
            library.add(new ElectricAttack());
            library.add(new FireAttack());
            library.add(new AirAttack());
            Home home = new Home("Home", INITIAL_COMFORT, INITIAL_SINGA, INITIAL_SINGA_CAPACITY, new Chest(INITIAL_CHEST_CAPACITY), library);
            home.addItem(new Weapon(2));
            demiurge.setHome(home);

            /*-----Dungeon-----*/
            System.out.println("\tCreating DUNGEON");
            Dungeon dungeon = new Dungeon();
            Room room;
            int id = 0;

            System.out.println("\tCreating ROOMS");
            room = new Room(id++, "Common room connected with HOME", new RoomSet(1), "1.png");
            room.addItem(Necklace.createNecklace(Domain.LIFE, 5));
            dungeon.addRoom(room);

            room = new Room(id++, "Common room in the middle", new RoomSet(0), "2.png");
            Creature creature = new Creature("Big Monster", 5, 1, Domain.ELECTRICITY);
            creature.addSpell(new ElectricAttack());
            room.setCreature(creature);
            dungeon.addRoom(room);

            room = new Room(id++, "Common room and Exit", new RoomSet(0), true, "3.png");
            dungeon.addRoom(room);

            System.out.println("\tCreating DOORS");
            new Door(home, dungeon.getRoom(0));
            new Door(dungeon.getRoom(0), dungeon.getRoom(1));
            new Door(dungeon.getRoom(1), dungeon.getRoom(2));

            System.out.println("\t\tTotal rooms in dungeon: " + id);
            demiurge.setDungeon(dungeon);

            /*-----Wizard-----*/
            System.out.println("\tAdding WIZARD to the system.");
            CrystalCarrier crystalCarrier = new CrystalCarrier(INITIAL_CRYSTAL_CARRIER_CAPACITY);
            crystalCarrier.add(SingaCrystal.createCrystal(10));
            Wearables wearables = new Wearables(INITIAL_MAX_WEAPONS, INITIAL_MAX_NECKLACES, INITIAL_MAX_RINGS);
            Wizard wizard = new Wizard("Merlin", INITIAL_LIFE, INITIAL_LIFE_MAX, INITIAL_ENERGY, INITIAL_ENERGY_MAX,
                    wearables, crystalCarrier, new JewelryBag(INITIAL_CRYSTAL_BAG_CAPACITY));
            wizard.addSpell(new FireAttack());
            wizard.addItem(new Weapon(1));

            demiurge.setWizard(wizard);

            /*-----End Conditions-----*/
            System.out.println("\tAdding END conditions.");
            demiurge.addCondition(new VisitAllRoomsCondition(dungeon));
            demiurge.addCondition(new KillAllCreaturesCondition(dungeon));

        } catch (ItemCreationErrorException | ContainerUnacceptedItemException | ContainerFullException |
                 SpellUnknowableException ignored) { }
    }

}
