package org.example.pesadillamago.game.character;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.actions.Attack;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.dungeon.Site;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.object.Weapon;
import org.example.pesadillamago.game.objectContainer.Container;
import org.example.pesadillamago.game.objectContainer.CrystalCarrier;
import org.example.pesadillamago.game.objectContainer.JewelryBag;
import org.example.pesadillamago.game.objectContainer.Wearables;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.example.pesadillamago.game.spell.Spell;
import org.example.pesadillamago.game.spell.SpellUnknowableException;
import org.example.pesadillamago.game.util.Value;
import org.example.pesadillamago.game.util.ValueOverMaxException;
import org.example.pesadillamago.game.util.ValueUnderMinException;


/**
 * Wizard's attributes and related data.
 *
 */
@Getter
@Setter
public class Wizard extends Character {
    private Site currentLocation;
    private final Value energy;
    @Getter
    private final Wearables wearables;
    //Containers
    @Getter
    private final CrystalCarrier crystalCarrier;
    @Getter
    private final JewelryBag jewelryBag;

    public Wizard(String n, int l, int lm, int e, int em, Wearables w, CrystalCarrier c, JewelryBag j) {
        super(n, Domain.NONE, l, lm, 1);

        energy = new Value(em, 0, e);
        this.currentLocation = null;
        wearables = w;
        crystalCarrier = c;
        jewelryBag = j;
    }

    public void drainLife(int v) throws CharacterKilledException {
        try {
            int protection = wearables.getNecklaceProtection(Domain.LIFE, v);
            life.decreaseValue(v);
        } catch (ValueUnderMinException e) {
            life.setToMinimum();
            throw new CharacterKilledException();
        }
    }

    //Energy
    public int getEnergy() {
        return energy.getValue();
    }
    public boolean hasEnoughEnergy(int checkValue) {return energy.availableToMinimum() > checkValue; }

    public void sleep(int maxRecovery) {
        recoverEnergy(maxRecovery);
    }
    public void recoverEnergy(int e) {
        try {
            energy.increaseValue(e);
        } catch (ValueOverMaxException ex) {
            energy.setToMaximum();
        }
    }
    public void drainEnergy(int e) throws WizardTiredException {
        try {
            int protection = wearables.getNecklaceProtection(Domain.ENERGY, e);
            energy.decreaseValue(protection);
        } catch (ValueUnderMinException ex) {
            energy.setToMinimum();
        }
        if (energy.getValue() <= 1)
            throw new WizardTiredException();
    }
    public int getEnergyMax() {
        return energy.getMaximum();
    }
    public void upgradeEnergyMax(int m) {
        energy.increaseMaximum(m);
    }


    public void addItem(Item item) throws ContainerUnacceptedItemException, ContainerFullException {
        if(item instanceof Attack)
            attacks.add((Attack) item);
        wearables.add(item);
    }

    public void addSpell(Spell spell) throws SpellUnknowableException {
        if(spell instanceof Attack)
            attacks.add((Attack) spell);
        memory.add(spell);
    }

    public void checkWeapon(){
        Attack remove = null;
        for (Attack a : attacks)
            if (a instanceof Weapon) {
                remove = a;
                break;
            }

        if (remove != null)
            attacks.remove(remove);

        Weapon w = wearables.getWeapon();
        if(w != null)
            attacks.add(w);
    }

    public int protect(int damage, Domain domain){
        int newDamage = damage - wearables.getRingProtection(domain);;
        if(newDamage < 1)
            newDamage = 1;
        return  newDamage;
    }

    public String toString() {
        return name + "\tEnergy" + energy + "\tLife" + life + "\n\t"
                + crystalCarrier + "\n\t"
                + wearables + "\n\t"
                + jewelryBag + "\n\t"
                + memory + "\n\t"
                + attacks.toString();
    }

}
