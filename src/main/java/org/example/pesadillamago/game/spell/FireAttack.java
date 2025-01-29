package org.example.pesadillamago.game.spell;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Attack;
import org.example.pesadillamago.game.character.Character;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;

public class FireAttack extends Spell implements Attack {

    public FireAttack() { super(Domain.FIRE, 1); }

    @Override
    public int getDamage() { return level.getValue(); }

    @Override
    public void attack(Character character) throws CharacterKilledException {
        character.drainLife(character.protect(level.getValue(), domain));
    }
}
