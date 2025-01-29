package org.example.pesadillamago.game.object;

import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Attack;
import org.example.pesadillamago.game.character.Character;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;

public class Weapon extends Item implements Attack {

    public Weapon(int v) { super(Domain.NONE, v); }

    @Override
    public int getDamage() { return value.getValue(); }

    @Override
    public void attack(Character character) throws CharacterKilledException {
        character.drainLife(value.getValue());
    }

}
