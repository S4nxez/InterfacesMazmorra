package org.example.pesadillamago.game.spell;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Cast;
import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.game.dungeon.Room;

public class Jump extends Spell implements Cast {

    private Dungeon dungeon;

    public Jump(Dungeon dungeon) {
        super(Domain.JUMP, 1);
        this.dungeon = dungeon;
    }

    @Override
    public int
    cast(String param, int value) {
        // In this implementation, we'll use the 'param' as the target room ID

            int targetRoomId = Integer.parseInt(param);
            Room targetRoom = dungeon.getRoom(targetRoomId);
            if (targetRoom != null) {
                return targetRoomId;
            }

        return -1; // Return -1 if the jump failed
    }
}
