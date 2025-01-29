package org.example.pesadillamago.game.spell;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Cast;

public class DrainEnergy extends Spell implements Cast {

    public DrainEnergy() { super(Domain.ENERGY, 1); }

    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
