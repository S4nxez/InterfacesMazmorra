package game.spell;

import game.Domain;
import game.actions.Cast;
import game.dungeon.Dungeon;

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
