package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse implements Upgradable {
    int Level;

    private int capacity = this.getMaxCapacity();
    private ArrayList<Item> items = new ArrayList<>(0);


    public Warehouse(int level, int capacity, ArrayList<Item> items) {
        Level = level;
        this.capacity = capacity;
        this.items = items;
    }

    public Warehouse(int level, ArrayList<Item> items) {
        Level = level;
        this.items = items;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    //Finished
    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        if (Level == 3) {
            System.out.println("Wow you still want a better wareHouse");
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());
        Level += 1;
        return true;
    }


    @Override
    public int getUpgradeCost() {
        //todo
        return 0;
    }


    private int getMaxCapacity() {
        switch (Level) {
            case 0:
                return 50;
            case 1:
                return 150;
            case 2:
                return 300;
            case 3:
                return 600;
            default:
                return 0;
        }
    }

    //finished
    public List<Item> findSpecificItem(Item.ItemInfo itemType, int MaxNumberToFind) {
        ArrayList<Item> methodOutput = new ArrayList<>(0);
        int numberFound = 0;
        for (Item item : items) {
            if (item.getItemInfo().equals(itemType)) {
                methodOutput.add(item);
            }
            numberFound++;
            if (numberFound == MaxNumberToFind) {
                break;
            }
        }
        return methodOutput;


    }

    public void remove(Item toBeAddedItem) {
        items.remove(toBeAddedItem);
        capacity+=toBeAddedItem.itemInfo.DepotSize;
    }

    public void addItem(Item item) {
        items.add(item);
        capacity-= item.itemInfo.DepotSize;
    }

    public void print() {
        System.out.println("WareHouse :");
        System.out.println("Capacity = "+capacity);
        for (Item item : items) {
            item.Print();
        }
    }
}
