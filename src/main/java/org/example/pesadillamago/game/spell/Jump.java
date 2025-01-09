package org.example.pesadillamago.game.spell;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Cast;
import org.example.pesadillamago.game.dungeon.Dungeon;

public class Jump extends Spell implements Cast {

    Dungeon dungeon;

    public Jump(Dungeon dungeon) {
        super(Domain.JUMP, 1);
        this.dungeon = dungeon;
    }
    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
