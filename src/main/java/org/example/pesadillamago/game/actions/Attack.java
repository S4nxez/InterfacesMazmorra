package org.example.pesadillamago.game.actions;

import org.example.pesadillamago.game.character.Character;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;

public interface Attack {
    public int getDamage();

    public void attack(Character character) throws CharacterKilledException;
}
