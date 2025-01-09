package org.example.pesadillamago.game.object;

import org.example.pesadillamago.game.Domain;

public class Necklace extends Item {

    private Necklace(Domain d, int v) { super(d, v); }

    public static Necklace createNecklace(Domain d, int v) throws ItemCreationErrorException {
        if (d == Domain.ENERGY || d == Domain.LIFE)
            return new Necklace(d, v);
        else
            throw new ItemCreationErrorException();
    }
}
