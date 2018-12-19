package Model;

import java.util.ArrayList;

public abstract class Vehicle implements Upgradable {
    int Level;
    int Capacity;
    int RemainingTurns;
    int Price;
    ArrayList<Item> items = new ArrayList<>(0);
    Integer FarmMoney;
    String name;





    public boolean isInTravel(){
        if (getRemainingTurns()>0){
            return true;
        }else {
            return false;
        }
    }




    public abstract void turn();
    public void clear(){

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

    public int getPrice() {
        return Price;
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

    public boolean addItem(){

    }
    private boolean getSpeed(){
        return true;
    }




    public abstract boolean go();



    public boolean goTravel(){
        RemainingTurns = getTravelTurns();
        Price = getPrice();

    }

    protected abstract int getTravelTurns();


}
