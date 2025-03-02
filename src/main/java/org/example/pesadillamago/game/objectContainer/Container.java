package org.example.pesadillamago.game.objectContainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerFullException;
import org.example.pesadillamago.game.objectContainer.exceptions.ContainerUnacceptedItemException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basic aggregation of elements that is limited in capacity
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Container extends Item {

     List<Item> items;

    /**
     *
     * @param d type of domain
     * @param v capacity of the container
     */
    public Container(Domain d, int v) {
        super(d, v);
        items = new ArrayList<>();
    }

    /**
     *
     * @return number of items
     */
    public int size() { return items.size(); }

    /**
     * Check if there is space for more items
     * @param q the number of elements to be added
     * @return true if the given number fits
     */
    public boolean fit(int q){ return getValue()>q+items.size(); }

    /**
     * Check if the storage is full
     * @return true if the storage is full
     */
    public boolean isFull() { return getValue()==items.size(); }
    public boolean isEmpty() { return getValue()==0; }

    /**
     * Allows the iteration of the collection
     * @return the iterator
     */
    public Iterator iterator(){ return items.iterator(); }

    public Item get(int index){ return items.get(index); }

    /**
     * Adds an element to the collection
     * @param i the element to be added
     * @throws ContainerFullException the container is full
     * @throws ContainerUnacceptedItemException the container don't accept that item
     */
    public void add(Item i) throws ContainerFullException, ContainerUnacceptedItemException {
        if(isFull())
            throw new ContainerFullException();
        else
            items.add(i);
    }

    public Item remove(int index){ return items.remove(index); }

    public String toString() {
        String exit = getClass().getSimpleName() + " (" + getValue() + ":" + items.size() + ") -> ";
        for (Item item : items)
            exit = exit.concat(item + " ");
        return exit;
    }

}
