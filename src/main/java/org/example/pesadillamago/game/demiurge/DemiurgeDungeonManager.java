package org.example.pesadillamago.game.demiurge;

import org.example.pesadillamago.game.actions.Attack;
import org.example.pesadillamago.game.character.Creature;
import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.character.exceptions.CharacterKilledException;
import org.example.pesadillamago.game.character.exceptions.WizardNotEnoughEnergyException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.demiurge.exceptions.EndGameException;
import org.example.pesadillamago.game.demiurge.exceptions.GoHomekException;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Home;
import org.example.pesadillamago.game.dungeon.Room;
import org.example.pesadillamago.game.dungeon.Site;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.example.pesadillamago.game.spell.Spell;

import java.util.Iterator;

public class DemiurgeDungeonManager {
    private final DungeonConfiguration dc;
    private final Wizard wizard;
    private Site site;
    private Creature creature;
    DemiurgeContainerManager containerManager;
    DemiurgeEndChecker endChecker;

    public DemiurgeDungeonManager(DungeonConfiguration dc, Wizard wizard, Site site, DemiurgeContainerManager dcm, DemiurgeEndChecker checker) {
        this.dc = dc;
        this.wizard = wizard;
        this.site = site;
        containerManager = dcm;
        endChecker = checker;
    }

    public void enterDungeon() throws WizardTiredException, EndGameException {
        try {
            openDoor(0);
        } catch (GoHomekException ignored) {
        }
    }

    public String getRoomInfo() {
        return site.toString();
    }
    public String wizardInfo() {
        return wizard.toString();
    }
    public String wizardLifeInfo() {
        return wizard.lifeInfo();
    }
    public void checkWeapon() { wizard.checkWeapon(); }
    public String creatureLifeInfo() {
        return creature.lifeInfo();
    }

    public int getNumberOfDoors() {
        return site.getNumberOfDoors();
    }
    public Iterator<Door> getDoorsIterator() { return site.iterator(); }
    public String showOtherSite(Door door) {
        Site other = door.showFrom(site);
        return "(" + other.getId() + ") " + other.getDescription();
    }

    public void openDoor(int index) throws WizardTiredException, GoHomekException, EndGameException {
        site = site.openDoor(index);

        wizard.drainEnergy(dc.get("basicEnergyConsumption"));
        site.visit();
        containerManager.setSite(site.getContainer());

        if(site.isExit() && endChecker.check())
            throw new EndGameException();

        if (site instanceof Home) {
            throw new GoHomekException();
        } else {
            Room currentRoom = (Room) site;
            creature = currentRoom.getCreature();
            if (creature != null)
                creature.view();
        }
    }

    public void gatherCrystals()  {
        Room currentRoom = (Room) site;
        while (true) {
            try {
                if (currentRoom.isEmpty() || wizard.getCrystalCarrier().isFull())
                    return;
                wizard.getCrystalCarrier().add(currentRoom.gather());
            } catch (ContainerUnacceptedItemException | ContainerFullException ignored) {
            }
        }
    }

    //CREATURE methods
    public boolean hasCreature() {
        return creature != null;
    }
    public boolean isAlive() {
        return ((Room) site).isAlive();
    }

    public boolean canRunAway() {
        return (int) (Math.random() * 100 + 1) > dc.get("minimumForRunAway");
    }
    public boolean priority() {
        return wizard.getEnergy() >= creature.getLife();
    }

    public int getNumberOfAttacks() {
        return wizard.getNumberOfAttacks();
    }
    public Iterator<Attack> getAttacksIterator() { return wizard.getAttacksIterator(); }
    public Attack getAttack(int index) {
        return wizard.getAttack(index);
    }

    public boolean wizardAttack(Attack attack) throws CharacterKilledException, WizardTiredException, WizardNotEnoughEnergyException {
        boolean success = false;
        //Check energy
        int energyConsumption = dc.get("basicEnergyConsumption");
        if (attack instanceof Spell)
            energyConsumption = ((Spell)attack).getLevel();
        if(!wizard.hasEnoughEnergy(energyConsumption))
            throw new WizardNotEnoughEnergyException();

        //Successful value
        int fightSuccessful = dc.get("fightSuccessLow");
        if (wizard.getEnergy() > wizard.getEnergyMax() / 10 * 8)
            fightSuccessful = dc.get("fightSuccessHigh");
        else if (wizard.getEnergy() > wizard.getEnergyMax() / 10 * 4)
            fightSuccessful = dc.get("fightSuccessMedium");


        if ((int) (Math.random() * 100 + 1) > fightSuccessful) {
            attack.attack(creature);
            success = true;
        }
        wizard.drainEnergy(energyConsumption);
        return success;
    }

    public boolean creatureAttack() throws CharacterKilledException {
        int value = dc.get("fightSuccessLow");
        if (creature.getLife() > wizard.getEnergy())
            value = dc.get("fightSuccessHigh");
        else if (creature.getLife() == wizard.getEnergy())
            value = dc.get("fightSuccessMedium");

        if ((int) (Math.random() * 100 + 1) > value) {
            creature.getRandomAttack().attack(wizard);
            return true;
        }
        return false;
    }

}
