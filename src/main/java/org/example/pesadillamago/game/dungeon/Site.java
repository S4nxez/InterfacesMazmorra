package org.example.pesadillamago.game.dungeon;

import lombok.Data;
import lombok.Getter;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.objectContainer.Container;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;

import java.util.ArrayList;
import java.util.Iterator;
@Data
public class Site implements Location {
    @Getter
    final int ID;
    @Getter
    final String description;
    @Getter
    final String imgRoute;
    @Getter
    boolean visited = false;
    @Getter
    boolean exit = false;
    @Getter
    final Container container;
    private final ArrayList<Door> doors;

    public Site(int ID, String description, Container container, String imgRoute) {
        this.ID = ID;
        this.description = description;
        this.container = container;
        this.imgRoute = imgRoute;
        doors = new ArrayList<>();
    }

    public Site(int ID, String description, Container container, boolean exit, String imgRoute) {
        this(ID, description, container, imgRoute);
        this.exit = exit;
    }

    public void visit() { visited = true; }

    public void addItem(Item s) throws ContainerUnacceptedItemException, ContainerFullException { container.add(s); }

    //Doors
    public int getNumberOfDoors() {
        return doors.size();
    }
    public void addDoor(Door p) {
        doors.add(p);
    }
    public Site openDoor(int index) {
        return doors.get(index).openFrom(this);
    }
    public Iterator<Door> iterator(){ return doors.iterator(); }

}
