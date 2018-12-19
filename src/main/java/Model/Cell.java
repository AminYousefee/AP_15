package Model;

import Model.Animals.*;
import Model.Positions.MapPosition;
import Model.Positions.Position;

import java.util.ArrayList;

public class Cell {
    public Grass getGrass() {
        return grass;
    }

    public void setGrass(Grass grass) {
        this.grass = grass;
    }

    Grass grass;
    MapPosition mapPosition;

    public Cage getCage() {
        return cage;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    Cage cage;
    ArrayList<Model.Item> items = new ArrayList<>(0);


    public boolean cage() {

        return true;
    }


    public boolean collect() {

        // Priority NonAnimal WildAnimal ProductiveAnimal Dog Cat
        Item toBeCollectedItem;
        for (Item item:items){
            if (item instanceof NonAnimalItem){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }

        for (Item item:items){
            if (item instanceof WildAnimal){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }
        for (Item item:items){
            if (item instanceof ProductiveAnimal){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }
        for (Item item:items){
            if (item instanceof Dog){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }

        for (Item item:items){
            if (item instanceof Cat){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }

        return false;


    }


    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(Position mapPosition) {
        this.mapPosition = mapPosition;
    }

    public ArrayList<Model.Item> getItems() {
        return items;
    }

    public void addItem(Model.Item item) {
        items.add(item);


    }
    public void removeItem(Item item){
        items.remove(item);
    }


    public Item getCatCollectableItem() {
        for (Item item : items) {
            if (item.itemInfo.isCatCollecable) {
                return item;
            }
        }
        return null;
    }

    public void PrintCell() {
        View.Farmys.Cell.PrintCell();
    }

    public void turn() {
        for (Item item : items) {
            item.turn();
        }
    }

    public boolean containsNonWildAnimalOrItem() {
        for (Item item : items) {
            if (item instanceof NonAnimalItem||item instanceof ProductiveAnimal){
                return true;
            }
        }
        return false;
    }

    public boolean containsWildAniaml() {
        for (Item item:items){
            if (item instanceof WildAnimal){
                return true;
            }
        }
        return false;
    }
}
