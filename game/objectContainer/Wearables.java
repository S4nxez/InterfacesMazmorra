package game.objectContainer;

import game.Domain;
import game.object.Item;
import game.object.Necklace;
import game.object.Ring;
import game.object.Weapon;
import game.objectContainer.exceptions.ContainerFullException;
import game.objectContainer.exceptions.ContainerUnacceptedItemException;
import game.util.ValueUnderMinException;

public class Wearables extends Container{

    private int weapons, necklaces, rings;
    private final int weaponsMAX;
    private final int necklacesMAX;
    private final int ringsMAX;

    public Wearables(int w, int n, int r) {
        super(Domain.NONE, w+n+r);
        weapons = necklaces = rings = 0;
        weaponsMAX = w;
        necklacesMAX = n;
        ringsMAX = r;
    }

    public void add(Item si) throws ContainerFullException, ContainerUnacceptedItemException {
        if (si instanceof Weapon)
            addWeapon((Weapon) si);
        else if (si instanceof Necklace)
            addNecklace((Necklace) si);
        else if (si instanceof Ring)
            addRing((Ring) si);
        else
            throw new ContainerUnacceptedItemException();
        si.view();
    }

    public Item remove(int index) {
        Item item = items.get(index);

        if (item instanceof Weapon)
            weapons--;
        else if (item instanceof Necklace)
            necklaces--;
        else if (item instanceof Ring)
            rings--;

        return items.remove(index);
    }

    public void exchangeWearable(Item o, Item n) throws ContainerUnacceptedItemException {
        if (o.getClass() == n.getClass()) {
            items.remove(o);
            items.add(n);
        } else
            throw new ContainerUnacceptedItemException();
    }

    private void addWeapon(Weapon w) throws ContainerFullException {
        if (weapons < weaponsMAX) {
            weapons++;
            items.add(w);
        } else
            throw new ContainerFullException();
    }

    private void addNecklace(Necklace n) throws ContainerFullException {
        if (necklaces < necklacesMAX) {
            necklaces++;
            items.add(n);
        } else
            throw new ContainerFullException();
    }

    private void addRing(Ring r) throws ContainerFullException {
        if (rings < ringsMAX) {
            rings++;
            items.add(r);
        } else
            throw new ContainerFullException();
    }

    public Weapon getWeapon() {
        for (Item s : items)
            if (s instanceof Weapon)
                return (Weapon) s;
        return null;
    }

    public int getRingProtection(Domain domain){
        int value = -1;
        for (Item s : items)
            if (s instanceof Ring) {
                Ring ring = (Ring) s;
                if (ring.getDomain() == domain && ring.getValue() > value)
                    value = ring.getValue();
                break;
            }
        return value;
    }

    public int getNecklaceProtection(Domain domain, int value){
        int newValue = 0;
        for (Item s : items)
            if (s instanceof Necklace) {
                Necklace necklace = (Necklace) s;
                if (necklace.getDomain() == domain) {
                    newValue = value - necklace.getValue();
                    if (newValue > 0 || newValue == 0){
                        items.remove(necklace);
                    }else {
                        try {
                            necklace.decreaseValue(newValue*-1);
                            newValue = 0;
                        } catch (ValueUnderMinException ignored) {}
                    }
                }
            }
        return newValue;
    }

}
