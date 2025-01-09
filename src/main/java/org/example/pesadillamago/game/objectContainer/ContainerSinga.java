package org.example.pesadillamago.game.objectContainer;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.object.Item;

public abstract class ContainerSinga extends Container {

    public ContainerSinga(int c) { super(Domain.NONE, c); }

    public int singa(){
        int singa = 0;
        for (Item i : items)
            singa += i.getValue();
        return singa;
    }

}
