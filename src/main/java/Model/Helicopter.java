package Model;

import java.util.ArrayList;

public class Helicopter extends Vehicle {


    public static ArrayList<Item.ItemInfo> buyableItems = new ArrayList<>(0);

    public Helicopter(int level, int capacity, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, capacity, remainingTurns, farm, price, items, farmMoney);
    }

    public static Item.ItemInfo findItem(String itemName) {
        for (Item.ItemInfo itemInfo : buyableItems) {
            if (itemName.equalsIgnoreCase(itemInfo.getItemName())) {
                return itemInfo;
            }

        }
        return null;
    }

    public void printHelicopter() {
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        if (CurrentMoney < getUpgradeCost()) {
            return false;
        }
        if (getLevel() == 3) {
            System.out.println("Unable to do update on helicopter as it's updated to level 3");
            return false;
        }
        CurrentMoney -= getUpgradeCost();
        Level += 1;
        return true;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public void turn() {
        if (getRemainingTurns() > 1) {
            setRemainingTurns(getRemainingTurns() - 1);
        } else if (getRemainingTurns() == 1) {
            setRemainingTurns(getRemainingTurns() - 1);
            for (Item item : items) {
                farm.getMap().addItemInRandom(item);
            }
            items.clear();

            setPrice(0);
        } else {
            // do nothing
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public boolean go() {
        if (this.isInTravel()) {
            System.out.println("Helicopter in Travel");
            return false;
        } else {
            this.goTravel();
            return true;
        }
    }

    @Override
    protected int getTravelTurns() {
        return 12 - 3 * getLevel();
    }


    public boolean goTravel() {
        RemainingTurns = getTravelTurns();
        Price = getPrice();
        setFarmMoney(getFarmMoney() - Price);

        return true;

    }


    public int getScatteringRadius() {
        switch (getLevel()) {
            case 0:
                return 120;
            case 1:
                return 100;
            case 2:
                return 60;
            case 3:
                return 20;
            default:
                return 0;
        }
    }
}
