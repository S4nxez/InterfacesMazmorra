package org.example.pesadillamago.game.demiurge;

import org.example.pesadillamago.dao.DaoPlayer;
import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.character.exceptions.WizardNotEnoughEnergyException;
import org.example.pesadillamago.game.character.exceptions.WizardSpellKnownException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.dungeon.Home;
import org.example.pesadillamago.game.dungeon.HomeNotEnoughSingaException;
import org.example.pesadillamago.game.object.SingaCrystal;
import org.example.pesadillamago.game.objectContainer.Container;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerEmptyException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerErrorException;
import org.example.pesadillamago.game.spell.Spell;
import org.example.pesadillamago.game.spell.SpellUnknowableException;
import org.example.pesadillamago.game.spellContainer.Knowledge;
import org.example.pesadillamago.game.util.ValueOverMaxException;

public class DemiurgeHomeManager {
    private DungeonConfiguration dc;
    private Wizard wizard;
    private Home home;
    private DemiurgeContainerManager containerManager;

    public DemiurgeHomeManager(DungeonConfiguration dc, Wizard wizard, Home home, DemiurgeContainerManager dcm){
        this.dc = dc;
        this.wizard = wizard;
        this.home = home;
        containerManager = dcm;
    }


    public String homeInfo() { return home.toString();}
    public String wizardInfo() {return wizard.toString();}

    public void checkWeapon() { wizard.checkWeapon(); }

    public int getLife(){return wizard.getLife();}
    public int getLifeMax() {return wizard.getLifeMax();}

    public int getSinga(){ return home.getSinga();}
    public int getSingaSpace() { return home.getSingaSpace(); }

    public int getSingaPerLifePoint() { return dc.get("singaPerLifePointCost"); }

    public Container getCarrier(){ return wizard.getCrystalCarrier(); }

    public void enterHome(){ containerManager.setSite(home.getContainer()); }

    public void recover(int points) throws HomeNotEnoughSingaException, WizardTiredException, ValueOverMaxException {
        if (points * dc.get("singaPerLifePointCost") > home.getSinga()) {
            throw new HomeNotEnoughSingaException();
        } else {
            wizard.recoverLife(points);
            home.useSinga(points * dc.get("singaPerLifePointCost"));
            wizard.drainEnergy(dc.get("basicEnergyConsumption"));
        }
    }

    public void mergeCrystal(int position) throws WizardTiredException, ContainerEmptyException, ContainerErrorException {
        home.mergeCrystal((SingaCrystal) wizard.getCrystalCarrier().remove(position));
        wizard.drainEnergy(dc.get("basicEnergyConsumption"));
    }

    public void upgradeLifeMax() throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException {
        if (wizard.getLifeMax() * dc.get("basicUpgradeCost") > home.getSinga()) {
            throw new HomeNotEnoughSingaException();
        } else if (wizard.getLifeMax() / dc.get("basicUpgradeCost") > wizard.getEnergy()) {
            throw new WizardNotEnoughEnergyException();
        } else {
            wizard.upgradeLifeMax(dc.get("basicIncrease"));
            home.useSinga(wizard.getLifeMax() * dc.get("basicUpgradeCost"));
            wizard.drainEnergy(wizard.getEnergyMax() / dc.get("basicUpgradeCost"));
        }
    }

    public void upgradeEnergyMax() throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException {
        if (wizard.getEnergyMax() * dc.get("basicUpgradeCost") > home.getSinga()) {
            throw new HomeNotEnoughSingaException();
        } else if (wizard.getEnergyMax() / dc.get("basicUpgradeCost") > wizard.getEnergy()) {
            throw new WizardNotEnoughEnergyException();
        } else {
            wizard.upgradeEnergyMax(dc.get("basicIncrease"));
            home.useSinga(wizard.getEnergyMax() * dc.get("basicUpgradeCost"));
            wizard.drainEnergy(wizard.getEnergyMax() / dc.get("basicUpgradeCost"));
        }
    }

    public void upgradeComfort() throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException {
        if (home.getComfort() * dc.get("comfortUpgradeCost") > home.getSinga()) {
            throw new HomeNotEnoughSingaException();
        } else if (home.getComfort() > wizard.getEnergy()) {
            throw new WizardNotEnoughEnergyException();
        } else {
            home.upgradeComfort();
            home.useSinga(home.getComfort() * dc.get("comfortUpgradeCost"));
            wizard.drainEnergy(home.getComfort());
        }
    }

    public void upgradeSingaMax() throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException {
        DaoPlayer.check_singa(home, wizard, dc);
    }

    public Knowledge getMemory() { return wizard.getMemory(); }
    public void improveSpell(int option) throws ValueOverMaxException, WizardTiredException, HomeNotEnoughSingaException, WizardNotEnoughEnergyException {
        Spell spell = wizard.getSpell(option);
        int nextLevel = spell.getLevel()+dc.get("basicIncrease");

        if (home.getSinga() < nextLevel) {
            throw new HomeNotEnoughSingaException();
        } else if (wizard.getEnergy() < nextLevel) {
            throw new WizardNotEnoughEnergyException();
        } else {
            spell.improve(dc.get("basicIncrease"));
            wizard.drainEnergy(nextLevel);
            home.useSinga(nextLevel);
        }
    }

    public Knowledge getLibrary() { return home.getLibrary(); }
    public void learnSpell(int option) throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException, WizardSpellKnownException {
        Spell spell = home.getSpell(option);

        if (home.getSinga() < 1) {
            throw new HomeNotEnoughSingaException();
        } else if (wizard.getEnergy() < 1) {
            throw new WizardNotEnoughEnergyException();
        } else if (wizard.existsSpell(spell)) {
            throw new WizardSpellKnownException();
        } else {
            try {
                wizard.addSpell(spell);
                wizard.drainEnergy(dc.get("basicEnergyConsumption"));
                home.useSinga(dc.get("basicEnergyConsumption"));
            } catch (SpellUnknowableException ignored) {
            }
        }
    }

}
