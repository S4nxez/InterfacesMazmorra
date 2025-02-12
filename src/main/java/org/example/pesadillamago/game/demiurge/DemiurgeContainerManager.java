package org.example.pesadillamago.game.demiurge;

import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.objectContainer.Container;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerInvalidExchangeException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;

public class DemiurgeContainerManager {

    Container wearables;
    Container bag;
    Container site;

    DemiurgeContainerManager(Container w, Container b, Container s){
        wearables = w;
        bag = b;
        site = s;
    }

    public Container getWearables() { return wearables; }
    public Container getBag() { return bag; }
    public Container getSite() { return site; }
    public void setSite(Container site) { this.site = site; }

    public void deleteItem(Container c, int aIndex) {
        c.remove(aIndex);
    }

    public void addItem(Container a, int aIndex, Container b) throws ContainerUnacceptedItemException, ContainerFullException {
        Item aItem = a.get(aIndex);
        b.add(aItem);
        a.remove(aIndex);
    }

    public void exchangeItem(Container a, int aIndex, Container b, int bIndex) throws ContainerInvalidExchangeException {
        Item aItem = a.get(aIndex);
        Item bItem = b.get(bIndex);

        if (aItem.getClass().equals(bItem.getClass())){
            a.remove(aIndex);
            b.remove(bIndex);

            try {
                a.add(bItem);
                b.add(aItem);
            } catch (ContainerFullException | ContainerUnacceptedItemException ignored) {
            }
        }else{
            throw new ContainerInvalidExchangeException();
        }
    }
}
