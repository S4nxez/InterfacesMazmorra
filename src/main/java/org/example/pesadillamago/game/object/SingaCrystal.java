package org.example.pesadillamago.game.object;

import org.example.pesadillamago.game.Domain;

public class SingaCrystal extends Item{

    private SingaCrystal(int s) { super(Domain.NONE, s); }

    public static SingaCrystal createCrystal(int maximum) { return new SingaCrystal((int) (Math.random() * maximum + 1)); }

}
