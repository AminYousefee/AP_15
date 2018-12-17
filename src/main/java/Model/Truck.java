package Model;

import View.VehicleView.TruckView;

import java.util.ArrayList;
import java.util.Collections;

public class Truck {
    int Level;
    int Capacity;
    int RemainingTurns;
    int Price;
    ArrayList<Item> items = new ArrayList<>(0);
    Integer FarmMoney;

    {


    }


    public Truck(Integer FarmMoney) {
        this.FarmMoney = FarmMoney;
    }

    private int getMaxCapacity() {
        //todo
    }

    private void turn() {
        if (RemainingTurns > 1) {
            RemainingTurns -= 1;
        } else if (RemainingTurns == 1) {
            FarmMoney = FarmMoney + Price;
            Price = 0;
        } else {
            // do nothing
        }
    }




    public void printTruck() {
        TruckView.PrintTruck(this);


    }
    public ArrayList<Item> getItems(){
        return (ArrayList<Item>) Collections.unmodifiableList(items);
    }


    public boolean goTravel(){
        RemainingTurns = getTravelTurns();
        Price = getPrice();

    }




    public int getTravelTurns(){
        //todo
    }

    public int getPrice(){
        int price=0;
        for (Item item:items){
            price+=item.getPrice();
        }
        return price;

    }

    @Override
    public boolean upgrade() {
        return false;
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        if (CurrentMoney<getUpgradeCost()){
            return false;
        }
        CurrentMoney -= getUpgradeCost();
        Level +=1;
        return true;

    }

    @Override
    public int getUpgradeCost() {
        //todo
    }

    public void addItem(Item item) {
        items.add(item);
    }


}
