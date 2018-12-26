package Model;

import Model.Animals.ProductiveAnimal;
import Model.Animals.WildAnimal;
import Model.Positions.MapPosition;

import java.util.ArrayList;

public class Cell {
    Grass grass;
    MapPosition mapPosition;
    ArrayList<Model.Item> items = new ArrayList<>(0);
    public Cell(int x, int y) {
        grass = new Grass();
        mapPosition = new MapPosition(x, y);
        items = new ArrayList<>(0);
    }

    public Cell(Grass grass, MapPosition mapPosition, ArrayList<Item> items) {
        this.grass = grass;
        this.mapPosition = mapPosition;
        this.items = items;
    }

    public Grass getGrass() {
        return grass;
    }

    public void setGrass(Grass grass) {
        this.grass = grass;
    }

    public boolean collect() {

        // Priority NonAnimal WildAnimal ProductiveAnimal Dog Cat
        Item toBeCollectedItem = null;
        for (Item item : items) {
            if (item instanceof NonAnimalItem) {
                toBeCollectedItem = item;
            }
        }
        if (toBeCollectedItem != null) {
            toBeCollectedItem.getCollected();

            return true;
        }

        for (Item item : items) {
            if (item instanceof WildAnimal && ((WildAnimal) item).getCage() != null && ((WildAnimal) item).getCage().getCompletnessPercetage() == ((WildAnimal) item).getCage().getProgressMaxValue()) {

                toBeCollectedItem = item;
            }
        }
        if (toBeCollectedItem != null) {
            toBeCollectedItem.getCollected();
            return true;
        }

        /*for (Item item:items){
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
        }*/

        return false;


    }

    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(MapPosition mapPosition) {
        this.mapPosition = mapPosition;
    }

    public ArrayList<Model.Item> getItems() {
        return items;
    }

    public void addItem(Model.Item item) {
        items.add(item);


    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getCatCollectableItem() {
        for (Item item : items) {
            if (item.itemInfo instanceof NonAnimalItem.NonAnimalItemInfo) {
                return item;
            }
        }
        return null;
    }

    public void PrintCell() {
        View.Farmys.Cell.PrintCell(items, this.getMapPosition(),this.grass);
    }

    public void turn() {
        for (int i = 0; i <items.size();) {
            Item item =items.get(i);
            System.out.println("ewe");
            if(item.turn()||item.isRemove){

            }else {
                i++;
            }
        }
    }

    public boolean containsNonWildAnimalOrItem() {
        for (Item item : items) {
            if (item instanceof NonAnimalItem || item instanceof ProductiveAnimal) {
                return true;
            }
        }
        return false;
    }

    public boolean containsWildAniaml() {
        for (Item item : items) {
            if (item instanceof WildAnimal) {
                return true;
            }
        }
        return false;
    }
}
