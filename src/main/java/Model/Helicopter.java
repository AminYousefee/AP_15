package Model;

import java.util.ArrayList;

public class Helicopter extends Vehicle {


    public Helicopter(Integer currentMoney) {

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
            setFarmMoney(getFarmMoney() - getPrice());
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
}
