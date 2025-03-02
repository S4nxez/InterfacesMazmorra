package org.example.pesadillamago.game.dungeon;

import org.example.pesadillamago.game.character.Creature;
import org.example.pesadillamago.game.object.SingaCrystal;
import org.example.pesadillamago.game.objectContainer.CrystalFarm;
import org.example.pesadillamago.game.objectContainer.RoomSet;

public class Room extends Site implements Location {
    private CrystalFarm farm;
    private Creature creature = null;

    public Room(int ID, String desc, RoomSet container, String image) {
        super(ID, desc, container, image);
        farm = new CrystalFarm(0);
    }

    public Room(int ID, String desc, RoomSet container, boolean e, String image) {
        super(ID, desc, container, e, image);
        farm = new CrystalFarm(0);
    }

    public static Room fromString(String roomText) {
        // Parsear el texto de la habitación para extraer los datos necesarios
        // Este es un ejemplo básico, ajusta según el formato exacto de roomText
        String[] parts = roomText.split(" ");
        int id = Integer.parseInt(parts[0].substring(3, parts[0].length() - 1));
        boolean exit = Boolean.parseBoolean(parts[1].substring(5, parts[1].length() - 1));
        String description = parts[2];
        String imageRoute = parts[3];

        // Crear un RoomSet vacío para el constructor
        RoomSet roomSet = new RoomSet(2);
        Room room = new Room(id, description, roomSet, exit, imageRoute);

        // Aquí puedes agregar lógica adicional para parsear y establecer otros atributos de Room

        return room;
    }


    //Crystals
    public void generateCrystals(int maxElements, int maxCapacity) {
        farm.grow(maxElements, maxCapacity);
    }

    public boolean isEmpty() {
        return farm.isEmpty();
    }

    public SingaCrystal gather() {
        return farm.gather();
    }


    //Creature
    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature c) {
        creature = c;
    }

    public boolean isAlive() {
        if (creature == null)
            return false;
        return creature.isAlive();
    }


    public String toString() {

        String exit = "ID(" + id + ") Exit(" + this.exit + ") " + description;
        if (creature != null)
            exit = exit.concat("\n\tCreature: " + creature);
        exit = exit.concat("\n\tCrystalFarm[" + farm.toString() + "]");
        exit = exit.concat("\n\tItems" + container.toString());
        return exit;
    }


}
