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
     int id;
     String description;
     String imgRoute;
    boolean visited = false;
    boolean exit = false;
    final Container container;
    private  ArrayList<Door> doors;

    public Site(int id, String description, Container container, String imgRoute) {
        this.id = id;
        this.description = description;
        this.container = container;
        this.imgRoute = imgRoute;
        doors = new ArrayList<>();
    }
    public Site(int id, String description, Container container) {
        this.id = id;
        this.description = description;
        this.container = container;
        doors = new ArrayList<>();
    }

    public Site(int id, String description, Container container, boolean exit, String imgRoute) {
        this(id, description, container, imgRoute);
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
