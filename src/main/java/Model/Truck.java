package Model;

import View.VehicleView.TruckView;

import java.util.ArrayList;
import java.util.Collections;

public class Truck extends Vehicle {


    public Truck(int level, int capacity, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, capacity, remainingTurns, farm, price, items, farmMoney);
    }

    public Truck(int level, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, remainingTurns, farm, price, items, farmMoney);
    }

    private int getMaxCapacity() {
        //todo
        return getLevel() * 50;
    }

    public void turn() {
        if (RemainingTurns > 1) {
            RemainingTurns -= 1;
        } else if (RemainingTurns == 1) {
            RemainingTurns -= 1;
            FarmMoney = FarmMoney + Price;
            Price = 0;
        } else {
            // do nothing
        }
    }


    public void printTruck() {
        TruckView.PrintTruck(this);


    }

    public ArrayList<Item> getItems() {
        return items;
    }


    public int getTravelTurns() {
        //todo
        return 20 - 5 * getLevel();
    }


    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        if (getLevel() == 3) {
            System.out.println("Unable to do update on truck as it's updated to level 3");
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());

        Level += 1;
        return true;

    }

    @Override
    public int getUpgradeCost() {
        return 0;
        //todo
    }

    public void addItem(Item item) {
        //if ()
        items.add(item);
    }


    @Override
    public boolean go() {
        if (this.isInTravel()) {
            System.out.println("Truck in Travel");
            return false;
        } else {
            this.goTravel();
            return true;
        }
    }


    public boolean goTravel() {
        RemainingTurns = getTravelTurns();
        Price = getPrice();

        return true;

    }

    public int getBoxNumbers() {
        return 2 + getLevel();
    }


}
