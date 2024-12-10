package game.demiurge;

import game.character.Wizard;
import game.character.exceptions.WizardNotEnoughEnergyException;
import game.character.exceptions.WizardSpellKnownException;
import game.character.exceptions.WizardTiredException;
import game.dungeon.Home;
import game.dungeon.HomeNotEnoughSingaException;
import game.object.SingaCrystal;
import game.objectContainer.Container;
import game.objectContainer.exceptions.ContainerEmptyException;
import game.objectContainer.exceptions.ContainerErrorException;
import game.spell.Spell;
import game.spell.SpellUnknowableException;
import game.spellContainer.Knowledge;
import game.util.ValueOverMaxException;

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
        if (home.getMaxSinga() > home.getSinga()) {
            throw new HomeNotEnoughSingaException();
        } else if (home.getMaxSinga() / 2 > wizard.getEnergy()) {
            throw new WizardNotEnoughEnergyException();
        } else {
            home.upgradeMaxSinga(dc.get("stoneIncrease"));
            home.emptySinga();
            wizard.drainEnergy(home.getMaxSinga() / 2);
        }
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
