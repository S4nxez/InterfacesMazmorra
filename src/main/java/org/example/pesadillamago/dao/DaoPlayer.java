package org.example.pesadillamago.dao;

import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.character.exceptions.WizardNotEnoughEnergyException;
import org.example.pesadillamago.game.character.exceptions.WizardTiredException;
import org.example.pesadillamago.game.demiurge.DungeonConfiguration;
import org.example.pesadillamago.game.dungeon.Home;
import org.example.pesadillamago.game.dungeon.HomeNotEnoughSingaException;
import org.example.pesadillamago.game.object.SingaCrystal;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerEmptyException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerErrorException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.example.pesadillamago.game.spell.SpellUnknowableException;
import org.example.pesadillamago.game.util.ValueOverMaxException;
import org.example.pesadillamago.loaderManual.GameState;


public class DaoPlayer {
    private Wizard wizard;
    private Home home;
    private DungeonConfiguration dc;
    private  GameState gameState;

    public DaoPlayer(Wizard wizard, Home home, DungeonConfiguration dc) {
        this.wizard = wizard;
        this.home = home;
        this.dc = dc;
    }

    public DaoPlayer() {
        this.gameState = new GameState();
        this.wizard = gameState.getWizard();
        this.home = new Home(null, 0, 0,0,null, null);
        this.dc = new DungeonConfiguration();

    }

    public void handleMergeSingaCrystal(int position) throws WizardTiredException, ContainerEmptyException, ContainerErrorException {
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
        check_singa(home, wizard, dc);
    }

    public static void check_singa(Home home, Wizard wizard, DungeonConfiguration dc) throws HomeNotEnoughSingaException, WizardNotEnoughEnergyException, WizardTiredException {
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

    public void handleSleep() {
        wizard.sleep(home.getComfort() * dc.get("comfortModifierForEnergy"));
    }

    public void handleRecoverLife() throws ValueOverMaxException {
        wizard.recoverLife(dc.get("basicIncrease"));
    }


    public void handleImproveSpell() throws ValueOverMaxException {
        wizard.getSpell(0).improve(1);
    }

    public void handleLearnNewSpell() throws SpellUnknowableException {
        //deberia llegar el int del spell que va a aprender
        wizard.addSpell(home.getSpell(0));
    }

    public void handleDeleteFromStorage() {
        home.getStorage().remove(0);
    }

    public void handleDeleteFromJewelryBag() {
        home.getJewelryBag().remove(0);
    }

    public void handleTakeFromStorage() throws ContainerUnacceptedItemException, ContainerFullException {
        wizard.getWearables().add(home.getStorage().remove(0));
    }

    public void handleTakeFromJewelryBag() throws ContainerUnacceptedItemException, ContainerFullException {
        wizard.getWearables().add(home.getJewelryBag().remove(0));
    }

    public void handleLeaveInChest() throws ContainerUnacceptedItemException, ContainerFullException {
        home.getStorage().add(wizard.getWearables().remove(0));
    }

    public void handleLeaveInJewelryBag() throws ContainerUnacceptedItemException, ContainerFullException {
        home.getJewelryBag().add(wizard.getWearables().remove(0));
    }

    public void handleExchangeWizardChest() throws ContainerUnacceptedItemException, ContainerFullException {
        home.getStorage().add(wizard.getWearables().remove(0));
        wizard.getWearables().add(home.getStorage().remove(0));
    }

    public void handleExchangeWizardJewelryBag() throws ContainerUnacceptedItemException, ContainerFullException {
        home.getJewelryBag().add(wizard.getWearables().remove(0));
        wizard.getWearables().add(home.getJewelryBag().remove(0));
    }
}