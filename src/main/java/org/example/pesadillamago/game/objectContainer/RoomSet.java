package org.example.pesadillamago.game.objectContainer;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.object.Necklace;
import org.example.pesadillamago.game.object.Ring;
import org.example.pesadillamago.game.object.Weapon;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;

public class RoomSet extends Container{

    public RoomSet(int c) { super(Domain.NONE, c); }

    /**
     * Adds items to a room
     * @param i the item to be added
     * @throws ContainerFullException the container is full
     * @throws ContainerUnacceptedItemException the container don't accept that item
     */
    public void add(Item i) throws ContainerUnacceptedItemException, ContainerFullException {
        if(isFull())
            throw new ContainerFullException();
        else if(i instanceof Necklace || i instanceof Ring || i instanceof Weapon) {
            items.add(i);
        }else
            throw new ContainerUnacceptedItemException();
    }

}
