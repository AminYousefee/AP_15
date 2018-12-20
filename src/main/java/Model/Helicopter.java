package Model;

import controller.Print;

import java.util.ArrayList;

public class Helicopter extends Vehicle {


    public static ArrayList<Item.ItemInfo> buyableItems = new ArrayList<>(0);

    public Helicopter(Integer currentMoney) {

    }

    public static Item.ItemInfo findItem(String itemName) {
        for (Item.ItemInfo itemInfo:buyableItems) {
            if (itemName.equalsIgnoreCase(itemInfo.getItemName())){
                return itemInfo;
            }

        }
        return null;
    }

    public void printHelicopter() {
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        return false;
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
            for (Item item:items){
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

        return 50;
    }




    public boolean goTravel(){
        RemainingTurns = getTravelTurns();
        Price = getPrice();
        setFarmMoney(getFarmMoney()- Price);

        return true;

    }
}
