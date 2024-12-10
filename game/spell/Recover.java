package game.spell;

import game.Domain;
import game.actions.Cast;
import game.dungeon.Dungeon;

public class Recover extends Spell implements Cast {

    Dungeon dungeon;

    public Recover(Dungeon dungeon) {
        super(Domain.LIFE, 1);
        this.dungeon = dungeon;
    }
    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
