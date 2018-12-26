package Model;

import java.util.ArrayList;

public abstract class Vehicle implements Upgradable {
    int Level;
    int Capacity;
    int RemainingTurns;
    Farm farm;
    int Price;
    ArrayList<Item> items = new ArrayList<>(0);
    transient Integer FarmMoney;
    String name;


    public Vehicle(int level, int capacity, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        Level = level;
        Capacity = capacity;
        RemainingTurns = remainingTurns;
        this.farm = farm;
        Price = price;
        this.items = items;
        FarmMoney = farmMoney;
    }

    public boolean isInTravel(){
        if (getRemainingTurns()>0){
            return true;
        }else {
            return false;
        }
    }




    public abstract void turn();
    public void clear(){
        items =new ArrayList<>( 0 );

    }


    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getRemainingTurns() {
        return RemainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        RemainingTurns = remainingTurns;
    }

    public int getPrice(){
        int price=0;
        for (Item item:items){
            if (this instanceof Truck){
                price+=item.getBuyCost();
            }else if (this instanceof Helicopter){
                price+=item.getSaleCost();
            }
        }
        return price;

    }

    public void setPrice(int price) {
        Price = price;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Integer getFarmMoney() {
        return FarmMoney;
    }

    public void setFarmMoney(Integer farmMoney) {
        FarmMoney = farmMoney;
    }

    private boolean getSpeed(){
        return true;
    }




    public abstract boolean go();





    protected abstract int getTravelTurns();


}
